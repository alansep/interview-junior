package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.core.exception.InterviewGenericException;
import br.com.brainweb.interview.core.features.powerstats.PowerStatsRepository;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.dto.*;
import br.com.brainweb.interview.model.enums.Skill;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final HeroRepository heroRepository;

    @Autowired
    private PowerStatsRepository powerStatsRepository;

    @Transactional
    public UUID create(CreateHeroRequest createHeroRequest) {
        UUID powerStatsId = powerStatsRepository.create(new PowerStats(createHeroRequest));
        return heroRepository.create(new Hero(createHeroRequest, powerStatsId));
    }

    public HeroDTO findById(UUID uuid) {
        HeroDTO hero = heroRepository.findById(uuid);
        hero.setPowerStats(powerStatsRepository.findById(hero.getPowerStats().getId()));
        return hero;
    }

    public void deleteById(UUID uuid) {
        HeroDTO hero = heroRepository.findById(uuid);
        heroRepository.deleteById(uuid);
        powerStatsRepository.deleteById(hero.getPowerStats().getId());
    }

    public List<HeroDTO> findByName(String name) {
        List<HeroDTO> heroes = heroRepository.findByName(name);
        heroes.forEach(hero -> hero.setPowerStats(powerStatsRepository.findById(hero.getPowerStats().getId())));
        return heroes;
    }


    public HeroComparationDTO compareHeroes(List<UUID> ids) {
        if(ids.size() == 1 || ids.size() == 0){
            throw new InterviewGenericException(HttpStatus.BAD_REQUEST,"You must send at least 2 heroes for this comparation!");
        }
        HeroComparationDTO comparation = new HeroComparationDTO();
        List<HeroDTO> heroes = new ArrayList<>();
        ids.forEach(id -> heroes.add(findById(id)));
        comparation.setHeroes(new ArrayList<>());
        heroes.forEach(hero -> comparation.getHeroes().add(new HeroSkill(hero.getName(), hero.getPowerStats().getTotalScore())));

        HeroComparationResultsDTO results = new HeroComparationResultsDTO();

        //AGILITY
        HeroSkillComparatorDTO agility = new HeroSkillComparatorDTO();
        agility.setStats(Skill.AGILITY.getExtractor().extractStats(heroes));

        //DEXTERITY
        HeroSkillComparatorDTO dexterity = new HeroSkillComparatorDTO();
        dexterity.setStats(Skill.DEXTERITY.getExtractor().extractStats(heroes));

        //INTELLIGENCE
        HeroSkillComparatorDTO intelligence = new HeroSkillComparatorDTO();
        intelligence.setStats(Skill.INTELLIGENCE.getExtractor().extractStats(heroes));

        //STRENGTH
        HeroSkillComparatorDTO strength = new HeroSkillComparatorDTO();
        strength.setStats(Skill.STRENGTH.getExtractor().extractStats(heroes));

        Comparator<HeroSkill> comparator = new Comparator<HeroSkill>() {
            @Override
            public int compare(HeroSkill o1, HeroSkill o2) {
                return o1.getScore().compareTo(o2.getScore()) * -1;
            }
        };

        Collections.sort(agility.getStats(), comparator);
        Collections.sort(dexterity.getStats(), comparator);
        Collections.sort(intelligence.getStats(), comparator);
        Collections.sort(strength.getStats(), comparator);

        results.setAgility(agility);
        results.setDexterity(dexterity);
        results.setIntelligence(intelligence);
        results.setStrength(strength);

        Collections.sort(comparation.getHeroes(), comparator);


        comparation.setResults(results);

        if(!comparation.getHeroes().get(0).getScore().equals(comparation.getHeroes().get(1).getScore())){
            comparation.setWinner(heroes.stream().filter(hero -> hero.getName().equals(comparation.getHeroes().get(0).getName())).collect(Collectors.toList()).get(0));
        } else{
            comparation.setDraw("Thats's a draw! T.T");
        }

        return comparation;
    }

    public HeroDTO updateHeroById(UUID id, HeroDTO newHero) {
        HeroDTO savedHero = findById(id);
        savedHero = mapHero(savedHero, newHero);
        heroRepository.updateHero(savedHero);
        powerStatsRepository.updateStats(savedHero.getPowerStats());
        return savedHero;
    }

    private HeroDTO mapHero(HeroDTO savedHero, HeroDTO newHero) {
        savedHero.setName(newHero.getName());
        savedHero.setRace(newHero.getRace());
        savedHero.setEnabled(newHero.isEnabled());
        savedHero.setPowerStats(mapPowerStats(savedHero.getPowerStats(), newHero.getPowerStats()));
        return savedHero;
    }

    private PowerStats mapPowerStats(PowerStats savedPowerStats, PowerStats newPowerStats) {
        savedPowerStats.setStrength(newPowerStats.getStrength());
        savedPowerStats.setAgility(newPowerStats.getAgility());
        savedPowerStats.setDexterity(newPowerStats.getDexterity());
        savedPowerStats.setIntelligence(newPowerStats.getIntelligence());
        return savedPowerStats;
    }
}
