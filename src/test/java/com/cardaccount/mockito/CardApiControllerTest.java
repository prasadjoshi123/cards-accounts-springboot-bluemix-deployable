package com.cardaccount.mockito;

import io.swagger.api.ApplicationError;
import io.swagger.api.CardApiController;
import io.swagger.model.AccountDetails;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by Sriram Sundararajan on 12/7/2016.
 */
@RunWith(MockitoJUnitRunner.class) public class CardApiControllerTest {
	private final CardDetails validCardDetails = new CardDetails();
	private final List<CardDetails> allCards = new ArrayList<>();
	// This accountId will be treated as a valid account Id for this unit test program
	private final Integer validCardNumber = 500;

	private final Integer validCustomerId = 1;

	// The class to be tested , where the mock invocations need to be stubbed
	@InjectMocks CardApiController cardApiController = new CardApiController();

	// The class whose invocations need to be stubbed
	@Mock private CardRepository repository;

	// The method to initialize the mock objects
	@Before public void setUp() {
		// Setting up the details for a valid account detail

		validCardDetails.setCardNumber(500);
		validCardDetails.setCardType("Debit");
		validCardDetails.setCardApplyMode("Enabled");
		validCardDetails.setCardStatus("Active");
		validCardDetails.setCustId(1);
		validCardDetails.setExpiryDate("21 Dec 2021");
		allCards.add(validCardDetails);

		/**
		 * Setting up the mock invocations with responses stubbed/mocked
		 * Ex : When the method repository.get(String cardNumber) is called
		 * with a value of validCardNumber=500; the response will be mocked
		 * with the validCardDetails object
		 */
		when(repository.get(validCardNumber.toString())).thenReturn(validCardDetails);

		/** Do nothing when the method is called,usually used for a method
		 that returns void **/
		Mockito.doNothing().when(repository).add(validCardDetails);

	}

	@Test public void testGetAllCards() {
		when(repository.getAll()).thenReturn(allCards);
		ResponseEntity response = cardApiController.getAllCards();

		int cardsListSize = 0;
		if (response.getBody() != null) {
			List<CardDetails> cardDetailsResponse = (List<CardDetails>)response.getBody();
			if (cardDetailsResponse != null && !cardDetailsResponse.isEmpty()) {
				cardsListSize = cardDetailsResponse.size();
			}
		}

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(allCards.size(), cardsListSize);

	}

	@Test public void testGetAllAccountsForEmptyResults() {
		when(repository.getAll()).thenReturn(null);

		ResponseEntity response = cardApiController.getAllCards();

		int cardsListSize = 100;
		if (response.getBody() instanceof ApplicationError) {
			cardsListSize = 0;
		}

		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assert.assertEquals(0, cardsListSize);

	}

	@Test public void testCreateCardWithoutCardNumber() {
		CardDetails invalidCardDetails = new CardDetails();
		invalidCardDetails.setCardNumber(0);
		invalidCardDetails.setCardType("Debit");
		invalidCardDetails.setCustId(validCustomerId);

		ResponseEntity response = cardApiController.createCard(invalidCardDetails);

		String actualErrorMessage = "";
		if (response.getBody() instanceof ApplicationError) {
			ApplicationError apiException = (ApplicationError)response.getBody();
			actualErrorMessage = apiException.getMessage();
		}

		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertEquals("Invalid Card Details - Card Number Missing", actualErrorMessage);

	}

	@Test public void testCreateCardWithoutCustomerId() {
		CardDetails invalidCardDetails = new CardDetails();
		invalidCardDetails.setCardNumber(validCardNumber);
		invalidCardDetails.setCardType("Debit");
		invalidCardDetails.setCustId(0);

		ResponseEntity response = cardApiController.createCard(invalidCardDetails);

		String actualErrorMessage = "";
		if (response.getBody() instanceof ApplicationError) {
			ApplicationError apiException = (ApplicationError)response.getBody();
			actualErrorMessage = apiException.getMessage();
		}

		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertEquals("Invalid Card Details - Customer ID Missing", actualErrorMessage);

	}

	@Test public void testCreateCardWithoutCardType() {
		CardDetails invalidCardDetails = new CardDetails();
		invalidCardDetails.setCardNumber(validCardNumber);
		invalidCardDetails.setCardType(null);
		invalidCardDetails.setCustId(validCustomerId);

		ResponseEntity response = cardApiController.createCard(invalidCardDetails);

		String actualErrorMessage = "";
		if (response.getBody() instanceof ApplicationError) {
			ApplicationError apiException = (ApplicationError)response.getBody();
			actualErrorMessage = apiException.getMessage();
		}

		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		Assert.assertEquals("Invalid Card Details - Card Type Missing", actualErrorMessage);

	}

	@Test public void testCreateCardWithValidCardDetails() {
		ResponseEntity response = cardApiController.createCard(validCardDetails);

		String actualCardStatus = null;
		String actualExpiryDate = null;
		if (response.getBody() instanceof CardDetails) {
			CardDetails cardDetailsResponse = (CardDetails)response.getBody();
			actualCardStatus = cardDetailsResponse.getCardStatus();
			actualExpiryDate = cardDetailsResponse.getExpiryDate();
		}

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(validCardDetails.getCardStatus(), actualCardStatus);
		Assert.assertEquals(validCardDetails.getExpiryDate(), actualExpiryDate);

	}

	@After public void tearDown() {
		// Add clean up code here
	}

}