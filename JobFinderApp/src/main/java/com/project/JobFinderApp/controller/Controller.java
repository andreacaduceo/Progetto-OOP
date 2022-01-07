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


    @GetMapping("/jobs")
    public JSONArray getByLocations(@RequestBody JSONObject città) throws IOException, ParseException {
        return service.chiamataPiuLocation(città);
    }

    @GetMapping("/locationSuggestion")
    public JSONObject locationSuggestion() throws IOException, ParseException {
        return service.suggerisciLocation();
    }

    @GetMapping("/jobsByContract")
    public JSONArray getByEmployment(@RequestBody JSONObject città, @RequestParam String contratto) throws IOException, ParseException {
        return service.chiamataFiltrataPerContratto(città,contratto);
    }

    @GetMapping("/jobsBySource")
    public JSONArray getBySource(@RequestBody JSONObject città, @RequestParam String source) throws IOException, ParseException {
        return service.chiamataFiltrataPerSource(città,source);
    }

    @GetMapping("/jobsByLanguage")
    public JSONArray getByLanguage(@RequestBody JSONObject città, @RequestParam String linguaggio) throws IOException, ParseException {
        return service.chiamataFiltrataPerLinguaggio(città, linguaggio);
    }
}

