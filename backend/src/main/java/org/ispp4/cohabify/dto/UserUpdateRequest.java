package org.ispp4.cohabify.dto;
import org.bson.types.ObjectId;
import org.ispp4.cohabify.tag.Tag;
import org.ispp4.cohabify.user.Gender;
import org.ispp4.cohabify.user.Plan;
import org.ispp4.cohabify.user.User;

import java.util.ArrayList;
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
public class UserUpdateRequest {

	@NotBlank
    @Size(max = 64)
	private String name;
	
	@NotBlank
    @Size(max = 14)
	private String username;
	
    @Size(max = 255)
	private String password;

    @NotNull
    private List<Tag> tag;

    @NotNull
    @Size(max = 255)
    private String description;
	
    @NotBlank
    @Size(max = 255)
    @Email
	private String email;
	
    @NotBlank
    @Size(max = 11)
    @Pattern(regexp = "^[0-9]{9}$")
    private String phone;
    
    @NotNull
    private Gender gender;

    @NotNull
    private Boolean changedImage;
    
}
