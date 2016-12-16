Cards and Accounts Spring Boot Micro Services:

#Prerequisite
Make sure IBM  Cloudant Docker image is available on your local if not please follow below link for the same.
https://hub.docker.com/r/ibmcom/cloudant-developer/

#Configuration
1. Create new project in IntelliJ by checking out code from git repository with address -https://github.com/vajadhav/account-springboot
2. Configure project as mvn
3. Resolve libraries dependency
4. Run - mvn clean install -U -->command on Terminal inside Intellij
5. Copy spring-cloud.properties from project resources to c:\dev on local
6. Make sure cloudant is running in your docker container on local
7. In intelliJ Terminal run command "java -jar target/swagger-spring-1.0.0.jar"
8. Open "http://localhost:8080/dashboard.html" and check if "cards_accounts_db" is created with 0 records
9. Created below indices in "cards_accounts_db"

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

#CRUD operations for CARDS can be tested in Rest Client / Postman with payload

API - Create Card
Headers- Content-Type:application/json
Method - POST
URI-
Payload -
{
  "cardNumber": 12398760,
  "cardType": "Debit",
  "custId": 10010,
  "cardStatus": "Active",
  "startDate": "12/12/2016",
  "expiryDate": "12/12/2021",
  "cardApplyMode": "mobile"
}
API - Retrieve Card by Card Number
Method - GET
URI - http://localhost:7070/card-accounts/fetch-cards/{cardNumber}
Payload - NA

API - Retrieve Card by Customer ID
Method - GET
http://localhost:7070/card-accounts/fetch-card-details/{custId}
Payload - NA

API - Retrieve All Cards
Method - GET
URI- http://localhost:7070/card-accounts/card
Payload - NA

#CRUD operations for ACCOUNTS can be tested in Rest Client / Postman with payload

Create Account
Headers- Content-Type:application/json
Method - POST
URI-http://localhost:7070/card-accounts/accounts
Payload -
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


Update  Account Details by Account Number
Headers- Content-Type:application/json
Method - PUT
http://localhost:7070/card-accounts/update-account/{accountNumber}
Payload -
{
  "userName": "Madhav Bhandari",
  "customerId": 890109,
  "accountType": "Saving",
  "accountStatus": "Active",
  "accountBalance": 1234556.34,
  "address": "Pune",
  "accountNumber": 5456707,
  "mobileNumber": 9899876285
}

Fetch Account Details by Account Number
Method - GET
http://localhost:7070/card-accounts/accounts/{accountNumber}
Payload -NA

Delete Account Details by Account Number
Method - DELETE
http://localhost:7070/card-accounts/manage-account/{accountNumber}
Payload -NA

#Deploy Cards and Accounts Spring Boot Micro Services on IBM Bluemix as Cloud Foundry App and Docker Container App:
Click below to deploy to your bluemix space
[![Deploy to Bluemix](https://bluemix.net/deploy/button.png)](https://bluemix.net/deploy)