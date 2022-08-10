package cl.mobdev.challege.rickandmorty.character.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Getter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.ToString
@lombok.Builder(toBuilder=true)
public class Character {
    private Integer id;
    private String name;
    private String status;
    private String species;
    private String type;
    @JsonProperty("episode_count")
    private Integer episodeCount;    
    private CharacterOrigin origin; 
}
