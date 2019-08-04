package onlineShop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Buffer 
{
    private static Buffer BUFFER;
    private Connection con;
    private Statement st;
    
    private Buffer()
    {
        con = DBConnection.getInstance().getConnection();
    }
    
    public synchronized static Buffer getInstance()
    {
        if(BUFFER == null)
            BUFFER = new Buffer();
        return BUFFER;
    }
    
    
    public synchronized boolean sellAlbum(Album a)
    {
        try
        {
            int quantity  = a.getQuantity();
            if(quantity <= 0)
            {
                JOptionPane.showMessageDialog(null, "Album not available", 
                        "Error", JOptionPane.ERROR_MESSAGE);
               return false;
            }
            else
            {
                st  = con.createStatement();
                String sql = "SELECT quantity FROM album "
                              + "WHERE name = '" + a.getName() + "'";
                ResultSet rs = st.executeQuery(sql);
                if(rs.next())
                    quantity = rs.getInt(1);
                quantity --;
                a.setQuantity(quantity);
                st = con.createStatement();
                sql = "UPDATE album SET quantity = " + quantity +
                             " WHERE name = '" + a.getName() + "'";
                st.executeUpdate(sql);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
        
    }
    
    public void addAlbum(Album a)
    {
        int quantity = 0;
        try
        {
           
            st = con.createStatement();
            String sql = "SELECT quantity FROM album WHERE name = '" 
                         + a.getName() + "'";
            ResultSet rss = st.executeQuery(sql);
            if(rss.next())
            {
                quantity = rss.getInt(1);
                quantity ++;
            }
            a.setQuantity(quantity);
            st = con.createStatement();
            sql = "UPDATE album SET quantity = " + quantity +
                         " WHERE name = '" + a.getName() + "'";
            st.executeUpdate(sql);
            
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    
}
