package io.swagger.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.configuration.CloudantBinding;
import io.swagger.model.CardDetails;

import io.swagger.annotations.*;

import io.swagger.model.CardRepository;
import io.swagger.utility.Utility;
import org.ektorp.DocumentNotFoundException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-12-02T10:00:02.459+05:30")

@SpringBootApplication
@RestController
@RequestMapping("/update-card")
public class UpdateCardApiController
		implements UpdateCardApi {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired private CardRepository repository;
	@Autowired CloudantBinding cloudantBinding;
	@Autowired RestTemplate restTemplate;
	@Value("${cards.search.by.cardnumber.url}")
	private String searchCardURL;

	@RequestMapping(method = RequestMethod.PUT, value = "{cardNumber}", consumes = "application/json")
	public ResponseEntity<?> updateCardDetails(
			@RequestBody CardDetails cardD, @PathVariable String cardNumber) {
		logger.info("Updating Card Details for "+cardNumber+"...");
		CardDetails cardDetails = null;
		String stringToParse = null;
		try {
			validateCardDetails(cardNumber);

			String URL = "http://" + cloudantBinding.getHost() + ":" + cloudantBinding.getPort() + searchCardURL + cardNumber;
			stringToParse = restTemplate.getForObject(URL, String.class);
			String id = getDocId(stringToParse);
			cardDetails = repository.get(id);
		} catch (ApiException ae) {
			return new ResponseEntity<ApplicationError>(new ApplicationError( 404,ae.getMessage()),
					HttpStatus.NOT_FOUND);
		} catch (DocumentNotFoundException ex) {
			return new ResponseEntity<ApplicationError>(
					new ApplicationError(HttpStatus.NOT_FOUND.value(), "Card Number to be updated not found."),
					HttpStatus.NOT_FOUND);
		} catch (IOException ex) {
			return new ResponseEntity<ApplicationError>(
					new ApplicationError(HttpStatus.NOT_FOUND.value(), "Card Number to be updated not found."),
					HttpStatus.NOT_FOUND);
		}
		cardDetails.setCardNumber(cardD.getCardNumber());
		cardDetails.setCardApplyMode(cardD.getCardApplyMode());
		cardDetails.setCardStatus(cardD.getCardStatus());
		cardDetails.setCardType(cardD.getCardType());
		cardDetails.setCustId(cardD.getCustId());
		cardDetails.setExpiryDate(cardD.getExpiryDate());
		cardDetails.setStartDate(cardD.getStartDate());
		repository.update(cardDetails);
		logger.info("Updated Card details successfully for "+ cardNumber+".");
		return new ResponseEntity<CardDetails>(cardDetails, HttpStatus.OK);
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

	public void validateCardDetails(String cardNumber) throws ApiException {
		if (Utility.isNullOrEmpty(cardNumber)) {
			throw new ApiException(405, "Invalid Card Details - Card Number Missing");
		}
	}

}