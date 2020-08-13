package br.com.brainweb.interview.model;

import br.com.brainweb.interview.model.request.CreateHeroRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
public class PowerStats {

    @NotNull
    private UUID id;

    @NotNull
    @Min(value = 0, message = "message.powerstats.strength.min")
    @Max(value = 10, message = "message.powerstats.strength.max")
    private int strength;

    @NotNull
    @Min(value = 0, message = "message.powerstats.strength.min")
    @Max(value = 10, message = "message.powerstats.strength.max")
    private int agility;

    @NotNull
    @Min(value = 0, message = "message.powerstats.strength.min")
    @Max(value = 10, message = "message.powerstats.strength.max")
    private int dexterity;

    @NotNull
    @Min(value = 0, message = "message.powerstats.strength.min")
    @Max(value = 10, message = "message.powerstats.strength.max")
    private int intelligence;

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

    public PowerStats(CreateHeroRequest createHeroRequest) {
        this.strength = createHeroRequest.getStrength();
        this.agility = createHeroRequest.getAgility();
        this.dexterity = createHeroRequest.getDexterity();
        this.intelligence = createHeroRequest.getIntelligence();
    }

    public Integer getTotalScore(){
        return this.strength + this.agility + this.dexterity + this.intelligence;
    }
}
