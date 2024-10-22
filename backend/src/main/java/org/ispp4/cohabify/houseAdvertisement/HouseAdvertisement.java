package org.ispp4.cohabify.houseAdvertisement;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import org.ispp4.cohabify.house.House;
import org.ispp4.cohabify.model.BaseEntity;
import org.ispp4.cohabify.user.User;

@Getter
@Setter
@Document(collection = "houseAdvertisements")
public class HouseAdvertisement extends BaseEntity {

    @NotBlank
    @Size(min = 1, max = 100)
    private String title;

    @NotBlank
    @Size(min = 1, max = 1500)
    private String description;

    @NotNull
    @PositiveOrZero
    private Double price;

    @DBRef
    private List<User> tenants;

    private List<String> images;
    
    @DBRef
    private House house;

    @DBRef
    private User author;

    private LocalDate promotionExpirationDate;

    private int views;

    public void incrementViews() {
        views++;
    }


}
