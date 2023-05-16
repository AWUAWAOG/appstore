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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/app")
public class ApplicationController {

    ApplicationService applicationService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Operation(summary = "Gives list of all applications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All applications found"),
            @ApiResponse(responseCode = "400", description = "Did not get applications"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "application did not found"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<ArrayList<Application>> getAllApplications() {
        ArrayList<Application> allApplications = applicationService.getAllApplications();
        logger.warn(String.valueOf(allApplications));
        return new ResponseEntity<>(allApplications, (!allApplications.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Gets application by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application found"),
            @ApiResponse(responseCode = "400", description = "Did not get application by id"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "No applications were found with such id"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Application> getAppById(@PathVariable int id) {
        Application application = applicationService.getAppById(id);
        if (application == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(application, HttpStatus.OK);
    }

    @Operation(summary = "Gets application by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application found"),
            @ApiResponse(responseCode = "400", description = "Did not get application by name"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "No applications were found with such name"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/name/{appMame}")
    public ResponseEntity<Application> findApplicationByAppName(@PathVariable String appMame) {
        Optional<Application> application = applicationService.findApplicationByAppName(appMame);
        return application.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Operation(summary = "Gets application by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application found"),
            @ApiResponse(responseCode = "400", description = "Did not get application by category"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "No applications were found with such category"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/cat/{category}")
    public ResponseEntity<Application> findApplicationByAppCategory(@PathVariable String category) {
        Optional<Application> application = applicationService.findApplicationByAppCategory(category);
        return application.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Operation(summary = "Gets application by rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application found"),
            @ApiResponse(responseCode = "400", description = "Did not get application by rating"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "No applications were found with such rating"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/rat/{rating}")
    public ResponseEntity<Application> findApplicationByRating(@PathVariable Double rating) {
        Optional<Application> application = applicationService.findApplicationByRating(rating);
        return application.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Operation(summary = "Creates new application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Application created successfully"),
            @ApiResponse(responseCode = "400", description = "Application did not created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Can not create an application"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Updates application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application updated"),
            @ApiResponse(responseCode = "400", description = "Did not update application"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Applications was not updated"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public void updateApp(@RequestBody Application application) {
        logger.warn("User" + application + " updated!");
        applicationService.updateApp(application);
    }

    @Operation(summary = "Adds developer to application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer added to application successfully"),
            @ApiResponse(responseCode = "400", description = "Developer did not added to application"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Can not add developer to application"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/addDev")
    public ResponseEntity<HttpStatus> addDev(@RequestParam int appId, @RequestParam int devId) {
        applicationService.addDevToApp(appId, devId);
        logger.warn("Developer added!" + devId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Deletes application from database (changes field 'is_deleted' to TRUE)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Application deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Application did not deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Can not delete an application"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteApplication(@PathVariable int id) {
        applicationService.deleteApplication(id);
        logger.warn("Application" + id + " deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
