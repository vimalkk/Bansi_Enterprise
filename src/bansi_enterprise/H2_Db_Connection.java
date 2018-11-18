package bansi_enterprise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author BANSIENTERPRISE
 */
public class H2_Db_Connection {
    static Connection conn;
    static ResultSet rs;
    static Statement stmt;
    
    public void Connectivity(){
        try {
            //H2 DATABASE Connectivity
            String DB_DRIVER = "org.h2.Driver";
            String DB_URL = "jdbc:h2:~/BANSIENTERPRISE";
            String DB_USER = "BANSIENTERPRISE";
            String DB_PASS = "Bansi@123.*";

            Class.forName(DB_DRIVER);
            conn=DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();
            
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}