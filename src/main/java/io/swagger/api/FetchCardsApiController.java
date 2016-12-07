package io.swagger.api;

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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-01T18:09:28.587+05:30")

@SpringBootApplication
@RestController
@RequestMapping("/fetch-cards")
public class FetchCardsApiController implements FetchCardsApi {

    @Autowired
    private CardRepository repository;

    @RequestMapping(value = "{custId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> fetchCardsForCustomer(@PathVariable Integer custId) {
        List<CardDetails> cardDetails = null;
       /* try {
            cardDetails = repository.findByCustomerId(customer_Id);
        } catch (DocumentNotFoundException ex) {
            return new ResponseEntity<ApplicationError>(
                    new ApplicationError(HttpStatus.NOT_FOUND.value(), "specified Customer_ID does not exist"),
                    HttpStatus.NOT_FOUND);
        }*/
        return new ResponseEntity<List<CardDetails>>(cardDetails, HttpStatus.OK);

    }

}
