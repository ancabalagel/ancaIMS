package ancaIMS;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

public class ListOfProducts {
	
	private ArrayList<Product> products = new ArrayList<Product>();  // stores each product name and its quantity such that it can be displayed on screen
    private ImsApplication ims;
    private JDBC jdbc;
	
	public ListOfProducts(JDBC jdbc) {
		this.jdbc = jdbc;
		for(int i = 0; i < jdbc.getProductID().size(); i++){
			products.add(new Product(jdbc.getProductID().get(i), jdbc.getProductName().get(i), jdbc.getProductQuantity().get(i), jdbc.getProductThreshold().get(i)));
		}
	}
	public ArrayList<Product> getProducts(){
		return products;
	}
	
	public void updateData(int productID, String data){
		for(int i = 0; i < products.size(); i++){
			if(productID == products.get(i).getID()){
				products.get(i).setQuantity(Integer.parseInt(data));								
				jdbc.amendRecords(Integer.parseInt(data), products.get(i).getName());				
			}
		}
	}
	
	public void updateTh(int row, int newTh){
		for(int i = 0; i < products.size(); i++){
			if(row == products.get(i).getID()){
				products.get(i).setTh(newTh);								
				jdbc.amendTh(row, newTh);				
			}
		}
	}
	
	public void printToFile() {
		
	try {										 
		Date date = new Date();
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("productsfile2.txt", true))); 							
		writer.println();
		writer.println(date);  //adding the date to the log file
		for (int i = 0; i < products.size(); i++) {
			writer.println(products.get(i).getName() + " "+ products.get(i).getQuantity());		//printing each stock change to productsfile2.txt			
		}		
		writer.close();
		
	} catch (FileNotFoundException e) {
		// file not found
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// cannot add to file
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}

