package com.example.securitySpring.Controller;

import com.example.securitySpring.entity.User;
import com.example.securitySpring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();



    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public String users(Model model){
        List<User> usersList = userRepository.findAll();
        model.addAttribute("usersList", usersList);
        return "users";
    }

    @PostMapping(value = "/CreateUser")
    public String CreateUser(User user, Model model){
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        model.addAttribute("message","user Created!");
        return "redirect:/";
    }

    @GetMapping("/CreateUser")
    public String CreatedUser(Model model){
        model.addAttribute("user", new User());
        return "CreateUser";
    }


    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){

        return null;
    }


    @RequestMapping("/delete/{id}")
    public String deletestudent(@PathVariable(name = "id") int id) {
        userRepository.deleteById(id);
        return "redirect:/";
    }
}
