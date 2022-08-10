package cl.mobdev.challege.rickandmorty.character.infrastructure.http.models;

@lombok.Getter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.ToString
@lombok.Builder(toBuilder=true)
public class PlaceModel {
    private String name;
    private String url;
}
