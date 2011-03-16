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

import java.io.InputStream;
import java.io.IOException;

/**
 * ByteArrayInputStream implementation that does not synchronize methods.
 */
public class FastByteArrayInputStream extends InputStream {
  /**
   * Our byte buffer
   */
  protected byte[] buf   = null;

  /**
   * Number of bytes that we can read from the buffer
   */
  protected int    count = 0;

  /**
   * Number of bytes that have been read from the buffer
   */
  protected int    pos   = 0;

  public FastByteArrayInputStream(byte[] buf, int count) {
    this.buf = buf;
    this.count = count;
  }

  public final int available() {
    return count - pos;
  }

  public final int read() {
    return (pos < count) ? (buf[pos++] & 0xff) : -1;
  }

  public final int read(byte[] b, int off, int len) {
    if (pos >= count)
      return -1;

    if ((pos + len) > count)
      len = (count - pos);

    System.arraycopy(buf, pos, b, off, len);
    pos += len;
    return len;
  }

  public final long skip(long n) {
    if ((pos + n) > count)
      n = count - pos;
    if (n < 0)
      return 0;
    pos += n;
    return n;
  }

}
