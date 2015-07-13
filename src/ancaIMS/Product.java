package ancaIMS;

public class Product  {

		private String name;
		private int stockLevel;
		private int productID;
		private int threshold;
				
		public Product (int id, String name, int quantity, int threshold) {
			this.productID = id;
			this.name = name;
			this.stockLevel = quantity;
			this.threshold = threshold;
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
		
		public void setTh(int newTh){
			threshold = newTh;
		}
		
		public int getQuantity() {
			return stockLevel;
		}
	
		public int getThreshold(){
			return threshold;
		}
		
		
	}