package presentation;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import businesslogic.ProductBLL;
import model.Product;

@SuppressWarnings("serial")
public class ProductView extends JFrame {

	private JLabel idL;
	private JLabel nameL;
	private JLabel quantityL;
	private JLabel priceL;
	private JTextField id;
	private JTextField name;
	private JTextField quantity;
	private JTextField price;
	private JButton insert;
	private JButton update;
	private JButton delete;
	private JButton find;
	private JButton display;
	private JTable table;
	private JScrollPane sp;

	public ProductView() {

		this.nameL = new JLabel("Name");
		this.nameL.setBounds(40, 40, 50, 20);
		this.name = new JTextField();
		this.name.setBounds(110, 40, 150, 20);

		this.quantityL = new JLabel("Quantity");
		this.quantityL.setBounds(40, 90, 50, 20);
		this.quantity = new JTextField();
		this.quantity.setBounds(110, 90, 150, 20);

		this.priceL = new JLabel("Price");
		this.priceL.setBounds(40, 140, 50, 20);
		this.price = new JTextField();
		this.price.setBounds(110, 140, 150, 20);

		this.insert = new JButton("Insert");
		this.insert.setBounds(40, 190, 100, 30);

		this.update = new JButton("Update");
		this.update.setBounds(160, 190, 100, 30);

		this.idL = new JLabel("ID:");
		this.idL.setBounds(40, 260, 50, 20);
		this.id = new JTextField();
		this.id.setBounds(110, 260, 150, 20);

		this.delete = new JButton("Delete");
		this.delete.setBounds(40, 310, 100, 30);

		this.find = new JButton("Find");
		this.find.setBounds(160, 310, 100, 30);

		this.display = new JButton("Display All Products");
		this.display.setBounds(40, 420, 220, 30);

		TableGenerator<Product> tg = new TableGenerator<Product>();
		Product p = new Product();
		this.table = tg.createTable(p);
		this.sp = new JScrollPane(table);
		this.sp.setBounds(300, 40, 600, 410);
		this.sp.setSize(new Dimension(600, 410));

		this.setTitle("Products");
		this.setLayout(null);

		this.add(idL);
		this.add(id);
		this.add(nameL);
		this.add(name);
		this.add(quantityL);
		this.add(quantity);
		this.add(priceL);
		this.add(price);
		this.add(insert);
		this.add(update);
		this.add(delete);
		this.add(find);
		this.add(display);
		this.add(sp);

		this.setSize(new Dimension(960, 530));

	}

	public void addInsertButtonListener(ActionListener a) {
		insert.addActionListener(a);
	}

	public void addUpdateButtonListener(ActionListener a) {
		update.addActionListener(a);
	}

	public void addDeleteButtonListener(ActionListener a) {
		delete.addActionListener(a);
	}

	public void addFindButtonListener(ActionListener a) {
		find.addActionListener(a);
	}

	public void addDisplayButtonListener(ActionListener a) {
		display.addActionListener(a);
	}

	public int checkFields() {
		if (name.getText().isEmpty() || quantity.getText().isEmpty() || price.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please complete all the fields", "ERROR", JOptionPane.ERROR_MESSAGE);
			return 1;
		}
		return 0;
	}

	public int checkIDField() {
		if (id.getText().isEmpty()) {
			return 1;
		}
		return 0;
	}

	public void setTableInfo(Long id) {

		ProductBLL p = new ProductBLL();
		List<Product> list = new ArrayList<Product>();
		Product product = p.findProductByID(id);
		if (product != null) {
			list.add(product);
			Product[] products = new Product[list.size()];
			products = list.toArray(products);
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(products.length);
			int i = -1;
			for (Product pr : products) {
				i++;
				model.setValueAt(pr.getId(), i, 0);
				model.setValueAt(pr.getName(), i, 1);
				model.setValueAt(pr.getQuantity(), i, 2);
				model.setValueAt(pr.getPrice(), i, 3);
			}
		}

	}

	public void setTableInfo() {

		ProductBLL p = new ProductBLL();
		List<Product> list = p.findAllProducts();
		Product[] products = new Product[list.size()];
		products = list.toArray(products);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(products.length);
		int i = -1;
		for (Product pr : products) {
			i++;
			model.setValueAt(pr.getId(), i, 0);
			model.setValueAt(pr.getName(), i, 1);
			model.setValueAt(pr.getQuantity(), i, 2);
			model.setValueAt(pr.getPrice(), i, 3);
		}

	}

	public Product getSelectedProduct() {
		Product p = new Product();
		int row = table.getSelectedRow();
		if (row != -1) {
			p.setId(Long.valueOf(table.getValueAt(row, 0).toString()));
			p.setName(table.getValueAt(row, 1).toString());
			p.setQuantity(Long.valueOf(table.getValueAt(row, 2).toString()));
			p.setPrice(Double.valueOf(table.getValueAt(row, 3).toString()));
			return p;
		}
		return null;
	}

	public Long getId() {
		return Long.valueOf(id.getText());
	}

	public String getName() {
		return name.getText();
	}

	public Long getQuantity() {
		if (!quantity.getText().isEmpty()) {
			return Long.valueOf(quantity.getText());
		}
		return Long.valueOf(0);
	}

	public Double getPrice() {
		if (!price.getText().isEmpty()) {
			return Double.valueOf(price.getText());
		}
		return Double.valueOf(0);
	}

}
