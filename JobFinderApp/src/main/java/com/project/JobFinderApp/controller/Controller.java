package com.project.JobFinderApp.controller;

import com.project.JobFinderApp.model.JobInfo;
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
import java.util.Vector;


@RestController
public class Controller {

    @Autowired
    Service service;


    @GetMapping("/jobs")
    public JSONArray getByLocations(@RequestBody JSONObject citta) throws IOException, ParseException {
        return service.chiamataPiuLocation(citta);
    }

    @GetMapping("/suggestion")
    public JSONObject locationSuggestion() throws IOException, ParseException {
        return service.suggerisciLocation();
    }
}

