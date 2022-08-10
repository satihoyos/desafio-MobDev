package cl.mobdev.challege.rickandmorty.character.infrastructure;

import java.util.NoSuchElementException;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.mobdev.challege.rickandmorty.character.domain.Character;
import cl.mobdev.challege.rickandmorty.character.domain.CharacterOrigin;
import cl.mobdev.challege.rickandmorty.character.domain.CharacterRepository;
import cl.mobdev.challege.rickandmorty.character.infrastructure.http.CharacterClient;
import cl.mobdev.challege.rickandmorty.character.infrastructure.http.models.CharacterModel;
import feign.FeignException;

@Service
public class CharacterHttpRepository implements CharacterRepository{

    @Autowired
    private CharacterClient cClientRest;

    @Override
    public Character get(Integer id, Function<Integer, CharacterOrigin> getOrigin) throws NoSuchElementException {
        CharacterModel cModel;

        try {
            cModel = this.cClientRest.get(id);
        } catch (FeignException fex) {
            if (fex.getMessage().contains("Character not found")) {
                throw new NoSuchElementException ("Character no existe", fex);
            }else {                
                throw new InternalError("Internal Server Error", fex);
            }       
        }
        
        String idLocation = cModel.getOrigin().getUrl().split("location/")[1];
        CharacterOrigin o= getOrigin.apply(Integer.parseInt(idLocation));
        return cModel.toCharacterEntity(o);
    }
    
}
