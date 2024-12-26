package org.peppermint.socialmedia.service;

import lombok.AllArgsConstructor;
import org.peppermint.socialmedia.config.JwtProvider;
import org.peppermint.socialmedia.exception.EntityNotFoundException;
import org.peppermint.socialmedia.exception.UserExistedException;
import org.peppermint.socialmedia.model.Post;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.repository.UserRepository;
import org.peppermint.socialmedia.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    BCryptPasswordEncoder encoder;
    @Override
    public AuthResponse registerUser(User user) {
        User isExist = userRepository.findUserByEmail(user.getEmail());
        if (isExist != null) throw new UserExistedException(user.getEmail());
        user.setPassword(encoder.encode(user.getPassword()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(token, "Register Successfully");
        userRepository.save(user);
        return authResponse;
    }

    @Override
    public User findUserById(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return unwrapUser(optionalUser, userId);
    }

    @Override
    public User findUserByEmail(String email) {
        return findUserByEmail(email);
    }

    @Override
    public User followUser(Integer userId1, Integer userId2) {
        User user1 = findUserById(userId1);
        User user2 = findUserById(userId2);
        user2.getFollowers().add(userId1);
        user1.getFollowings().add(userId2);
        userRepository.save(user1);
        userRepository.save(user2);
        return user1;
    }

    @Override
    public User updateUser(User user, Integer userId) {
        User oldUser = findUserById(userId);
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setGender(user.getGender());
        return userRepository.save(oldUser);
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }

    @Override
    public User savedPost(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserByJwt(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findUserByEmail(email);
        return user;
    }

    public User unwrapUser(Optional<User> optionalUser, Integer userId) {
        if (optionalUser.isPresent()) return optionalUser.get();
        else throw new EntityNotFoundException(userId, User.class);
    }
}
