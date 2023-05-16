package com.apps.repository;

import com.apps.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.ManyToOne;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUserLogin(String login);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByRole(String role);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO l_applications_users (id, users_id, dapplications_id) VALUES (DEFAULT, :userId, :appId)",
            countQuery = "SELECT * FROM users WHERE users_id = :userId, SELECT * FROM applications WHERE applications_id = :appId")
    void addAppToUser(Integer userId, Integer appId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users SET is_deleted = true WHERE id = :id",
            countQuery = "SELECT * FROM users WHERE id = :id")
    void deleteUser(Integer id);
}