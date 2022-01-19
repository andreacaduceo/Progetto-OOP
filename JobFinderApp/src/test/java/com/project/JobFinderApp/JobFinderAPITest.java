package com.project.JobFinderApp;

import com.project.JobFinderApp.util.JobFinderAPI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;

/**
 * Classe che si occupa di testare il metodo estraiValori della classe JobFinderAPI.
 */


public class JobFinderAPITest {

    private JobFinderAPI api;
    private JSONArray response;


    /**
     * Inizializza il JSONArray response come una response di Findwork.
     */

    @BeforeEach
    void setUp() {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        array.add("aws");
        array.add("sql");
        object.put("id","103480");
        object.put("role","US Startup Point Pickup is seeking an experienced hands-on Engineering Manager");
        object.put("company_name","Point Pickup Inc");
        object.put("company_num_employees",null);
        object.put("employment_type",null);
        object.put("location","San Francisco");
        object.put("remote",true);
        object.put("logo","https://we-work-remotely.imgix.net/logos/0074/2666/logo.gif?ixlib=rails-4.0.0&w=50&h=50&dpr=2&fit=fill&auto=compress");
        object.put("url","https://findwork.dev/103480/us-startup-point-pickup-is-seeking-an-experienced-hands-on-engineering-manager-at-point-pickup-inc");
        object.put("text","<div><strong>Qualifications</strong></div><div><br></div><div>Leadership:</div><ul><li>At least 6 years of experience managing 10+ developers</li><li>Effective delegation</li><li>Interpersonal skills to manage a team of engineers</li><li>Ability to give constructive criticism</li><li>Hands-On Leadership</li></ul><div><br></div><div>Technical Skills:</div><ul><li>6 years of working experience in C# &amp; SQL Databases</li><li>Experience with architecting solutions</li></ul><div>General:</div><ul><li>High level of spoken English</li><li>Dedicated workspace for remote working</li><li>Team player with strong communication and organizational skills</li><li>Proactive attitude</li><li>High level of attention to detail.</li><li>Very organized</li><li>Self-motivated</li><li>Ability to participate in meetings during US Hours.</li></ul><div><br></div><div><strong>Advantages</strong></div><div>- Domain-Driven Design</div><div>- AWS Stack</div><div><br></div><div><strong>Time Zone and working schedule:</strong></div><div>The position is remote and permanent (monthly salary). &nbsp;</div><div>Candidates should be between GMT and GMT + 5 timezone.&nbsp;</div><div>To apply, you MUST include the timezone you will be working from.</div><div><br><br></div>");
        object.put("date_posted","2022-01-18T17:14:16Z");
        object.put("keywords",array);
        object.put("source","Weworkremotely");
        JSONArray appArr = new JSONArray();
        appArr.add(object);
        JSONObject obResult = new JSONObject();
        obResult.put("results",appArr);
        this.response = new JSONArray();
        response.add(obResult);
    }


    /**
     *  Metodo per resettare ciò che è stato inizializzato in setUp().
     */

    @AfterEach
    void tearDown() {}

    /**
     * Test per la corretta estrazione dei parametri dalla response di Findwork.
     */

    @Test
    public void testEstraiValori() {
        setUp();
        this.api = new JobFinderAPI();
        JSONArray toCompare = new JSONArray();
        JSONObject ob = new JSONObject();
        JSONArray arrayToAdd = new JSONArray();
        arrayToAdd.add("aws");
        arrayToAdd.add("sql");
        ob.put("Lavoro da remoto","Disponibile");
        ob.put("Nome della compagnia","Point Pickup Inc");
        ob.put("Tipo di contratto","part time");
        ob.put("Località","San Francisco");
        ob.put("Linguaggi",arrayToAdd);
        ob.put("Data annuncio","2022-01-18");
        ob.put("Source","Weworkremotely");
        toCompare.add(ob);
        assertEquals(toCompare.toString(), this.api.estraiValori(this.response).toString());
    }
}
