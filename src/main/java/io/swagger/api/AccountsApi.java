package io.swagger.api;

import io.swagger.model.AccountDetails;
import io.swagger.model.Accounts;

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

@Api(value = "account", description = "the accounts API")
public interface AccountsApi {

	@ApiOperation(value = "Creates an account", notes = "Adds a new account to the Finkit system.", response = Void.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Account succesfully created.", response = Void.class),
			@ApiResponse(code = 400, message = "Error creating the account", response = Void.class) })
	@RequestMapping(value = "/account", method = RequestMethod.POST)
	ResponseEntity<?> createAccount(
			@ApiParam(value = "The account to be created.") @RequestBody AccountDetails accountDetails);

	@ApiOperation(value = "Gets the account details for the given account number", notes = "Returns teh account details for the given account number", response = AccountDetails.class, tags = {})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "The required account details", response = AccountDetails.class),
			@ApiResponse(code = 404, message = "Account does not exist.", response = AccountDetails.class) })
	@RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
	//ResponseEntity<?> getAccountDetails(@ApiParam(value = "The person's accountNumber", required = true) @PathVariable("accountNumber") Integer accountNumber);
	ResponseEntity<?> getAccountDetails(String id);
	@ApiOperation(value = "Gets account details", notes = "Returns a list containing all account details", response = Accounts.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "A list of Accounts", response = Accounts.class) })
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	ResponseEntity<?> getAllAccounts();

}
