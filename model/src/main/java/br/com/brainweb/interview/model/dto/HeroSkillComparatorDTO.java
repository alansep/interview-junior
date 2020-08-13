package br.com.brainweb.interview.model.dto;

import lombok.Data;

import java.util.List;


@Data
public class HeroSkillComparatorDTO {

    private String winner;
    private List<HeroSkill> stats;

}
