package br.com.brainweb.interview.model.interfaces.impl;

import br.com.brainweb.interview.model.dto.HeroDTO;
import br.com.brainweb.interview.model.dto.HeroSkill;
import br.com.brainweb.interview.model.interfaces.HeroStatsExtractor;

import java.util.ArrayList;
import java.util.List;

public class StrengthExtractor implements HeroStatsExtractor {

    @Override
    public List<HeroSkill> extractStats(List<HeroDTO> heroes) {
        List<HeroSkill> stats = new ArrayList<>();
        heroes.forEach(hero -> stats.add(new HeroSkill(hero.getName(), hero.getPowerStats().getStrength())));
        return stats;
    }
}
