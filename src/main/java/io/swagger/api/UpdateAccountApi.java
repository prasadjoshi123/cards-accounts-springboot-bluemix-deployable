package io.swagger.api;

import io.swagger.model.AccountDetails;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-12-02T10:00:02.459+05:30")

@Api(value = "update-account", description = "the update-account API")
public interface UpdateAccountApi {

    @ApiOperation(value = "Account details update", notes = "Updates the account details for the given accountNumber", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Account Details succesfully updated", response = Void.class),
        @ApiResponse(code = 404, message = "Account does not exist.", response = Void.class) })
    @RequestMapping(value = "/update-account/{accountNumber}",
        method = RequestMethod.PUT)
    ResponseEntity<?> updateAccountDetails(@RequestBody AccountDetails accountDetails, @PathVariable String id);
}
