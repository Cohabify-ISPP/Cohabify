package org.ispp4.cohabify.dto;
import org.ispp4.cohabify.tag.Tag;
import org.ispp4.cohabify.user.Gender;


import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequest {
	
	@NotBlank
    @Size(max = 100)
	private String name;
	
	@NotBlank
    @Size(max = 50)
	private String username;
	
	private String password;

    @NotNull
    private List<Tag> tag;
	
    @NotBlank
    @Size(max = 255)
    @Email
	private String email;
	
    @NotBlank
    @Pattern(regexp = "^[0-9]{9}$")
    private String phone;
    
    @NotNull
    private Gender gender;

    private String googleOAuthToken;
    
}
