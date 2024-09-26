package com.vijay.tasker.controller;

import com.vijay.tasker.model.AuthenticationResponse;
import com.vijay.tasker.model.User;
import com.vijay.tasker.service.UserService;
import com.vijay.tasker.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        // Encode the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.saveUser(user);
    }

    @GetMapping("/find/{username}")
    public User findUser(@PathVariable String username) {
        return userService.findByUsername(username).orElse(null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

        if (passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            // Generate JWT token upon successful login
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
