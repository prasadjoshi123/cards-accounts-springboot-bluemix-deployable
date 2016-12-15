package io.swagger.api;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;

import io.swagger.configuration.CloudantBinding;
import io.swagger.model.AccountDetails;
import io.swagger.model.AccountRepository;
import io.swagger.utility.Utility;
import org.ektorp.DocumentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-01T18:09:28.587+05:30")

@Controller
@RequestMapping("/manage-account")

public class ManageAccountApiController implements ManageAccountApi {
    @Autowired
    private AccountRepository acountRepository;
    @Autowired
    CloudantBinding cloudantBinding;
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.DELETE, value = "{accountNumber}")
    public ResponseEntity<?> deleteAccountDetails(@PathVariable String accountNumber) {
        AccountDetails accountDetails =null;
        String id=null;
        try {
            validateAccountDetails(accountNumber);
            String URL ="http://" + cloudantBinding.getHost() + ":" + cloudantBinding.getPort() +"/account_db/_design/AccountDetails/_search/search_account_details?q=accountNumber:"+accountNumber;

            String accountDetailsString = restTemplate.getForObject(URL, String.class);
            id=getDocId(accountDetailsString);
        } catch (ApiException ae) {
            return new ResponseEntity<ApplicationError>(new ApplicationError(ae.getCode(), ae.getMessage()),
                    HttpStatus.NOT_FOUND);
        } catch (DocumentNotFoundException ex) {
            return new ResponseEntity<ApplicationError>(
                    new ApplicationError(HttpStatus.NOT_FOUND.value(), "ID to be deleted not found"),
                    HttpStatus.NOT_FOUND);
        }catch (IOException ex) {
            return new ResponseEntity<ApplicationError>(
                    new ApplicationError(HttpStatus.NOT_FOUND.value(), "document to be updated not found"),
                    HttpStatus.NOT_FOUND);
        }
        acountRepository.remove(acountRepository.get(id));
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);

    }

    public void validateAccountDetails(String accountId) throws ApiException {
        if (Utility.isNullOrEmpty(accountId)) {
            throw new ApiException(405, "Invalid Account Details - Account Number Missing");
        }

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
