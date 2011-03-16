/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.parser.bytecode;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.parser.AbstractParserFactory;
import org.bundlemaker.core.parser.IParser;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ByteCodeParserFactory extends AbstractParserFactory {

  /**
   * {@inheritDoc}
   */
  @Override
  public IParser createParser(IBundleMakerProject bundleMakerProject, boolean parseIndirectReferences)
      throws CoreException {

    // return the new byte code parser
    return new ByteCodeParser();
  }
}
