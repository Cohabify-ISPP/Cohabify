package org.ispp4.cohabify.model;

import java.beans.Transient;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonGetter;

import jakarta.validation.constraints.NotNull;
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

