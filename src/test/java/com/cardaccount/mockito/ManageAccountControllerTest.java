package com.cardaccount.mockito;

import io.swagger.api.ApplicationError;
import io.swagger.api.ManageAccountApiController;
import io.swagger.model.AccountDetails;
import io.swagger.model.AccountRepository;
import junit.framework.Assert;
import org.ektorp.DocumentNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

/**
 * Created by Sriram Sundararajan on 12/7/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ManageAccountControllerTest {
	private final AccountDetails validAccountDetails = new AccountDetails();
	private final AccountDetails toBeUpdatedAccountDetails = new AccountDetails();

	// This accountId will be treated as a valid account Id for this unit test program
	private final String validAccountId = "1000";
	// This accountId will be treated as a inValid AccountId for this unit test program
	private final String inValidAccountId = "9999";

	@InjectMocks
	ManageAccountApiController manageAccountApiController = new  ManageAccountApiController();

	// The class whose invocations need to be stubbed
	@Mock
	private AccountRepository repository;

	// The method to initialize the mock objects
	@Before
	public void setUp() {
		// Setting up the details for a valid account detail
		validAccountDetails.setAccountNumber(1000);
		validAccountDetails.setAccountType("Savings");
		validAccountDetails.setUserName("Arjun");
		validAccountDetails.setMobileNumber(9790931623l);
		validAccountDetails.setAddress("Chennai");

		toBeUpdatedAccountDetails.setAccountNumber(1000);
		toBeUpdatedAccountDetails.setAccountType("Current");
		toBeUpdatedAccountDetails.setUserName("Sriram");
		toBeUpdatedAccountDetails.setMobileNumber(9890931623l);
		toBeUpdatedAccountDetails.setAddress("Pune");

		/**
		 * Setting up the mock invocations with responses stubbed/mocked
		 * Ex : When the method repository.get(String accountId) is called
		 * with a value of validAccountId=1000; the response will be mocked
		 * with the validAccountDetails object
		 */
		Mockito.doNothing().when(repository).remove(toBeUpdatedAccountDetails);
		when(repository.get(validAccountId)).thenReturn(validAccountDetails);
		when(repository.get(inValidAccountId)).thenThrow(new DocumentNotFoundException("No Account found"));

	}

	@Test
	public void testUpdateAccountsWithValidAccountId() {
		ResponseEntity response = manageAccountApiController.deleteAccountDetails(validAccountId);

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateAccountsWithInValidAccountId() {
		ResponseEntity response = manageAccountApiController.deleteAccountDetails(inValidAccountId);

		String actualErrorMessage = "";
		if (response.getBody() instanceof ApplicationError) {
			ApplicationError apiException = (ApplicationError)response.getBody();
			actualErrorMessage = apiException.getMessage();
		}

		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assert.assertEquals("ID to be deleted not found", actualErrorMessage);
	}
}
