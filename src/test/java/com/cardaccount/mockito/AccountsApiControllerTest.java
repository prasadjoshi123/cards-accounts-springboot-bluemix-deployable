package com.cardaccount.mockito;

import io.swagger.api.AccountsApiController;
import io.swagger.api.ApplicationError;
import io.swagger.configuration.CloudantBinding;
import io.swagger.model.AccountDetails;
import io.swagger.model.AccountRepository;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by Sriram Sundararajan on 12/7/2016.
 */
@RunWith(MockitoJUnitRunner.class) public class AccountsApiControllerTest {
	private final AccountDetails validAccountDetails = new AccountDetails();

	// This accountId will be treated as a valid account Id for this unit test program
	private final String validAccountId = "1000";

	// The class to be tested , where the mock invocations need to be stubbed
	@InjectMocks AccountsApiController accountsApiController = new AccountsApiController();

	// The class whose invocations need to be stubbed
	@Mock private AccountRepository repository;

	// Setting up mocked rest template services
	private final Integer validPortNumber = 8080;
	private final String validHostName = "http://ibm.finkit";

	@Mock RestTemplate restTemplate;

	@Mock CloudantBinding cloudantBinding;

	// The method to initialize the mock objects
	@Before public void setUp() {
		// Setting up the details for a valid account detail
		validAccountDetails.setAccountNumber(1000);
		validAccountDetails.setAccountType("Savings");
		validAccountDetails.setUserName("Arjun");
		validAccountDetails.setMobileNumber(9790931623l);
		validAccountDetails.setAddress("Chennai");

		/**
		 * Setting up the mock invocations with responses stubbed/mocked
		 * Ex : When the method repository.get(String accountId) is called
		 * with a value of validAccountId=1000; the response will be mocked
		 * with the validAccountDetails object
		 */
		when(repository.get(anyString())).thenReturn(validAccountDetails);

		when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(validAccountId);

		when(cloudantBinding.getHost()).thenReturn(validHostName);
		when(cloudantBinding.getPort()).thenReturn(validPortNumber);

		/** Do nothing when the method is called,usually used for a method
		 that returns void **/
		Mockito.doNothing().when(repository).add(validAccountDetails);

	}

	@Test public void testGetAllAccounts() {
		List<AccountDetails> allAccounts = new ArrayList<AccountDetails>();
		allAccounts.add(validAccountDetails);
		when(repository.getAll()).thenReturn(allAccounts);

		ResponseEntity response = accountsApiController.getAllAccounts();

		int acccountsListSize = 0;
		if (response.getBody() != null) {
			List<AccountDetails> accountDetailsResponse = (List<AccountDetails>)response.getBody();
			if (accountDetailsResponse != null && !accountDetailsResponse.isEmpty()) {
				acccountsListSize = accountDetailsResponse.size();
			}
		}

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(allAccounts.size(), acccountsListSize);

	}

	@Test public void testGetAllAccountsForEmptyResults() {
		when(repository.getAll()).thenReturn(null);

		ResponseEntity response = accountsApiController.getAllAccounts();

		int acccountsListSize = 100;
		if (response.getBody() instanceof ApplicationError) {
			acccountsListSize = 0;
		}

		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assert.assertEquals(0, acccountsListSize);

	}

	@Test public void testGetAccountDetailsWithoutAccountNumber() {
		ResponseEntity response = accountsApiController.getAccountDetails(null);
		String actualErrorMessage = "";
		if (response.getBody() instanceof ApplicationError) {
			ApplicationError apiException = (ApplicationError)response.getBody();
			actualErrorMessage = apiException.getMessage();
		}

		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assert.assertEquals("Invalid Account Details - Account Number Missing", actualErrorMessage);

	}

	@Test public void testGetAccountDetailsWithValidAccountNumber() throws IOException {
		ResponseEntity response = accountsApiController.getAccountDetails(validAccountId);

		String actualUserName = null;
		if (response.getBody() instanceof AccountDetails) {
			AccountDetails accountDetailsResponse = (AccountDetails)response.getBody();
			actualUserName = accountDetailsResponse.getUserName();
		}

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(validAccountDetails.getUserName(), actualUserName);

	}

	@Test public void testCreateAccountWithoutUserName() {
		AccountDetails invalidAccountDetails = new AccountDetails();
		invalidAccountDetails.setUserName(null);
		invalidAccountDetails.setAddress("Chennai");
		invalidAccountDetails.setMobileNumber(9790931623l);

		ResponseEntity response = accountsApiController.createAccount(invalidAccountDetails);

		String actualErrorMessage = "";
		if (response.getBody() instanceof ApplicationError) {
			ApplicationError apiException = (ApplicationError)response.getBody();
			actualErrorMessage = apiException.getMessage();
		}

		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertEquals("Invalid Account Details - User Name Missing", actualErrorMessage);

	}

	@Test public void testCreateAccountWithoutMobileNumber() {
		AccountDetails invalidAccountDetails = new AccountDetails();
		invalidAccountDetails.setUserName("Arjun");
		invalidAccountDetails.setAddress("Chennai");
		invalidAccountDetails.setMobileNumber(0l);

		ResponseEntity response = accountsApiController.createAccount(invalidAccountDetails);

		String actualErrorMessage = "";
		if (response.getBody() instanceof ApplicationError) {
			ApplicationError apiException = (ApplicationError)response.getBody();
			actualErrorMessage = apiException.getMessage();
		}

		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertEquals("Invalid Account Details - Mobile Number Missing", actualErrorMessage);

	}

	@Test public void testCreateAccountWithoutAddress() {
		AccountDetails invalidAccountDetails = new AccountDetails();
		invalidAccountDetails.setUserName("Arjun");
		invalidAccountDetails.setAddress(null);
		invalidAccountDetails.setMobileNumber(9790931623l);

		ResponseEntity response = accountsApiController.createAccount(invalidAccountDetails);

		String actualErrorMessage = "";
		if (response.getBody() instanceof ApplicationError) {
			ApplicationError apiException = (ApplicationError)response.getBody();
			actualErrorMessage = apiException.getMessage();
		}

		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertEquals("Invalid Account Details - Address Missing", actualErrorMessage);

	}

	@Test public void testCreateAccountWithValidAccountNumber() {
		ResponseEntity response = accountsApiController.createAccount(validAccountDetails);

		String actualUserName = null;
		if (response.getBody() instanceof AccountDetails) {
			AccountDetails accountDetailsResponse = (AccountDetails)response.getBody();
			actualUserName = accountDetailsResponse.getUserName();
		}

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(validAccountDetails.getUserName(), actualUserName);

	}

	@After public void tearDown() {
		// Add clean up code here
	}

}