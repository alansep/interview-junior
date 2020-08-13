package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.core.features.powerstats.PowerStatsRepository;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.dto.HeroDTO;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    public HeroDTO findById(UUID uuid){
        HeroDTO hero = heroRepository.findById(uuid);
        hero.setPowerStats(powerStatsRepository.findById(hero.getPowerStats().getId()));
        return hero;
    }

    public void deleteById(UUID uuid){
        HeroDTO hero = heroRepository.findById(uuid);
        heroRepository.deleteById(uuid);
        powerStatsRepository.deleteById(hero.getPowerStats().getId());
    }

    public List<HeroDTO> findByName(String name){
        List<HeroDTO> heroes = heroRepository.findByName(name);
        heroes.forEach(hero -> hero.setPowerStats(powerStatsRepository.findById(hero.getPowerStats().getId())));
        return heroes;
    }


}
