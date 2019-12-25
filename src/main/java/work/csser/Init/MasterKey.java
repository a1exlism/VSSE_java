package work.csser.Init;

import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import work.csser.utils.CryptoPrimitives;
import work.csser.utils.SerializableElement;
import work.csser.utils.SerializationDemonstrator;

import java.io.Serializable;
import java.util.Arrays;

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
  public static void generateKey() {
    Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");

    byte[] Kx = CryptoPrimitives.randomBytes(128 / 8);
    byte[] Ki = CryptoPrimitives.randomBytes(128 / 8);
    byte[] Kz = CryptoPrimitives.randomBytes(128 / 8);
    byte[] Ks = CryptoPrimitives.randomBytes(128 / 8);

    SerializableElement g1 = new SerializableElement(pairing.getZr().newRandomElement());
    SerializableElement g2 = new SerializableElement(pairing.getZr().newRandomElement());
    SerializableElement g3 = new SerializableElement(pairing.getZr().newRandomElement());
    //  sk
    SerializableElement s = new SerializableElement(pairing.getZr().newRandomElement());

    MasterKey MK = new MasterKey(0, 0, Kx, Ki, Kz, Ks, g1, g2, g3, s);
    SerializationDemonstrator.serialize(MK, "keys", "master.key");

    //  For test
//    System.out.println("For Test: " + MK.toString());
  }

  public static MasterKey readKey() {
    return SerializationDemonstrator.deserialize("keys", "master.key");
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("===========Master Key:{K_X,K_I,K_Z,K_S,p,q,s}===========");
    sb.append(System.lineSeparator());
    sb.append("p: ").append(p);
    sb.append(System.lineSeparator());
    sb.append("q: ").append(q);
    sb.append(System.lineSeparator());
    sb.append("Kx: ").append(Arrays.toString(Kx));
    sb.append(System.lineSeparator());
    sb.append("Ki: ").append(Arrays.toString(Ki));
    sb.append(System.lineSeparator());
    sb.append("Kz: ").append(Arrays.toString(Kz));
    sb.append(System.lineSeparator());
    sb.append("Ks: ").append(Arrays.toString(Ks));
    sb.append(System.lineSeparator());
    sb.append("g1: ").append(g1.toString());
//    sb.append("g1 bytes: ").append(Arrays.toString(g1.getElement().toBytes()));
    sb.append(System.lineSeparator());
    sb.append("g2: ").append(g2.toString());
    sb.append(System.lineSeparator());
    sb.append("g3: ").append(g3.toString());
    sb.append(System.lineSeparator());
    sb.append("s: ").append(s.toString());
    sb.append(System.lineSeparator());
    sb.append("=================================================");
    return sb.toString();
  }
}
