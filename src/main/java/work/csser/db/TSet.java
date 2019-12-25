package work.csser.db;

import it.unisa.dia.gas.jpbc.Element;
import work.csser.utils.SerializableElement;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author a1exlism
 * @className TSet
 * @description
 * @since 2019/12/7 20:37
 */
public class TSet implements Serializable {
  private static final long serialVersionUID = 6231930749184494820L;
  //  label
  private String l;
  //  cipher from fileName
  private byte[] e;
  //  index
  private SerializableElement y;
  //  keywords - TSet
  private String keyword;

  public TSet(String l, byte[] e, Element y, String keyword) {
    this.l = l;
    this.e = e;
    this.y = new SerializableElement(y);
    this.keyword = keyword;
  }

  public byte[] getE() {
    return e;
  }

  public String getL() {
    return l;
  }

  public SerializableElement getY() {
    return y;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("=== TSet Info ===");
    sb.append(System.lineSeparator());
    sb.append("\t" + "标签 Tag | label: ").append(l);
    sb.append(System.lineSeparator());
    sb.append("\t" + "密文 Cipher | e: ").append(Arrays.toString(e));
    sb.append(System.lineSeparator());
    sb.append("\t" + "索引 Index | y: ").append(y.toString());
    sb.append(System.lineSeparator());
    sb.append("====================================");
    return sb.toString();
  }
}
