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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/dev")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All developers/developer found/updated"),
        @ApiResponse(responseCode = "201", description = "Developer created successfully"),
        @ApiResponse(responseCode = "204", description = "Developer deleted successfully"),
        @ApiResponse(responseCode = "409", description = "Conflict"),
        @ApiResponse(responseCode = "400", description = "Did not get developer(s)"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Developer(s) did not found"),
        @ApiResponse(responseCode = "440", description = "Login time-out"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
})
public class DeveloperController {

    private final DeveloperService developerService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @Operation(summary = "Gives list of all developers")
    @GetMapping
    public ResponseEntity<ArrayList<Developer>> getAllDevelopers() {
        ArrayList<Developer> allDevelopers = developerService.getAllDevelopers();
        logger.info(String.valueOf(allDevelopers));
        return new ResponseEntity<>(allDevelopers, (!allDevelopers.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Gets developer by id")
    @GetMapping("/{id}")
    public ResponseEntity<Developer> getDevById(@PathVariable int id) {
        Developer developer = developerService.getDevById(id);
        if (developer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(developer, HttpStatus.OK);
    }

    @Operation(summary = "Gets developer by firstname and lastname")
    @GetMapping("/fnln/{firstName}/{lastName}")
    public ResponseEntity<Developer> findDeveloperByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
        Optional<Developer> developer = developerService.findDeveloperByFirstNameAndLastName(firstName, lastName);
        return developer.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Operation(summary = "Gets developer by birthdate")
    @GetMapping("/bd/{birthDate}")
    public ResponseEntity<Developer> findDeveloperByBirthDate(@PathVariable Date birthDate) {
        Optional<Developer> developer = developerService.findDeveloperByBirthDate(birthDate);
        return developer.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Operation(summary = "Creates developer")
    @Valid
    @PostMapping
    public ResponseEntity<HttpStatus> createDeveloper(@RequestBody Developer developer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("BindingResult error " + o);
            }
        }
        developerService.createDeveloper(developer);
        logger.info("Developer" + developer + " created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Updates developer")
    @PutMapping
    public void updateDev(@RequestBody Developer developer) {
        developerService.updateDev(developer);
        logger.info("User" + developer + " updated!");
    }

    @Operation(summary = "Deletes developer from database (changes field 'is_deleted' to TRUE)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDeveloper(@PathVariable int id) {
        developerService.deleteDeveloper(id);
        logger.info("Developer" + id + " deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}