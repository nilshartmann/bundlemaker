/*******************************************************************************
 * Copyright (c) 2013 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package client;

import a.api.AService;
import a.impl.AServiceImpl;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public class BrokenClient {
  
  public static void main(String[] args) {
    // ok
    AService service;

    // forbidden
    AServiceImpl serviceImpl;

  }

}
