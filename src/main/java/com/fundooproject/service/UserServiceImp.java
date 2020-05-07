package com.fundooproject.service;

import com.fundooproject.dto.LoginDto;

import com.fundooproject.dto.UserDto;
import com.fundooproject.exception.UserException;
import com.fundooproject.model.User;
import com.fundooproject.repository.IUserRepository;
import com.fundooproject.utility.JwtTokenUtil;
import com.fundooproject.utility.RedisTempl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * This class contains Implementation of user service(Business logic) methods.
 *
 * @author Geeth
 * @version 1.0.0
 * @since July-2020
 */
@Service
public class UserServiceImp implements IUserService {

	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RedisTempl<Object> redis;

	private String redisKey = "Key";

	/**
	 * Method for register user.
	 *
	 * @param userDto
	 * @return User
	 */
	@Override
	public User registerUser(UserDto userDto) {
		if (validateUser(userDto.email)) {
			throw new UserException(UserException.exceptionType.USER_ALREADY_EXIST);
		}
		User user = new User(userDto);
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		user.setRegDate(localDate.format(dateFormat));
		user.setPassword(passwordEncoder.encode(userDto.password));
		return userRepository.save(user);
	}

	/**
	 * Method for login user.
	 *
	 * @param loginDto
	 * @return UserID
	 */
	@Override
	public Long loginUser(LoginDto loginDto) {
		User user = userRepository.findByEmail(loginDto.email)
				.orElseThrow(() -> new UserException(UserException.exceptionType.INVALID_EMAIL_ID));
		if (!passwordEncoder.matches(loginDto.password, user.getPassword()))
			throw new UserException(UserException.exceptionType.INVALID_PASSWORD);

		String token = jwtTokenUtil.createToken(user.getId());
		redis.putMap(redisKey, user.getEmail(), token);
		return user.getId();
	}

	/**
	 * Method for Validate user email in DB.
	 *
	 * @param email
	 * @return false if user exist, or true.
	 */
	@Override
	public Boolean validateUser(String email) {
		Optional<User> byEmail = userRepository.findByEmail(email);
		if (byEmail.isPresent())
			return true;
		return false;
	}

	/**
	 * Method for get all user.
	 *
	 * @return list of users.
	 */
	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

}
