package com.apps.repository;

import com.apps.domain.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
    Optional<Developer> findDeveloperByFirstNameAndLastName(String firstName, String lastName);
}
