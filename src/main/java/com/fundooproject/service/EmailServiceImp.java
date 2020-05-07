package com.fundooproject.service;

import com.fundooproject.dto.LoginDto;

import com.fundooproject.dto.RabbitMqDto;
import com.fundooproject.exception.UserException;
import com.fundooproject.model.User;
import com.fundooproject.repository.IUserRepository;
import com.fundooproject.utility.JwtTokenUtil;
import com.fundooproject.utility.RabbitMqImp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This class exposes the email methods of Interface {@link IEmailService}.
 * @author Geeth
 * @since May-2020
 */

@Service
public class EmailServiceImp implements IEmailService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private RabbitMqDto rabbitMqDto;

	@Autowired
	private RabbitMqImp rabbitMq;

	/**
	 * This method is for validating the email is exist or not.
	 *
	 * @param email
	 * @return response(if present sent mail to user, or else throwing exception -
	 *         user does not exist)
	 */
	@Override
	public Long validateEmail(String email) {
		return userRepository.findByEmail(email).map(user -> {
			String token = jwtTokenUtil.createToken(user.getId());
			String body = token;
			rabbitMqDto.setTo(email);
			rabbitMqDto.setFrom("geeth53xy@gmail.com");
			rabbitMqDto.setSubject("Reset password");
			rabbitMqDto.setBody(body);
			rabbitMq.sendMessageToQueue(rabbitMqDto);
			rabbitMq.send(rabbitMqDto);
			return user.getId();
		}).orElseThrow(() -> new UserException(UserException.exceptionType.INVALID_EMAIL_ID));
	}

	/*
	 * This Method is to validate the token from email
	 * 
	 * @param verification Token
	 * 
	 */
	@Override
	public void validateToken(String verificationtoken) {
		long id = jwtTokenUtil.decodeToken(verificationtoken);
		System.out.println(id + "this is id");
		Optional<User> userDetails = userRepository.findById(id);
		if (userDetails != null) {
			userDetails.map(user -> {
				user.setVerificationStatus("verfied");
				return user;
			}).map(userRepository::save).get();
		} else
			throw new UserException(UserException.exceptionType.USER_NOT_EXIT);

	}

	/**
	 * This method is used for setting the new password for user.
	 *
	 * @param loginDto
	 * @param token
	 */
	@Override
	public User setPassword(LoginDto loginDto) {
		// long id = jwtTokenUtil.decodeToken(token);
		return userRepository.findByEmail(loginDto.email).map(user -> {
			String password = passwordEncoder.encode(loginDto.password);
			user.setPassword(password);
			return user;
		}).map(userRepository::save).get();
	}
}
