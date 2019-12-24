package work.csser.Init;

import org.junit.Test;

/**
 * @author a1exlism
 * @className MasterKeyTest
 * @description
 * @since 2019/12/8 13:16
 */
public class MasterKeyTest {
  @Test
  public void initialTest() {
    //  generate
    MasterKey.generateKey();
    //  recovery
    System.out.println(MasterKey.readKey().toString());
    MasterKey.readKey();
  }
}
