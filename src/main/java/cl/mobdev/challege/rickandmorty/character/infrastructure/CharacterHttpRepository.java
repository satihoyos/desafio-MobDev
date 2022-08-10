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

    private CharacterClient cClientRest;

    @Autowired
    public CharacterHttpRepository(CharacterClient cClientRest) {
        this.cClientRest = cClientRest;
    }

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
        
        Character rs;
        if (cModel.getOrigin() == null || 
                    cModel.getOrigin().getUrl() == null ||
                        cModel.getOrigin().getUrl().isEmpty()) 
        {
            rs = cModel.toCharacterEntity();
        }else {

            String url = cModel.getOrigin().getUrl();
            if (!url.contains("location/") || !url.split("location/")[1].matches("[0-9]+")){
                throw new IllegalArgumentException("Character's location id no es numérico");
            }

            String idLocation = url.split("location/")[1];
            CharacterOrigin o= getOrigin.apply(Integer.parseInt(idLocation));
            rs = cModel.toCharacterEntity(o);
        }
        
        return rs;
    }
    
}
