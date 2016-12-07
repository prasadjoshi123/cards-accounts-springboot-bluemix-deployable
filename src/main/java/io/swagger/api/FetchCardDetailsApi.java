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

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-01T18:09:28.587+05:30")

@Api(value = "fetch-card-details", description = "the fetch-card-details API")
public interface FetchCardDetailsApi {

    @ApiOperation(value = "Fetch Card details for given card number", notes = "Fetches the card details for the given card number", response = CardDetails.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Card Details of the given card number", response = CardDetails.class),
        @ApiResponse(code = 404, message = "Card does not exist.", response = CardDetails.class) })
    @RequestMapping(value = "/fetch-card-details/{cardNumber}",
        method = RequestMethod.GET)
    ResponseEntity<?> fetchCardsDetailsById( String cardNumber);

}
