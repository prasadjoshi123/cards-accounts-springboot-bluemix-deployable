package io.swagger.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.configuration.CloudantBinding;
import io.swagger.model.AccountDetails;

import io.swagger.annotations.*;

import io.swagger.model.AccountRepository;
import io.swagger.utility.Utility;
import org.ektorp.DocumentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-12-02T10:00:02.459+05:30")
@Controller
@RequestMapping("update-account")
public class UpdateAccountApiController implements UpdateAccountApi {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired private AccountRepository repository;
	@Autowired CloudantBinding cloudantBinding;
	@Autowired RestTemplate restTemplate;
	@Value("${accounts.search.url}")
	private String searchAccountURL;

	@RequestMapping(method = RequestMethod.PUT, value = "{accountNumber}", consumes = "application/json") public ResponseEntity<?> updateAccountDetails(
			@RequestBody AccountDetails accountDetails, @PathVariable String accountNumber) {
		logger.info("Updating Account Details for "+accountNumber+"...");

		AccountDetails accountDetails1 = null;
		String id = null;
		try {
			String URL = "http://" + cloudantBinding.getHost() + ":" + cloudantBinding.getPort() + searchAccountURL + accountNumber;
			validateAccountDetails(accountNumber);
			String accountDetailsString = restTemplate.getForObject(URL, String.class);
			id = getDocId(accountDetailsString);
			accountDetails1 = repository.get(id);
		} catch (ApiException ae) {
			return new ResponseEntity<ApplicationError>(new ApplicationError(ae.getCode(), ae.getMessage()),
					HttpStatus.NOT_FOUND);
		} catch (DocumentNotFoundException ex) {
			return new ResponseEntity<ApplicationError>(
					new ApplicationError(HttpStatus.NOT_FOUND.value(), "Account Number to be updated not found."),
					HttpStatus.NOT_FOUND);
		} catch (IOException ex) {
			return new ResponseEntity<ApplicationError>(
					new ApplicationError(HttpStatus.NOT_FOUND.value(), "Account Number to be updated not found."),
					HttpStatus.NOT_FOUND);
		}
		accountDetails1.setAccountNumber(accountDetails.getAccountNumber());
		accountDetails1.setAccountStatus(accountDetails.getAccountStatus());
		accountDetails1.setAccountType(accountDetails.getAccountType());
		accountDetails1.setAddress(accountDetails.getAddress());
		accountDetails1.setCustomerId(accountDetails.getCustomerId());
		accountDetails1.setMobileNumber(accountDetails.getMobileNumber());
		accountDetails1.setUserName(accountDetails.getUserName());
		accountDetails1.setAccountBalance(accountDetails.getAccountBalance());

		repository.update(accountDetails1);
		logger.info("Updated Account details successfully for "+accountNumber+".");
		return new ResponseEntity<AccountDetails>(accountDetails1, HttpStatus.OK);
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