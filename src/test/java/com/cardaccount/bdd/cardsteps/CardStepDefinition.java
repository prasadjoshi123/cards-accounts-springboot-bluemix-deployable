package com.cardaccount.bdd.cardsteps;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class CardStepDefinition {

    private static final String RESTFUL_URL = "http://localhost:7070/card-accounts/card";
    private static final String RESTFUL_URL_FETCH = "http://localhost:7070/card-accounts/fetch-card-details";
    private static final String RESTFUL_URL_UPDATE = "http://localhost:7070/card-accounts/update-card";
    private static final String RESTFUL_URL_DELETE = "http://localhost:7070/card-accounts/manage-card";
    private static final String CARD_NUMBER = "/1234";
    private static final String CARD_NUMBER_DELETE = "/1234";
    private static final String APPLICATION_JSON = "application/json";
    private static final String JSON_FILE_UPDATE = "card_upd.json";
    private static final String JSON_FILE = "card.json";
    

    private static String convertHttpResponseToString(HttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getEntity().getContent();
        return convertInputStreamToString(inputStream);
    }

    private static String convertInputStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        String string = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return string;
    }

    @Given("^Card details$")
    public void card_details() throws Throwable {

    }

    @When("^users submits the data$")
    public void users_submits_the_data() throws Throwable {
        InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream(JSON_FILE);
        String jsonString = new Scanner(jsonInputStream, "UTF-8").useDelimiter("\\Z").next();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(RESTFUL_URL);
        StringEntity entity = new StringEntity(jsonString);
        request.addHeader("content-type", APPLICATION_JSON);
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Then("^server returns a status of add Card$")
    public void server_returns_a_status_of_add_Card() throws Throwable {

    }

    @Given("^Card Number is provided$")
    public void card_Number_is_provided() throws Throwable {

    }

    @When("^users want to get information about the Card$")
    public void users_want_to_get_information_about_the_Card() throws Throwable {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(RESTFUL_URL_FETCH+CARD_NUMBER);
        HttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertHttpResponseToString(httpResponse);
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    }

    @Then("^the requested Card data is returned$")
    public void the_requested_Card_data_is_returned() throws Throwable {

    }

    @Given("^RESTFul url is provided$")
    public void restful_url_is_provided() throws Throwable {

    }

    @When("^users wants to get all Card information$")
    public void users_wants_to_get_all_Card_information() throws Throwable {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(RESTFUL_URL);
        HttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertHttpResponseToString(httpResponse);
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    }

    @Then("^server returns a status of get all Cards operation$")
    public void server_returns_a_status_of_get_all_Cards_operation() throws Throwable {

    }

    @When("^users want to update Card information$")
    public void users_want_to_update_Card_information() throws Throwable {
        InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream(JSON_FILE_UPDATE);
        String jsonString = convertInputStreamToString(jsonInputStream);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPost = new HttpPut(RESTFUL_URL_UPDATE + CARD_NUMBER);
        StringEntity entity = new StringEntity(jsonString, "UTF-8");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", APPLICATION_JSON);
        httpPost.setHeader("Content-type", APPLICATION_JSON);
        CloseableHttpResponse httpResponse = client.execute(httpPost);
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
        client.close();
    }

    @Then("^server returns status of update Cards operation$")
    public void server_returns_status_of_update_Cards_operation() throws Throwable {

    }

    @When("^users want to delete Card information$")
    public void users_want_to_delete_Card_information() throws Throwable {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete request = new HttpDelete(RESTFUL_URL_DELETE + CARD_NUMBER_DELETE);
        HttpResponse httpResponse = httpClient.execute(request);
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    }

    @Then("^server returns status of delete Cards operation$")
    public void server_returns_status_of_delete_Cards_operation() throws Throwable {

    }

}

