Feature: Testing a REST API for Card
  Users should be able to sent GET/POST/PUT/DELETE requests to a Card RESTFul web service.

  Scenario: Add an Card details
    Given Card details
    When users submits the data
    Then server returns a status of add Card

  Scenario: Card details retrieval from a web service
    Given Card id is provided
    When users want to get information about an Card
    Then the requested Card data is returned


  Scenario: All Card details retrieval from a web service
    Given RESTFul url is provided
    When users wants to get all Card informations
    Then server returns a status of get all Cards


  Scenario:  Card details is  updated using a web service
    Given Card Id is provided
    When users want to update Card informations
    Then server returns a status of update Cards

  Scenario:  Card details is  deleted using a web service
    Given Card Id is provided
    When users want to delete Card informations
    Then server returns a status of delete Cards
