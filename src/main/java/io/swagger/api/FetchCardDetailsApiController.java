package io.swagger.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.configuration.CloudantBinding;
import io.swagger.model.CardDetails;

import io.swagger.annotations.*;

import io.swagger.model.CardRepository;
import org.ektorp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.handler.MessageContext;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static javax.servlet.SessionTrackingMode.URL;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-01T18:09:28.587+05:30")

@SpringBootApplication
@RestController
@RequestMapping("/fetch-card-details")
public class FetchCardDetailsApiController implements FetchCardDetailsApi{
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CardRepository repository;
    @Autowired
    CloudantBinding cloudantBinding;

    String id = null;
    @RequestMapping(value = "/{cardNumber}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> fetchCardsDetailsById(@PathVariable String cardNumber) {
        logger.info("Retriving Card Details for "+cardNumber+"...");
        CardDetails cardDetails=new CardDetails();
        try {
//            String URL = "http://" + cloudantBinding.getHost() + ":" + cloudantBinding.getPort() + "/cards_accounts_db/_design/CardDetails/_search/search_card_details?q=cardNumber:" + cardNumber;
            String URL = "http://" + cloudantBinding.getHost() + "/cards_accounts_db/_design/CardDetails/_search/search_card_details?q=cardNumber:" + cardNumber;

            RestTemplate restTemplate = new RestTemplate();
            String accountDetailsString = restTemplate.getForObject(URL, String.class);
            id=getDocId(accountDetailsString);
            cardDetails = repository.get(id);
        }catch (IOException ex) {
            return new ResponseEntity<ApplicationError>(
                    new ApplicationError(HttpStatus.NOT_FOUND.value(), "Card Number not found"),
                    HttpStatus.NOT_FOUND);
        }
        logger.info("Retrived Card Details successfully for Card Number "+cardNumber+".");
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