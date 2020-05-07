package com.fundooproject.service;

import com.fundooproject.dto.LoginDto;
import com.fundooproject.dto.UserDto;
import com.fundooproject.model.User;
import java.util.List;

public interface IUserService {

	User registerUser(UserDto userDto);

	Long loginUser(LoginDto loginDto);

	Boolean validateUser(String email);

	List<User> getAll();
}