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
   Index name: search_card_details<BR>
   Save to Design Document: CardDetails<BR>
   Search Index Function:<BR>

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
<BR>
    Index name: search_account_details<BR>
    Save to Design Document: AccountDetails<BR>
    Search index function:<BR>

    function(doc){
      index("default", doc._id);
        if(doc.accountNumber){
        index("accountNumber", doc.accountNumber, {"facet": true, "store": true});
      }
      if (doc['class']){
        index("class", doc['class'], {"facet": true, "store": true});
      }
    }
<BR>
#APIs Exposed by CARDS and ACCOUNTS micro services.

http://localhost:7070/card-accounts/card(Create Card , Method-POST, Headers-Content-Type:application/json)<BR>
http://localhost:7070/card-accounts/update-card/{cardNumber}( Update Card, Mthod=PUT, Headers-Content=Type:application/json)<BR>
http://localhost:7070/card-accounts/fetch-cards/{cardNumber}(Retrieve Card by Card Number, Method-GET)<BR>
http://localhost:7070/card-accounts/fetch-card-details/{custId}(Retrieve Card by Cust ID, Method-GET)<BR>
http://localhost:7070/card-accounts/card(Retrieve all Cards, Method-GET)<BR>
http://localhost:7070/card-accounts/manage-card/(Delete Card, Method-DELETE)<BR>

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

http://localhost:7070/card-accounts/accounts(Create Account, Method-POST, Headers-Content-Type:application/json)<BR>
http://localhost:7070/card-accounts/update-account/{accountNumber}(Update Account, Method-PUT, Headers-Content-Type:application/json)<BR>
http://localhost:7070/card-accounts/accounts/{accountNumber}(Fetch Account Details by Account Number, Method-GET)<BR>
http://localhost:7070/card-accounts/manage-account/{accountNumber}(Delete Account, Method-DELETE)<BR>

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

# Deployment on IBM Bluemix Cloud Foundry App and Docker Container

Click below to deploy to your bluemix space
[![Deploy to Bluemix](https://bluemix.net/deploy/button.png)](https://bluemix.net/deploy)