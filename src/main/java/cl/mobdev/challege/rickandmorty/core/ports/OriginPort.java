package cl.mobdev.challege.rickandmorty.core.ports;

import cl.mobdev.challege.rickandmorty.core.entities.OriginEntity;

public interface OriginPort {
    OriginEntity get (Integer id);
}
