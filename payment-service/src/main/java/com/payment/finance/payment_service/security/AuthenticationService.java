package com.payment.finance.payment_service.security;

import com.payment.finance.payment_service.entity.*;
import com.payment.finance.payment_service.exception.*;
import com.payment.finance.payment_service.repository.*;
import com.payment.finance.payment_service.response.*;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

@Service
public class AuthenticationService {

    private final UsersRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AuthenticationService(UsersRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public AuthenticationResponse register(Users request)  {
        Users user = new Users();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserName(request.getEmailId());
        user.setEmailId(request.getEmailId());
        user.setMobileNo(request.getMobileNo());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.USER);
        if (userRepository.findByEmailId(request.getEmailId()).isEmpty()) {

            user = userRepository.save(user);

            String token = jwtService.generateToken(user);



            return new AuthenticationResponse(user, token);
        } else {
            throw new UserEmailIdExistsException("Given email id exists");
        }

    }

    public AuthenticationResponse authenticate(Users request) throws Exception {
        Users user = userRepository.findByEmailId(request.getEmailId()).orElseThrow(() -> new UserNotFoundException("Given email not exist"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmailId(),
                        request.getPassword()
                )

        );


        String token = jwtService.generateToken(user);


        return new AuthenticationResponse(user, token);

    }


}