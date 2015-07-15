package ancaIMS;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class ListOfProducts {
	
	private ArrayList<Product> products = new ArrayList<Product>();  // stores each product name and its quantity such that it can be displayed on screen
    private JDBC jdbc;
    private boolean ok = false;
	
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
				jdbc.updateDB(Integer.parseInt(data), products.get(i).getID());
				if(ok){
					if (products.get(i).getQuantity() < products.get(i).getThreshold()) {
						JOptionPane.showMessageDialog(null, "Stock level is low for this product.");
					}
				}
			}			
		}		
	}

	public void setOk(boolean ok){
		this.ok = ok;
	}
	
	public void updateTh(int row, int newTh){
		for(int i = 0; i < products.size(); i++){
			if(row == products.get(i).getID()){				
				products.get(i).setTh(newTh);								
				jdbc.amendTh(newTh, row);				
			}
		}
	}	

	public void saveReportToFile(){
		try{
			int numOfChars;
			int totalSpacesLeft;
			int productID;
			int productQuantity;
			int count =0;
			ArrayList <Integer> order = new ArrayList<Integer>(); 
			
			Date date = new Date();
			String report = "Stock Report Generated at " + date + "\r\n";
			report += "\r\n";
			report += "|----ID----|------Product Name------|-Quantity-|\r\n";
			report += "\r\n";
			File reportFile = new File("productsfile2.txt");
			for(int i = 0; i <= products.size() -1; i++){
				if(products.get(i).getQuantity() < products.get(i).getThreshold()){
				count++;
				order.add(products.get(i).getID());
				totalSpacesLeft = 20 ;
				productID = products.get(i).getID();
				productQuantity = products.get(i).getQuantity();

				if(productID < 10){
					report += "|  " + products.get(i).getID() + "       |";					
				}
				else if(productID  > 9 && productID < 100){
					report += "|  " + products.get(i).getID() + "      |";					
				}
				else if(productID > 99 && productID < 1000){
					report += "|  " + products.get(i).getID() + "     |";				
				}
				else if(productID > 999 && productID < 10000){
					report += "|  " + products.get(i).getID() + "    |";				
				}			
				
				report += "   " + products.get(i).getName() ;
				
				numOfChars = products.get(i).getName().length();			
				totalSpacesLeft = totalSpacesLeft - numOfChars;
				
				for(int whiteSpace = 0; whiteSpace <= totalSpacesLeft; whiteSpace++){
					report += " ";
				}
				
				report += "|  ";
				
				if(productQuantity < 9 && productQuantity >= 0){
					report +=  productQuantity + "       |";
				}
				else if(productQuantity < 0 && productQuantity > -10){
					report +=  productQuantity + "      |";
				}
				else if(productQuantity < -9 && productQuantity > -100){
					report +=  productQuantity + "     |";
				}
				
				else if(productQuantity > 8 && productQuantity < 100){
					report +=  products.get(i).getQuantity() + "      |" ;
				}
				else if(products.get(i).getQuantity() > 99 && products.get(i).getQuantity() < 1000){
					report +=  products.get(i).getQuantity() + "     |" ;
				}
				else if(products.get(i).getQuantity() > 999 && products.get(i).getQuantity() < 10000){
					report +=  products.get(i).getQuantity() + "     |" ;
				}				
				report += "\r\n";				
				report += "\r\n";
				}
			}
			if (count>5) {
				JOptionPane.showMessageDialog(null, "Purchase order has been sent");			
				for(int i: order){
					updateData(i,Integer.toString(products.get(i-1).getThreshold()));
					
				}
			}

			//System.out.println(report);
			FileWriter fw = new FileWriter(reportFile);
			fw.write(report);
			fw.close();
		}catch (IOException e){
			e.printStackTrace();
		}
}}

