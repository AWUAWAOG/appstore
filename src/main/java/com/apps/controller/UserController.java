package com.apps.controller;

import com.apps.domain.User;
import com.apps.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping
    public ResponseEntity<ArrayList<User>> getAllUsers() {
        ArrayList<User> userList = userService.getAllUsers();
        logger.warn(String.valueOf(userList));
        return new ResponseEntity<>(userList, (!userList.isEmpty()) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                logger.warn("BindingResult error " + o);
            }
        }
        userService.createUser(user);
        logger.warn("User" + user + " created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/lg/{login}")
    public ResponseEntity<User> findUserByUserLogin(@PathVariable String login) {
        Optional<User> user = userService.findUserByUserLogin(login);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @GetMapping("/em/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findUserByEmail(email);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @GetMapping("/rl/{role}")
    public ResponseEntity<User> findUserByRole(@PathVariable String role) {
        Optional<User> user = userService.findUserByRole(role);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PutMapping
    public void updateUser(@RequestBody User user) {
        logger.warn("User" + user + " updated!");
        userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        logger.warn("User" + id + " deleted.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
