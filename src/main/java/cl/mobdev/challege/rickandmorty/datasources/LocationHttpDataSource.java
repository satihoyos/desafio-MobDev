package cl.mobdev.challege.rickandmorty.datasources;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.mobdev.challege.rickandmorty.core.entities.OriginEntity;
import cl.mobdev.challege.rickandmorty.core.ports.OriginPort;
import cl.mobdev.challege.rickandmorty.datasources.http.LocationClientRest;
import cl.mobdev.challege.rickandmorty.datasources.http.models.LocationModel;
import feign.FeignException;

@Service
public class LocationHttpDataSource implements OriginPort{

    @Autowired
    private LocationClientRest lClientRest;

    @Override
    public OriginEntity get(Integer id) throws NoSuchElementException {
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
