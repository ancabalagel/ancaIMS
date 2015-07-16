package ancaIMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class JDBC {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://10.50.20.38:3306/test";
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
			rs.close();
		}catch (SQLException e) {
			// cannot get connection
			e.printStackTrace();
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

    public void updateDB(int sql5, int sql4){ // Update an entry in a particular row in the database
 	   try {
 	   stmt = conn.createStatement();
 	   String sql3 = "UPDATE Products SET stockLevel = " + sql5 + " WHERE productID = " + sql4 + "";
 	   stmt.executeUpdate(sql3);
 	   }catch (SQLException e){
 		// TODO Auto-generated catch block
            e.printStackTrace();
 	   }
    }
	
	public void amendRecords(int inQuantity, String inName) {		//changing stock levels in the db		
		try {
			stmt = conn.createStatement();
			String sql = "UPDATE Products SET stockLevel = " + inQuantity + " WHERE productName = " + inName + "";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("error while trying to amend stock level");
			e.printStackTrace();
		}
	}
	
	public void amendTh(int newTh, int row){
		try {
			stmt = conn.createStatement();
			String sql = "UPDATE Products SET threshold =" + newTh+ " WHERE productID = "+ row+ "";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("error while trying to amend stock level");
			e.printStackTrace();
		}
	}
	
	public void addProduct(int productID, String productName){ 
        try {
        	accessDB();
               stmt = conn.createStatement();
               String sql = "INSERT INTO Products VALUES (" + productID + ", '" + productName + "', " + 0 + ", " + 5 + ")";
               stmt.executeUpdate(sql);
               
        } catch (SQLException e) {
        	System.out.println("error while trying to amend stock level");
               e.printStackTrace();
        }      
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