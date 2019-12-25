package work.csser.Init;

import org.junit.Test;

/**
 * @author a1exlism
 * @className KeyGenTest
 * @description
 * @since 2019/12/8 13:16
 */
public class KeyGenTest {
  @Test
  public void MasterKeyTest() {
    //  generate
    MasterKey.generateKey();
    //  recovery
    MasterKey MK = MasterKey.readKey();
    System.out.println(MK.toString());
  }
  @Test
  public void PublicKeyTest() {
    MasterKey MK = MasterKey.readKey();
    //  generate
    PublicKey.generateKey(MK, 30);
    //  recovery
    PublicKey PK = PublicKey.readKey();
    System.out.println(PK.toString());
  }
}
