package cl.mobdev.challege.rickandmorty.character.domain;

import java.util.NoSuchElementException;
import java.util.function.Function;

public interface CharacterRepository {
    Character get(Integer id, Function<Integer,CharacterOrigin> getOrigin) throws NoSuchElementException;    
}
