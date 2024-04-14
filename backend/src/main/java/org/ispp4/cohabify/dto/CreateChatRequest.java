package org.ispp4.cohabify.dto;

import java.io.Serializable;
import java.util.List;

import org.ispp4.cohabify.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateChatRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<User> users;
	
}