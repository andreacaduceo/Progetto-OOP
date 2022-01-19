package com.project.JobFinderApp;

import com.project.JobFinderApp.model.JobInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


import java.util.Vector;

import static org.junit.Assert.assertEquals;

/**
 * Classe che si occupa del test del metodo toJSONObject() della classe JobInfo.
 */

public class JobInfoTest {

    private JobInfo jobInfo;

    /**
     * Metodo per inizializzare i dati per effettuare il test.
     */

    @BeforeEach
    void setUp() {
        String nome = "Microsoft";
        String tipoContratto = "part time";
        String location = "Roma";
        String data = "2022-01-20";
        String source = "StackOverflow";
        String remoto = "Disponibile";
        JSONArray linguaggi = new JSONArray();
        linguaggi.add(0,"php");
        linguaggi.add(1,"java");
        this.jobInfo = new JobInfo(nome, tipoContratto, location, data, source, remoto, linguaggi);
    }

    /**
     * Metodo per resettare cio' che e' stato inizializzato per il test in setUp().
     */
    @AfterEach
    void tearDown() {}

    /**
     * Test per il casting in JSONObject di un oggetto JobInfo.
     */

    @Test
    public void testCastingJSONObject() {
        setUp();
        Vector<String> ling = new Vector<>();
        ling.add("php");
        ling.add("java");
        JSONObject object = new JSONObject();
        object.put("Nome della compagnia", "Microsoft");
        object.put("Tipo di contratto", "part time");
        object.put("Località", "Roma");
        object.put("Data annuncio", "2022-01-20");
        object.put("Source", "StackOverflow");
        object.put("Lavoro da remoto", "Disponibile");
        object.put("Linguaggi", ling);
        assertEquals(object.toString(), this.jobInfo.toJSONObject().toString());
    }
}