package io.swagger.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;

import io.swagger.configuration.CloudantBinding;
import io.swagger.model.CardDetails;
import io.swagger.model.CardRepository;
import io.swagger.utility.Utility;
import org.ektorp.DocumentNotFoundException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2016-11-01T18:09:28.587+05:30")
@SpringBootApplication
@RestController
@RequestMapping("/manage-card")
public class ManageCardApiController
		implements ManageCardApi {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired private CardRepository repository;
	@Autowired CloudantBinding cloudantBinding;
	@Autowired RestTemplate restTemplate;
	@Value("${cards.search.by.cardnumber.url}")
	private String searchCardURL;

	@RequestMapping(method = RequestMethod.DELETE, value = "{cardNumber}") public ResponseEntity<?> deleteCardDetails(
			@PathVariable String cardNumber) {
		logger.info("Deleting Card with "+cardNumber+"...");
		CardDetails cardDetails = null;
		String stringToParse = null;
		try {
			validateCardDetails(cardNumber);
			String URL = "http://" + cloudantBinding.getHost() + ":" + cloudantBinding.getPort() + searchCardURL + cardNumber;

			stringToParse = restTemplate.getForObject(URL, String.class);
			String id = getDocId(stringToParse);
			repository.remove(repository.get(id));
		} catch (ApiException ae) {
			return new ResponseEntity<ApplicationError>(new ApplicationError(ae.getCode(), ae.getMessage()),
					HttpStatus.NOT_FOUND);
		} catch (DocumentNotFoundException ex) {
			return new ResponseEntity<ApplicationError>(
					new ApplicationError(HttpStatus.NOT_FOUND.value(), "Card Number to be deleted not found."),
					HttpStatus.NOT_FOUND);
		} catch (IOException ex) {
			return new ResponseEntity<ApplicationError>(
					new ApplicationError(HttpStatus.NOT_FOUND.value(), "Card Number to be deletedd not found."),
					HttpStatus.NOT_FOUND);
		}
		logger.info("Deleted Card  with "+cardNumber+" successfully.");
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	public void validateCardDetails(String cardNumber) throws ApiException {
		if (Utility.isNullOrEmpty(cardNumber)) {
			throw new ApiException(405, "Invalid Card Details - Card Number Missing");
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
