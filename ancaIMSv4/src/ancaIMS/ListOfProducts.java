package ancaIMS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ListOfProducts {
	
	ArrayList<String> productEntry = new ArrayList<String>();
	ArrayList<Product> products = new ArrayList<Product>();

	public ListOfProducts() {
		
		//read product details from a file
		//productEntry holds products after reading the file line by line
		try {
			BufferedReader file = new BufferedReader(new FileReader("productsfile.txt"));
			String str;
			while ((str =  file.readLine()) != null){
				productEntry.add(str);
				
				String[] parts = str.split(" ");
				String qu = parts[1];
				int q = Integer.parseInt(qu);
				products.add(new Product(parts[0], q)); //creates products arraylist which was empty until now
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
	}
	//stock levels can be amended manually
	public void adjustStockLevel() {
		
		int amendedQuantity = 0;
		
		System.out.println("Do you wish to manually adjust stock levels? 1=Y; 2=N ");
		Scanner scan = new Scanner(System.in);
		int question = scan.nextInt();
		
		if (question == 1) {
			
			System.out.println("Enter product name");
			Scanner pscan = new Scanner(System.in);
			String amendedProduct = pscan.nextLine();
			
			System.out.println("Enter new quantity");
			Scanner qscan = new Scanner(System.in);
			amendedQuantity = qscan.nextInt();	
			
			for (int i=0; i<this.products.size(); i++) {
				if (products.get(i).getName().equalsIgnoreCase(amendedProduct)) {
					products.get(i).setQuantity(amendedQuantity);
					}
			}
			
			PrintWriter writer;
			try {
				writer = new PrintWriter("productsfile2.txt", "UTF-8");
				Date date = new Date();
				System.out.println(date);
				writer.println(date);
				for (int j=0; j<products.size(); j++){
					System.out.println(products.get(j).getName() + " " + products.get(j).getQuantity());
					writer.println(products.get(j).getName() + " " + products.get(j).getQuantity());
					}
			writer.close();	
			} 
			  catch (FileNotFoundException e) {
				// file not found
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// cannot add to file
				e.printStackTrace();
			}
		}		
		else System.out.println("Stock has not been changed.");
		}
	}
