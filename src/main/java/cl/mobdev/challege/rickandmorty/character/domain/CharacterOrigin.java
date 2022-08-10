package cl.mobdev.challege.rickandmorty.character.domain;

@lombok.Getter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.ToString
@lombok.Builder(toBuilder=true)
public class CharacterOrigin {
    private String name;
    private String url;
    private String dimension;
    private String residents[];    
}

