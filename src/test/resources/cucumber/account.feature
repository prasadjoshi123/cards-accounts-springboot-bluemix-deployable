Feature: Testing a REST API for Account
  Users should be able to sent GET/POST/PUT/DELETE requests to a Account RESTFul web service.

  Scenario: Add an Account details
    Given Account details
    When users submits the data
    Then server returns a status of add Account

  Scenario: Account details retrieval from a web service
    Given Account id is provided
    When users want to get information about an Account
    Then the requested Account data is returned


  Scenario: All Account details retrieval from a web service
    Given RESTFul url is provided
    When users wants to get all Account informations
    Then server returns a status of get all Accounts


  Scenario:  Account details is  updated using a web service
    Given Account Id is provided
    When users want to update Account informations
    Then server returns a status of update Accounts

  Scenario:  Account details is  deleted using a web service
    Given Account Id is provided
    When users want to delete Account informations
    Then server returns a status of delete Accounts
