package ancaIMS;

public class ImsApplication {
	
	private static JDBC jdbc;

	public static void main(String[] args) {
				
		jdbc = new JDBC();		
		jdbc.listProducts();
		ListOfProducts ims = new ListOfProducts(jdbc);
		GUI sD = new GUI(ims);		
		sD.addProducts(ims.getProducts());		
	}

}
