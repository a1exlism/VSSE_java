package work.csser.accumulator;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import work.csser.utils.SerializableElement;

import java.io.Serializable;
import java.nio.charset.Charset;
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
  //  accumulator value
  private SerializableElement AkX = null;
  //  AkX's exp product
  private SerializableElement fXk = null;

  //  return original input set X(string type)
  public List<String> getY() {
    return Y;
  }

  public BilinearAccumulator(List<String> Y) {
    this.Y = Y;
    this.X = new ArrayList<SerializableElement>();
    //  initial a identity element
    Element product = pairing.getZr().newOneElement();
    for (String y : Y) {
      Element x = pairing.getZr().newElementFromHash(
          y.getBytes(StandardCharsets.UTF_8), 0, y.getBytes(StandardCharsets.UTF_8).length);
      X.add(new SerializableElement(x));
      product.mul(x.duplicate().add(BAMK.getK().getElement()));
    }
    fXk = new SerializableElement(product);
    AkX = new SerializableElement(BAMK.getG().getElement().duplicate().powZn(product));
  }

  /**
   * @param y: Elemental member
   * @return work.csser.accumulator.NonWitness
   * @formula: -\prod_{x \in X}(x-y)
   * @method getNonWitness
   * @params [y]
   */
  private NonWitness getNonWitness(Element y) {
    Element product = pairing.getZr().newOneElement();
    //  Element Set X
    for (SerializableElement x : X) {
      product = product.duplicate().mul(
          x.getElement().duplicate().sub(y.duplicate())
      );
    }
    Element Uy = product.duplicate().negate();
    Element exp = fXk.getElement().duplicate().add(Uy);
    exp.div(y.duplicate().add(BAMK.getK().getElement()));
    Element Wy = BAMK.getG().getElement().duplicate().powZn(exp);
    return new NonWitness(new SerializableElement(Uy), new SerializableElement(Wy));
  }

  /**
   * @param y: string member(raw input
   * @return work.csser.accumulator.NonWitness
   * @method getElementNonWitness
   * @params [y]
   */
  public NonWitness getElementNonWitness(String y) throws Exception {
    Element e = pairing.getZr().newElementFromHash(
        y.getBytes(StandardCharsets.UTF_8), 0, y.getBytes(StandardCharsets.UTF_8).length
    );
    return this.getNonWitness(e);
  }

  /**
   * @param y:          raw string need to verify
   * @param nonWitness:
   * @return boolean
   * @description LaTeX Formula(4) 是否构成证据
   * e\left(w_{y}, g^{y} \cdot h\right)=e\left(A_{\kappa}(X) \cdot g^{u_{y}}, g\right)
   * Satisfy 2 conditions => verification test establish
   * @method establishNonWitness
   * @params [y, nonWitness]
   */
  public boolean establishNonWitness(String y, NonWitness nonWitness) {
    Element Ey = pairing.getZr().newElementFromHash(
        y.getBytes(StandardCharsets.UTF_8), 0, y.getBytes(StandardCharsets.UTF_8).length);
    //  Exponentiation - base: g
    Element g = ((SerializableElement) BAPK.getPk().get(0)).getElement().duplicate();
    //  publicly known group element : h=g^{\kappa}
    Element h = ((SerializableElement) BAPK.getPk().get(1)).getElement().duplicate();
    Element Wy = nonWitness.getWy().getElement().duplicate();
    Element Uy = nonWitness.getUy().getElement().duplicate();
    Element rightE =
        g.duplicate().powZn(Ey).duplicate()
            .mul(h.duplicate());
    //  1: left pairing; 2: right pairing
    Element e1 = pairing.pairing(Wy, rightE);
    Element e2 = pairing.pairing(this.AkX.getElement(), g);
    //  TODO: check the verify case
    return e1.isEqual(e2) && !Uy.isEqual(pairing.getZr().newZeroElement());
  }



}
