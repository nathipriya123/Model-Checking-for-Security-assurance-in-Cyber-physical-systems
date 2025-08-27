/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bwddosattacksserver;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
/**
 *
 * @author admin
 */
public class DBConnection 
{
    public Statement stt;
    public Connection con;
    public DBConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bwdos","root","");
            stt=con.createStatement();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
