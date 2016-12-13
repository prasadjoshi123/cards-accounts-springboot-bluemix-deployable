package io.swagger.model;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRepository extends CouchDbRepositorySupport<AccountDetails>{

	@Autowired
	  public AccountRepository(CouchDbConnector connector) {
	    super(AccountDetails.class, connector);
	    initStandardDesignDocument();
	  }
}
