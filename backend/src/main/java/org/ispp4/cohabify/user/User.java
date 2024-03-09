package org.ispp4.cohabify.user;

import java.util.ArrayList;
import java.util.List;

import org.ispp4.cohabify.model.BaseEntity;
import org.ispp4.cohabify.tag.Tag;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User extends BaseEntity {

    @NotNull
    @Size(max = 14)
    private String username;
    
    @NotNull
    @Size(max = 255)
    @JsonProperty(access = Access.WRITE_ONLY)
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$",
             message = "La contraseña debe contener al menos una letra mayúscula, una minúscula, un número y un carácter especial")
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
    private Gender gender;
    
    private String imageUri;
    
    @NotNull
    private Boolean isVerified;

    @NotNull
    private List<String> authorities;

    @DBRef
    private List<User> likes = new ArrayList<User>();
    

    public Boolean isAdmin() {
        return this.authorities.contains("Admin");
    }

}