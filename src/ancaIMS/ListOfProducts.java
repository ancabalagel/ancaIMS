package ancaIMS;
import java.util.ArrayList;

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
}

