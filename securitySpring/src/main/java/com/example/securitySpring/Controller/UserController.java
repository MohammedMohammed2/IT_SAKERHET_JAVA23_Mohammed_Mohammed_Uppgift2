package com.example.securitySpring.Controller;

import com.example.securitySpring.model.AuthenticationResponse;
import com.example.securitySpring.model.User;
import com.example.securitySpring.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User req){
        return ResponseEntity.ok(userService.register(req));
    }

    @GetMapping("/login")
    public ResponseEntity<AuthenticationResponse>login(@RequestBody User req){
        return ResponseEntity.ok(userService.login(req));
    }
    @PostMapping("/sendmessage")
    public ResponseEntity<String> message(@RequestBody User req) throws Exception {
        return ResponseEntity.ok(userService.sendMessage(req));
    }
    @GetMapping("/seemessage")
    public ResponseEntity<String> seemessage(@RequestBody User req) throws Exception {
        return ResponseEntity.ok(userService.seeMessage(req));
    }

}
