package com.apps.controller;

import com.apps.domain.Application;
import com.apps.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/app")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All applications/application found/updated"),
        @ApiResponse(responseCode = "201", description = "Application created successfully"),
        @ApiResponse(responseCode = "204", description = "Application deleted successfully"),
        @ApiResponse(responseCode = "409", description = "Conflict"),
        @ApiResponse(responseCode = "400", description = "Did not get application(s)"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Application(s) did not found"),
        @ApiResponse(responseCode = "440", description = "Login time-out"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
})
public class ApplicationController {

    private final ApplicationService applicationService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Operation(summary = "Gives list of all applications")
    @GetMapping
    public ResponseEntity<ArrayList<Application>> getAllApplications() {
        ArrayList<Application> allApplications = applicationService.getAllApplications();
        logger.info(String.valueOf(allApplications));
        return new ResponseEntity<>(allApplications, (!allApplications.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Gets application by id")
    @GetMapping("/{id}")
    public ResponseEntity<Application> getAppById(@PathVariable int id) {
        Application application = applicationService.getAppById(id);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(application, HttpStatus.OK);
    }

    @Operation(summary = "Gets application by name")
    @GetMapping("/name/{appName}")
    public ResponseEntity<Application> findApplicationByAppName(@PathVariable String appName) {
        Optional<Application> application = applicationService.findApplicationByAppName(appName);
        return application.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Operation(summary = "Gets application by category")
    @GetMapping("/cat/{category}")
    public ResponseEntity<Application> findApplicationByAppCategory(@PathVariable String category) {
        Optional<Application> application = applicationService.findApplicationByAppCategory(category);
        return application.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Operation(summary = "Gets application by rating")
    @GetMapping("/rat/{rating}")
    public ResponseEntity<Application> findApplicationByRating(@PathVariable Double rating) {
        Optional<Application> application = applicationService.findApplicationByRating(rating);
        return application.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Operation(summary = "Creates new application")
    @Valid
    @PostMapping
    public ResponseEntity<HttpStatus> createApplication(@RequestBody Application application, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("BindingResult error " + o);
            }
        }
        applicationService.createApplication(application);
        logger.info("Application" + application + " created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Updates application")
    @PutMapping
    public void updateApp(@RequestBody Application application) {
        applicationService.updateApp(application);
        logger.info("User" + application + " updated!");
    }

    @Operation(summary = "Adds developer to application")
    @PostMapping("/addDev")
    public ResponseEntity<HttpStatus> addDev(@RequestParam int appId, @RequestParam int devId) {
        applicationService.addDevToApp(appId, devId);
        logger.info("Developer added!" + devId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Deletes application from database (changes field 'is_deleted' to TRUE)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteApplication(@PathVariable int id) {
        applicationService.deleteApplication(id);
        logger.info("Application" + id + " deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}