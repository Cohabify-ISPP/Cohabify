package org.ispp4.cohabify.dto;

import java.util.List;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.ispp4.cohabify.house.Heating;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseRequest {

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

    @NotNull
    private Float x;
    
    @NotNull    
    private Float y;

    @NotNull
    @Size(min=20, max = 20)
    private String cadastre;

    @NotNull
    private List<String> tags;

}
