package com.andre.project_finances.service;

import com.andre.project_finances.dto.UserDTO;
import com.andre.project_finances.dto.UserResponseDTO;
import com.andre.project_finances.domain.entities.User;
import com.andre.project_finances.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO createUser(UserDTO userDTO) {
        String password = this.passwordEncoder.encode(userDTO.password());
        User user = new User(userDTO, password);
        this.saveUser(user);

        return new UserResponseDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username);
    }
}
