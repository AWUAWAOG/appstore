package com.apps.repository;

import com.apps.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUserLogin(String login);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByRole(String role);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users SET is_deleted = true WHERE id = :id", countQuery = "SELECT * FROM users where id = :id")
    void deleteUser(Integer id);
}
