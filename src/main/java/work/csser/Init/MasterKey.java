package work.csser.Init;

import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import work.csser.utils.CryptoPrimitives;
import work.csser.utils.SerializableElement;
import work.csser.utils.SerializationDemonstrator;

import java.io.Serializable;

/**
 * @author a1exlism
 * @className MasterKey
 * @description MK ← {Kx,Ki,Kz,Ks,p,q,s}
 * @since 2019/12/7 20:33
 */
public class MasterKey implements Serializable {
  private static final long serialVersionUID = 7314785127856819240L;

  //  big prime
  private long p;
  private long q;
  //  For generating index x
  private byte[] Kx = null;
  //  For generating index y
  private byte[] Ki = null;
  private byte[] Kz = null;
  //  For Generating stag
  private byte[] Ks = null;

  //  g_1,g_2,g_3 \in Z_n
  private SerializableElement g1 = null;
  private SerializableElement g2 = null;
  private SerializableElement g3 = null;
  //  private key s \xleftarrow{random select} Z_r
  private SerializableElement s = null;

  private MasterKey(long p, long q, byte[] Kx, byte[] Ki, byte[] Kz, byte[] Ks,
                    SerializableElement g1, SerializableElement g2, SerializableElement g3, SerializableElement s) {
    //  TODO: check why origin codes | kx,kz,ks
    this.p = p;
    this.q = q;
    this.Kx = Kx;
    this.Ki = Ki;
    this.Kz = Kz;
    this.Ks = Ks;
    this.g1 = g1;
    this.g2 = g2;
    this.g3 = g3;
    this.s = s;
  }

  /**
   * create write object into keys/master.key
   *
   * @return void
   * @method initial
   */
  public static void generate() {
    Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");

    byte[] Kx = CryptoPrimitives.randomBytes(128 / 8);
    byte[] Ki = CryptoPrimitives.randomBytes(128 / 8);
    byte[] Kz = CryptoPrimitives.randomBytes(128 / 8);
    byte[] Ks = CryptoPrimitives.randomBytes(128 / 8);

    SerializableElement g1 = new SerializableElement(pairing.getZr().newRandomElement());
    SerializableElement g2 = new SerializableElement(pairing.getZr().newRandomElement());
    SerializableElement g3 = new SerializableElement(pairing.getZr().newRandomElement());
    //  s???
    SerializableElement s = new SerializableElement(pairing.getZr().newRandomElement());

    MasterKey mk = new MasterKey(0, 0, Kx, Ki, Kz, Ks, g1, g2, g3, s);
    SerializationDemonstrator.serialize(mk, "keys", "master.key");
  }

  public long getP() {
    return p;
  }

  public long getQ() {
    return q;
  }

  public byte[] getKx() {
    return Kx;
  }

  public byte[] getKi() {
    return Ki;
  }

  public byte[] getKz() {
    return Kz;
  }

  public byte[] getKs() {
    return Ks;
  }

  public SerializableElement getG1() {
    return g1;
  }

  public SerializableElement getG2() {
    return g2;
  }

  public SerializableElement getG3() {
    return g3;
  }

  public SerializableElement getS() {
    return s;
  }
}
