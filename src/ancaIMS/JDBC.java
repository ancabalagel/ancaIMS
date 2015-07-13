package ancaIMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JDBC {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	static final String USER = "IMT";
	static final String PASS = "netbuilder";
	Connection conn = null;
	Statement stmt = null;
	
	private ArrayList<Integer> productQuantity = new ArrayList<Integer>();
	private ArrayList<String> productName = new ArrayList<String>();
	private ArrayList<Integer> productID = new ArrayList<Integer>();
	private ArrayList<Integer> productThreshold = new ArrayList<Integer>();
	
	public void accessDB() {	//connecting to the db
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (Exception e) {
			System.out.println("failed" + e.toString());}
	}

	public void listProducts() {  // displaying low stock items on screen
		accessDB(); // connecting to db
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM products"; // getting all records
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) { 	//running through all records
				productID.add(rs.getInt("productID"));
				productName.add(rs.getString("productName"));
				productQuantity.add(rs.getInt("stockLevel"));
				productThreshold.add(rs.getInt("threshold"));
			}
			for(int i = 0; i < productID.size(); i++){						
				if (productQuantity.get(i) < 5) {  // if low stock then display message
						System.out.println("Stock is low for the following product:"+ " " + productName.get(i) + ";" + " " + "Stock Level:"+ " " + productQuantity.get(i));
				}					
			rs.close();
			} 
		}catch (SQLException e) {
			// cannot get connection
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public void closeConnection() {
		try {
			System.out.println("closing connection...");
			if (conn != null){
				conn.close();
			    System.out.println("Connection closed successfully.");
			}
			else {
				System.out.println("no connection to close");
			}
		} catch (SQLException f) {
			// cannot close connection
			f.printStackTrace();
		}
	}

	public void amendRecords(int inQuantity, String InName) {		//changing stock levels in the db		
		accessDB();		
		try {
			PreparedStatement stmt= conn.prepareStatement("UPDATE Products SET stockLevel = ? WHERE productName = ?");			
			stmt.setInt(1, inQuantity);
			stmt.setString(2, InName);			
			stmt.executeUpdate(); //amending quantity by name			
		} catch (SQLException e) {
			System.out.println("error while trying to amend stock level");
			e.printStackTrace();
		}
		closeConnection();
	}
	
	public void amendTh(int row, int newTh){
		accessDB();
		try {
			stmt = conn.createStatement();
			String sql = "UPDATE Products SET threshold =" + newTh+ " WHERE productID = "+ row+ "";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("error while trying to amend stock level");
			e.printStackTrace();
		}
		closeConnection();
	}
	
	public void addProduct(int productID, String productName){ 
		accessDB();
        try {
               stmt = conn.createStatement();
               String sql = "INSERT INTO Products VALUES (" + productID + ", '" + productName + "', " + 0 + ", "+5+")";
               stmt.executeUpdate(sql);
               
        } catch (SQLException e) {
        	System.out.println("error while trying to amend stock level");
               e.printStackTrace();
        }
        closeConnection();
 }
	
	public ArrayList<Integer> getProductID(){
		return productID;
	}
	
	public ArrayList<String> getProductName(){
		return productName;
	}
	
	public ArrayList<Integer> getProductQuantity(){
		return productQuantity;
	}
	
	public ArrayList<Integer> getProductThreshold(){
		return productThreshold;
	}
}