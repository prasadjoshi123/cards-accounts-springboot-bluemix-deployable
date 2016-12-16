#Cards and Accounts Spring Boot Micro Services:

#Prerequisite
Make sure IBM  Cloudant Docker image is available on your local if not please follow below link for the same.
https://hub.docker.com/r/ibmcom/cloudant-developer/

#Development Environment configuration
1. Create new project in IntelliJ by checking out code from git repository with address -https://github.com/vajadhav/swagger-springboot-cards-with-search-indices.git<BR>
2. Configure project as mvn<BR>
3. Resolve libraries dependency<BR>
4. Run - mvn clean install -U -->command on Terminal inside Intellij<BR>
5. Copy spring-cloud.properties from project resources to c:\dev on local<BR>
6. Make sure cloudant is running in your docker container on local<BR>
7. In intelliJ Terminal run command "java -jar target/swagger-spring-1.0.0.jar"<BR>
8. Open "http://localhost:8080/dashboard.html" and check if "cards_accounts_db" is created with 0 records<BR>
9. Created below indices in "cards_accounts_db"<BR>

    Index name- search_card_details
    Save to Design Document- CardDetails
    Search Index Function-

    function(doc){
      index("default", doc._id);

        if(doc.custId ){
        index("custId", doc.custId , {"facet": true, "store": true});
      }

      if(doc.cardNumber){
        index("cardNumber", doc.cardNumber, {"facet": true, "store": true});
      }
      if (doc['class']){
        index("class", doc['class'], {"facet": true, "store": true});
      }
    }

    Index name : search_account_details
    Save to Design Document: AccountDetails
    Search index function:

    function(doc){
      index("default", doc._id);
        if(doc.accountNumber){
        index("accountNumber", doc.accountNumber, {"facet": true, "store": true});
      }

      if (doc['class']){
        index("class", doc['class'], {"facet": true, "store": true});
      }
    }

#Postman or RestClient can be used for testing below operations/APIs supported by CARDS and ACCOUNTS micro services.

Create Card: http://localhost:7070/card-accounts/card(Method-POST, Headers- Content-Type:application/json)<BR>
Update Card: http://localhost:7070/card-accounts/update-card/{cardNumber}(Method=PUT, Headers-Content=Type:application/json)<BR>
Payload for Create and Update Card:<BR>

    {
      "cardNumber": 12398760,
      "cardType": "Debit",
      "custId": 10010,
      "cardStatus": "Active",
      "startDate": "12/12/2016",
      "expiryDate": "12/12/2021",
      "cardApplyMode": "mobile"
    }

Retrieve Card by Card Number: http://localhost:7070/card-accounts/fetch-cards/{cardNumber}(Method-GET)<BR>
Retrieve Card by Cust ID: http://localhost:7070/card-accounts/fetch-card-details/{custId}(Method-GET)<BR>
Retrieve all Cards: http://localhost:7070/card-accounts/card(Method-GET)<BR>
Delete Card: http://localhost:7070/card-accounts/manage-card/(Method-DELETE)<BR>
Create Account:http://localhost:7070/card-accounts/accounts(Method-POST, Headers-Content-Type:application/json)<BR>
Update Account Details : http://localhost:7070/card-accounts/update-account/{accountNumber}(Method-PUT, Headers-Content-Type:application/json)<BR>
Payload for Create and Update Account:<BR>

    {
      "userName": "Seema Kulkarni",
      "customerId": 890109,
      "accountType": "Saving",
      "accountStatus": "Active",
      "accountBalance": 123.34,
      "address": "Pune",
      "accountNumber": 5456707,
      "mobileNumber": 9899876285
    }

Fetch Account Details by Account Number: http://localhost:7070/card-accounts/accounts/{accountNumber}(Method-GET)<BR>
Delete Account Details by Account Number: http://localhost:7070/card-accounts/manage-account/{accountNumber}(Method-DELETE)<BR>

# Deploy on IBM Bluemix Cloud Foundry App and Docker Container

Click below to deploy to your bluemix space
[![Deploy to Bluemix](https://bluemix.net/deploy/button.png)](https://bluemix.net/deploy)