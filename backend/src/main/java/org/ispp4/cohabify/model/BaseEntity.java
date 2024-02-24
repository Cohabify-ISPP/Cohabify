package org.ispp4.cohabify.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseEntity {

    @Id
    protected ObjectId id;

}
