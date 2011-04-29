package org.bundlemaker.core.internal.projectdescription;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bundlemaker.core.util.GenericCache;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public class MessageDigestGenerator {

  private GenericCache<File, byte[]> _cache;

  /** - */
  static final byte[]                HEX_CHAR_TABLE = { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4',
      (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd',
      (byte) 'e', (byte) 'f'                       };

  public MessageDigestGenerator() {

    _cache = new GenericCache<File, byte[]>() {

      @Override
      protected byte[] create(File key) {
        return messageDigest(key.getAbsolutePath(), "SHA");
      }
    };

  }

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @param child
   * @return
   */
  public byte[] getMessageDigest(File root, String child) {

    //
    if (root.isDirectory()) {
      try {
        return messageDigest(root.getAbsolutePath() + File.separatorChar + child, "SHA");
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        throw new RuntimeException();
      }
    } else {
      return _cache.getOrCreate(root);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param raw
   * @return
   * @throws UnsupportedEncodingException
   */
  public static String getHexString(byte[] raw) throws UnsupportedEncodingException {
    byte[] hex = new byte[2 * raw.length];
    int index = 0;

    for (byte b : raw) {
      int v = b & 0xFF;
      hex[index++] = HEX_CHAR_TABLE[v >>> 4];
      hex[index++] = HEX_CHAR_TABLE[v & 0xF];
    }
    return new String(hex, "ASCII");
  }

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @param algo
   * @return
   * @throws Exception
   */
  private static byte[] messageDigest(String file, String algo) {
    try {
      MessageDigest messagedigest = MessageDigest.getInstance(algo);
      byte[] md = new byte[8192];
      InputStream in = new FileInputStream(file);
      for (int n = 0; (n = in.read(md)) > -1;)
        messagedigest.update(md, 0, n);
      return messagedigest.digest();
    } catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new RuntimeException();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new RuntimeException();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

}
