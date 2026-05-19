package com.financeall.repository;

import com.financeall.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByEmail(String email);
    List<User> findByUsernameContainingIgnoreCase(String username);
    List<User> findTop5ByOrderByIdDesc();
}