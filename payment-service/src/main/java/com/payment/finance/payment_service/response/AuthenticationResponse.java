package com.payment.finance.payment_service.response;

import com.payment.finance.payment_service.entity.*;

public class AuthenticationResponse {

	private UsersResponse user;
	private String token;

	public AuthenticationResponse(Users user, String token) {
		super();
        this.user = this.userToUserResponse(user);
		System.out.println(this.user);
		this.token = token;
	}

	public UsersResponse userToUserResponse(Users user){
		UsersResponse usersResponse = new UsersResponse();
		usersResponse.setUserName(user.getUsername());
		usersResponse.setFirstName(user.getFirstName());
		usersResponse.setLastName(user.getLastName());
		usersResponse.setEmailId(user.getEmailId());
		usersResponse.setMobileNo(user.getMobileNo());

		return usersResponse;
	}

	public String getToken() {
		return token;
	}
	public UsersResponse getUser() {
		return user;
	}






}
