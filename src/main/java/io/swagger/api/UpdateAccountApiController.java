package io.swagger.api;

import io.swagger.model.AccountDetails;

import io.swagger.annotations.*;

import io.swagger.model.AccountRepository;
import org.ektorp.DocumentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-12-02T10:00:02.459+05:30")

@Controller

@RequestMapping ("update-account")
public class UpdateAccountApiController implements UpdateAccountApi {

    @Autowired
    private AccountRepository repository;

    @RequestMapping(method = RequestMethod.PUT, value = "{id}", consumes = "application/json")
    //public ResponseEntity<?> updateAccountDetails(@ApiParam(value = "The account to be updated."  ) @RequestBody AccountDetails accountDetails) {
    public ResponseEntity<?> updateAccountDetails(@RequestBody AccountDetails accountDetails, @PathVariable String id) {
            AccountDetails accountDetails1 = null;
            try {
                accountDetails1 = repository.get(id);
            } catch (DocumentNotFoundException ex) {
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
    }


