package work.csser.accumulator;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import work.csser.utils.SerializableElement;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

  //  maps sets in Z_p^* | Element Set
  private List<SerializableElement> X = null;
  //  RAW input | String List
  private List<String> Y = null;
  //  accumulator value; Acc(S)
  private SerializableElement AkX = null;
  //  AkX's exp product
  private SerializableElement fXk = null;

  //  return original input set X(string type)
  public List<String> getY() {
    return Y;
  }

  /**
   * @param x: string
   * @return it.unisa.dia.gas.jpbc.Element
   * @description transfer String into Element
   * @method str2Ele
   * @params [x]
   */
  private static Element str2Ele(String x) {
    return pairing.getZr().newElementFromHash(
        x.getBytes(StandardCharsets.UTF_8), 0, x.getBytes(StandardCharsets.UTF_8).length
    );
  }

  //  Acc(S) = A_k(X) = g^{\Pi_{i=1}^{N} x_i+s} = g^{f_X(k)}
  public BilinearAccumulator(List<String> Y) {
    this.Y = Y;
    this.X = new ArrayList<SerializableElement>();
    //  initial a identity element
    Element product = pairing.getZr().newOneElement();
    for (String y : Y) {
      Element x = str2Ele(y);
      X.add(new SerializableElement(x));
      product.mul(x.duplicate().add(BAMK.getK().getElement()));
    }
    fXk = new SerializableElement(product);
    AkX = new SerializableElement(BAMK.getG().getElement().duplicate().powZn(product));
  }

  /**
   * @param x: verify Element x
   * @return work.csser.accumulator.Witness
   * @method getWitness
   * @params [x]
   */
  private Witness getWitness(Element x) {
    Element g = BAMK.getG().getElement().getImmutable();
    Element k = BAMK.getK().getElement().getImmutable();
    Element exp = fXk.getElement().duplicate().div(x.duplicate().add(k));
    Element Wx = g.powZn(exp);
    return new Witness(new SerializableElement(Wx), null);
  }

  public Witness getElementWitness(String y) throws Exception {
    return this.getWitness(str2Ele(y));
  }

  /**
   * @param x:       detect value x
   * @param witness: witness for x \in X
   * @return boolean
   * @method verifyWitness
   * @params [x, witness]
   */
  private boolean verifyWitness(Element x, Witness witness) {
    //  g = g^{k^0}
    Element g = BAPK.getPk().get(0).getElement().getImmutable();
    //  h = g^{k^1}
    Element h = BAPK.getPk().get(1).getElement().getImmutable();
    Element Wx = witness.getWy().getElement().duplicate();
    Element RE = g.powZn(x).mul(h);
    Element e1 = pairing.pairing(Wx, RE);
    Element e2 = pairing.pairing(AkX.getElement(), g);
    return e1.isEqual(e2);
  }

  /**
   * @param y: Elemental member
   * @return work.csser.accumulator.NonWitness
   * @formula: -\prod_{x \in X}(x-y)
   * @method getNonWitness
   * @params [y]
   */
  private Witness getNonWitness(Element y) {
    Element product = pairing.getZr().newOneElement();
    //  Element Set X
    for (SerializableElement x : X) {
      product = product.duplicate().mul(
          x.getElement().duplicate().sub(y)
      );
    }
    Element Uy = product.duplicate().negate();
    Element exp = fXk.getElement().duplicate().add(Uy);
    exp.div(y.duplicate().add(BAMK.getK().getElement()));
    Element Wy = BAMK.getG().getElement().duplicate().powZn(exp);
    return new Witness(new SerializableElement(Wy), new SerializableElement(Uy));
  }

  /**
   * @param y: string member(raw input
   * @return work.csser.accumulator.NonWitness
   * @method getElementNonWitness
   * @params [y]
   */
  public Witness getElementNonWitness(String y) throws Exception {
    return this.getNonWitness(str2Ele(y));
  }


  /**
   * @param y:          raw string need to verify
   * @param nonWitness:
   * @return boolean
   * @description LaTeX Formula(4) 是否构成证据
   * e\left(w_{y}, g^{y} \cdot h\right)=e\left(A_{\kappa}(X) \cdot g^{u_{y}}, g\right)
   * Satisfy 2 conditions => verification test establish
   * @method verifyNonWitness
   * @params [y, nonWitness]
   */
  public boolean verifyNonWitness(String y, Witness nonWitness) {
//    Element Ey = pairing.getZr().newElementFromHash(y.getBytes(StandardCharsets.UTF_8), 0, y.getBytes(StandardCharsets.UTF_8).length);
    Element Ey = str2Ele(y);
    //  Exponentiation - base: g
    Element g = ((SerializableElement) BAPK.getPk().get(0)).getElement().duplicate();
    //  publicly known group element : h=g^{\kappa}
    Element h = ((SerializableElement) BAPK.getPk().get(1)).getElement().duplicate();
    Element Wy = nonWitness.getWy().getElement().duplicate();
    Element Uy = nonWitness.getUy().getElement().duplicate();
    Element rightE = g.duplicate().powZn(Ey).duplicate()
        .mul(h.duplicate());
    //  1: left pairing; 2: right pairing
    Element e1 = pairing.pairing(Wy, rightE);
    Element e2 = pairing.pairing(this.AkX.getElement(), g);
    //  TODO: check the verify case
    return e1.isEqual(e2) && !Uy.isEqual(pairing.getZr().newZeroElement());
  }

  /**
   * @param Y1: Subset S_1
   * @return work.csser.accumulator.Witness
   * @method getSubsetWitness
   * @scheme Wang Scheme
   * @params [Y1]
   */
  private Witness getSubsetWitness(List<String> Y1) {
    //  LeftSet \in S-S_1(Y-Y_1)
    List<String> leftSet = new ArrayList<>(Y);
    leftSet.removeAll(Y1);
    Element expS = pairing.getZr().newOneElement();
    Element expS1 = pairing.getZr().newOneElement();
    Element s = BAMK.getK().getElement().duplicate();
    Element g = BAMK.getG().getElement().duplicate();
    for (String y : leftSet) {
      Element yE = str2Ele(y);
      expS = expS.duplicate().mul(yE.add(s));
    }

    Element W_Subset = g.powZn(expS);
    //  U_Subset 不需要计算, verify需要用检测的subset进行计算
    return new Witness(new SerializableElement(W_Subset), null);
  }

  /**
   * @param Y1:            subset raw input S_1
   * @param subsetWitness: W_{S_1,S} subset witness
   * @return boolean is S_1(Y1) belong to S(Y)
   * @method verifySubsetWitness
   * @params [Y1, subsetWitness]
   * @scheme Wang Scheme
   */
  public boolean verifySubsetWitness(List<String> Y1, Witness subsetWitness) {
    Element expS1 = pairing.getZr().newOneElement();
    Element s = BAMK.getK().getElement().getImmutable();
    Element g = BAMK.getG().getElement().getImmutable();
    //  subset S_1
    for (String y : Y1) {
      Element yE = str2Ele(y);
      expS1 = expS1.duplicate().mul(yE.add(s));
    }
    Element U_Subset = g.powZn(expS1);
    Element e1 = pairing.pairing(subsetWitness.getWy().getElement(), U_Subset);
    Element e2 = pairing.pairing(AkX.getElement(), g);
    return e1.isEqual(e2);
  }

  //  TODO: add subset Non-Witness
}
