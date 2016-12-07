package io.swagger.api;

import io.swagger.model.CardDetails;

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

@Api(value = "update-card", description = "the update-card API")
public interface UpdateCardApi {

    @ApiOperation(value = "Card details update", notes = "Updates the card details for the given cardNumber", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Card Details succesfully updated", response = Void.class),
        @ApiResponse(code = 404, message = "Card does not exist.", response = Void.class) })
    @RequestMapping(value = "/update-card/{cardNumber}",
        method = RequestMethod.PUT)
    ResponseEntity<?> updateCardDetails(@RequestBody CardDetails cardDetails, @PathVariable String id);
}
