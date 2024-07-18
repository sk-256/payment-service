package com.payment.finance.payment_service.security;


import com.payment.finance.payment_service.repository.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Service
public class UsersDetailImplementation implements  UserDetailsService	 {


	private final UsersRepository usersRepository;

	public UsersDetailImplementation(UsersRepository repository) {
		this.usersRepository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return usersRepository.findByUserName(username)
				               .orElseThrow(()-> new UsernameNotFoundException("User not found"));
	}

}
