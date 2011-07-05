package org.bundlemaker.analysis.testkit.framework;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 *
 * @param <Item>
 */
public class RingBuffer<Item> implements Iterable<Item> {

  /** - */
  private List<Item> _content;

  /** - */
  private int        _capacity;

  /**
   * <p>
   * Creates a new instance of type {@link RingBuffer}.
   * </p>
   * 
   * @param capacity
   */
  public RingBuffer(int capacity) {
    _content = new LinkedList<Item>();
    _capacity = capacity;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isEmpty() {
    return _content.isEmpty();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public int size() {
    return _content.size();
  }

  /**
   * <p>
   * </p>
   * 
   * @param item
   */
  public void enqueue(Item item) {

    //
    if (_content.size() == _capacity) {
      _content.remove(0);
    }

    //
    _content.add(item);
  }

  // remove the least recently added item - doesn't check for underflow
  public Item dequeue() {
    if (isEmpty()) {
      throw new RuntimeException("Ring buffer underflow");
    }
    return  _content.remove(0);
  }

  public Iterator<Item> iterator() {
    return _content.iterator();
  }


  // a test client
  public static void main(String[] args) {
    RingBuffer<Character> ring = new RingBuffer<Character>(4);
    ring.enqueue('a');
    ring.enqueue('w');
    ring.enqueue('a');
    ring.enqueue(' ');

    while (!ring.isEmpty()) {
      System.out.print(ring.dequeue());
    }
  }

}
