package cl.mobdev.challege.rickandmorty.character.infrastructure.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.mobdev.challege.rickandmorty.character.infrastructure.http.models.LocationModel;

@FeignClient(name = "location", url = "${feign.baseurl}")
public interface LocationClient {
    
    @GetMapping("/location/{id}")
    LocationModel get(@PathVariable(name = "id") Integer id);
    
}
