package com.project.JobFinderApp.controller;

import com.project.JobFinderApp.service.Service;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class Controller {

    @Autowired
    Service service;

    @GetMapping("/jobsOneLocation")
    public JSONObject getByOneLocation(@RequestParam String location) throws IOException, ParseException {
        return service.chiamataLocation(location);
    }

    @GetMapping("/jobs")
    public JSONArray getByLocations(@RequestBody JSONObject citta) throws IOException, ParseException {
        return service.chiamataPiuLocation(citta);
    }
}

