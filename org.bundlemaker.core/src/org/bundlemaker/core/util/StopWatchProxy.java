package org.bundlemaker.core.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class StopWatchProxy implements InvocationHandler {

  /** - */
  private Object _target;

  /**
   * <p>
   * </p>
   * 
   * @param obj
   * @return
   */
  public static Object newInstance(Object obj) {
    return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new StopWatchProxy(
        obj));
  }

  private StopWatchProxy(Object obj) {
    this._target = obj;
  }

  public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {

    Object result;

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    try {

      result = m.invoke(_target, args);

    } catch (InvocationTargetException e) {
      throw e.getTargetException();
    } catch (Exception e) {
      throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
    } finally {

      stopWatch.stop();

      //
      System.out.println(m.getName() + " : " + stopWatch.getElapsedTime());
    }

    return result;
  }
}
