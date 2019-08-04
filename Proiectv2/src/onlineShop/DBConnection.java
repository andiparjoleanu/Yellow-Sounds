package onlineShop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection 
{
     private static DBConnection con;
     private Connection connection;
     
     private DBConnection()
     {
            String url = "jdbc:mysql://localhost/magazin_online";
            String username = "root";
            String password = "";
            try
            {
               connection = (Connection)DriverManager.getConnection(url, 
                       username, password);
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
     }
     
     public static DBConnection getInstance()
     {
         if(con == null)
             con = new DBConnection();
         return con;
     }
     
     public Connection getConnection()
     {
         return connection;
     }
}
