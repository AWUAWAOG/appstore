package com.apps.service;

import com.apps.domain.Developer;
import com.apps.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class DeveloperService {

    private final DeveloperRepository developerRepository;

    @Autowired
    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public ArrayList<Developer> getAllDevelopers() {
        return (ArrayList<Developer>) developerRepository.findAll();
    }

    public Developer getDevById(int id) {
        return developerRepository.findById(id).orElse(new Developer());
    }

    public Developer createDeveloper(Developer developer) {
        return developerRepository.save(developer);
    }

    public Developer updateDev(Developer developer) {
        developer.setCreated(new Date(System.currentTimeMillis()));
        return developerRepository.saveAndFlush(developer);
    }

    @Transactional
    public void deleteDeveloper(int id) {
        developerRepository.deleteDeveloper(id);
    }

    public Optional<Developer> findDeveloperByFirstNameAndLastName(String firstName, String lastName) {
        return developerRepository.findDeveloperByFirstNameAndLastName(firstName, lastName);
    }

    public Optional<Developer> findDeveloperByBirthDate(Date birthDate) {
        return developerRepository.findDeveloperByBirthDate(birthDate);
    }
}