package work.csser.accumulator;

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
public class BAPrivateKey implements Serializable {

  private static final long serialVersionUID = -8618975498800886975L;

  //  Generator of G
  private SerializableElement g;
  //  trapdoor information \in Z_p^* OR the Secret key s
  private SerializableElement k;

  public BAPrivateKey(SerializableElement g, SerializableElement k) {
    this.g = g;
    this.k = k;
  }

  public static void generateKey() {
    Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");

    SerializableElement g = new SerializableElement(pairing.getG1().newRandomElement());
    SerializableElement k = new SerializableElement(pairing.getZr().newRandomElement());

    BAPrivateKey BAMK = new BAPrivateKey(g, k);

    SerializationDemonstrator.serialize(BAMK, "keys", "BA_private.key");

  }

  public static BAPrivateKey readKey() {
    return SerializationDemonstrator.deserialize("keys", "BA_private.key");
  }

  public SerializableElement getG() {
    return g;
  }

  public SerializableElement getK() {
    return k;
  }
}
