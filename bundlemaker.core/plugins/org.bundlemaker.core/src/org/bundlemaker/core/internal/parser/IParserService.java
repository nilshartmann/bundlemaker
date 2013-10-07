package org.bundlemaker.core.internal.parser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.internal.Activator;
import org.bundlemaker.core.parser.IProblem;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.spi.parser.IParsableResource;
import org.bundlemaker.core.spi.parser.IParser;
import org.bundlemaker.core.spi.parser.IParserFactory;
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
     * @author GWUETHER
     */
    private static final class ParserService implements IParserService {

      /** - */
      private List<IParser> _parsers;

      /**
       * <p>
       * </p>
       */
      public ParserService() {

        //
        _parsers = new LinkedList<IParser>();

        //
        for (IParserFactory parserFactory : Activator.getDefault().getParserFactoryRegistry().getParserFactories()) {

          try {

            //
            _parsers.add(parserFactory.createParser());

          } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }

      /**
       */
      @Override
      public List<IProblem> parseResource(IProjectContentEntry content, IParsableResource resource,
          boolean parseReferences)
          throws CoreException {

        //
        for (IParser parser : _parsers) {
          if (parser.canParse(resource)) {
            return parser.parseResource(content, resource, parseReferences, false);
          }
        }

        return Collections.emptyList();
      }
    }

    // TODO: Evil singleton
    private static IParserService _parserService;

    /**
     * @return
     */
    public static IParserService getParserService() {

      //
      if (_parserService == null) {

        //
        _parserService = new ParserService();
      }

      //
      return _parserService;
    }
  }
}
