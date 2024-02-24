package org.ispp4.cohabify.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotNull;


public class BaseEntity {

    @NotNull
    @Id
    protected ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

}

