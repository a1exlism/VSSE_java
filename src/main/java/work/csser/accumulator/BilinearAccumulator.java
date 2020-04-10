package work.csser.accumulator;

import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import work.csser.utils.SerializableElement;

import java.io.Serializable;
import java.util.List;

/**
 * @author a1exlism
 * @reference Supporting Non-membership Proofs with Bilinear-map Accumulators
 * @reference http://eprint.iacr.org/2008/538
 * @className BilinearAccumulator
 * @since 2020/4/9 14:11
 */
public class BilinearAccumulator implements Serializable {

  private static final long serialVersionUID = -8967716927815684208L;
  private static BAPrivateKey BAMK;
  private static BAPublicKey BAPK;
  private static Pairing pairing;

  //  initial
  static {
    BAMK = BAPrivateKey.readKey();
    BAPK = BAPublicKey.readKey();
    pairing = PairingFactory.getPairing("params/curves/a.properties");
  }

  //  maps sets in Z_p^*
  private List<SerializableElement> X = null;

}
