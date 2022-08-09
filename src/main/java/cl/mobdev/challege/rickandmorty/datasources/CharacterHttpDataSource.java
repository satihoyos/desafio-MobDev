package cl.mobdev.challege.rickandmorty.datasources;

import java.util.NoSuchElementException;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.mobdev.challege.rickandmorty.core.entities.CharacterEntity;
import cl.mobdev.challege.rickandmorty.core.entities.OriginEntity;
import cl.mobdev.challege.rickandmorty.core.ports.CharacterPort;
import cl.mobdev.challege.rickandmorty.datasources.http.CharacterClientRest;
import cl.mobdev.challege.rickandmorty.datasources.http.models.CharacterModel;
import feign.FeignException;

@Service
public class CharacterHttpDataSource implements CharacterPort{

    @Autowired
    private CharacterClientRest cClientRest;

    @Override
    public CharacterEntity get(Integer id, Function<Integer, OriginEntity> getOrigin) throws NoSuchElementException {
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
        OriginEntity o= getOrigin.apply(Integer.parseInt(idLocation));
        return cModel.toCharacterEntity(o);
    }
    
}
