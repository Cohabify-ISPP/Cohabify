package org.ispp4.cohabify.dto;

import org.ispp4.cohabify.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtTokenDto {

    private User user;
    private String token;

}
