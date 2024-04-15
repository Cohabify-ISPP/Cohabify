package org.ispp4.cohabify.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.ispp4.cohabify.model.BaseEntity;
import org.ispp4.cohabify.tag.Tag;
import org.springframework.data.mongodb.core.index.Indexed;
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
import lombok.Builder.Default;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User extends BaseEntity {

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 50)
    private String username;
    
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @NotNull    
    private Boolean enabled;

    @NotBlank
    private String verificationCode;

    @NotNull
    private Boolean isOwner;

    @NotNull
    private int numAdvertisements = 0;
    
    @NotNull
    @Size(max = 9)
    @Pattern(regexp = "^[0-9]{9}$")
    @Indexed(unique = true)
    private String phone;
    
    @NotNull
    @Size(max = 255)
    @Email
    @Indexed(unique = true)
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

    private String googleOAuthToken;

    private LocalDate expirationDate;

    @DBRef
    private List<User> likes = new ArrayList<User>();
    
    public Boolean isAdmin() {
        return this.authorities.contains("Admin");
    }

    @Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

}