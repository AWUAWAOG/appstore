package com.apps.controller;

import com.apps.domain.Application;
import com.apps.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    ApplicationService applicationService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<Application>> getAllApplications() {
        ArrayList<Application> allApplications = applicationService.getAllApplications();
        logger.warn(String.valueOf(allApplications));
        return new ResponseEntity<>(allApplications, (!allApplications.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{appMame}")
    public ResponseEntity<Application> findApplicationByAppName(@PathVariable String appMame) {
        Optional<Application> application = applicationService.findApplicationByAppName(appMame);
        return application.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @GetMapping("/cat/{category}")
    public ResponseEntity<Application> findApplicationByAppCategory(@PathVariable String category) {
        Optional<Application> application = applicationService.findApplicationByAppCategory(category);
        return application.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @GetMapping("/rat/{rating}")
    public ResponseEntity<Application> findApplicationByRating(@PathVariable Double rating) {
        Optional<Application> application = applicationService.findApplicationByRating(rating);
        return application.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createApplication(@RequestBody Application application, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("BindingResult error " + o);
            }
        }
        applicationService.createApplication(application);
        logger.warn("Application" + application + " created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteApplication(@PathVariable int id) {
        applicationService.deleteApplication(id);
        logger.warn("Application" + id + " deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
