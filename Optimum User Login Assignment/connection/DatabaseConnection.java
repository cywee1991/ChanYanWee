package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static DatabaseConnection DBConnectionRef;
    private static Connection connectionRef ;
    	
	private DatabaseConnection() {
		// private constructor //
	}
	
	public static DatabaseConnection getInstance(){
	    if(DBConnectionRef==null){
	    	DBConnectionRef= new DatabaseConnection();
	    }
	    return DBConnectionRef;
	}
	
	public static Connection getConnection() {

        String url = "jdbc:mysql://localhost:3306/loginauthentication";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "newpassword";

        try {
	        Class.forName(driver).newInstance();
	        connectionRef = DriverManager.getConnection(url,userName,password);
        } catch (Exception e){
        	e.printStackTrace();
        }

        return connectionRef;
    } // end of getConnection
	

	// to close connection
	public static void closeConnection(Connection conn) {

        try {
            conn.close();

        } catch (SQLException e) {
        	//
        }

    }
}