package com.example.autogradertyp.data.service;

import com.example.autogradertyp.data.entity.User;
import com.example.autogradertyp.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * A service class that provide CRUD of the user table in the database
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 25/01/2023
 */

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * A method that gets a user by their username
     *
     * @param username The username
     * @return The wanted user
     * @throws UsernameNotFoundException
     */
    @Override
    public User loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not present"));
        return user;
    }


    /**
     * A method that adds a user to the user table
     *
     * @param user The wanted to be added
     */
    public void add(UserDetails user) {

        userRepository.save((User) user);
    }
}