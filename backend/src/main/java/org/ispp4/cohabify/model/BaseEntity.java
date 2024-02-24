package org.ispp4.cohabify.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


public class BaseEntity {

    @Id
    protected ObjectId id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

}