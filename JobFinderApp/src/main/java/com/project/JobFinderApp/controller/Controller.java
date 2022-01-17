package com.project.JobFinderApp.controller;

import com.project.JobFinderApp.exception.DataException;
import com.project.JobFinderApp.exception.CityException;
import com.project.JobFinderApp.exception.ParamException;
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
    public JSONArray getByLocations(@RequestBody JSONObject citta) throws CityException {
        return service.chiamataPiuLocation(citta);
    }

    @GetMapping("/locationSuggestion")
    public JSONObject locationSuggestion() throws IOException, ParseException {
        return service.suggerisciLocation();
    }

    @GetMapping("/jobsByContract")
    public JSONArray getByEmployment(@RequestBody JSONObject citta, @RequestParam String contratto) throws ParamException, CityException {
        return service.chiamataFiltrataPerContratto(citta, contratto);
    }

    @GetMapping("/jobsBySource")
    public JSONArray getBySource(@RequestBody JSONObject citta, @RequestParam String source) throws ParamException, CityException {
        return service.chiamataFiltrataPerSource(citta, source);
    }

    @GetMapping("/jobsByLanguage")
    public JSONArray getByLanguage(@RequestBody JSONObject citta, @RequestParam String linguaggio) throws ParamException, CityException {
        return service.chiamataFiltrataPerLinguaggio(citta, linguaggio);
    }

    @GetMapping("/stats")
    public JSONArray stats(@RequestBody JSONObject citta) throws CityException {
        return service.statisticheGenerali(citta);
    }

    @GetMapping("/statsBySource")
    public JSONArray statsBySource(@RequestBody JSONObject citta, @RequestParam String source) throws ParamException, CityException {
        return service.statisticheSource(citta, source);
    }

    @GetMapping("/statsByData")
    public JSONArray statsByData(@RequestBody JSONObject citta, @RequestParam String data) throws DataException, ParamException, CityException {
        return service.statisticheData(citta, data);
    }

    @GetMapping("/statsByRemote")
    public JSONArray statsByRemote(@RequestBody JSONObject citta, @RequestParam String remoto) throws ParamException, CityException {
        return service.statisticheRemoto(citta, remoto);
    }

}

