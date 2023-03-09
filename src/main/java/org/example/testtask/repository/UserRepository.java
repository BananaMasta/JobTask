package org.example.testtask.repository;

import org.example.testtask.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsById(String id);

    boolean existsUserById(UUID id);

    User findByName(String name);

    boolean existsByEmail(String email);
}
