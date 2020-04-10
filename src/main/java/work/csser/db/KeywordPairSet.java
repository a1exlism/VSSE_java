package work.csser.db;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * return Keyword Pair`s` for 1 keyword - 1 stagW
 *
 * @author a1exlism
 * @className KeywordPairSet
 * @since 2019/12/25 11:33
 */
public class KeywordPairSet {
  /*
   * TSet:
   * cipher e;
   * label  y
   */
  private ArrayList<TSet> TSets;
  /*
   * all XSet match single Keyword
   */
  private ArrayList<String> XSets;
  /*
   * Trapdoor of the given Keyword
   */
  private byte[] stagw;

  public ArrayList<TSet> getTSets() {
    return this.TSets;
  }

  public ArrayList<String> getXSets() {
    return this.XSets;
  }

  public byte[] getStagw() {
    return this.stagw;
  }

  public KeywordPairSet(ArrayList<TSet> TSets, ArrayList<String> XSets, byte[] stagw) {
    this.TSets = TSets;
    this.XSets = XSets;
    this.stagw = stagw;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("============ Keyword Pair ============");
    sb.append(System.lineSeparator());
    sb.append("=== TSets:");
    sb.append(System.lineSeparator());
    int i = 0;
    for (TSet TSet : TSets) {
      sb.append("#").append(i);
      sb.append(System.lineSeparator());
      sb.append("\tlabel l: ").append(TSet.getL()).append(" ");
      sb.append(System.lineSeparator());
      sb.append("\tcipher e: ").append(Arrays.toString(TSet.getE())).append(" ");
      sb.append(System.lineSeparator());
      sb.append("\tindex y: ").append(TSet.getY().toString());
      sb.append(System.lineSeparator());
      i++;
    }
    sb.append("=== XSets: ");
    sb.append(System.lineSeparator());
    i = 0;
    for (String xSet : XSets) {
      sb.append("#").append(i);
      sb.append(System.lineSeparator());
      sb.append("\t").append(xSet);
      sb.append(System.lineSeparator());
      i++;
    }
    sb.append("=== stagw: ").append(Arrays.toString(stagw));
    sb.append(System.lineSeparator());
    return sb.toString();
  }
}
