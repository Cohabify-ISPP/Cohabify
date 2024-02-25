package org.ispp4.cohabify.user;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
	
	private UserService userService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserDetails(@PathVariable("userId") ObjectId id) {
		User user = userService.findById(id);
		
		if(user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
			
	}
	
}
