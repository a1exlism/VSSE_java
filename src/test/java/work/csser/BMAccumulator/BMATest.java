package work.csser.BMAccumulator;


import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author a1exlism
 * @className BMATest
 * @since 2021/3/15 09:53
 */
public class BMATest {
  private BMAPrivateKey mk;
  private BMAPublicKey pk;
  public static Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");

  @Test
  public void testKeyGen() {
    this.mk = BMAPrivateKey.generateKey();
    this.pk = BMAPublicKey.generateKey(this.mk, 100);
    BMAPrivateKey t1 = BMAPrivateKey.readKey();
    BMAPublicKey t2 = BMAPublicKey.readKey();
    Assert.assertEquals(this.mk.getG().getElement(), t1.getG().getElement());
    Assert.assertEquals(this.pk.getPk().get(5).getElement(), t2.getPk().get(5).getElement());
  }

  //测试某个元素的witness
  @Test
  public void testElementWitness() throws Exception {
    List<String> s = new ArrayList<>();
    List<String> ns = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      String t = Long.toHexString(Double.doubleToLongBits(Math.random()));

      if (i < 5) {
        s.add(t);
      }
      ns.add(t);
    }

    Accumulator bma = new Accumulator(s);

    //  Single Element Witness
    for (String x : ns) {
      Witness witness = bma.getElementWitness(x);
      boolean res = bma.verifyWitness(x, witness);
      System.out.println(x + " in set S " + res);
    }

  }
  //  Single Non Element Witness
}
