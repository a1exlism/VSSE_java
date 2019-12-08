package work.csser.utils;

import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author a1exlism
 * @className Common
 * @description common functions for this project
 * @since 2019/11/6 17:08
 */
public class Common {

  //  bilinear pairing
  final public static Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");

  /**
   * @return void
   * @method writeFile
   * @description write file with `Java7+`
   * @refer https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java
   * @params [directory, fileName, b]
   */
  public static void writeFile(String directory, String fileName, byte[] data) throws IOException {
    String fPath = System.getProperty("user.dir")
        + File.separator + directory + File.separator + fileName;
    //  1. create
    Path file = Paths.get(fPath);
    //  2. write
    Files.write(file, data);
    System.out.println("utils/Common: file wrote.");
  }

  /*
   * @method readFile
   * @description read file module
   * @params [directory, fileName]
   * @param directory folder path
   * @param fileName
   * @return java.io.File
   * @createBy a1exlism
   * @date 2019/12/8 00:02
   */
  public static File readFile(String directory, String fileName) {
    String path = System.getProperty("user.dir")
        + File.separator + directory;

    return new File(path, fileName);
  }

//  public static byte[] PRF_F() { }

//  public static byte[] PRF_Fp() { }
}
