package org.peppermint.socialmedia.controller;

import org.peppermint.socialmedia.config.JwtProvider;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.request.LoginRequest;
import org.peppermint.socialmedia.response.AuthResponse;
import org.peppermint.socialmedia.service.CustomerUserDetailService;
import org.peppermint.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    BCryptPasswordEncoder encoder;
    private CustomerUserDetailService customerUserDetailService;

    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/signin")
    public AuthResponse signin(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        String token = JwtProvider.generateToken(authentication);
        return new AuthResponse(token, "Sucessfully singin.");
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customerUserDetailService.loadUserByUsername(email);
        if (userDetails == null) throw new BadCredentialsException("Invalid username");
        if (!encoder.matches(password, userDetails.getPassword())) throw new BadCredentialsException("Invalid password");
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
