package work.csser.BMAccumulator;

import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import work.csser.utils.SerializableElement;
import work.csser.utils.SerializationDemonstrator;

import java.io.Serializable;

/**
 * @author a1exlism
 * @className BAPrivateKey
 * @since 2020/4/9 15:54
 */
public class BMAPrivateKey implements Serializable {

  private static final long serialVersionUID = -8618975498800886975L;

  //  Generator of G
  private SerializableElement g;
  //  trapdoor information \in Z_p^* OR the `Secret key` k
  private SerializableElement k;

  public BMAPrivateKey(SerializableElement g, SerializableElement k) {
    this.g = g;
    this.k = k;
  }

  public static BMAPrivateKey generateKey() {
    Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");

    SerializableElement g = new SerializableElement(pairing.getG1().newRandomElement());
    SerializableElement k = new SerializableElement(pairing.getZr().newRandomElement());

    BMAPrivateKey BMAMK = new BMAPrivateKey(g, k);

    SerializationDemonstrator.serialize(BMAMK, "keys", "BMA_private.key");

    return BMAMK;
  }

  public static BMAPrivateKey readKey() {
    return SerializationDemonstrator.deserialize("keys", "BMA_private.key");
  }

  public SerializableElement getG() {
    return g;
  }

  public SerializableElement getK() {
    return k;
  }
}
