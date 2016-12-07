package io.swagger.api;

import io.swagger.model.Cards;

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

@Api(value = "fetch-cards", description = "the fetch-cards API")
public interface FetchCardsApi {

    @ApiOperation(value = "Fetch Card details for the customer", notes = "Fetches the card details for the given customer id", response = Cards.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Card Details succesfully updated", response = Cards.class),
        @ApiResponse(code = 404, message = "Card does not exist.", response = Cards.class) })
    @RequestMapping(value = "/fetch-cards/{Customer_ID}",
        method = RequestMethod.GET)
    ResponseEntity<?> fetchCardsForCustomer( Integer custId);

}
