package br.com.brainweb.interview.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class HeroComparationDTO {

    private HeroDTO winner;
    private String draw;
    private List<HeroSkill> heroes;
    private HeroComparationResultsDTO results;


}
