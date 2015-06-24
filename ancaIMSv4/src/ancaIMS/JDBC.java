package ancaIMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
	static final String USER = "IMT";
	static final String PASS = "Alabala,123";

	public void accessDB() {
		Connection conn = null;
		Statement stmt = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				//System.out.println("yesss!!");
			}
			catch(Exception e) {
				System.out.println("failed" + e.toString());
				}
		try {
			stmt = conn.createStatement();
			String sql = "SELECT productID,productName, stockLevel FROM products";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("productID");
				String name = rs.getString("productName");
				int quantity = rs.getInt("stockLevel");
				if (quantity<5) {
					System.out.println("Stock is low for the following product(s):"+ " " + name);
				}
				
				}
			rs.close();
			} 
		catch (SQLException e) {
				// cannot get connection
				e.printStackTrace();
		}
	try {
		if (conn != null)
			conn.close();		
	} catch (SQLException f) {
		// cannot close connection
		f.printStackTrace();
	}
	}
}