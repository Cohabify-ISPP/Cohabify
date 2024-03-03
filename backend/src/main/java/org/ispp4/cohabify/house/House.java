package org.ispp4.cohabify.house;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
//import org.ispp4.cohabify.tag.Tag; <-TODO: Implementar Tag
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.ispp4.cohabify.model.BaseEntity;
import org.ispp4.cohabify.tag.Tag;

@Getter
@Setter
@Document(collection = "houses")
public class House extends BaseEntity{

    @NotNull
    @Min(1)
    @Max(6)
    private int roomsNumber;

    @NotNull
    @Min(1)
    @Max(4)
    private int bathroomsNumber;
 
    @NotNull
    @Positive
    private int area;

    @NotNull
    private Heating heating;

    @NotNull
    private int floor;

    @NotBlank
    @Size(min = 1, max = 100)
    private String location;

    @GeoSpatialIndexed
    private GeoJsonPoint locationPoint;
    
    @NotNull
    @Size(min=20, max = 20)
    private String cadastre;

    @NotNull
    @DBRef
    private List<Tag> tags;
    

}
