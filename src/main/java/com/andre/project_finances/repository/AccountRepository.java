package com.andre.project_finances.repository;

import com.andre.project_finances.domain.entities.Account;
import com.andre.project_finances.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountByUser(User user);
}
