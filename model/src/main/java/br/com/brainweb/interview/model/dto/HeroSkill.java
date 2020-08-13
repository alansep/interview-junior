package br.com.brainweb.interview.model.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.Comparator;

@Data
@AllArgsConstructor
public class HeroSkill {

    private String name;
    private Integer score;

}
