/*******************************************************************************
 * Copyright (c) 2013 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.util.history;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.bundlemaker.core.common.history.History;
import org.junit.Test;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class HistoryTest {

  private HistoryOfStrings _history = new HistoryOfStrings();

  @Test
  public void addSingleElement() {
    add("A");
  }

  @Test
  public void addTwoElements() {
    add("A, B");
  }

  @Test
  public void addThreeEleemnt() {
    add("A, B, C");
  }

  @Test
  public void addThreeGoBackOne() {
    add("A, B, C");

    // go back one
    goBack("B");
    _history.dumpHistory();
    assertTrue(_history.canGoBack());
  }

  @Test
  public void addThreeGoBackTwo() {
    add("A, B, C");
    // _history.dumpHistory();
    // go back
    goBack("B");
    _history.dumpHistory();

    System.out.println("==========");
    goBack("A");
    assertFalse(_history.canGoBack());
  }

  @Test
  public void addThreeGoBackTwoAddOne() {
    add("A, B, C");
    // go back
    goBack("B");
    goBack("A");

    // add another one
    add("D");
  }

  @Test
  public void addThreeGoBackTwoAddOneGoBackOne() {
    add("A, B, C");
    // go back
    goBack("B");
    goBack("A");
    assertEquals("A", _history.getCurrent());

    // add another one
    add("D"); // ->
    _history.dumpHistory();
    assertEquals("D", _history.getCurrent());
    goBack("A");
  }

  @Test
  public void addThreeGoBackTwoAddOneGoBackTwo() {
    add("A, B, C");
    // go back
    goBack("B");
    goBack("A");
    // _history.dumpHistory();
    assertFalse(_history.canGoBack());

    // add another one
    add("D");
    System.out.println("NACH D:");
    _history.dumpHistory();
    goBack("A");
    System.out.println("NACH GO BACK A:");
    _history.dumpHistory();
    goBack("B");
  }

  @Test
  public void addThreeGoBackTwoAddOneGoBackTwoAddOne() {
    add("A, B, C");
    // go back
    goBack("B");
    goBack("A");

    // add another one
    add("D");
    goBack("A");
    goBack("B");
    add("E");
  }

  @Test(expected = IllegalStateException.class)
  public void addOneGoBackward() {
    add("A");
    assertFalse(_history.canGoBack());
    _history.goBack();
  }

  @Test(expected = IllegalStateException.class)
  public void addOneGoForward() {
    add("A");
    assertFalse(_history.canGoForward());
    _history.goForward();
  }

  @Test(expected = IllegalStateException.class)
  public void goBackInEmptyHistory() {
    assertFalse(_history.canGoBack());
    _history.goBack();
  }

  @Test(expected = IllegalStateException.class)
  public void goForwardInEmptyHistory() {
    assertFalse(_history.canGoForward());
    _history.goForward();
  }

  @Test
  public void addTwoGoBackGoForward() {
    // _history.dumpHistory();
    add("A");
    // _history.dumpHistory();
    add("B");
    _history.dumpHistory();
    goBack("A");
    _history.dumpHistory();
    goForward("B");

  }

  @Test
  public void addThreGoBackTwoGoForward() {
    add("A");
    add("B");
    add("C");
    goBack("B");
    goBack("A");
    goForward("B");
  }

  @Test
  public void afterAdding_FowardImpossible() {
    assertFalse(_history.canGoForward());
    add("A");
    assertFalse(_history.canGoForward());
    add("B");
    assertFalse(_history.canGoForward());
    add("C");
    assertFalse(_history.canGoForward());

  }

  @Test
  public void addThreGoBackTwoAddOne_ForwardImpossible() {
    add("A");
    add("B");
    add("C");
    goBack("B");
    goBack("A");
    add("D");
    assertFalse(_history.canGoForward());
  }

  @Test
  public void bla() {
    add("A");
    add("B");
    goBack("A");
    goForward("B");
    goBack("A");
    goForward("B");

    add("C");
    goBack("B");
    goBack("A");
    goForward("B");
    goForward("C");

    add("D,E");
    goBack("D");
    goForward("E");
    goBack("D");
    goBack("C");
  }

  @Test
  public void addThreGoBackTwoAddOneGoBackAndForward() {
    add("A");
    add("B");
    add("C");
    goBack("B");
    goBack("A");
    add("D");
    assertFalse(_history.canGoForward());
    goBack("A");
    goForward("D");
    _history.dumpHistory();
    assertFalse(_history.canGoForward());
  }

  @Test
  public void forwardToEndOfHistory() {
    add("A,B,C");
    goBack("B");
    assertTrue(_history.canGoForward());
    goBack("A");
    _history.dumpHistory();
    assertTrue(_history.canGoForward());
    goForward("B");
    assertTrue(_history.canGoForward());
    goForward("C");
    _history.dumpHistory();
    assertFalse(_history.canGoForward());

  }

  /**
   * @param string
   */
  private void goForward(String expectedNewValue) {
    assertTrue(_history.canGoForward());
    _history.goForward();
    assertEquals("Go forward failed",expectedNewValue, _history.getCurrent());
//    assertEquals("Go forward failed", expectedNewValue, result);

  }

  protected void goBack(String expectedNewValue) {
    assertTrue(_history.canGoBack());
    _history.goBack();
    assertEquals("Go back failed", expectedNewValue, _history.getCurrent());
  }

  protected void add(String values) {
    String last = null;
    for (String v : values.split(",")) {
      _history.add(v.trim());
      last = v.trim();
    }

    assertEquals(last, _history.getCurrent());
  }

  class HistoryOfStrings extends History<String> {

  }

}
