package work.csser.accumulator;

import it.unisa.dia.gas.jpbc.Element;
import work.csser.utils.SerializableElement;
import work.csser.utils.SerializationDemonstrator;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Public Key: set {g^{s^i}|1 ≤ i ≤ t}; t, upper bound on |X|
 *
 * @author a1exlism
 * @className BAPublicKey
 * @since 2020/4/9 15:54
 */
public class BAPublicKey implements Serializable {

  private static final long serialVersionUID = -3011602482479597559L;

  private ArrayList<SerializableElement> pk = null;

  public ArrayList<SerializableElement> getPk() {
    return pk;
  }

  public BAPublicKey(ArrayList<SerializableElement> pk) {
    this.pk = pk;
  }

  /*
   * TODO: Multi-user Public Key generation is related with `input set X upper bound`.
   *  and should return a g^{k^i} set
   */
  public static void generateKey(BAPrivateKey BAMK, int q) {
    Element g = BAMK.getG().getElement().duplicate();
    Element k = BAMK.getK().getElement().duplicate();

    ArrayList<SerializableElement> pk = new ArrayList<>();
    for (int i = 0; i < q + 1; ++i) {
      Element expK = k.duplicate().pow(new BigInteger(i + ""));
      Element ele = g.duplicate().powZn(expK.duplicate());
      pk.add(new SerializableElement(ele));
    }


    BAPublicKey BAPK = new BAPublicKey(pk);

    SerializationDemonstrator.serialize(BAPK, "keys", "BA_public.key");
  }

  public static BAPublicKey readKey() {
    return SerializationDemonstrator.deserialize("keys", "BA_public.key");
  }


}
