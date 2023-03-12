package com.apps.controller;

import com.apps.domain.Application;
import com.apps.domain.User;
import com.apps.service.ApplicationService;
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
@RequestMapping("/application")
public class ApplicationController {

    ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);

    @GetMapping("/{id}")
    public String getApplication(@PathVariable int id, Model model) {
        Application application = applicationService.getApplicationById(id);
        if (application.getId() == 0) {
            log.warn("getApplication ERROR " + id);
        }
        model.addAttribute("application", application);
        return "Application";
    }

    @PostMapping
    public String createApplication(
            @RequestParam String app_name,
            @RequestParam String app_category,
            @RequestParam Double rating,
            @RequestParam String description,
            @RequestParam int app_year,
            @RequestParam Double price
    ) throws SQLException {
        log.info("createApplication method DONE");
        boolean result = applicationService.createApplication(app_name, app_category, rating, description, app_year, price);
        return result ? "successfully" : "unsuccessfully";
    }

    @PutMapping
    public String updateApplication(
            @RequestParam String id,
            @RequestParam Double rating,
            @RequestParam String description,
            @RequestParam Double price
    ) {
        boolean result = applicationService.updateApplication(Integer.parseInt(id), rating, description, price);
        return result ? "successfully" : "unsuccessfully";
    }
}
