package com.project.JobFinderApp.controller;

import com.project.JobFinderApp.service.Service;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {

    @Autowired
    Service service;

    @GetMapping("/jobs")
    public JSONObject getByLocation(@RequestParam String cityName) {
        return null;
    }


}

