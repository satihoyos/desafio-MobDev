package cl.mobdev.challege.rickandmorty.character.infrastructure;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.mobdev.challege.rickandmorty.character.domain.CharacterOrigin;
import cl.mobdev.challege.rickandmorty.character.domain.CharacterOriginRepository;
import cl.mobdev.challege.rickandmorty.character.infrastructure.http.LocationClient;
import cl.mobdev.challege.rickandmorty.character.infrastructure.http.models.LocationModel;
import feign.FeignException;

@Service
public class CharacterOriginHttpRepository implements CharacterOriginRepository{

    private LocationClient lClientRest;

    @Autowired
    public CharacterOriginHttpRepository(LocationClient lClientRest) {
        this.lClientRest = lClientRest;
    }

    @Override
    public CharacterOrigin get(Integer id) throws NoSuchElementException {
        LocationModel lm;
        try {
            lm = lClientRest.get(id);
        } catch (FeignException fex) {
            if (fex.getMessage().contains("Location not found")) {
                throw new NoSuchElementException ("Character's Location no existe", fex);
            }else {               
                throw new InternalError("Internal Server Error", fex);
            } 
        }         
        return lm.toOriginEntity();
    }    
}
