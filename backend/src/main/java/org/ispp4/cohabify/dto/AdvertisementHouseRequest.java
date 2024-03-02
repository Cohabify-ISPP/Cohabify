package org.ispp4.cohabify.dto;

import org.ispp4.cohabify.house.House;
import org.springframework.data.mongodb.core.mapping.DBRef;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertisementHouseRequest {
    @NotBlank
    @Size(max = 64)
	private String title;
	
	@NotBlank
    @Size(max = 14)
	private String description;

    @NotNull
    @PositiveOrZero
    private Double price;

    @NotNull
    @Min(0)
    @Max(6)
    private Integer tenants;
}
