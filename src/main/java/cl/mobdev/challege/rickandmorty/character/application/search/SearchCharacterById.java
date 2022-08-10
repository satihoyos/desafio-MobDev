package cl.mobdev.challege.rickandmorty.character.application.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.mobdev.challege.rickandmorty.character.domain.Character;
import cl.mobdev.challege.rickandmorty.character.domain.CharacterRepository;
import cl.mobdev.challege.rickandmorty.character.domain.CharacterOriginRepository;


@Service
public class SearchCharacterById {
    
    private CharacterRepository cPort;
    private CharacterOriginRepository oPort;

    @Autowired
    public SearchCharacterById (CharacterRepository cPort, CharacterOriginRepository oPort) {
        this.cPort = cPort;
        this.oPort = oPort;
    }

    public Character getById (Integer id) {
        return cPort.get(id, oPort::get);
    }
    
}
