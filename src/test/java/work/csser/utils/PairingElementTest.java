package work.csser.utils;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import org.junit.Test;

/**
 * @author a1exlism
 * @className PairingElementTest
 * @since 2019/12/8 20:30
 */
public class PairingElementTest {

  private Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");

  /**
   * Get the length for better coding
   *
   * @return void
   * @method lengthTest
   */
  @Test
  public void lengthTest() {
    Element g = null;
    Element gRandom = null;
    Element z = null;
    Element zRandom = null;
    Element gt = null;
    Element gtRandom = null;
    g = pairing.getG1().newElement();
    gRandom = pairing.getG1().newRandomElement();
    //  g \in G_1 both length 128
    System.out.println("G1 Element: g len: " + g.getLengthInBytes() + " gRandom len: " + gRandom.getLengthInBytes());
    z = pairing.getZr().newElement();
    zRandom = pairing.getZr().newRandomElement();
    //  g \in Z_r both length 20
    System.out.println("Zr Element: z len: " + z.getLengthInBytes() + " zRandom len: " + zRandom.getLengthInBytes());
    gt = pairing.getGT().newElement();
    gtRandom = pairing.getGT().newRandomElement();
    //  gt \in GT both length 128
    System.out.println("GT Element: gt len: " + gt.getLengthInBytes() + " gtRandom len: " + gtRandom.getLengthInBytes());
  }
}
