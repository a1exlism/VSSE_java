package work.csser.utils;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * @author a1exlism
 * @className Common
 * @description common functions for this project
 * @since 2019/11/6 17:08
 */
public class Common {

  //  bilinear pairing
  final private static Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");
  //  AES 128 Initialization Vector; initial byte array below this default to 0
  final private static byte[] ivBytes = new byte[16];

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

  /**
   * pseudo-random function F
   * {0,1}^lambda \times {0,1}^lambda \to {0,1}^lambda
   *
   * @param key: secret key
   * @param msg: message to authenticate
   * @return byte[]
   * @method PRF_F
   * @params [key, msg]
   */
  public static byte[] PRF_F(final byte[] key, final String msg) throws UnsupportedEncodingException {
    return CryptoPrimitives.generateCmac(key, msg);
  }

  /**
   * pseudo-random function F
   * {0,1}^lambda \times {0,1}^lambda \to {0,1}^lambda
   *
   * @param key:
   * @param msg: type Element
   * @return byte[]
   * @method PRF_F
   * @params [key, msg]
   */
  public static byte[] PRF_F(final byte[] key, final Element msg) throws UnsupportedEncodingException {
    return CryptoPrimitives.generateCmac(key, msg.toString());
  }

  /**
   * pseudo-random function Fp
   *
   * @param key:
   * @param msg:
   * @return it.unisa.dia.gas.jpbc.Element
   * @method PRF_Fp
   * @params [key, msg]
   */
  public static Element PRF_Fp(final byte[] key, final String msg) throws Exception {
    byte[] res = CryptoPrimitives.generateCmac(key, msg);
    Element e = pairing.getZr().newElement();
    e.setFromBytes(res);
    return e;
  }

  /**
   * AES encryption with CBC mode
   * encrypt plaintext to cipher-text
   *
   * @param key:
   * @param plaintext:
   * @return byte[] cipher
   * @method Enc
   * @params [key, plaintext]
   */
  public static byte[] Enc(byte[] key, String plaintext) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException, InvalidKeyException, IOException {
    return CryptoPrimitives.encryptAES_CBC(key, ivBytes, plaintext.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * AES decryption with CBC mode
   * decrypt cipher-text to plaintext
   *
   * @param key:
   * @param cipher:
   * @return java.lang.String
   * @method Dec
   * @params [key, cipher]
   */
  public static String Dec(byte[] key, byte[] cipher) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException, InvalidKeyException, IOException {
    byte[] s = CryptoPrimitives.decryptAES_CBC(cipher, key);
    return new String(s, StandardCharsets.UTF_8);
  }

}
