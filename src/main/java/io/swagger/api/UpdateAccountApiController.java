package io.swagger.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.configuration.CloudantBinding;
import io.swagger.model.AccountDetails;

import io.swagger.annotations.*;

import io.swagger.model.AccountRepository;
import org.ektorp.DocumentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-12-02T10:00:02.459+05:30")

@Controller

@RequestMapping ("update-account")
public class UpdateAccountApiController implements UpdateAccountApi {

    @Autowired
    private AccountRepository repository;
    @Autowired
    CloudantBinding cloudantBinding;
    @RequestMapping(method = RequestMethod.PUT, value = "{accountNumber}", consumes = "application/json")
     public ResponseEntity<?> updateAccountDetails(@RequestBody AccountDetails accountDetails, @PathVariable String accountNumber) {
            AccountDetails accountDetails1 = null;
        String id=null;
            try {
                String URL ="http://" + cloudantBinding.getHost() + ":" + cloudantBinding.getPort() +"/acc_db/_design/AccountDetails/_search/search_account_details?q=accountNumber:"+accountNumber;
                RestTemplate restTemplate = new RestTemplate();
                String accountDetailsString = restTemplate.getForObject(URL, String.class);
                id=getDocId(accountDetailsString);
                accountDetails1 = repository.get(id);
            } catch (DocumentNotFoundException ex) {
                return new ResponseEntity<ApplicationError>(
                        new ApplicationError(HttpStatus.NOT_FOUND.value(), "document to be updated not found"),
                        HttpStatus.NOT_FOUND);
            }catch (IOException ex) {
                return new ResponseEntity<ApplicationError>(
                        new ApplicationError(HttpStatus.NOT_FOUND.value(), "document to be updated not found"),
                        HttpStatus.NOT_FOUND);
            }
            System.out.println("Account Number......"+accountDetails.getAccountNumber());
            accountDetails1.setAccountNumber(accountDetails.getAccountNumber());
            accountDetails1.setAccountStatus(accountDetails.getAccountStatus());
            accountDetails1.setAccountType(accountDetails.getAccountType());
            accountDetails1.setAddress(accountDetails.getAddress());
            accountDetails1.setCustomerId(accountDetails.getCustomerId());
            accountDetails1.setMobileNumber(accountDetails.getMobileNumber());
            accountDetails1.setUserName(accountDetails.getUserName());

            repository.update(accountDetails1);
            return new ResponseEntity<AccountDetails>(accountDetails1, HttpStatus.OK);
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


