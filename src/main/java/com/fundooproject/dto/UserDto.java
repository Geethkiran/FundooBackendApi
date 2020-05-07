package com.fundooproject.dto;

import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@ToString
public class UserDto {

	@NotEmpty(message = "Please Enter First Name")
	public String firstName;

	@NotEmpty(message = "Please Enter Last Name")
	public String lastName;
	@Pattern(regexp = ("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
	@Email(message = "Please Enter Valid Email")
	@NotEmpty(message = "Please Enter Email")
	public String email;

	@NotEmpty(message = "Please Enter Password")
	public String password;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}