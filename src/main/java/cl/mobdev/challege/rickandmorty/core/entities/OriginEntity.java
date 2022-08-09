package cl.mobdev.challege.rickandmorty.core.entities;

@lombok.Getter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.ToString
@lombok.Builder(toBuilder=true)
public class OriginEntity {
    private String name;
    private String url;
    private String dimension;
    private String residents[];    
}

