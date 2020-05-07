package com.fundooproject.controller;

import com.fundooproject.configuration.ApplicationConfig;
import com.fundooproject.dto.LoginDto;
import com.fundooproject.dto.UserDto;
import com.fundooproject.dto.VerificationToken;
import com.fundooproject.model.User;
import com.fundooproject.response.UserResponseDTO;
import com.fundooproject.service.IEmailService;
import com.fundooproject.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * This class is use to expose user rest APIs
 * {@link RestController @RestController}.
 *
 * @author Geeth
 * @version 1.0.0
 * @since May-2020
 */

@RequestMapping(value = "/fundooapi")
@RestController
public class UserController {

	@Autowired
	private IEmailService service;

	@Autowired
	private IUserService userService;

	/**
	 * API for register new users.
	 *
	 * @param userDto
	 * @return response (user registered successfully or not)
	 */
	@PostMapping("/register")
	@ApiOperation("User Register API")
	public ResponseEntity<UserResponseDTO> registration(@RequestBody @Valid UserDto userDto) {
		User user = userService.registerUser(userDto);
		service.validateEmail(userDto.email);
		return new ResponseEntity<>(new UserResponseDTO(user, ApplicationConfig.getMessageAccessor().getMessage("101")),
				HttpStatus.CREATED);
	}

	/*
	 * @param verificationToken
	 */
	@PostMapping("/VerifyToken")
	public ResponseEntity<UserResponseDTO> tokenVerification(@RequestBody @Valid VerificationToken verificationToken) {
		System.out.println("hello");
		service.validateToken(verificationToken.getEmailToken());
		System.out.println("geeth");
		return new ResponseEntity<>(
				new UserResponseDTO(verificationToken, ApplicationConfig.getMessageAccessor().getMessage("106")),
				HttpStatus.OK);
	}

	/**
	 * API for login users.
	 *
	 * @param loginDto
	 * @return response (user login successfully or not)
	 */
	@PostMapping("/login")
	@ApiOperation("User Login API")
	public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody LoginDto loginDto) {
		long userId = userService.loginUser(loginDto);
		return new ResponseEntity<>(
				new UserResponseDTO(userId, ApplicationConfig.getMessageAccessor().getMessage("102")), HttpStatus.OK);
	}

	/**
	 * API for forgot password.
	 *
	 * @param email
	 * @return sending mail to your register emailId.
	 */
	@PutMapping("/forgotPassword")
	@ApiOperation("Forgot Password API")
	public ResponseEntity<UserResponseDTO> forgot(@RequestParam String email) {
		Long userId = service.validateEmail(email);
		return new ResponseEntity<>(
				new UserResponseDTO(userId, ApplicationConfig.getMessageAccessor().getMessage("103")),
				HttpStatus.ACCEPTED);
	}

	/**
	 * API for reset password.
	 *
	 * @param loginDto
	 * @param token
	 * @return response (password updated or not)
	 */
	@PutMapping("/reset")
	@ApiOperation("Reset Password API")
	public ResponseEntity<UserResponseDTO> resetPassword(@Valid @RequestBody LoginDto loginDto) {
		User user = service.setPassword(loginDto);
		return new ResponseEntity<>(new UserResponseDTO(user, ApplicationConfig.getMessageAccessor().getMessage("104")),
				HttpStatus.ACCEPTED);
	}

	/**
	 * API for get all users..
	 * 
	 * @return list of notes.
	 */
	@GetMapping
	@ApiOperation(value = "Get All Users.")
	public ResponseEntity<UserResponseDTO> getAll() {
		List<User> allUser = userService.getAll();
		return new ResponseEntity<>(
				new UserResponseDTO(allUser, ApplicationConfig.getMessageAccessor().getMessage("105")), HttpStatus.OK);
	}
}