package io.swagger.api;

import io.swagger.model.CardDetails;

import io.swagger.annotations.*;

import io.swagger.model.CardRepository;
import org.ektorp.DocumentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-12-02T10:00:02.459+05:30")

@SpringBootApplication
@RestController
@RequestMapping("/update-card")
public class UpdateCardApiController implements UpdateCardApi {
    @Autowired
    private CardRepository repository;

    @RequestMapping(method = RequestMethod.PUT, value = "{id}", consumes = "application/json")
    public ResponseEntity<?> updateCardDetails(@RequestBody CardDetails cardD, @PathVariable String id) {
        CardDetails cardDetails = null;
        try {
            cardDetails = repository.get(id);
        } catch (DocumentNotFoundException ex) {
            return new ResponseEntity<ApplicationError>(
                    new ApplicationError(HttpStatus.NOT_FOUND.value(), "document to be updated not found"),
                    HttpStatus.NOT_FOUND);
        }
        System.out.println("uuuuuuuuuuuuuuuu"+cardD.getCardNumber());
        cardDetails.setCardNumber(cardD.getCardNumber());
        cardDetails.setCardApplyMode(cardD.getCardApplyMode());
        cardDetails.setCardStatus(cardD.getCardStatus());
        cardDetails.setCardType(cardD.getCardType());
        cardDetails.setCustId(cardD.getCustId());
        cardDetails.setExpiryDate(cardD.getExpiryDate());
        cardDetails.setStartDate(cardD.getStartDate());
        repository.update(cardDetails);
        return new ResponseEntity<CardDetails>(cardDetails, HttpStatus.OK);
    }
}
