package ancaIMS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame {
	private JFrame mainFrame;
	private JPanel controlPanel;	
	private JTable productList;
	private String columnNames[] = {"Product ID" , "Product Name", "Quantity", "Threshold"};
	private DefaultTableModel tableModel;
	private JScrollPane tableScroll;
	private ListOfProducts lop;	
	private int column;
	private int row;
	private String updatedData;
	private int updatedTh;
	private int count;
	private String newProduct;
	private static JDBC j;
	
	
public GUI(ListOfProducts lop){
	j = new JDBC();
	this.lop = lop;
	prepareGUI();}	

private void prepareGUI() {
	
    JMenuBar menuBar = new JMenuBar();
    JMenu submenu1 = new JMenu("Product");  
    JMenu submenu2 = new JMenu("Stock Report");
    menuBar.add(submenu1);    
    menuBar.add(submenu2);
    JMenuItem addProduct = new JMenuItem("Add New Product");    
    submenu1.add(addProduct);    
    JMenuItem saveReport = new JMenuItem("Generate Stock Report");
    JMenuItem makeOrder = new JMenuItem("Generate Purchase Order");
    submenu2.add(saveReport);
    submenu2.add(makeOrder);
	
    addProduct.addActionListener(new ActionListener(){
    	@Override
    	public void actionPerformed(ActionEvent e){    		
    		newProduct = (String)JOptionPane.showInputDialog("Product Name:");
			count = (tableModel.getRowCount()+1);
			j.addProduct(count, newProduct);
			tableModel.addRow(new Object[]{count,newProduct,0,5});
    	}
    });   
    
    controlPanel = new JPanel(new BorderLayout());				
	tableModel = new DefaultTableModel(columnNames, 0){	
		private static final long serialVersionUID = 1L;  //makes cells uneditable except for quantity column
		public boolean isCellEditable(int row, int column){  
			if(column == 2 || column == 3)
				return true;
			else
				return false;
		}		 
	};
	productList = new JTable(tableModel);
	tableScroll = new JScrollPane (productList);
	tableScroll.setBorder(BorderFactory.createEmptyBorder());
	
	productList.getModel().addTableModelListener(new TableModelListener() {
	      public void tableChanged(TableModelEvent e) { 
	    	 //column = e.getColumn();
	    	 row = e.getLastRow();	 
	    	 if(column != -1){	    		 
	    		 updatedData = (String) productList.getModel().getValueAt(row, 2);
	    		 lop.updateData(row + 1, updatedData);
	    		 updatedTh = Integer.parseInt((String)(productList.getModel().getValueAt(row, 3)));
	    		 lop.updateTh(row+1, updatedTh);			
	    	 }	    	 
 	      }
	    });
	
	mainFrame = new JFrame ("Inventory Management System");
	mainFrame.setSize(700, 450);
    mainFrame.add(tableScroll,BorderLayout.CENTER);
    mainFrame.add(menuBar,BorderLayout.NORTH);
	mainFrame.setVisible(true);
	}

	public void addProducts(ArrayList<Product> products){ // adds quantities
		DefaultTableModel model = (DefaultTableModel) productList.getModel();
		for(int i = 0; i < products.size() ; i++ ){	
			model.addRow(new Object[]{Integer.toString(i + 1) , products.get(i).getName(), Integer.toString(products.get(i).getQuantity()), Integer.toString(products.get(i).getThreshold())});			
		}
	}

	public String getUpdatedData(){
		return updatedData;
	}

	public int getUpdateID(){
		return row + 1;
	}
	
	
}