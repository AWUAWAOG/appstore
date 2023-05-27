package com.apps.service;

import com.apps.domain.Application;
import com.apps.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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

    public Application getAppById(int id) {
        return applicationRepository.findById(id).orElse(new Application());
    }

    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    public Application updateApp(Application application) {
        application.setCreated(new Date(System.currentTimeMillis()));
        return applicationRepository.saveAndFlush(application);
    }

    @Transactional
    public void addDevToApp(int appId, int devId) {
        applicationRepository.addDevToApp(appId, devId);
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