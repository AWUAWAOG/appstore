package com.apps.controller;


import com.apps.domain.Application;
import com.apps.domain.Developer;
import com.apps.domain.User;
import com.apps.service.DeveloperService;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
@RequestMapping("/dev")
public class DeveloperController {

    DeveloperService developerService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping("/{id}")
    public String getDeveloper(@PathVariable int id, Model model) {
        /*log.info("getDeveloper method");*/
        Developer developer = developerService.getDeveloperById(id);
        if (developer.getId() == 0) {
            /*log.warn("User is not found! Trying find id=" + id);*/
        }
        model.addAttribute("developer", developer);
        return "Developer";
    }

    @PostMapping
    public String createDeveloper(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam int age,
            @RequestParam Date birth_date
    ) throws SQLException {
        boolean result = developerService.createDeveloper(firstName, lastName, age, birth_date);
        return result ? "successfully" : "unsuccessfully";
    }

    @PutMapping
    public String updateDeveloper(
            @RequestParam int id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam int age,
            @RequestParam Date birth_date
    ) {
        boolean result = developerService.updateDeveloper(id, firstName, lastName, age, birth_date);
        return result ? "successfully" : "unsuccessfully";
    }

    @DeleteMapping("/{id}")
    public String deleteDeveloper(@PathVariable int id){
        /*log.info("deleteDeveloper method");*/
        boolean result = developerService.deleteDeveloper(id);
        return result ? "successfully" : "unsuccessfully";
    }
}
