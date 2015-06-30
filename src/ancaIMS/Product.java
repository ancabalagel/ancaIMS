package ancaIMS;

import java.util.Scanner;

public class Product  {

		private String name;
		private int stockLevel;
		private int productID;
				
		public Product (int id, String name, int quantity) {
			this.productID = id;
			this.name = name;
			this.stockLevel = quantity;
		}
		
		public int getID(){
			return productID;
		}
				
		public String getName() {
			return name;
		}
		
		public void setQuantity(int newQuantity) {
			 stockLevel = newQuantity;
		}
		
		public int getQuantity() {
			return stockLevel;
		}
	
		
		
		
	}