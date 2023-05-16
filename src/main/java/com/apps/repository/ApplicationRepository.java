package com.apps.repository;

import com.apps.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Optional<Application> findApplicationByAppName(String appName);

    Optional<Application> findApplicationByAppCategory(String appCategory);

    Optional<Application> findApplicationByRating(Double rating);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO l_applications_developers (id, applications_id, developers_id) VALUES (DEFAULT, :appId, :devId)",
            countQuery = "SELECT * FROM applications WHERE id = :appId, SELECT * FROM developers WHERE id = :devId")
    void addDevToApp(Integer appId, Integer devId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE applications SET is_deleted = true WHERE id = :id",
            countQuery = "SELECT * FROM applications where id = :id")
    void deleteApplication(Integer id);
}