package io.swagger.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.configuration.CloudantBinding;
import io.swagger.model.CardDetails;
import io.swagger.model.CardRepository;
import io.swagger.model.Cards;

import io.swagger.annotations.*;

import org.ektorp.DocumentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-01T18:09:28.587+05:30")

@SpringBootApplication
@RestController
@RequestMapping("/fetch-cards")
public class FetchCardsApiController implements FetchCardsApi {

    @Autowired
    private CardRepository repository;
    @Autowired
    CloudantBinding cloudantBinding;

    String id = null;
    @RequestMapping(value = "{custId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> fetchCardsForCustomer(@PathVariable Integer custId) {
        CardDetails cardDetails=new CardDetails();
        try {
            String URL = "http://"  + cloudantBinding.getHost() + ":" + cloudantBinding.getPort() + "/cards_accounts_db/_design/CardDetails/_search/search_card_details?q=custId:" + custId;
            RestTemplate restTemplate = new RestTemplate();
            String accountDetailsString = restTemplate.getForObject(URL, String.class);
            id=getDocId(accountDetailsString);
            cardDetails =  repository.get(id);
        }catch (IOException ex) {
            return new ResponseEntity<ApplicationError>(
                    new ApplicationError(HttpStatus.NOT_FOUND.value(), "Customer Id not found"),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<CardDetails>(cardDetails, HttpStatus.OK);
    }

    private String getDocId(String str) throws IOException {
        String id = null;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(str);
        JsonNode rowNode = root.path("rows");
        if (rowNode.isArray()) {
            // If this node an Arrray?
        }
        for (JsonNode node : rowNode) {
            id = node.path("id").asText();

        }
        return id;
    }




}
