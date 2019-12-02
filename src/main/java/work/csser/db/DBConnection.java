package work.csser.db;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author a1exlism
 * @className DBConnection
 * @description
 * @since 2019/12/2 19:45
 */
public class DBConnection {

  public static Connection getConnection() throws FileNotFoundException {
    Properties props = new Properties();
    FileInputStream fis;
    Connection conn = null;
    try {
      InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("jdbc.properties");

      props.load(inputStream);
      //  load Driver Class
      Class.forName(props.getProperty("MySQL_DRIVER_CLASS"));
      //  create the connection
      conn = DriverManager.getConnection(
          props.getProperty("MySQL_URL") + props.getProperty("MySQL_DB"),
          props.getProperty("MySQL_USERNAME"),
          props.getProperty("MySQL_PASSWORD")
      );

    } catch (IOException | ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    return conn;
  }

  public static Connection getConnection(String DBName) throws FileNotFoundException {
    Properties props = new Properties();
    FileInputStream fis;
    Connection conn = null;
    try {
      InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("jdbc.properties");

      props.load(inputStream);
      //  load Driver Class
      Class.forName(props.getProperty("MySQL_DRIVER_CLASS"));
      //  create the connection
      conn = DriverManager.getConnection(
          props.getProperty("MySQL_URL") + DBName,
          props.getProperty("MySQL_USERNAME"),
          props.getProperty("MySQL_PASSWORD")
      );

    } catch (IOException | ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    return conn;
  }
}
