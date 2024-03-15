package org.ispp4.cohabify.dto;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ispp4.cohabify.user.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRatingRequest {
    @DBRef
    private User user;

    @DBRef
    private User ratedUser;
    
    @Size(max = 400)
    private String comment;
}
