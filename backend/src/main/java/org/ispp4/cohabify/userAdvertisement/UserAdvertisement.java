package org.ispp4.cohabify.userAdvertisement;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.model.BaseEntity;
import org.ispp4.cohabify.user.User;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "userAdvertisements")
public class UserAdvertisement extends BaseEntity {

    private String description;
    
    @NotNull
    @DecimalMin(value = "0", inclusive = false, message = "El valor de maxBudget debe ser mayor que 0")
    @DecimalMax(value = "50000", inclusive = true, message = "El valor de maxBudget debe ser menor o igual a 50000")
    private Double maxBudget;

    @NotBlank
    private String desiredLocation;

    @NotNull
    private LocalDate entranceDate;

    private LocalDate exitDate;

    @NotNull
    private Integer maxCohabitants;

    @DBRef
    private User author;

    private LocalDate promotionExpirationDate;
    
    public UserAdvertisement() {
        
    } 

    public UserAdvertisement(ObjectId id, String description, Double maxBudget, String desiredLocation, LocalDate entranceDate, LocalDate exitDate, Integer maxCohabitants, User author) {
        this.id = id; 
        this.description = description;
        this.maxBudget = maxBudget;
        this.desiredLocation = desiredLocation;
        this.entranceDate = entranceDate;
        this.exitDate = exitDate;
        this.maxCohabitants = maxCohabitants;
        this.author = author;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", description='" + getDescription() + "'" +
            ", maxBudget='" + getMaxBudget() + "'" +
            ", desiredLocation='" + getDesiredLocation() + "'" +
            ", entranceDate='" + getEntranceDate() + "'" +
            ", exitDate='" + getExitDate() + "'" +
            ", maxCohabitants='" + getMaxCohabitants() + "'" +
            ", user='" + getAuthor() + "'"+
            "}";   
    }

}