package io.swagger.model;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardRepository extends CouchDbRepositorySupport<CardDetails>{

	@Autowired
	public CardRepository(CouchDbConnector connector) {
		super(CardDetails.class, connector);
		initStandardDesignDocument();
	}

}
