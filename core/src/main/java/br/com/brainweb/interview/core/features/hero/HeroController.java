package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.dto.HeroDTO;
import br.com.brainweb.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    private final HeroService heroService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Validated
                                       @RequestBody CreateHeroRequest createHeroRequest) {
        final UUID id = heroService.create(createHeroRequest);
        return created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    @GetMapping
    public ResponseEntity<HeroDTO> find(@RequestParam(value = "id", defaultValue = "", required = true) UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(heroService.findById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> findByName(@RequestParam(value = "name", required = false, defaultValue = "") String name) {
        List<HeroDTO> heroes = heroService.findByName(name);
        if (heroes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(heroes);
        }
    }

    @PostMapping("/compare")
    public ResponseEntity<?> compareHeroes(@RequestBody List<UUID> ids) {
        return ResponseEntity.ok().body(heroService.compareHeroes(ids));
    }

    @PutMapping
    public ResponseEntity<HeroDTO> update(@RequestParam(value = "id", defaultValue = "", required = true) UUID id, @RequestBody @Valid HeroDTO updatedHero) {
        return ResponseEntity.status(HttpStatus.OK).body(heroService.updateHeroById(id, updatedHero));
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam(value = "id", defaultValue = "", required = true) UUID id) {
        heroService.deleteById(id);
    }
}
