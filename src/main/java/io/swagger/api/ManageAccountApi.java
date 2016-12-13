package io.swagger.api;


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

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-01T18:09:28.587+05:30")

@Api(value = "manage-account", description = "the manage-account API")
public interface ManageAccountApi {

    @ApiOperation(value = "Account details delete", notes = "Deletes the account details for the given accountNumber", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Account succesfully deleted", response = Void.class),
        @ApiResponse(code = 404, message = "Account does not exist.", response = Void.class) })
    @RequestMapping(value = "/manage-account/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<?> deleteAccountDetails(@PathVariable String id);

}
