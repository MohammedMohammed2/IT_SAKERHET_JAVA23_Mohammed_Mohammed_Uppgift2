package com.example.securitySpring.service;

import com.example.securitySpring.model.AuthenticationResponse;
import com.example.securitySpring.model.User;
import com.example.securitySpring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private String emailSaved;

    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    //craetes a new user in the database with the information given by the client
    public String register(User req){
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(req.getRole());
        user = userRepository.save(user);
        return "created";
    }

    //extracts the user upon login and generates a token
    public AuthenticationResponse login(User request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getUsername());
        emailSaved = request.getUsername();
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }


    // sends the message encrypted to the data base
    public String sendMessage(User req) throws Exception {
       User findUser = userRepository.findByEmail(emailSaved);

       String messageEnkrypted = AesKeyMessage.AESCrypt(req.getMessage());
        findUser.setMessage(messageEnkrypted);

        userRepository.save(findUser);

        return messageEnkrypted;
    }

    //finds the message of the user
    public String seeMessage(User req) throws Exception {
        User findencryptedMessage = userRepository.findMessageByEmail(emailSaved);
        String message=AesKeyMessage.AESDecrypt(findencryptedMessage.getMessage());
        return message;
    }
}
