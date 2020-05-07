package com.fundooproject.service;

import com.fundooproject.dto.LoginDto;
import com.fundooproject.model.User;

/**
 * This interface contains methods, which provides us email services.These
 * methods are implemented in class {@link EmailServiceImp}.
 * 
 * @since May-2020
 * @version 1.0.0
 * @author Geeth
 *
 */
public interface IEmailService {

	Long validateEmail(String email);

	User setPassword(LoginDto loginDto);

	void validateToken(String verificationtoken);
	
}