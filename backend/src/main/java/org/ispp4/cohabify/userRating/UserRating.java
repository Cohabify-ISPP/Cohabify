package org.ispp4.cohabify.userRating;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Document(collection = "userRatings")
public class UserRating extends BaseEntity {
    
    @DBRef
    @NotNull
    @Size(max = 20)
    private ObjectId userId;

    @DBRef
    @NotNull
    @Size(max = 20)
    private ObjectId ratedUserId;

    @NotNull
    private Boolean like;

    @Size(max = 400)
    private String comment;

    public UserRating() {
        super();
    }

    @Override
    public String toString() {
        return "UserRating{" +
            "userId='" + userId + '\'' +
            ", ratedUserId='" + ratedUserId + '\'' +
            ", like=" + like +
            ", comment='" + comment + '\'' +
            '}';
    }


}
