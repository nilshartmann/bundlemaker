package org.bundlemaker.core.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ManifestHeaderUtils {

  /**
   * <p>
   * </p>
   * 
   * @param <T>
   * @param collection
   * @return
   */
  public static <T> String listToString(Collection<T> collection) {

    //
    return listToString(collection, new ToStringStrategy<T>() {

      @Override
      public String toString(T element) {
        return element.toString();
      }
    }, ",");
  }

  public static <T> String listToString(Collection<T> collection, ToStringStrategy<T> strategy) {

    return listToString(collection, strategy, ",");
  }

  /**
   * <p>
   * </p>
   * 
   * @param collection
   * @return
   */
  public static <T> String listToString(Collection<T> collection, ToStringStrategy<T> strategy, String delimiter) {

    // create the string builder
    StringBuilder stringBuilder = new StringBuilder();

    // get the iterator
    Iterator<T> iterator = collection.iterator();

    // iterate over the collection
    while (iterator.hasNext()) {

      //
      T entry = iterator.next();

      //
      stringBuilder.append(strategy.toString(entry));

      //
      if (iterator.hasNext()) {
        stringBuilder.append(delimiter);
      }

      //
      entry.toString();
    }

    //
    return stringBuilder.toString();
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   * 
   * @param <T>
   */
  public interface ToStringStrategy<T> {
    String toString(T element);
  }
}
