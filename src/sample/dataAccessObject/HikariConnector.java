package sample.dataAccessObject;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Testing Hikari database connection
 * */

public class HikariConnector  {

   private static HikariDataSource hikari;
  private static connectionProperties connectData=new connectionProperties();

  static {

     hikari=new HikariDataSource();
     hikari.setMaximumPoolSize(10);
   //  hikari.setDataSourceClassName(connectData.getDataSource());
     hikari.setDriverClassName(connectData.getDriverClassName());
     hikari.setJdbcUrl(connectData.getDatabaseUrl());
     hikari.setUsername(connectData.getUser());
     hikari.setPassword(connectData.getPassword());
     try {
        hikari.setLoginTimeout(300);
     } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Connection Timeout hikari");
     }

  }

   private static DataSource getDataSource(){
      return hikari;
   }

   public Connection getConnection(){
     Connection con=null;

      try {
         con=getDataSource().getConnection();
      } catch (SQLException e) {
         e.printStackTrace();
      }

      return con;
   }

}
