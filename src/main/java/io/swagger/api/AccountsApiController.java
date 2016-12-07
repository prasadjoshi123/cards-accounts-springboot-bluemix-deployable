package io.swagger.api;

import io.swagger.model.*;
import io.swagger.utility.Utility;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.UpdateConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiParam;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-01T18:09:28.587+05:30")

@RestController public class AccountsApiController implements AccountsApi {

	@Autowired private AccountRepository repository;

	@RequestMapping(value = "/account", method = RequestMethod.POST) public ResponseEntity<?> createAccount(
			@ApiParam(value = "The account to be created.") @RequestBody AccountDetails accountDetails) {
		try {
			/**
			 * Making Username,mobile number & address mandatory
			 */
			validateCreateAccount(accountDetails);
			repository.add(accountDetails);

		} catch (ApiException ae) {
			return new ResponseEntity<ApplicationError>(new ApplicationError(ae.getCode(), ae.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (UpdateConflictException ex) {
			return new ResponseEntity<ApplicationError>(new ApplicationError(HttpStatus.BAD_REQUEST.value(),
					"update conflicted, add was aborted. Please check your payload"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<AccountDetails>(accountDetails, HttpStatus.OK);

	}

	//@RequestMapping(value = "/accounts/{accountNumber}", method = RequestMethod.GET)
	@RequestMapping(value = "/account/{id}", method = RequestMethod.GET) public ResponseEntity<?> getAccountDetails(
			@PathVariable String id) {
		//public ResponseEntity<?> getAccountDetails(@ApiParam(value = "The person's accountNumber",required=true ) @PathVariable("accountNumber") Integer accountNumber) {
		AccountDetails accountDetails = new AccountDetails();
		try {
			validateGetAccountDetails(id);
			accountDetails = repository.get(id);
		} catch (ApiException ae) {
			return new ResponseEntity<ApplicationError>(new ApplicationError(ae.getCode(), ae.getMessage()),
					HttpStatus.NOT_FOUND);
		} catch (DocumentNotFoundException ex) {
			return new ResponseEntity<ApplicationError>(
					new ApplicationError(HttpStatus.NOT_FOUND.value(), "specified ID does not exist"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AccountDetails>(accountDetails, HttpStatus.OK);
	}

	@RequestMapping(value = "/account", method = RequestMethod.GET) public ResponseEntity<?> getAllAccounts() {
		List<AccountDetails> allAccounts = repository.getAll();
		if (allAccounts == null || allAccounts.isEmpty())
			return new ResponseEntity<ApplicationError>(
					new ApplicationError(HttpStatus.NOT_FOUND.value(), "no documents found"), HttpStatus.NOT_FOUND);
		return new ResponseEntity<List<AccountDetails>>(allAccounts, HttpStatus.OK);

	}

	public void validateCreateAccount(AccountDetails accountDetails) throws ApiException {
		if (Utility.isNullOrEmpty(accountDetails.getUserName())) {
			throw new ApiException(405, "Invalid Account Details - User Name Missing");
		}
		if (accountDetails.getMobileNumber() == 0) {
			throw new ApiException(405, "Invalid Account Details - Mobile Number Missing");
		}
		if (Utility.isNullOrEmpty(accountDetails.getAddress())) {
			throw new ApiException(405, "Invalid Account Details - Address Missing");
		}
	}

	public void validateGetAccountDetails(String accountId) throws ApiException {
		if (Utility.isNullOrEmpty(accountId)) {
			throw new ApiException(405, "Invalid Account Details - Account Number Missing");
		}

	}

}
