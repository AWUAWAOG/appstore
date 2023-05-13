package com.apps.controller;

import com.apps.domain.Developer;
import com.apps.service.DeveloperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/dev")
public class DeveloperController {

    DeveloperService developerService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<Developer>> getAllDevelopers() {
        ArrayList<Developer> allDevelopers = developerService.getAllDevelopers();
        logger.warn(String.valueOf(allDevelopers));
        return new ResponseEntity<>(allDevelopers, (!allDevelopers.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND));
    }

    @GetMapping("/fnln/{firstName}, {lastName}")
    public ResponseEntity<Developer> findDeveloperByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        Optional<Developer> developer = developerService.findDeveloperByFirstNameAndLastName(firstName, lastName);
        return developer.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @GetMapping("/bd/ {birthDate}")
    public ResponseEntity<Developer> findDeveloperByBirthDate(@PathVariable Date birthDate) {
        Optional<Developer> developer = developerService.findDeveloperByBirthDate(birthDate);
        return developer.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createDeveloper(@RequestBody Developer developer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("BindingResult error " + o);
            }
        }
        developerService.createDeveloper(developer);
        logger.warn("Developer" + developer + " created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDeveloper(@PathVariable int id) {
        developerService.deleteDeveloper(id);
        logger.warn("Developer" + id + " deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
