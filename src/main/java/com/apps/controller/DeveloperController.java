package com.apps.controller;

import com.apps.domain.Developer;
import com.apps.service.DeveloperService;
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

    @Operation(summary = "Gives list of all developers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All developers found"),
            @ApiResponse(responseCode = "400", description = "Did not get developers"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Developers did not found"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<ArrayList<Developer>> getAllDevelopers() {
        ArrayList<Developer> allDevelopers = developerService.getAllDevelopers();
        logger.warn(String.valueOf(allDevelopers));
        return new ResponseEntity<>(allDevelopers, (!allDevelopers.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Gets developer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer found"),
            @ApiResponse(responseCode = "400", description = "Did not get developer by id"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "No developers were found with such id"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Developer> getDevById(@PathVariable int id) {
        Developer developer = developerService.getDevById(id);
        if (developer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(developer, HttpStatus.OK);
    }

    @Operation(summary = "Gets developer by firstname and lastname")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer found"),
            @ApiResponse(responseCode = "400", description = "Did not get developer by firstname and lastname"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "No developers was found with such firstname and lastname"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/fnln/{firstName}, {lastName}")
    public ResponseEntity<Developer> findDeveloperByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        Optional<Developer> developer = developerService.findDeveloperByFirstNameAndLastName(firstName, lastName);
        return developer.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Operation(summary = "Gets developer by birthdate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer found"),
            @ApiResponse(responseCode = "400", description = "Did not get developer by birthdate"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "No developers was found with such birthdate"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/bd/ {birthDate}")
    public ResponseEntity<Developer> findDeveloperByBirthDate(@PathVariable Date birthDate) {
        Optional<Developer> developer = developerService.findDeveloperByBirthDate(birthDate);
        return developer.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Operation(summary = "Creates developer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Developer created successfully"),
            @ApiResponse(responseCode = "400", description = "Developer did not created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Can not create a developer"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(summary = "Deletes developer from database (changes field 'is_deleted' to TRUE)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Developer deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Developer did not deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Can not delete a developer"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDeveloper(@PathVariable int id) {
        developerService.deleteDeveloper(id);
        logger.warn("Developer" + id + " deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
