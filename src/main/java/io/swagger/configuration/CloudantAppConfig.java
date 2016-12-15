package io.swagger.configuration;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.http.StdHttpClient.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CloudantAppConfig {

	@Autowired
	CloudantBinding bluemixDbaas;

	@Bean
	public CouchDbConnector couchDbConnector(CouchDbInstance couchDbInstance) {
		CouchDbConnector connector = new StdCouchDbConnector("cards_accounts_db", couchDbInstance);
		connector.createDatabaseIfNotExists();
		return connector;
	}

	@Bean
	public CouchDbInstance couchDbInstance() {

		Builder builder = new Builder().host(bluemixDbaas.getHost()).port(bluemixDbaas.getPort())
				.username(bluemixDbaas.getUser()).password(bluemixDbaas.getPwd())
				.enableSSL(bluemixDbaas.isSslEnabled());
		HttpClient httpClient = builder.build();
		CouchDbInstance instance = new StdCouchDbInstance(httpClient);
		return instance;
	}

}
