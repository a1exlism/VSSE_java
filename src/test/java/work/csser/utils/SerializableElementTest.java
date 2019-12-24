package work.csser.utils;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import org.junit.Test;

/**
 * @author a1exlism
 * @className SerializableElementTest
 * @description
 * @since 2019/12/8 13:53
 */
public class SerializableElementTest {

  private static Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");

  /**
   * Project Irrelevant
   * test for override usage.
   *
   * @return void
   * @method toStringTest
   */
  @Test
  public void toStringTest() {
    Element e = pairing.getZr().newRandomElement();
    SerializableElement se = new SerializableElement(e);
    System.out.println(e.toString());
    System.out.println(se.toString());
  }

  @Test
  public void getFieldLenTest() {
    Element g = pairing.getG1().newRandomElement().getImmutable();
    Element z = pairing.getZr().newRandomElement().getImmutable();
    System.out.println("g \\in G1 :" + g.toString() + " Length: " + g.toString().length());
    System.out.println("z \\in Zr :" + z.toString() + " Length: " + z.toString().length());
  }
}
