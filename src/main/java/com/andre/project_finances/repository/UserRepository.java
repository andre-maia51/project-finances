package com.andre.project_finances.repository;

import com.andre.project_finances.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);
}
