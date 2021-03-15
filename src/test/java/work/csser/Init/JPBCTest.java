package work.csser.Init;


import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import org.junit.Test;

/**
 * @author a1exlism
 * @className JPBCTest
 * @since 2021/3/15 09:28
 */
public class JPBCTest {
  @Test
  public void eleGen() {
    //  type-A symmetric bilinear-map
    Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");
    Element z = pairing.getZr().newRandomElement();
    Element g1 = pairing.getG1().newRandomElement();
    Element g2 = pairing.getG2().newRandomElement();
    Element gt = pairing.pairing(g1, g2);
    //  20/128/128/128
    System.out.println(z.getLengthInBytes() == 20);
    System.out.println(g1.getLengthInBytes() == 128);
    System.out.println(g2.getLengthInBytes());
    System.out.println(gt.getLengthInBytes());
  }

}
