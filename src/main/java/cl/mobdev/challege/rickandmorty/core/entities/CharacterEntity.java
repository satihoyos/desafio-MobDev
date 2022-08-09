package cl.mobdev.challege.rickandmorty.core.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Getter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.ToString
@lombok.Builder(toBuilder=true)
public class CharacterEntity {
    private Integer id;
    private String name;
    private String status;
    private String species;
    private String type;
    @JsonProperty("episode_count")
    private Integer episodeCount;    
    private OriginEntity origin; 
}
