package ancaIMS;

import java.util.Scanner;

public class Product  {

		private String name;
		private int stockLevel;
		
		public Product () {
			
		System.out.println("enter product");
		Scanner x = new Scanner(System.in);
		name = x.next();
		System.out.println("enter quantity");
		Scanner y = new Scanner(System.in);
		stockLevel = y.nextInt();
		}
		
		public Product (String name, int quantity) {
			this.name = name;
			this.stockLevel = quantity;
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