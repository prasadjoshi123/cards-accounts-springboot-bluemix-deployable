package com.cardaccount.mockito;

import io.swagger.api.ApplicationError;
import io.swagger.api.ManageCardApiController;
import io.swagger.api.UpdateCardApiController;
import io.swagger.configuration.CloudantBinding;
import io.swagger.model.CardDetails;
import io.swagger.model.CardRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by Sriram Sundararajan on 12/7/2016.
 */
@RunWith(MockitoJUnitRunner.class) public class UpdateCardApiControllerTest {
	private final CardDetails validCardDetails = new CardDetails();
	private final CardDetails toBeUpdatedCardDetails = new CardDetails();
	private final List<CardDetails> allCards = new ArrayList<>();
	// This accountId will be treated as a valid account Id for this unit test program
	private final String validCardNumber = "500";
	private final String inValidCardNumber = "";

	// The class to be tested , where the mock invocations need to be stubbed
	@InjectMocks UpdateCardApiController updateCardApiController = new UpdateCardApiController();

	// The class whose invocations need to be stubbed
	@Mock private CardRepository repository;

	// Setting up mocked rest template services
	private final Integer validPortNumber = 8080;
	private final String validHostName = "http://ibm.finkit";

	@Mock RestTemplate restTemplate;

	@Mock CloudantBinding cloudantBinding;

	// The method to initialize the mock objects
	@Before public void setUp() {
		// Setting up the details for a valid account detail

		validCardDetails.setCardNumber(500);
		validCardDetails.setCardType("Debit");
		validCardDetails.setCardApplyMode("Enabled");
		validCardDetails.setCardStatus("Active");
		validCardDetails.setCustId(1);
		validCardDetails.setExpiryDate("21 Dec 2021");

		toBeUpdatedCardDetails.setCardNumber(500);
		toBeUpdatedCardDetails.setCardType("Debit");
		toBeUpdatedCardDetails.setCardApplyMode("Enabled");
		toBeUpdatedCardDetails.setCardStatus("Active");

		allCards.add(validCardDetails);

		when(repository.get(anyString())).thenReturn(validCardDetails);

		/** Do nothing when the method is called,usually used for a method
		 that returns void **/
		Mockito.doNothing().when(repository).update(validCardDetails);
		when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(String.valueOf(validCardNumber));

		when(cloudantBinding.getHost()).thenReturn(validHostName);
		when(cloudantBinding.getPort()).thenReturn(validPortNumber);

	}

	@Test public void testUpdateCardsWithValidCardNumber() {
		ResponseEntity response = updateCardApiController.updateCardDetails(toBeUpdatedCardDetails, validCardNumber);

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test public void testUpdateCardsWithInValidCardNumber() {
		ResponseEntity response = updateCardApiController.updateCardDetails(toBeUpdatedCardDetails, inValidCardNumber);

		String actualErrorMessage = "";
		if (response.getBody() instanceof ApplicationError) {
			ApplicationError apiException = (ApplicationError)response.getBody();
			actualErrorMessage = apiException.getMessage();
		}

		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assert.assertEquals("Invalid Card Details - Card Number Missing", actualErrorMessage);
	}

	@After public void tearDown() {
		// Add clean up code here
	}

}