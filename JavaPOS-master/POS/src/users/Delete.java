
package users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class Delete {
    Connection conn=null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Statement stm = null;
    
    public void DeleteUser(String User)
    {
        DatabaseConnection.DbConnection Connect = new DatabaseConnection.DbConnection();
        conn = Connect.Connectdb();
        
        try{
            String delete ="DELETE FROM `profilemaster` WHERE `Id` = '"+User+"'";
            pst = conn.prepareStatement(delete);
            pst.execute();

        }catch(Exception e)
        {
             JOptionPane.showMessageDialog(null, e);
        }
    }
}