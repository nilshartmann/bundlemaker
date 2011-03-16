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
package org.bundlemaker.core.internal.deepcopy;

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

/**
 * ByteArrayOutputStream implementation that doesn't synchronize methods and doesn't copy the data on toByteArray().
 */
public class FastByteArrayOutputStream extends OutputStream {
  /**
   * Buffer and size
   */
  protected byte[] buf  = null;

  protected int    size = 0;

  /**
   * Constructs a stream with buffer capacity size 5K
   */
  public FastByteArrayOutputStream() {
    this(5 * 1024);
  }

  /**
   * Constructs a stream with the given initial size
   */
  public FastByteArrayOutputStream(int initSize) {
    this.size = 0;
    this.buf = new byte[initSize];
  }

  /**
   * Ensures that we have a large enough buffer for the given size.
   */
  private void verifyBufferSize(int sz) {
    if (sz > buf.length) {
      byte[] old = buf;
      buf = new byte[Math.max(sz, 2 * buf.length)];
      System.arraycopy(old, 0, buf, 0, old.length);
      old = null;
    }
  }

  public int getSize() {
    return size;
  }

  /**
   * Returns the byte array containing the written data. Note that this array will almost always be larger than the
   * amount of data actually written.
   */
  public byte[] getByteArray() {
    return buf;
  }

  public final void write(byte b[]) {
    verifyBufferSize(size + b.length);
    System.arraycopy(b, 0, buf, size, b.length);
    size += b.length;
  }

  public final void write(byte b[], int off, int len) {
    verifyBufferSize(size + len);
    System.arraycopy(b, off, buf, size, len);
    size += len;
  }

  public final void write(int b) {
    verifyBufferSize(size + 1);
    buf[size++] = (byte) b;
  }

  public void reset() {
    size = 0;
  }

  /**
   * Returns a ByteArrayInputStream for reading back the written data
   */
  public InputStream getInputStream() {
    return new FastByteArrayInputStream(buf, size);
  }

}
