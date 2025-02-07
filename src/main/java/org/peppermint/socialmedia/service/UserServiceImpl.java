package org.peppermint.socialmedia.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.peppermint.socialmedia.model.Post;
import org.peppermint.socialmedia.securityconfig.SecurityConstants;
import org.peppermint.socialmedia.exception.EntityNotFoundException;
import org.peppermint.socialmedia.exception.UserExistedException;
import org.peppermint.socialmedia.model.User;
import org.peppermint.socialmedia.repository.UserRepository;
import org.peppermint.socialmedia.response.AuthResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
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
//        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
//        String token = JwtProvider.generateToken(authentication);
        String token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
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
        User user = userRepository.findUserByEmail(email);
        if (user == null) throw new EntityNotFoundException(404, User.class);
        else return user;
    }

    @Override
    public User followUser(Integer userId1, Integer userId2) {
        User user1 = findUserById(userId1);
        User user2 = findUserById(userId2);
        user2.getFollowers().add(user1);
        user1.getFollowings().add(user2);
        userRepository.save(user1);
        userRepository.save(user2);
        return user1;
    }

    @Override
    public User updateUser(User user, Integer userId) {
        User oldUser = findUserById(userId);
//        if (user.getEmail() != null) oldUser.setEmail(user.getEmail());
        if (user.getPassword() != null) oldUser.setPassword(encoder.encode(user.getPassword()));
        if (user.getFirstName() != null) oldUser.setFirstName(user.getFirstName());
        if (user.getLastName() != null) oldUser.setLastName(user.getLastName());
        if (user.getGender() != null) oldUser.setGender(user.getGender());
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
//        String email = JwtProvider.getEmailFromJwtToken(jwt);
        jwt = jwt.replace(SecurityConstants.BEARER, "").trim();
        String email = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                .build()
                .verify(jwt)
                .getSubject();
        User user = userRepository.findUserByEmail(email);
        return user;
    }

    @Override
    public List<User> getFollower(Integer id) {
        User user = findUserById(id);
        return user.getFollowers();
    }

    @Override
    public List<User> getFollowing(Integer id) {
        User user = findUserById(id);
        return user.getFollowings();
    }

    @Override
    public List<Post> getSavedPost(Integer id) {
        User user = findUserById(id);
        return user.getSavedPost();
    }

    public User unwrapUser(Optional<User> optionalUser, Integer userId) {
        if (optionalUser.isPresent()) return optionalUser.get();
        else throw new EntityNotFoundException(userId, User.class);
    }
}
