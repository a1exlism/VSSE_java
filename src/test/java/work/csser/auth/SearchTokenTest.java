package work.csser.auth;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author a1exlism
 * @className SearchTokenTest
 * @since 2020/1/3 19:55
 */
public class SearchTokenTest {
  @Test
  public void KeyGen() throws Exception {
    ArrayList<String> kws = new ArrayList<>() {
      private static final long serialVersionUID = -6632060874595165291L;

      {
        add("aaa");
        add("bbb");
        add("ccc");
      }
    };
    int lfc = 10;
    SearchToken st = SearchToken.TokenGen(kws, lfc);
    System.out.println(Arrays.toString(st.getStag()));
    System.out.println(Arrays.deepToString(st.getXToken()));
  }
}
