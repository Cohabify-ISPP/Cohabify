package org.ispp4.cohabify.user;

import java.util.List;

import org.ispp4.cohabify.model.BaseEntity;
import org.ispp4.cohabify.tag.Tag;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "users")
public class User extends BaseEntity {

    @NotNull
    @Size(max = 14)
    private String username;
    
    @NotNull
    @Size(max = 255)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @NotNull
    private Boolean isOwner;
    
    @NotNull
    @Size(max = 9)
    @Pattern(regexp = "^[0-9]{9}$")
    private String phone;
    
    @NotNull
    @Size(max = 255)
    @Email
    private String email;
    
    @DBRef
    private List<Tag> tag;
    
    @NotNull
    @Size(max = 255)
    private String description;
    
    @NotNull
    private Plan plan;
    
    @NotNull
    private Genre genre;
    
    private String imageUri;
    
    @NotNull
    private Boolean isVerified;

    @NotNull
    private List<String> authorities;
    
    @NotNull
    private String image;

    @NotNull
    private Gender gender;

    public User() {
    
    }

    public Boolean isAdmin() {
        return this.authorities.contains("Admin");
    }

}