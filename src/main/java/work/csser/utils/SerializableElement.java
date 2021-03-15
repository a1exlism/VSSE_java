package work.csser.utils;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * <br> len(x \in Z_r) = 20
 * <br> len(x \in G_1/G_2) = 128
 * <br> len(x \in G_T) = 128
 * <br> Can't detect whether element belong to G_1/2 or G_T by byte length
 *
 * @author a1exlism
 * @refer /test/../utils/PairingElementTest.lengthTest
 * @className SerializableElement
 * @description Serializable function for `it.unisa.dia.gas.jpbc.Element`
 * @since 2019/12/8 13:36
 */
public class SerializableElement implements Serializable {
  private static final long serialVersionUID = 6386861894456723631L;

  private Element element;

  public SerializableElement(final Element e) {
    super();
    this.element = e;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public Element getElement() {
    return this.element;
  }

  public void setElement(Element e) {
    this.element = e;
  }

  /**
   * Serialize this instance.
   *
   * @param oos Target to which this instance is written.
   * @throws IOException Thrown if exception occurs during serialization.
   */
  private void writeObject(ObjectOutputStream oos) throws IOException {
    byte[] x = this.element.toBytes();
    oos.write(x);
  }

  /**
   * Deserialize this instance from input stream.
   *
   * @param ois Input Stream from which this instance is to be deserialized.
   * @throws IOException            Thrown if error occurs in deserialization.
   * @throws ClassNotFoundException Thrown if expected class is not found.
   */
  private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
    //  Bilinear Pairing
    Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");
    Element z = pairing.getZr().newRandomElement();
    Element g = pairing.getG1().newRandomElement();

    byte[] arr = IOUtils.toByteArray(ois);
    if (arr.length == z.getLengthInBytes()) {
      this.element = z;
    } else if (arr.length == g.getLengthInBytes()) {
      this.element = g;
    } else {
      throw new IOException("Element Serialize Error, check the Field");
    }
    this.element.setFromBytes(arr);
  }

  @Override
  public String toString() {
    return this.element.toString();
  }
}
