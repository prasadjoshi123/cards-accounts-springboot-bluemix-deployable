package io.swagger.api;

import io.swagger.model.CardDetails;

import io.swagger.annotations.*;

import io.swagger.model.CardRepository;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static javax.servlet.SessionTrackingMode.URL;

//import com.cloudant.client.api.CloudantClient;
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-01T18:09:28.587+05:30")

@SpringBootApplication
@RestController
@RequestMapping("/fetch-card-details")
public class FetchCardDetailsApiController implements FetchCardDetailsApi {
    @Autowired
    private CardRepository repository;

    @RequestMapping(method = RequestMethod.GET, value = "{cardNumber}", consumes = "application/json")
    public ResponseEntity<?> fetchCardsDetailsById(@PathVariable String cardNumber) {
        String URL ="http://localhost:8080/card_db/_design/CardDetails/_search/search_card_details?q=cardNumber:"+cardNumber;
        RestTemplate restTemplate = new RestTemplate();
        String card = restTemplate.getForObject(URL, String.class);
        return new ResponseEntity<String>(card, HttpStatus.OK);
    }

}