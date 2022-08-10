package cl.mobdev.challege.rickandmorty.character.infrastructure.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.mobdev.challege.rickandmorty.character.infrastructure.http.models.CharacterModel;

@FeignClient(name = "character", url = "${feign.baseurl}")
public interface CharacterClient {

    @GetMapping("/character/{id}")
    CharacterModel get(@PathVariable(name = "id") Integer id);
}
