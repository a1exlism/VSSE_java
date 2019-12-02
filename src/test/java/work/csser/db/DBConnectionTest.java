package work.csser.db;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author a1exlism
 * @className DBConnectionTest
 * @description
 * @refer https://www.runoob.com/java/java-mysql-connect.html
 * @since 2019/12/2 21:13
 */

public class DBConnectionTest {
  @Test
  public void test() {
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = DBConnection.getConnection("vsse_test");
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT mail,name FROM user";
      //  select
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String mail = rs.getString("mail");
        String name = rs.getString("name");
        System.out.println("Mail: " + mail + " username: " + name);
      }
      //  ATTENTION: close the connection
      rs.close();
      stmt.close();
      conn.close();
    } catch (FileNotFoundException | SQLException e) {
      e.printStackTrace();
    } finally {
      //  check the SQL status
      try {
        if (stmt != null)
          stmt.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      try {
        if (conn != null)
          conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
