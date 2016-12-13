package io.swagger.api;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;

import io.swagger.configuration.CloudantBinding;
import io.swagger.model.CardDetails;
import io.swagger.model.CardRepository;
import org.ektorp.DocumentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import java.io.IOException;
import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-01T18:09:28.587+05:30")

@SpringBootApplication
@RestController
@RequestMapping("/manage-card")
public class ManageCardApiController implements ManageCardApi {

    @Autowired
    private CardRepository repository;
    @Autowired
    CloudantBinding cloudantBinding;

    @RequestMapping(method = RequestMethod.DELETE,value = "{cardNumber}")
    public ResponseEntity<?> deleteCardDetails(@PathVariable String cardNumber) {
        CardDetails cardDetails = null;
        String stringToParse = null;
        try {
            String URL ="http://"+cloudantBinding.getHost()+":"+cloudantBinding.getPort()+"/card_db/_design/CardDetails/_search/search_card_details?q=cardNumber:"+cardNumber;
            RestTemplate restTemplate = new RestTemplate();
            stringToParse = restTemplate.getForObject(URL, String.class);
            String id=getDocId(stringToParse);
            repository.remove(repository.get(id));
        } catch (DocumentNotFoundException ex) {
            return new ResponseEntity<ApplicationError>(
                    new ApplicationError(HttpStatus.NOT_FOUND.value(), "document to be Deleted not found"),
                    HttpStatus.NOT_FOUND);
        } catch (IOException ex) {
            return new ResponseEntity<ApplicationError>(
                    new ApplicationError(HttpStatus.NOT_FOUND.value(), "document to be Deleted not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
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
