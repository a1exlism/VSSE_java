package work.csser.BMAccumulator;

import it.unisa.dia.gas.jpbc.Element;
import work.csser.utils.SerializableElement;
import work.csser.utils.SerializationDemonstrator;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Public Key: set {g^{s^i}|1 ≤ i ≤ t}; t, upper bound on |X|
 *
 * @author a1exlism
 * @className BAPublicKey
 * @since 2020/4/9 15:54
 */
public class BMAPublicKey implements Serializable {

  private static final long serialVersionUID = -3011602482479597559L;

  private ArrayList<SerializableElement> pk = null;

  public ArrayList<SerializableElement> getPk() {
    return pk;
  }

  public BMAPublicKey(ArrayList<SerializableElement> pk) {
    this.pk = pk;
  }

  /*
   * TODO: q of ${pk}$: `input set X upper bound`(length).
   *  and should return a g^{k^i} set
   * t in paper Wang.et.al
   */
  public static BMAPublicKey generateKey(BMAPrivateKey BMAMK, int q) {
    Element g = BMAMK.getG().getElement().duplicate();
    Element k = BMAMK.getK().getElement().duplicate();

    //  ATTENTION: Public Key also get the `g` value
    ArrayList<SerializableElement> pk = new ArrayList<>();
    for (int i = 0; i < q + 1; ++i) {
      Element expK = k.duplicate().pow(new BigInteger(i + ""));
      Element ele = g.duplicate().powZn(expK.duplicate());
      pk.add(new SerializableElement(ele));
    }


    BMAPublicKey BMAPK = new BMAPublicKey(pk);

    SerializationDemonstrator.serialize(BMAPK, "keys", "BMA_public.key");
    return BMAPK;
  }

  public static BMAPublicKey readKey() {
    return SerializationDemonstrator.deserialize("keys", "BMA_public.key");
  }


}
