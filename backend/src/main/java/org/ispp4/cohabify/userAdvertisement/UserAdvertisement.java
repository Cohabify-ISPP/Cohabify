package org.ispp4.cohabify.userAdvertisement;

import java.time.LocalDate;
import java.util.List;

import org.ispp4.cohabify.model.BaseEntity;
import org.ispp4.cohabify.tag.Tag;
import org.ispp4.cohabify.user.User;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "userAdvertisements")
public class UserAdvertisement extends BaseEntity {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Double maxBudget;

    @NotBlank
    private String desiredLocation;

    @NotNull
    private LocalDate entranceDate;

    private LocalDate exitDate;

    @NotNull
    private Integer maxCohabitants;

    @NotNull
    @PositiveOrZero
    private List<User> likes;

    @DBRef
    @NotNull
    private User author;

    public UserAdvertisement(String title, String description, Double minBudget, Double maxBudget, String desiredLocation, LocalDate entranceDate, LocalDate exitDate, Integer maxCohabitants, List<User> likes, List<Tag> tags, User user) {
        this.title = title;
        this.description = description;
        this.maxBudget = maxBudget;
        this.desiredLocation = desiredLocation;
        this.entranceDate = entranceDate;
        this.exitDate = exitDate;
        this.maxCohabitants = maxCohabitants;
        this.likes = likes;
        this.tags = tags;
        this.author = user;
        
    }

    @Override
    public String toString() {
        return "UserAdvertisement [description=" + description + ", desiredLocation=" + desiredLocation + ", entranceDate="
                + entranceDate + ", exitDate=" + exitDate + ", likes=" + likes + ", maxBudget=" + maxBudget
                + ", maxCohabitants=" + maxCohabitants + ", tags=" + tags + ", title=" + title + ", user=" + user + "]";
    }

}