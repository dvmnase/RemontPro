package org.example.remontpro.services;


import org.example.remontpro.UserDetailsImpl;
import org.example.remontpro.models.User;
import org.example.remontpro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {


    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername (String username)  throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User %s not found", username)
        ));
        return UserDetailsImpl.build(user);
    }
    public List<User> getAllUsers() {
        return userRepository.findAllByOrderByIdAsc();
    }
}
