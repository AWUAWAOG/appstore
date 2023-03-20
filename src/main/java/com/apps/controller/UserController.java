package com.apps.controller;

import com.apps.domain.Application;
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
import java.sql.SQLException;
import java.util.ArrayList;

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
        ArrayList<User> list = userService.getAllUsers();
        return new ResponseEntity<>(list,(!list.isEmpty())?HttpStatus.OK:HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/getApp/{id}")
    public ResponseEntity<ArrayList<Application>> giveAllApplicationsForUser(@PathVariable int id) throws SQLException {
        return new ResponseEntity<>(userService.getApplicationsForSingleUser(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@ModelAttribute @Valid User user, BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            for (ObjectError o :bindingResult.getAllErrors()){
                logger.warn("BindingResult error " + o);
            }
        }
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/addApp")
    public ResponseEntity<HttpStatus> addApplication(@RequestParam int userId, @RequestParam int applicationId) {
        userService.addApplicationToUser(userId, applicationId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*@DeleteMapping("/deleteApp")
    public ResponseEntity<HttpStatus> deleteApplication(@RequestParam int userId, @RequestParam int applicationId) {
        userService.deleteApplicationFromUser(userId, applicationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    @PutMapping
    public void updateUser(@RequestParam User user) {
       userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
