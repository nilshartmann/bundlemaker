package org.bundlemaker.core.parser;

import java.util.Collections;
import java.util.List;

import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.spi.parser.IParsableResource;
import org.eclipse.core.runtime.CoreException;

/**
 */
public interface IParserService {

  /**
   * <p>
   * </p>
   * 
   * @param content
   * @param resource
   * @param parseReferences
   * @return
   * @throws CoreException
   */
  List<IProblem> parseResource(IProjectContentEntry content, IParsableResource resource,
      boolean parseReferences)
      throws CoreException;

  /**
   * <p>
   * </p>
   */
  public static class Factory {

    /**
     * @return
     */
    public static IParserService getParserService() {

      //
      return new IParserService() {

        @Override
        public List<IProblem> parseResource(IProjectContentEntry content, IParsableResource resource,
            boolean parseReferences)
            throws CoreException {

          System.out.println("Parse Resource " + resource.getName());

          return Collections.emptyList();
        }
      };
    }
  }
}
