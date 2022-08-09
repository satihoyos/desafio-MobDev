package cl.mobdev.challege.rickandmorty.datasources.http.models;

import java.util.Date;

import cl.mobdev.challege.rickandmorty.core.entities.CharacterEntity;
import cl.mobdev.challege.rickandmorty.core.entities.OriginEntity;

@lombok.Getter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.ToString
@lombok.Builder(toBuilder=true)
public class CharacterModel {
    private Integer id;
    private String name;
    private String status;
    private String species; 
    private String type;
    private String gender;
    private PlaceModel origin;
    private PlaceModel location;
    private String  image;
    private String episode[];
    private String   url;
    private Date    created;


    public CharacterEntity toCharacterEntity (OriginEntity o) {
       return  CharacterEntity
                .builder()
                    .id(this.id)
                    .name(this.name)
                    .type(this.type)
                    .status(this.status)
                    .species(this.species) 
                    .episodeCount(episode.length)
                    .origin (o)
                    .build();
    }
}
