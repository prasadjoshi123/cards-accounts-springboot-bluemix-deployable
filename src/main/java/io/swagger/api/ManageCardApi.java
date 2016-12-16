package io.swagger.api;


import io.swagger.annotations.*;
import io.swagger.model.CardDetails;
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

@Api(value = "manage-card", description = "the manage-card API")
public interface ManageCardApi {

    @ApiOperation(value = "Card details delete", notes = "Deletes the card details for the given cardNumber", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Card Details succesfully deleted", response = Void.class),
        @ApiResponse(code = 404, message = "Card does not exist.", response = Void.class) })
    @RequestMapping(value = "/manage-card/{cardNumber}",
        method = RequestMethod.DELETE)
    ResponseEntity<?> deleteCardDetails(@PathVariable String cardNumber);

}
