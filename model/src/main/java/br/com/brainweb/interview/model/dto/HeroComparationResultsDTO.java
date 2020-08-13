package br.com.brainweb.interview.model.dto;

import lombok.Data;

@Data
public class HeroComparationResultsDTO {

    private HeroSkillComparatorDTO strength;
    private HeroSkillComparatorDTO dexterity;
    private HeroSkillComparatorDTO intelligence;
    private HeroSkillComparatorDTO agility;

}
