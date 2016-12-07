package io.swagger.api;


import io.swagger.annotations.*;

import io.swagger.model.AccountDetails;
import io.swagger.model.AccountRepository;
import org.ektorp.DocumentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-01T18:09:28.587+05:30")

@Controller
@RequestMapping("/manage-account")

public class ManageAccountApiController implements ManageAccountApi {
    @Autowired
    private AccountRepository acountRepository;

//    @RequestMapping(value = "/manage-account/{accountNumber}")
    @RequestMapping(value = "/manage-account/{id}")
    public ResponseEntity<?> deleteAccountDetails(@PathVariable String id) {
        AccountDetails accountDetails =null;

        try {
            accountDetails = acountRepository.get(id);
        } catch (DocumentNotFoundException ex) {
            return new ResponseEntity<ApplicationError>(
                    new ApplicationError(HttpStatus.NOT_FOUND.value(), "ID to be deleted not found"),
                    HttpStatus.NOT_FOUND);
        }
        acountRepository.remove(acountRepository.get(id));
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);

    }

}
