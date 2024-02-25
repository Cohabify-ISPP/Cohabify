package org.ispp4.cohabify.user;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.ispp4.cohabify.model.BaseEntity;

@Getter
@Setter
@Document(collection = "users")
public class User extends BaseEntity {

    @NotNull
    @Size(max = 14)
    private String username;
    
    @NotNull
    @Size(max = 255)
    private String password;

    @NotNull
    private Boolean is_owner;
    
    @NotNull
    @Size(max = 9)
    @Pattern(regexp = "^[0-9]{9}$")
    private String phone;
    
    @NotNull
    @Size(max = 255)
    @Email
    private String email;
    

    // TODO: relacion onetomany hacia la entidad tag
    // @NotNull
    // @OneToMany
    //private Tag tag;
    
    @NotNull
    @Size(max = 255)
    private String description;
    
    @NotNull
    private Plan plan;
    
    @NotNull
    private Boolean isVerified;

    public User() {
    
    }

}
