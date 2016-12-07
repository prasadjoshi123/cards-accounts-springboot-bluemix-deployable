package io.swagger.api;


import io.swagger.annotations.*;

import io.swagger.model.CardDetails;
import io.swagger.model.CardRepository;
import org.ektorp.DocumentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-01T18:09:28.587+05:30")

@SpringBootApplication
@RestController
@RequestMapping("/manage-card")
public class ManageCardApiController implements ManageCardApi {

    @Autowired
    private CardRepository repository;

    @RequestMapping(method = RequestMethod.DELETE, consumes = "application/json",value = "{id}")
    public ResponseEntity<?> deleteCardDetails(@PathVariable String id) {
       CardDetails cardDetails =null;

        try {
            cardDetails = repository.get(id);
        } catch (DocumentNotFoundException ex) {
            return new ResponseEntity<ApplicationError>(
                    new ApplicationError(HttpStatus.NOT_FOUND.value(), "ID to be deleted not found"),
                    HttpStatus.NOT_FOUND);
        }
        repository.remove(repository.get(id));
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
