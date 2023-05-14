package com.apps.service;

import com.apps.domain.Application;
import com.apps.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public ArrayList<Application> getAllApplications() {
        return (ArrayList<Application>) applicationRepository.findAll();
    }

    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Transactional
    public void deleteApplication(int id) {
        applicationRepository.deleteApplication(id);
    }

    public Optional<Application> findApplicationByAppName(String appName) {
        return applicationRepository.findApplicationByAppName(appName);
    }

    public Optional<Application> findApplicationByAppCategory(String category) {
        return applicationRepository.findApplicationByAppCategory(category);
    }

    public Optional<Application> findApplicationByRating(Double rating) {
        return applicationRepository.findApplicationByRating(rating);
    }
}
