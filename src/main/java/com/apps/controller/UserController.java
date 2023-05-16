package com.apps.controller;

import com.apps.domain.User;
import com.apps.domain.request.RegistrationRequest;
import com.apps.service.UserService;
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

import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Gives list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users found"),
            @ApiResponse(responseCode = "400", description = "Did not get users"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Users did not found"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<ArrayList<User>> getAllUsers() {
        ArrayList<User> userList = userService.getAllUsers();
        logger.warn(String.valueOf(userList));
        return new ResponseEntity<>(userList, (!userList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Gets user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "400", description = "Did not get user by id"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "No user was found with such id"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Creates new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "User did not created"),
            @ApiResponse(responseCode = "401", description = "User unauthorized"),
            @ApiResponse(responseCode = "404", description = "Can not create a user"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("BindingResult error " + o);
            }
        }
        userService.createUser(user);
        logger.warn("User" + user + " created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "User did not created"),
            @ApiResponse(responseCode = "401", description = "User unauthorized"),
            @ApiResponse(responseCode = "404", description = "Can not create a user"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/reg")
    public ResponseEntity<HttpStatus> register(@RequestBody RegistrationRequest registrationRequest) {
        Boolean result = userService.userRegistration(registrationRequest);
        return new ResponseEntity<>(result ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Gets user by login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "400", description = "Did not get user by login"),
            @ApiResponse(responseCode = "401", description = "User Unauthorized"),
            @ApiResponse(responseCode = "404", description = "No users was found with such login"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/lg/{login}")
    public ResponseEntity<User> findUserByUserLogin(@PathVariable String login) {
        Optional<User> user = userService.findUserByUserLogin(login);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Operation(summary = "Gets user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "400", description = "Did not get user by email"),
            @ApiResponse(responseCode = "401", description = "User unauthorized"),
            @ApiResponse(responseCode = "404", description = "No users was found with such email"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/em/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findUserByEmail(email);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Operation(summary = "Gets user by role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "400", description = "Did not get user by role"),
            @ApiResponse(responseCode = "401", description = "User unauthorized"),
            @ApiResponse(responseCode = "404", description = "No users was found with such role"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/rl/{role}")
    public ResponseEntity<User> findUserByRole(@PathVariable String role) {
        Optional<User> user = userService.findUserByRole(role);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @Operation(summary = "Updates user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Did not update user"),
            @ApiResponse(responseCode = "401", description = "User unauthorized"),
            @ApiResponse(responseCode = "404", description = "Users was not updated"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public void updateUser(@RequestBody User user) {
        logger.warn("User" + user + " updated!");
        userService.updateUser(user);
    }

    @Operation(summary = "Deletes user from database (changes field 'is_deleted' to TRUE)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "User did not deleted"),
            @ApiResponse(responseCode = "401", description = "User unauthorized"),
            @ApiResponse(responseCode = "404", description = "Can not delete a user"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        logger.warn("User" + id + " deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Adds application to single user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application added successfully"),
            @ApiResponse(responseCode = "400", description = "Application did not added"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Can not add an application"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
            @ApiResponse(responseCode = "440", description = "Login time-out"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/addApp")
    public ResponseEntity<HttpStatus> addApp(@RequestParam int userId, @RequestParam int appId) {
        userService.addAppToUser(userId, appId);
        logger.warn("Application added!" + appId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
