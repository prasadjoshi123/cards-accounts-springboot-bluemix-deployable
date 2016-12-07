package io.swagger.configuration;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.impl.StdCouchDbConnector;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudantAppConfig extends AbstractCloudConfig{
	
	/*@Bean
	  public CouchDbConnector couchDbConnector(CouchDbInstance couchDbInstance) {
	    CouchDbConnector cardsConnector = new StdCouchDbConnector("card_db", couchDbInstance);
	    cardsConnector.createDatabaseIfNotExists();
	    return cardsConnector;
	  }*/
	@Bean
	public CouchDbConnector couchDbConnector(CouchDbInstance couchDbInstance) {
		CouchDbConnector accountsConnector = new StdCouchDbConnector("acc_db", couchDbInstance);
		accountsConnector.createDatabaseIfNotExists();
		return accountsConnector;
	}

	@Bean
    public CouchDbInstance couchDbInstance() {
      CouchDbInstance instance = connectionFactory().service(CouchDbInstance.class);
      return instance;
    }

}
