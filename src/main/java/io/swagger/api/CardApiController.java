package io.swagger.api;

import io.swagger.model.*;

import io.swagger.annotations.*;

import org.ektorp.UpdateConflictException;
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

import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-01T18:09:28.587+05:30")

@SpringBootApplication
@RestController
@RequestMapping("/card")
public class CardApiController implements CardApi {

    @Autowired
    private CardRepository repository;


    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> createCard(@ApiParam(value = "The card to be created."  ) @RequestBody CardDetails cardDetails) {
        try {
            System.out.println("................."+cardDetails.getCardNumber());
            repository.add(cardDetails);

        } catch (UpdateConflictException ex) {
            return new ResponseEntity<ApplicationError>(new ApplicationError(HttpStatus.BAD_REQUEST.value(),
                    "update conflicted, add was aborted. Please check your payload"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<CardDetails>(cardDetails,HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllCards() {

        List<CardDetails> allCards = repository.getAll();
        if (allCards == null || allCards.isEmpty())
            return new ResponseEntity<ApplicationError>(
                    new ApplicationError(HttpStatus.NOT_FOUND.value(), "no documents found"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<CardDetails>>(allCards, HttpStatus.OK);
    }
}