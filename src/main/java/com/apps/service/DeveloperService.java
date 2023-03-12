package com.apps.service;

import com.apps.domain.Application;
import com.apps.domain.Developer;
import com.apps.domain.User;
import com.apps.repository.DeveloperRepository;
import com.apps.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class DeveloperService {

    DeveloperRepository developerRepository;

    @Autowired
    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public Developer getDeveloperById(int id) {
        return developerRepository.getDeveloperById(id);
    }

    public boolean createDeveloper(String first_name, String last_name, int age, Date birth_date) throws SQLException {
        return developerRepository.createDeveloper(first_name, last_name, age, birth_date);
    }

    public boolean updateDeveloper(int id, String firstName, String lastName, int age, Date birth_date) {
        return developerRepository.updateDeveloper(id, firstName, lastName, age, birth_date);
    }

    public boolean deleteDeveloper(int id) {
        return developerRepository.deleteDeveloper(id);
    }
}
