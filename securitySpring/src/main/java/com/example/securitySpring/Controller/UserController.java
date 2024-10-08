package com.example.securitySpring.Controller;

import com.example.securitySpring.model.AuthenticationResponse;
import com.example.securitySpring.model.User;
import com.example.securitySpring.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User req){
        return ResponseEntity.ok(userService.register(req));
    }

    @GetMapping("/login")
    public ResponseEntity<AuthenticationResponse>login(@RequestBody User req){

        return ResponseEntity.ok(userService.login(req));
    }

}
