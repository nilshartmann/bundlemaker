package org.bm.test.app;

import org.bm.test.domain.DomainController;
import org.bm.test.domain.DomainFactory;

public class Application {
	
	public DomainFactory getFactory() {
		return null;
	}
	
	public void run() {
		DomainController domainController = getFactory().create();
		
		domainController.execute();
	}
	

}
