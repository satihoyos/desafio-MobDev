package cl.mobdev.challege.rickandmorty.character.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.mobdev.challege.rickandmorty.character.application.search.SearchCharacterById;

@RestController
@RequestMapping(value="/api/v1")
public class CharacterController {

    @Autowired
    private SearchCharacterById getCharacter;
    
    @GetMapping("/character/{id}")
    public ResponseEntity<?> getCharacterById (@PathVariable Integer id ) {
        return ResponseEntity.ok(this.getCharacter.getById(id));
    }    
}
