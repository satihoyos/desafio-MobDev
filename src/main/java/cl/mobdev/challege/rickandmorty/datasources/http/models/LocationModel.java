package cl.mobdev.challege.rickandmorty.datasources.http.models;

import java.util.Date;

import cl.mobdev.challege.rickandmorty.core.entities.OriginEntity;

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

    public OriginEntity toOriginEntity () {
        return OriginEntity
                .builder()
                    .name(this.name)
                    .url(this.url)
                    .dimension(this.dimension)
                    .residents(this.residents)
                    .build();
    }    
}
