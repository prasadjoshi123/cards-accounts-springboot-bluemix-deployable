#Cards and Accounts Spring Boot Micro Services:

1. Make sure IBM  Cloudant Docker image is available on your local if not please follow below link for the same.
https://hub.docker.com/r/ibmcom/cloudant-developer/<BR>
2. Create new project in IntelliJ by checking out code from git repository with address -https://github.com/vajadhav/swagger-springboot-cards-with-search-indices.git<BR>
3. Configure project as mvn<BR>
4. Resolve libraries dependency<BR>
5. Run"mvn clean install -U"command on Terminal inside Intellij<BR>
6. Copy spring-cloud.properties from project resources to c:\dev on local<BR>
7. Make sure cloudant is running in your docker container on local<BR>
8. In intelliJ Terminal run command "java -jar target/swagger-spring-1.0.0.jar"<BR>
9. Open "http://localhost:8080/dashboard.html" and check if "cards_accounts_db" is created with 0 records<BR>
10. Create below search indices on "cards_accounts_db"<BR>
</P>
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
</P>
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

11. API Endpoints and Payload<BR>

http://localhost:7070/card-accounts/card(Create Card, POST, Content-Type:application/json)<BR>
http://localhost:7070/card-accounts/update-card/{cardNumber}(Update Card, PUT, Content-Type:application/json)<BR>
http://localhost:7070/card-accounts/fetch-cards/{cardNumber}(Retrieve Card by Card Number, GET)<BR>
http://localhost:7070/card-accounts/fetch-card-details/{custId}(Retrieve Card by Cust ID, GET)<BR>
http://localhost:7070/card-accounts/card(Retrieve all Cards, GET)<BR>
http://localhost:7070/card-accounts/manage-card/(Delete Card, DELETE)<BR>

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

http://localhost:7070/card-accounts/accounts(Create Account, POST, Content-Type:application/json)<BR>
http://localhost:7070/card-accounts/update-account/{accountNumber}(Update Account, PUT, Content-Type:application/json)<BR>
http://localhost:7070/card-accounts/accounts/{accountNumber}(Fetch Account Details by Account Number, GET)<BR>
http://localhost:7070/card-accounts/manage-account/{accountNumber}(Delete Account, DELETE)<BR>

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