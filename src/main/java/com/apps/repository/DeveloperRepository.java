package com.apps.repository;

import com.apps.domain.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
    Optional<Developer> findDeveloperByFirstNameAndLastName(String firstName, String lastName);

    Optional<Developer> findDeveloperByBirthDate(Date birthDate);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE developers SET is_deleted = true WHERE id = :id",
            countQuery = "SELECT * FROM developers where id = :id")
    void deleteDeveloper(Integer id);
}