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
 * WARNING
 * Zr中的元素 20
 * <br>G1中的元素 128
 * <br>G2中的元素 128
 * <br>GT中的元素 128
 * <br>我们只处理G1 Zr中的元素，所以我是使用字节长度区分的
 * <br>但是如果涉及到了GT中的元素，则序列化会出错
 * @from Zhongjun Zhang
 *
 * @author a1exlism
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

  public Element getElement() {
    return this.element;
  }

  public void setElement(Element e) {
    this.element = e;
  }

  /**
   * Serialize this instance.
   *
   * @param out Target to which this instance is written.
   * @throws IOException Thrown if exception occurs during serialization.
   */
  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeObject(this.element.toBytes());
  }

  /**
   * Deserialize this instance from input stream.
   *
   * @param in Input Stream from which this instance is to be deserialized.
   * @throws IOException            Thrown if error occurs in deserialization.
   * @throws ClassNotFoundException Thrown if expected class is not found.
   */
  private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
    //  Bilinear Pairing
    Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");
    byte[] arr = IOUtils.toByteArray(in);
    //  TODO: check the method
    if (arr.length == 20) {
      this.element = pairing.getZr().newRandomElement();
    } else if (arr.length == 128) {
      this.element = pairing.getG1().newRandomElement();
    } else {
      throw new IOException("序列化出错，请检查你的元素属于那个域");
    }
    this.element.setFromBytes(arr);
  }

  @Override
  public String toString() {
    return this.element.toString();
  }
}
