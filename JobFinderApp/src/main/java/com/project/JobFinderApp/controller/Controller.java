package com.project.JobFinderApp.controller;

import com.project.JobFinderApp.exception.DataException;
import com.project.JobFinderApp.exception.CityException;
import com.project.JobFinderApp.exception.ParamException;
import com.project.JobFinderApp.service.Service;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 *  Classe che implementa il controller,esso funge da intermediario tra utente e l'applicazione, permettendo all'utente
 *  di effettuare le richieste che desidera.
 *
 */

@RestController
public class Controller {

    @Autowired
    private Service service;

    /**
     * La seguente rotta permette all'utente di consultare i lavori disponibili nelle citta' richieste.
     *
     * @param citta JSONObject contenente le localita' delle quali si vogliono conoscere i lavori.
     * @return JSONArray contenente informazioni sui lavori delle citta' indicate.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    @PostMapping("/jobs")
    public JSONArray getByLocations(@RequestBody JSONObject citta) throws CityException {
        return service.chiamataPiuLocation(citta);
    }

    /**
     * La seguente rotta mostra all'utente un set di cinque citta' preimpostate, con il relativo di numero di lavori
     * presenti.
     *
     * @return JSONObject con le citta' consigliate ed il numero di lavori in tali localita'.
     * @throws IOException Eccezione lanciata qualora ci sia un problema di input/output.
     * @throws ParseException Eccezione che viene lanciata qualora ci siano eventuali problemi nel parsing.
     */

    @GetMapping("/locationSuggestion")
    public JSONObject locationSuggestion() throws IOException, ParseException {
        return service.suggerisciLocation();
    }

    /**
     * La seguente rotta permette all'utente di consultare i lavori, filtrati per tipologia di contratto, presenti nelle
     * citta' richieste.
     * @param citta JSONObject contenente le localita' delle quali si vogliono conoscere i lavori.
     * @param contratto Stringa del tipo di contratto (part-time/full-time) sulla quale verrano filtrati i lavori.
     * @return JSONArray delle informazioni dei lavori filtrati per tipologia di contratto.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro contratto non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    @PostMapping("/jobsByContract")
    public JSONArray getByEmployment(@RequestBody JSONObject citta, @RequestParam String contratto) throws ParamException, CityException {
        return service.chiamataFiltrataPerContratto(citta, contratto);
    }

    /**
     * La seguente rotta permette all'utente di consultare i lavori, filtrati per source, presenti nelle citta' richieste.
     *
     * @param citta JSONObject contenente le localita' delle quali si vogliono conoscere i lavori.
     * @param source Stringa per il filtraggio dei lavori a seconda del source inserito.
     * @return JSONArray delle informazioni dei lavori filtrati per source.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    @PostMapping("/jobsBySource")
    public JSONArray getBySource(@RequestBody JSONObject citta, @RequestParam String source) throws ParamException, CityException {
        return service.chiamataFiltrataPerSource(citta, source);
    }

    /**
     * La seguente rotta permette all'utente di consultare i lavori, filtrati per linguaggio di programmazione, presenti
     * nelle citta' richieste.
     *
     * @param citta JSONObject contenente le localita' delle quali si vogliono conoscere i lavori.
     * @param linguaggio Stringa per il filtraggio dei lavori a seconda del linguaggio scelto.
     * @return JSONArray delle informazioni dei lavori filtrati per linguaggio di programmazione.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    @PostMapping("/jobsByLanguage")
    public JSONArray getByLanguage(@RequestBody JSONObject citta, @RequestParam String linguaggio) throws ParamException, CityException {
        return service.chiamataFiltrataPerLinguaggio(citta, linguaggio);
    }

    /**
     * La seguente rotta permette all'utente di consultare delle statistiche generali sulle citta' richieste.
     *
     * @param citta JSONObject contenente le localita' delle quali si vogliono conoscere le statistiche sui lavori.
     * @return JSONArray contente statistiche riguardanti ogni localita' inserita.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    @PostMapping("/stats")
    public JSONArray stats(@RequestBody JSONObject citta) throws CityException {
        return service.statisticheGenerali(citta);
    }

    /**
     * La seguente rotta permette all'utente di consultare le statistiche, filtrate per source, delle citta' scelte.
     *
     * @param citta JSONObject contente le località delle quali si vogliono conoscere le statistiche filtrate.
     * @param source Stringa per il filtraggio delle statistiche a seconda del source.
     * @return JSONArray contente statistiche, filtrate per source, riguardanti ogni localita' inserita.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro source non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    @PostMapping("/statsBySource")
    public JSONArray statsBySource(@RequestBody JSONObject citta, @RequestParam String source) throws ParamException, CityException {
        return service.statisticheSource(citta, source);
    }

    /**
     * La seguente rotta permette all'utente di consultare le statistiche, filtrate per data dell'annuncio, delle citta'
     * scelte.
     *
     * @param citta JSONObject contente le località delle quali si vogliono conoscere le statistiche filtrate.
     * @param data Stringa per il filtraggio delle statistiche a seconda della data in cui e' stato postato l'annuncio.
     * @return JSONArray contente statistiche, filtrate per data di annuncio, riguardanti ogni localita' inserita.
     * @throws DataException Eccezione lanciata qualora il parametro data non sia dela formato [yyyy-mm-dd].
     * @throws ParamException Eccezione che viene lanciata qualora il parametro data non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    @PostMapping("/statsByData")
    public JSONArray statsByData(@RequestBody JSONObject citta, @RequestParam String data) throws DataException, ParamException, CityException {
        return service.statisticheData(citta, data);
    }

    /**
     * La seguente rotta permette all'utente di consultare le statistiche, filtrate per possibilita' di lavoro da remoto,
     * delle citta' inserite.
     *
     * @param citta JSONObject contente le località delle quali si vogliono conoscere le statistiche filtrate.
     * @param remoto Stringa del lavoro da remoto (Disponibile/Non disponibile) usate per il filtraggio delle statistiche.
     * @return JSONArray contente statistiche, filtrate per possibilita' di lavoro da remoto, riguardanti ogni localita' inserita.
     * @throws ParamException Eccezione che viene lanciata qualora il parametro remoto non venga inserito.
     * @throws CityException Eccezione lanciata se il JSONObject risulta vuoto o se le citta al suo interno non sono
     *         presenti all'interno di FindWork.
     */

    @PostMapping("/statsByRemote")
    public JSONArray statsByRemote(@RequestBody JSONObject citta, @RequestParam String remoto) throws ParamException, CityException {
        return service.statisticheRemoto(citta, remoto);
    }

}

