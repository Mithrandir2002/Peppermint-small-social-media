package org.peppermint.socialmedia.controller;

import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.response.AuthResponse;
import org.peppermint.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder encoder;

    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

}
