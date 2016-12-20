package io.swagger.api;

import io.swagger.configuration.CloudantBinding;
import io.swagger.model.*;

import io.swagger.annotations.*;

import io.swagger.utility.Utility;
import org.ektorp.UpdateConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.apache.catalina.startup.ClassLoaderFactory.RepositoryType.URL;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-01T18:09:28.587+05:30")
@SpringBootApplication
@RestController
@RequestMapping("/card")
public class CardApiController implements CardApi {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CardRepository repository;

    @Autowired
    CloudantBinding cloudantBinding;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> createCard(@ApiParam(value = "The card to be created."  ) @RequestBody CardDetails cardDetails) {
        logger.info("Creating New Card...");

        try {
            validateCreateCard(cardDetails);
            repository.add(cardDetails);

        }catch (ApiException ae) {
            return new ResponseEntity<ApplicationError>(new ApplicationError(ae.getCode(), ae.getMessage()),
                    HttpStatus.BAD_REQUEST);
        } catch (UpdateConflictException ex) {
            return new ResponseEntity<ApplicationError>(new ApplicationError(HttpStatus.BAD_REQUEST.value(),
                    "Update conflicted, add was aborted. Please check your payload"), HttpStatus.BAD_REQUEST);
        }
        logger.info("Card Created Successfully.");
        return new ResponseEntity<CardDetails>(cardDetails,HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllCards() {
        logger.info("Retriving All Cards...");

        //String URL ="http://" + cloudantBinding.getHost() + ":" + cloudantBinding.getPort() + "/cards_accounts_db/_design/CardDetails/_view/cards_view?include_docs=true";
        String URL ="http://" + cloudantBinding.getHost() + "/cards_accounts_db/_design/CardDetails/_view/cards_view?include_docs=true";

        String cards = restTemplate.getForObject(URL, String.class);

        if (cards == null || cards.isEmpty())
            return new ResponseEntity<ApplicationError>(
                    new ApplicationError(HttpStatus.NOT_FOUND.value(), "No card documents found."), HttpStatus.NOT_FOUND);
        logger.info("Retrived All Cards successfully.");
        return new ResponseEntity<String>(cards, HttpStatus.OK);

    }
    public void validateCreateCard(CardDetails cardDetails) throws ApiException {
        if (cardDetails.getCardNumber() == 0) {
            throw new ApiException(405, "Invalid Card Details - Card Number Missing");
        }
        if (Utility.isNullOrEmpty(cardDetails.getCardType())) {
            throw new ApiException(405, "Invalid Card Details - Card Type Missing");
        }
        if (cardDetails.getCustId() == 0) {
            throw new ApiException(405, "Invalid Card Details - Customer ID Missing");
        }
    }
}
