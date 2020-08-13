package br.com.brainweb.interview.model.dto;

import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.enums.Race;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HeroDTO {

    @NotNull
    private UUID id;

    @NotEmpty
    private String name;

    @NotNull
    private Race race;

    @NotNull
    private boolean enabled;

    @JsonProperty("powerStats")
    @NotNull
    private PowerStats powerStats;

    @JsonIgnore
    private Date createdAtDate;
    @JsonIgnore
    private Date updatedAtDate;

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(createdAtDate);
    }

    @JsonProperty("updatedAt")
    public String getUpdatedAt() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(updatedAtDate);
    }


}
