/*******************************************************************************
 * Copyright (C) 2007  db4objects Inc.  http://www.db4o.com
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the db4o Opensource Compatibility License (dOCL)
 * which accompanies this distribution, and is available at
 * http://www.db4o.com/about/company/legalpolicies/docl.aspx
 *******************************************************************************/
package com.db4o.osgi;

import org.osgi.framework.*;

class Db4oServiceFactory implements ServiceFactory {

  public Object getService(Bundle bundle, ServiceRegistration registration) {
    return new Db4oServiceImpl(bundle);
  }

  public void ungetService(Bundle bundle, ServiceRegistration registration, Object service) {
  }

}
