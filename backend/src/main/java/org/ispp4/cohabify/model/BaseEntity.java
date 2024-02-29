package org.ispp4.cohabify.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity {

    @NotNull
    @Id
    protected ObjectId id;

    @Transient
    @JsonGetter("id")
    public String getJsonId() {
        return this.id.toString();
    }
}

