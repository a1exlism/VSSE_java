package work.csser.db;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author a1exlism
 * @className DBModule
 * @since 2020/1/3 20:49
 */
public class DBModule {
  private static Connection conn;
  private static PreparedStatement pres;

  static {
    try {
      conn = DBConnection.getConnection();
      System.out.println("== DB Connected ==");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Insert keyword - files pair into vsse
   *
   * @param kps: KeywordPairSet
   * @return void
   * @method insertKeyPairSet
   * @params [kps]
   */
  public void insertKeyPairSet(KeywordPairSet kps) throws SQLException {
    /*
     * TSet insert
     * */
    ArrayList<TSet> TSets = kps.getTSets();
    String insertTSets = "INSERT INTO TSets(label,e,y,keyword) values (?,?,?,?)";
    pres = conn.prepareStatement(insertTSets);
    for (TSet ts : TSets) {
      pres.setString(1, ts.getL());
      pres.setBytes(2, ts.getE());
      pres.setBytes(3, ts.getY().getElement().duplicate().toBytes());
      pres.setString(4, ts.getKeyword());
      //  insert into queue
      pres.addBatch();
    }
    pres.executeBatch();
    /*
     * XSet insert;
     * TIPS: adapt to BloomFilter
     * */
    ArrayList<String> XSets = kps.getXSets();
    String insertXSets = "INSERT INTO XSets(xSet) VALUE (?)";
    pres = conn.prepareStatement(insertXSets);
    for (String xs : XSets) {
      pres.setString(1, xs);
      pres.addBatch();
    }
    pres.executeBatch();
    /*
     * stagws insert
     * */
    byte[] stag = kps.getStagw();
    String insertStagws = "INSERT INTO stagws(stagw) VALUE (?)";
    pres = conn.prepareStatement(insertStagws);
    pres.setString(1, Arrays.toString(stag));
    pres.execute();

    //  Close pre-statement
    pres.close();
  }

  /**
   * Reserved | insert tables with specific tables
   *
   * @param kps:         keyword pair set
   * @param tableTSets:
   * @param tableXSets:
   * @param tableStagws:
   * @return void
   * @method insertKeyPairSet
   * @params [kps, tableTSets, tableXSets, tableStagws]
   */
  public void insertKeyPairSet(KeywordPairSet kps, String tableTSets, String tableXSets, String tableStagws) throws SQLException {
    /*
     * TSet insert
     * */
    ArrayList<TSet> TSets = kps.getTSets();
    String insertTSets = "INSERT INTO " + tableTSets + "(label,e,y,keyword) values (?,?,?,?)";
    pres = conn.prepareStatement(insertTSets);
    for (TSet ts : TSets) {
      pres.setString(1, ts.getL());
      pres.setBytes(2, ts.getE());
      pres.setBytes(3, ts.getY().getElement().duplicate().toBytes());
      pres.setString(4, ts.getKeyword());
      //  insert into queue
      pres.addBatch();
    }
    pres.executeBatch();
    /*
     * XSet insert
     * */
    ArrayList<String> XSets = kps.getXSets();
    String insertXSets = "INSERT INTO " + tableXSets + "(xSet) VALUE (?)";
    pres = conn.prepareStatement(insertXSets);
    for (String xs : XSets) {
      pres.setString(1, xs);
      pres.addBatch();
    }
    pres.executeBatch();
    /*
     * stagws insert
     * */
    byte[] stag = kps.getStagw();
    String insertStagws = "INSERT INTO " + tableStagws + "(stagw) VALUE (?)";
    pres = conn.prepareStatement(insertStagws);
    pres.setString(1, Arrays.toString(stag));
    pres.execute();

    //  Close pre-statement
    pres.close();
  }

  /**
   * @param label:
   * @return work.csser.db.TSet
   * @description TSet SQL query with default DB table
   * @method getTSet
   * @params [label]
   */
  public static TSet getTSet(String label) throws SQLException {
    Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");

    TSet ts = null;
    String queryStr = "SELECT * FROM TSets WHERE label=(?) LIMIT 1";
    pres = conn.prepareStatement(queryStr);
    pres.setString(1, label);
    ResultSet res = pres.executeQuery();
    Element ele = pairing.getZr().newElement();
    while (res.next()) {
      String l = res.getString(1);
      byte[] e = res.getBytes(2);
      Element y = ele.duplicate();
      y.setFromBytes(res.getBytes(3));
      String keyword = res.getString(4);
      ts = new TSet(l, e, y, keyword);
    }

    pres.close();
    return ts;
  }

  /**
   * @param label:
   * @param tableName:
   * @return work.csser.db.TSet
   * @description TSet SQL query with specific table in DB
   * @method getTSet
   * @params [label, tableName]
   */
  public static TSet getTSet(String label, String tableName) throws SQLException {
    Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");

    TSet ts = null;
    String queryStr = "SELECT * FROM (?) WHERE label=(?) LIMIT 1";
    pres = conn.prepareStatement(queryStr);
    pres.setString(1, tableName);
    pres.setString(2, label);
    ResultSet res = pres.executeQuery();
    Element ele = pairing.getZr().newElement();
    while (res.next()) {
      String l = res.getString(1);
      byte[] e = res.getBytes(2);
      Element y = ele.duplicate();
      y.setFromBytes(res.getBytes(3));
      String keyword = res.getString(4);
      ts = new TSet(l, e, y, keyword);
    }

    pres.close();
    return ts;
  }

  /**
   * Legacy: check whether xs in XSets with Normal Method
   *
   * @param xs: XSet (single)
   * @return boolean
   * @method isInXSets
   * @params [xs]
   */
  public static boolean isInXSets(String xs) throws SQLException {
    String sqlSelect = "SELECT COUNT(*) FROM XSets WHERE xSet=(?)";
    pres = conn.prepareStatement(sqlSelect);
    pres.setString(1, xs);
    ResultSet res = pres.executeQuery();
    int count = 0;
    if (res.next()) {
      count = res.getInt(1);
    }
    pres.close();
    return count > 0;
  }

  /**
   * TODO create isInXSetsBF with BloomFilter
   *  public static boolean isInXSets(String xs) throws SQLException
   */
  /**
   * get `count` numbers XSets from DB
   *
   * @param offset: start index
   * @param num:    select numbers
   * @return java.util.ArrayList<java.lang.String>
   * @method getXSets
   * @params [offset, count]
   */
  public static ArrayList<String> getXSets(int offset, int num) throws SQLException {
    ArrayList<String> XSets = new ArrayList<>();
    String sqlSelect = "SELECT * FROM XSets LIMIT " + offset + "," + num;
    pres = conn.prepareStatement(sqlSelect);
    ResultSet res = pres.executeQuery();
    while (res.next()) {
      String xs = res.getString(1);
      XSets.add(xs);
    }
    pres.close();
    return XSets;
  }

  /**
   * get `count` numbers stagws from DB
   *
   * @param offset: start index
   * @param num:    select numbers
   * @return java.util.ArrayList<java.lang.String>
   * @method getStagws
   * @params [offset, count]
   */
  public static ArrayList<String> getStagws(int offset, int num) throws SQLException {
    ArrayList<String> stagws = new ArrayList<>();
    String sqlSelect = "SELECT * FROM stagws LIMIT " + offset + "," + num;
    pres = conn.prepareStatement(sqlSelect);
    ResultSet res = pres.executeQuery();
    while (res.next()) {
      String stagw = res.getString(1);
      stagws.add(stagw);
    }

    pres.close();
    return stagws;
  }

}
