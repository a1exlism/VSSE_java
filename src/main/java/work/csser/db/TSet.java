package work.csser.db;

import it.unisa.dia.gas.jpbc.Element;

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
  private Element y;
  //  keywords - TSet
  private String keyword;

  public TSet(String l, byte[] e, Element y, String keyword) {
    this.l = l;
    this.e = e;
    this.y = y;
    this.keyword = keyword;
  }

  public byte[] getE() {
    return e;
  }

  public String getL() {
    return l;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("===您正在查看TSet的信息===");
    sb.append(System.lineSeparator());
    sb.append("\t" + "标签label: ").append(l);
    sb.append(System.lineSeparator());
    sb.append("\t" + "密文e: ").append(Arrays.toString(e));
    sb.append(System.lineSeparator());
    sb.append("\t" + "索引y: ").append(y.toString());
    sb.append(System.lineSeparator());
    sb.append("====================================");
    return sb.toString();
  }
}
