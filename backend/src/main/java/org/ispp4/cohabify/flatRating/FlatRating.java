package org.ispp4.cohabify.flatRating;
import org.ispp4.cohabify.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "flatRatings")
public class FlatRating extends BaseEntity{
    
    @NotNull
    @Size(max = 255)
    private String flatId;
   
    @NotNull
    @Size(max = 255)
    private String userId;

    @NotNull
    @PositiveOrZero
    @Max(5)
    private Integer rating;

    @NotNull
    @Size(max = 100)
    private String comment;

    public FlatRating() {
        super();
    }

    @Override
    public String toString() {
        return "FlatRating{" +
            "flatId='" + flatId + '\'' +
            ", userId='" + userId + '\'' +
            ", rating=" + rating +
            ", comment='" + comment + '\'' +
            '}';
    }
}
