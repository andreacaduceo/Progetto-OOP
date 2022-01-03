package com.project.JobFinderApp.service;

import com.project.JobFinderApp.model.JobInfo;
import com.project.JobFinderApp.util.JobFinderAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Vector;


@Component
public class Service {

    JobFinderAPI api = new JobFinderAPI();

    public JSONArray chiamataPiuLocation(JSONObject nomiCitta) throws IOException, ParseException {
        JSONArray arrayResult = api.estraiValori(api.getMoreLocations(nomiCitta));
        return arrayResult;
    }

    public JSONObject suggerisciLocation() throws IOException, ParseException {
        JSONObject objResult = api.suggerisciLocation();
        return objResult;
    }
}
