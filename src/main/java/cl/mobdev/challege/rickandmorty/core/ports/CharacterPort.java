package cl.mobdev.challege.rickandmorty.core.ports;

import java.util.NoSuchElementException;
import java.util.function.Function;

import cl.mobdev.challege.rickandmorty.core.entities.CharacterEntity;
import cl.mobdev.challege.rickandmorty.core.entities.OriginEntity;

public interface CharacterPort {
    CharacterEntity get(Integer id, Function<Integer,OriginEntity> getOrigin) throws NoSuchElementException;    
}
