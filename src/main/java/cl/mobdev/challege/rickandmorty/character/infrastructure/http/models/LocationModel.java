package cl.mobdev.challege.rickandmorty.character.infrastructure.http.models;

import java.util.Date;

import cl.mobdev.challege.rickandmorty.character.domain.CharacterOrigin;

@lombok.Getter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.ToString
@lombok.Builder(toBuilder=true)
public class LocationModel {
    private Integer id;
    private String name;
    private String type;
    private String dimension;
    private String residents[];
    private String url;
    private Date created;

    public CharacterOrigin toOriginEntity () {
        return CharacterOrigin
                .builder()
                    .name(this.name)
                    .url(this.url)
                    .dimension(this.dimension)
                    .residents(this.residents)
                    .build();
    }    
}
