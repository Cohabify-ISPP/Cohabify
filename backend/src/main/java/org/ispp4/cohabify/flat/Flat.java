package org.ispp4.cohabify.flat;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import org.ispp4.cohabify.model.BaseEntity;

@Getter
@Setter
@Document(collection = "flats")
public class Flat extends BaseEntity{

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @NotBlank
    @Size(min = 1, max = 1500)
    private String description;

    @NotNull
    @PositiveOrZero
    private Float price;

    @NotNull
    @Min(1)
    @Max(6)
    private int rooms_number;

    @NotNull
    @Min(1)
    @Max(4)
    private int bathrooms_number;

    @NotNull
    @Positive
    private int area;

    @NotNull
    private Heating heating;

    @NotNull
    private int floor;

    @NotNull
    @Min(0)
    @Max(6)
    private int tenants;

    @NotBlank
    @Size(min = 1, max = 100)
    private String location;

    @NotNull
    @Size(min=20, max = 20)
    private int cadastre;

    @NotNull
    @Size(min = 1, max = 5)
    private List<String> images;

    @NotNull
    private boolean elevator; 

    @NotNull
    @DocumentReference
    private List<String> tags;
}
