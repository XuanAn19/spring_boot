package com.health.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.health.entity.User;
import com.health.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
   

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public Long getUserIdByEmail(String email) {
        Optional<User> user = Optional.of(userRepository.findByEmail(email));
        if (user.isPresent()) {
            return user.get().getUserId(); // Trả về userId của người dùng
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }
    
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    
}
