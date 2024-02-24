package org.ispp4.cohabify.userAdvertisement;

import java.time.LocalDate;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document(collection = "userAdvertisements")
public class UserAdvertisement {
    
    @Id
    @NotNull
    private ObjectId id;

    private String description;
    @NotNull
    private Double minBudget;

    @NotNull
    private Double maxBudget;

    @NotBlank
    private String desiredLocation;

    @NotNull
    private LocalDate entranceDate;

    @NotNull
    private LocalDate exitDate;

    @NotNull
    private Integer maxCohabitants;

    @Id
    @NotNull
    private ObjectId userId;

    public UserAdvertisement(ObjectId id, String description, Double minBudget, Double maxBudget, String desiredLocation, LocalDate entranceDate, LocalDate exitDate, Integer maxCohabitants) {
        this.id = id;
        this.description = description;
        this.minBudget = minBudget;
        this.maxBudget = maxBudget;
        this.desiredLocation = desiredLocation;
        this.entranceDate = entranceDate;
        this.exitDate = exitDate;
        this.maxCohabitants = maxCohabitants;
    }

    public ObjectId getId() {
        return this.id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMinBudget() {
        return this.minBudget;
    }

    public void setMinBudget(Double minBudget) {
        this.minBudget = minBudget;
    }

    public Double getMaxBudget() {
        return this.maxBudget;
    }

    public void setMaxBudget(Double maxBudget) {
        this.maxBudget = maxBudget;
    }

    public String getDesiredLocation() {
        return this.desiredLocation;
    }

    public void setDesiredLocation(String desiredLocation) {
        this.desiredLocation = desiredLocation;
    }

    public LocalDate getEntranceDate() {
        return this.entranceDate;
    }

    public void setEntranceDate(LocalDate entranceDate) {
        this.entranceDate = entranceDate;
    }

    public LocalDate getExitDate() {
        return this.exitDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public Integer getMaxCohabitants() {
        return this.maxCohabitants;
    }

    public void setMaxCohabitants(Integer maxCohabitants) {
        this.maxCohabitants = maxCohabitants;
    }

    public ObjectId getUserId() {
        return this.userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", description='" + getDescription() + "'" +
            ", minBudget='" + getMinBudget() + "'" +
            ", maxBudget='" + getMaxBudget() + "'" +
            ", desiredLocation='" + getDesiredLocation() + "'" +
            ", entranceDate='" + getEntranceDate() + "'" +
            ", exitDate='" + getExitDate() + "'" +
            ", maxCohabitants='" + getMaxCohabitants() + "'" +
            ", userId='" + getUserId() + "'"+
            "}";
    }


}
