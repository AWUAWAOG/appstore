package com.apps.controller;

import com.apps.domain.Application;
import com.apps.domain.User;
import com.apps.service.UserService;
import jdk.internal.org.jline.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
@RequestMapping("/user")
public class UserController {

    UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        ArrayList<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "...";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "...";
    }

    @GetMapping("/getApp/{id}")
    public String giveAllApplicationsForUser(@PathVariable int id, Model model) throws SQLException {
        ArrayList<Application> applicationList = userService.getApplicationsForSingleUser(id);
        model.addAttribute("applicationList", applicationList.toString());
        return "...";
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public String createUser(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o :bindingResult.getAllErrors()){
                logger.warn("BindingResult error " + o);
            }
            return "unsuccessfully";
        }
        boolean result = userService.createUser(user);
        if (result) {
            return "successfully";
        } else
        return "unsuccessfully";
    }

    @PostMapping("/addApp")
    public String addApplication(@RequestParam int userId, @RequestParam int applicationId) {
        if (userService.addApplicationToUser(userId, applicationId)) {
            return "successfully";
        } else
        return "unsuccessfully";
    }

    @DeleteMapping("/deleteApp")
    public String deleteApplication(@RequestParam int userId, @RequestParam int applicationId) {
        if (userService.deleteApplicationFromUser(userId, applicationId)) {
            return "successfully";
        } else
            return "unsuccessfully";
    }


    @PutMapping
    public String updateUser(
            @RequestParam String id,
            @RequestParam String user_login,
            @RequestParam String user_password,
            @RequestParam String email,
            @RequestParam String first_name,
            @RequestParam String last_name,
            @RequestParam Boolean edited
    ) {
        boolean result = userService.updateUser(Integer.parseInt(id), user_login, user_password, email, first_name, last_name, edited);
        if (result) {
            return "successfully";
        } else
        return "unsuccessfully";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        if (userService.deleteUser(id)) {
            return "successfully";
        } else
        return "unsuccessfully";
    }
}
