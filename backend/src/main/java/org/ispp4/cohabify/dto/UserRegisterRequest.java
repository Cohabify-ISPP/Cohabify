package org.ispp4.cohabify.dto;

import org.ispp4.cohabify.user.Genre;

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
    @Size(max = 64)
	private String name;
	
	@NotBlank
    @Size(max = 14)
	private String username;
	
	@NotBlank
    @Size(max = 255)
	private String password;
	
    @NotBlank
    @Size(max = 255)
    @Email
	private String email;
	
    @NotBlank
    @Size(max = 11)
    @Pattern(regexp = "^[0-9]{9}$")
    private String phone;
    
    @NotNull
    private Genre genre;
    
}
