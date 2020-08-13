package br.com.brainweb.interview.model.interfaces;

import br.com.brainweb.interview.model.dto.HeroDTO;
import br.com.brainweb.interview.model.dto.HeroSkill;

import java.util.List;

public interface HeroStatsExtractor {

    public List<HeroSkill> extractStats(List<HeroDTO> heroes);

}
