package ancaIMS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JLabel messageLabel;
	private JPanel controlPanel;	
	private JTable productList;
	private String columnNames[] = {"Product ID" , "Product Name", "Quantity"};
	private DefaultTableModel tableModel;
	private JScrollPane tableScroll;
	private ListOfProducts lop;	
	private int column;
	private int row;
	private String updatedData;
	
	
	
public GUI(ListOfProducts lop){
	this.lop = lop;
	prepareGUI();}	

private void prepareGUI() {
	controlPanel = new JPanel(new BorderLayout());				
	tableModel = new DefaultTableModel(columnNames, 0){		

		private static final long serialVersionUID = 1L;  //makes cells uneditable except for quantity column
		public boolean isCellEditable(int row, int column){  
			 if(column == 2)
				 return true;
			else
				 return false;
		 }
		 
	};
	productList = new JTable(tableModel);
	productList.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){

		private static final long serialVersionUID = 1L;

		@Override    // changes row color for low stock levels
	    public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row, int col) {

	        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

	        String quantity = (String)table.getModel().getValueAt(row, 2);
	        
	        if (Integer.parseInt(quantity) < 5) {	          
	            setForeground(Color.RED);	
	            
	        } else {	            
	            setForeground(table.getForeground()); 
	        }       
	        return this;
	    }   
	});

	tableScroll = new JScrollPane (productList);
	tableScroll.setBorder(BorderFactory.createEmptyBorder());
	
	productList.getModel().addTableModelListener(new TableModelListener() {

	      public void tableChanged(TableModelEvent e) { 
	    	 column = e.getColumn();
	    	 row = e.getLastRow();	 
	    	 if(column != -1){	    		 
	    		 updatedData = (String) productList.getModel().getValueAt(row, column);
	    		 lop.updateData(row + 1, updatedData);
	    		 lop.printToFile();			// print changes to file
	    	 }	    	 
 	      }
	    });
	
	mainFrame = new JFrame ("Inventory Management System");
	mainFrame.setSize(700, 450);
	mainFrame.setLayout(new GridLayout(3, 1));
	mainFrame.add(tableScroll);
	mainFrame.add(controlPanel);	
	mainFrame.setVisible(true);
	}

	public void addProducts(ArrayList<Product> products){ // adds new quantities
		DefaultTableModel model = (DefaultTableModel) productList.getModel();
		for(int i = 0; i < products.size() ; i++ ){	
			model.addRow(new Object[]{Integer.toString(i + 1) , products.get(i).getName(), Integer.toString(products.get(i).getQuantity())});			
		}
	}

	public String getUpdatedData(){
		return updatedData;
	}

	public int getUpdateID(){
		return row + 1;
	}
	
	
}