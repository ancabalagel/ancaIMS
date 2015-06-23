package ancaIMS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IMS {

	public IMS() {
		
		//read product details from a file
		
		ArrayList<String> productEntry = new ArrayList<String>();		
		
		try {
			BufferedReader file = new BufferedReader(new FileReader("productsfile.txt"));
			String str;
			while ((str =  file.readLine()) != null){
				productEntry.add(str);
				}
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//file not found
		} catch (IOException e) {
			// can't read from file
			e.printStackTrace();
		}
		
		for (int i=0; i<productEntry.size(); i++) {
			System.out.println(productEntry.get(i));
		}
		/* prints a previously inputed list of products and their quantity
		
		String[] names = new String[5];
		int[] quantities = new int[5];
		names[0] = "gnome";
		names[1] = "jacuzzi";
		names[2] = "garden hose";
		
		quantities[0] = 12;
		quantities[1] = 5;
		quantities[2] = 65;
		
		System.out.println("products"+ " "+ "quantity" );
		for (int i=0; i<3; i++) {
			System.out.println(names[i] + " " + quantities[i]);
		}
		*/
		
		/* reads user input and prints all of it in the end by saving into an array
		
		ArrayList<Product> products;
		products = new ArrayList<Product>();
		for (int i=0; i<5; i++){
			products.add(new Product());
		}
		for (int i=0; i<products.size(); i++){
		System.out.println(products.get(i).getName() + " " + products.get(i).getQuantity());
		
		}*/
	}
}
