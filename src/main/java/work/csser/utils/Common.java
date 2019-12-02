package work.csser.utils;

import java.io.*;

/**
 * @author a1exlism
 * @className Common
 * @description common functions for this project
 * @since 2019/11/6 17:08
 */
public class Common {
  /*
   * @description:  get bytes to path
   * @params: [path, filename, b]
   * @param path: /PATH
   * @param filename: name
   * @param b:
   * @return: void
   */
  public static void writeFile(String path, String filename, byte[] b) {
    OutputStream os;
    try {
      os = new FileOutputStream(path + File.separator + filename);

      os.write(b);
      System.out.println("Write done");
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
