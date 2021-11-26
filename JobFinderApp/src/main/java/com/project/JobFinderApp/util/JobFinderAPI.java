package com.project.JobFinderApp.util;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class JobFinderAPI {

    private String token = "Token dd38bfa287efe1e654279eeded4b3d1bfa0d2c4a";

    public JSONObject getJobs() throws IOException {
        URLConnection con = new URL("https://findwork.dev/api/jobs/").openConnection();
        return null;
    }
}
