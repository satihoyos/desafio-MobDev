package cl.mobdev.challege.rickandmorty.core.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.mobdev.challege.rickandmorty.core.entities.CharacterEntity;
import cl.mobdev.challege.rickandmorty.core.ports.CharacterPort;
import cl.mobdev.challege.rickandmorty.core.ports.OriginPort;


@Service
public class GetCharacterUseCase {
    
    private CharacterPort cPort;
    private OriginPort oPort;

    @Autowired
    public GetCharacterUseCase (CharacterPort cPort, OriginPort oPort) {
        this.cPort = cPort;
        this.oPort = oPort;
    }

    public CharacterEntity getById (Integer id) {
        return cPort.get(id, oPort::get);
    }
    
}
