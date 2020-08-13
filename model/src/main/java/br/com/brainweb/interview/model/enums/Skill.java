package br.com.brainweb.interview.model.enums;

import br.com.brainweb.interview.model.interfaces.HeroStatsExtractor;
import br.com.brainweb.interview.model.interfaces.impl.AgilityExtractor;
import br.com.brainweb.interview.model.interfaces.impl.DexterityExtractor;
import br.com.brainweb.interview.model.interfaces.impl.IntelligenceExtractor;
import br.com.brainweb.interview.model.interfaces.impl.StrengthExtractor;
import lombok.Getter;

@Getter
public enum Skill {

    STRENGTH(new StrengthExtractor()), AGILITY(new AgilityExtractor()), INTELLIGENCE(new IntelligenceExtractor()), DEXTERITY(new DexterityExtractor());

    private final HeroStatsExtractor extractor;

    private Skill(HeroStatsExtractor extractor){
        this.extractor = extractor;
    }

}
