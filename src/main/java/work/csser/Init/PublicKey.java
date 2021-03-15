package work.csser.Init;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import work.csser.utils.SerializableElement;
import work.csser.utils.SerializationDemonstrator;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author a1exlism
 * @className PublicKey
 * @description
 * @since 2019/12/7 20:33
 */
public class PublicKey implements Serializable {
  private static final long serialVersionUID = 5676379982230560665L;
  // n = pq
  private long n;
  //  g \in \mathbb{G}
  private SerializableElement g;
  //  [g^{s^1},...,g^{s^t}]
  private List<SerializableElement> pk;

  public PublicKey(long n, SerializableElement g, List<SerializableElement> pk) {
    this.n = n;
    this.g = g;
    this.pk = pk;
  }

  public SerializableElement getG() {
    return this.g;
  }

  /**
   * Generate PublicKey PK
   *
   * @param MK: master key MK
   * @param t:  upper bound on the number of the cardinality(by Paper
   *            maybe means 'the MAX number of documents associated keyword in Setup'
   * @return void
   * @method generateKey
   * @params [MK, t]
   */
  public static void generateKey(MasterKey MK, int t) {
    Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");
    Element g = pairing.getG1().newRandomElement();
    //  accumulator key, sk
    Element s = MK.getS().getElement();
    SerializableElement sG = new SerializableElement(g);
    List<SerializableElement> pk = new ArrayList<>();
    for (int i = 1; i < t + 1; i++) {
      Element exp = s.duplicate().pow(new BigInteger(i + ""));
      Element ele = g.duplicate().powZn(exp);
      SerializableElement sEle = new SerializableElement(ele);
      pk.add(sEle);
    }
    PublicKey PK = new PublicKey(0, sG, pk);

    SerializationDemonstrator.serialize(PK, "keys", "public.key");
  }

  public static PublicKey readKey() {
    return SerializationDemonstrator.deserialize("keys", "public.key");
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("===============Public Key: PK{n,g,pk}====================");
    sb.append(System.lineSeparator());
    sb.append("n: ").append(n);
    sb.append(System.lineSeparator());
    sb.append("g: ").append(g.getElement().toString());
    sb.append(System.lineSeparator());
    sb.append("Public key of Accumulator: ");
    sb.append(System.lineSeparator());
    for (int i = 0; i < pk.size(); i++) {
      sb.append("#").append(i).append(": ").append(pk.get(i).getElement().toString());
      sb.append(System.lineSeparator());
    }
    sb.append("==============================================");
    return sb.toString();
  }
}
