package user;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
//import javax.persistence.OneToMany;
import tag.Tag;

@Document(collection = "users")
public class User {

  @Id
  @NotEmpty
  public String username;
  
  @NotBlank
  String password;
  
  @NotNull
  Boolean is_owner;
  
  @NotNull
  Integer phone_number;
  
  @NotBlank
  String email;

  String description;

  String plan;

  @NotNull
  Boolean is_verified;
  
  //@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
  //private Tag tag;

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean isIs_owner() {
    return this.is_owner;
  }

  public Boolean getIs_owner() {
    return this.is_owner;
  }

  public void setIs_owner(Boolean is_owner) {
    this.is_owner = is_owner;
  }

  public Integer getPhone_number() {
    return this.phone_number;
  }

  public void setPhone_number(Integer phone_number) {
    this.phone_number = phone_number;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPlan() {
    return this.plan;
  }

  public void setPlan(String plan) {
    this.plan = plan;
  }

  public boolean isIs_verified() {
    return this.is_verified;
  }

  public boolean getIs_verified() {
    return this.is_verified;
  }

  public void setIs_verified(boolean is_verified) {
    this.is_verified = is_verified;
  }
  
}
