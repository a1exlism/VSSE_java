package work.csser;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import work.csser.Init.MasterKey;
import work.csser.Init.PublicKey;
import work.csser.utils.Common;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Algorithm 2.TokenGen() For `authenticated` search
 *
 * @author a1exlism
 * @className SearchToken
 * @since 2020/1/3 10:57
 */
public class SearchToken {
  //  1st keyword w_1 query trapdoor
  private byte[] stag = null;
  private Element[][] xToken = null;

  public SearchToken(byte[] stag, Element[][] xToken) {
    this.stag = stag;
    this.xToken = xToken;
  }

  public byte[] getStag() {
    return this.stag;
  }

  public Element[][] getXToken() {
    return this.xToken;
  }

  /**
   * Generate single keyword trapdoor
   *
   * @param keyword:
   * @return byte[]
   * @method stagGen
   * @params [keyword]
   */
  private static byte[] stagGen(String keyword) throws UnsupportedEncodingException {
    // initial environment
    Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");
    MasterKey mk = MasterKey.readKey();

    Element g1 = mk.getG1().getElement().getImmutable();
    Element w1 = pairing.getZr().newElement();
    w1.setFromBytes(keyword.getBytes(StandardCharsets.UTF_8));
    Element w1Inv = w1.duplicate().invert();
    return Common.PRF_F(mk.getKs(), g1.powZn(w1Inv));
  }

  /**
   * create authenticated search token for search
   *
   * @param kws: all search keywords
   * @param lfc: least frequency keyword file count, default with keyword w_1
   * @return work.csser.SearchToken
   * @method TokenGen
   * @params [kws, lfc]
   */
  public static SearchToken TokenGen(ArrayList<String> kws, int lfc) throws Exception {
    // initial environment
    Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");
    MasterKey mk = MasterKey.readKey();
    PublicKey pk = PublicKey.readKey();

    // sterm: the least frequent keyword in a given search query (assume w_1)
    String sterm = kws.get(0);
    byte[] stag = stagGen(sterm);
    //  pro-Generate all w_i invert value
    ArrayList<Element> wInvs = new ArrayList<>();
    for (String kw : kws) {
      Element w = pairing.getZr().newElement();
      w.setFromBytes(kw.getBytes(StandardCharsets.UTF_8));
      Element wInv = w.duplicate().invert().getImmutable();
      wInvs.add(wInv);
    }
    Element w1Inv = wInvs.get(0);

    // xToken generate
    Element xToken[][] = new Element[lfc][kws.size()];

    Element g = pk.getG().getElement().getImmutable();
    Element g2 = mk.getG2().getElement().getImmutable();
    Element g3 = mk.getG3().getElement().getImmutable();
    String expAI = g2.powZn(w1Inv).toString();
    //  until the server stops
    for (int c = 0; c < lfc; ++c) {
      for (int i = 2; i < kws.size(); ++i) {
        Element expA = Common.PRF_Fp(mk.getKz(), expAI + c);
        Element expB = Common.PRF_Fp(mk.getKx(), g3.powZn(wInvs.get(i)).toString());

        xToken[c][i] = g.powZn(expA.duplicate().mul(expB));
      }
    }
    return new SearchToken(stag, xToken);
  }
}
