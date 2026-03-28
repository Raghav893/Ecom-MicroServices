package com.raghav.userservice.repo;

import com.raghav.userservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmailId(String emailId);
}
