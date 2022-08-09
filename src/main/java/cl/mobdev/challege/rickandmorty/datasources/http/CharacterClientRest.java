package cl.mobdev.challege.rickandmorty.datasources.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.mobdev.challege.rickandmorty.datasources.http.models.CharacterModel;

@FeignClient(name = "character", url = "${feign.baseurl}")
public interface CharacterClientRest {

    @GetMapping("/character/{id}")
    CharacterModel get(@PathVariable(name = "id") Integer id);
}
