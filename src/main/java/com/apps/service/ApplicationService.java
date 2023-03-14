package com.apps.service;

import com.apps.domain.Application;
import com.apps.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class ApplicationService {

    ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public Application getApplicationById(int id) {
        return ApplicationRepository.getApplicationById(id);
    }

    public boolean createApplication(String app_name, String app_category, Double rating, String description, int app_year, Double price) throws SQLException {
        return applicationRepository.createApplication(app_name, app_category, rating, description, app_year, price); //TODO: передать все параметры
    }

    public boolean updateApplication(int id, Double rating, String description, Double price) {
        return applicationRepository.updateApplication(id, rating, description, price);
    }
}
