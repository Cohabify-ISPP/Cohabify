package org.ispp4.cohabify.dto;

import java.util.List;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.house.House;
import org.ispp4.cohabify.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertisementHouseRequest {

    @Id
    private ObjectId id;
    @Id
    private ObjectId houseId;

    @NotBlank
    
	private String title;
	
	@NotBlank
   
	private String description;

    @NotNull
    @PositiveOrZero
    private Double price;
    
    @NotNull
    @DBRef
    private List<User> tenants;

       
    private House house;

    @DBRef
    private User author;

    private int views;

    private List<String> imagesB;

}
