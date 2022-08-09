package cl.mobdev.challege.rickandmorty.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.mobdev.challege.rickandmorty.core.usecase.GetCharacterUseCase;

@RestController
@RequestMapping(value="/api/v1")
public class RickyAndMartinController {

    @Autowired
    private GetCharacterUseCase getCharacter;
    
    @GetMapping("/character/{id}")
    public ResponseEntity<?> getCharacterById (@PathVariable Integer id ) {
        return ResponseEntity.ok(this.getCharacter.getById(id));
    }    
}
