package work.csser;

import work.csser.BMAccumulator.Accumulator;
import work.csser.BMAccumulator.Witness;


/**
 * @author a1exlism
 * @description Proof: non-membership witness
 * @className Proof
 * @since 2020/4/27 13:26
 */
public class Proof {
  //  Search Case
  private int CASE;
  private Witness proof1;
  private Witness proof2;
  private Accumulator accStag;
  private Accumulator accXSet;
  private String stagw1;

  public Proof(Witness proof1, Witness proof2,
               Accumulator accStag, Accumulator accXSet) {
    this.proof1 = proof1;
    this.proof2 = proof2;
    this.accStag = accStag;
    this.accXSet = accXSet;
  }

  public void setCase(int CASE) {
    this.CASE = CASE;
  }

  public void setStagw1(String stagw1) {
    this.stagw1 = stagw1;
  }

  public int getCASE() {
    return CASE;
  }

  public Witness getProof1() {
    return proof1;
  }

  public Witness getProof2() {
    return proof2;
  }

  public Accumulator getAccStag() {
    return accStag;
  }

  public Accumulator getAccXSet() {
    return accXSet;
  }

  public String getStagw1() {
    return stagw1;
  }

//  public static boolean Verify(Proof proof, ArrayList<String> Rw1, ArrayList<String> R,
//                               ArrayList<String> kws) {
//
//  }
}
