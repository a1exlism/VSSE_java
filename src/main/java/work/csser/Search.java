package work.csser;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import work.csser.Init.MasterKey;
import work.csser.Init.PublicKey;
import work.csser.accumulator.Witness;
import work.csser.db.DBModule;
import work.csser.db.TSet;
import work.csser.utils.Common;
import work.csser.utils.SerializableElement;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author a1exlism
 * @className Search
 * @since 2020/4/27 13:19
 */
public class Search {
  final static private Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");
  private static MasterKey MK = MasterKey.readKey();
  private static PublicKey PK = PublicKey.readKey();
  private ArrayList<ArrayList<byte[]>> Rw1_R;
  private Proof proof;


  /**
   * @param st        :        search token
   * @param tableName : Database table name
   * @return java.util.ArrayList<ArrayList < byte [ ]>>
   * @description Alg.3. Search: search with search token from DB
   * @method Search
   * @params [st, tableName]
   */
  public static ArrayList<ArrayList<byte[]>> search(SearchToken st, String tableName) throws UnsupportedEncodingException, SQLException {
    ArrayList<byte[]> R = new ArrayList<>();
    //  listE, e_c
    ArrayList<byte[]> Rw1 = new ArrayList<>();
    //  listY, y_c
    ArrayList<SerializableElement> listY = new ArrayList<>();
    ArrayList<String> B = new ArrayList<>();
    Witness proof1;
    Witness proof2;
    /*
     * Phase.1
     */
    int c = 0;
    byte[] stag = st.getStag();
    while (true) {
      byte[] l = Common.PRF_F(stag, c + "");
      String label = Arrays.toString(l);
      TSet tSet = DBModule.getTSet(label, tableName);
      if (tSet == null) {
        break;
      }
      listY.add(tSet.getY());
      Rw1.add(tSet.getE());
      c++;
    }
    if (Rw1.isEmpty()) {
      //  Case 1
    }
    /*
     * Phase.2
     */
  }

}
