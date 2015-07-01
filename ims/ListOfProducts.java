

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ListOfProducts {
	
	ArrayList<String> productEntry = new ArrayList<String>(); //productEntry holds products after reading the file line by line
	ArrayList<Product> products = new ArrayList<Product>();  // stores each product name and its quantity such that it can be displayed on screen

	public ListOfProducts() {
		
		//read product details from a file
		try {
			BufferedReader file = new BufferedReader(new FileReader("productsfile.txt"));
			String str;
			while ((str =  file.readLine()) != null){
				productEntry.add(str);	//adding each line to the arraylist
				String[] parts = str.split(" "); //stores each part in a line (quantity and name which are separated by a space )
				String qu = parts[1]; // takes the second part of the line, which is the quantity
				int q = Integer.parseInt(qu); // parses quantity to an int
				products.add(new Product(parts[0], q)); //creates products arraylist which was empty until now adding firstly the product name and then the parsed quantity
				}
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			//file not found
		} catch (IOException e) {
			// can't read from file
			e.printStackTrace();
		}
		
		for (int i=0; i<productEntry.size(); i++) {   // printing the arraylist that contains all the info from the file line by line
			System.out.println(productEntry.get(i));
		}	
	}
	//stock levels can be amended manually
	public void adjustStockLevel() {
		
		int amendedQuantity = 0;  //initialising the variable that will store the quantity the user will input
		JDBC jdbc = new JDBC();	 // creating an instance of the database
		boolean ok = true;
		
		while (ok) {  // while the user is changing the stock levels

			System.out.println("Do you wish to manually adjust stock levels? 1=Y; 2=N ");
			Scanner scan = new Scanner(System.in);
			int question = scan.nextInt();

			if (question == 1) { // if the user choses to amend stock levels

				System.out.println("Enter product name");
				Scanner pscan = new Scanner(System.in);
				String amendedProduct = pscan.nextLine(); // storing the product name

				System.out.println("Enter new quantity");
				Scanner qscan = new Scanner(System.in);
				amendedQuantity = qscan.nextInt(); 		 //storing the new quantity

				for (int i = 0; i < this.products.size(); i++) {   	// searching for the product chosen by the user and amending its quantity
					if (products.get(i).getName().equalsIgnoreCase(amendedProduct)) {
						products.get(i).setQuantity(amendedQuantity);
					}
				}
				PrintWriter writer;
				PrintWriter file1;
				try {										 
					Date date = new Date();
					writer = new PrintWriter(new BufferedWriter(new FileWriter("productsfile2.txt", true))); 
					file1 = new PrintWriter("productsfile.txt", "UTF-8");									
					writer.println();
					writer.println(date);  //adding the date to the log file
					System.out.println(date);
					for (int j = 0; j < products.size(); j++) {
						System.out.println(products.get(j).getName() + " "+ products.get(j).getQuantity());	 //printing each stock change to the screen
						writer.println(products.get(j).getName() + " "+ products.get(j).getQuantity());		//printing each stock change to productsfile2.txt
						file1.println(products.get(j).getName() + " "+ products.get(j).getQuantity());	   //updating the initial file with the latest figures
					}
					jdbc.amendRecords(amendedQuantity, amendedProduct);		// changing stock levels in the database
					writer.close();
					file1.close();
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
			} else { // if the user does not wish to make any other changes to the stock records
				System.out.println("Stock has not been changed.");
				ok = false;
			}

		}
		}
	}

