package work.csser.db;

import java.util.ArrayList;

/**
 * return keyword - filename(fID) pair`s`
 *
 * @author a1exlism
 * @className KeywordPair
 * @since 2019/12/25 13:22
 */
public class KeywordPair {
  private String keyword;
  private ArrayList<String> filenames;

  public KeywordPair(String keyword, ArrayList<String> filenames) {
    this.keyword = keyword;
    this.filenames = filenames;
  }

  public String getKeyword() {
    return keyword;
  }

  public ArrayList<String> getFilenames() {
    return filenames;
  }
}
