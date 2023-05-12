package com.apps.repository;

import com.apps.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Optional<Application> findApplicationByAppName(String appName);

    Optional<Application> findApplicationByAppCategory(String appCategory);

    Optional<Application> findApplicationByRating(Double rating);
}
