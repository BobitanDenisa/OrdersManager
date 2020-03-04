package presentation;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import businesslogic.ClientBLL;
import businesslogic.OrderBLL;
import businesslogic.OrderProductsBLL;
import businesslogic.ProductBLL;
import model.Client;
import model.Order;
import model.OrderProducts;
import model.Product;

@SuppressWarnings("serial")
public class OrderView extends JFrame {

	private JLabel cli;
	private JComboBox<String> clientID;
	private JLabel prod;
	private JComboBox<String> prodName;
	private JLabel qtt;
	private JTextField quantity;
	private JScrollPane sp2;
	private JLabel table2;
	private JTable orders;
	private JScrollPane sp1;
	private JLabel table1;
	private JTable cart;
	private JButton addToCart;
	private JButton deleteFromCart;
	private JButton order;

	public OrderView() {

		this.setTitle("Orders");
		this.setLayout(null);

		this.cli = new JLabel("Client ID:");
		this.cli.setBounds(40, 45, 50, 20);

		ClientBLL c = new ClientBLL();
		List<Client> list = c.findAllClients();
		ComboBoxGenerator<Client> cbg1 = new ComboBoxGenerator<Client>();
		this.clientID = cbg1.createComboBox(list, 0); // 0 is the index of id field
		this.clientID.setBounds(100, 45, 50, 20);

		this.prod = new JLabel("Product name:");
		this.prod.setBounds(180, 45, 85, 20);

		ProductBLL p = new ProductBLL();
		List<Product> listp = p.findAllProducts();
		ComboBoxGenerator<Product> cbg2 = new ComboBoxGenerator<Product>();
		this.prodName = cbg2.createComboBox(listp, 1); // 1 is the index of name field
		this.prodName.setBounds(275, 45, 200, 20);

		this.qtt = new JLabel("Quantity:");
		this.qtt.setBounds(505, 45, 50, 20);

		this.quantity = new JTextField();
		this.quantity.setBounds(565, 45, 50, 20);

		this.addToCart = new JButton("Add to cart");
		this.addToCart.setBounds(650, 40, 150, 30);

		this.table1 = new JLabel("Cart", JLabel.CENTER);
		this.table1.setBounds(40, 100, 500, 20);

		TableGenerator<Product> tg1 = new TableGenerator<Product>();
		this.cart = tg1.createTable(new Product());
		this.sp1 = new JScrollPane(cart);
		this.sp1.setBounds(40, 130, 500, 500);

		this.table2 = new JLabel("Orders", JLabel.CENTER);
		this.table2.setBounds(570, 100, 500, 20);

		TableGenerator<Order> tg2 = new TableGenerator<Order>();
		Order o = new Order();
		this.orders = tg2.createTable(o);
		this.sp2 = new JScrollPane(orders);
		this.sp2.setBounds(570, 130, 500, 500);
		displayOrdersTable();

		this.deleteFromCart = new JButton("Delete from cart");
		this.deleteFromCart.setBounds(40, 680, 200, 30);

		this.order = new JButton("Submit order");
		this.order.setBounds(40, 740, 200, 30);

		this.add(cli);
		this.add(clientID);
		this.add(prod);
		this.add(prodName);
		this.add(qtt);
		this.add(quantity);
		this.add(addToCart);
		this.add(table1);
		this.add(sp1);
		this.add(table2);
		this.add(sp2);
		this.add(deleteFromCart);
		this.add(order);

		this.setSize(new Dimension(1130, 850));
	}

	public void addInsertToCartButtonListener(ActionListener a) {
		addToCart.addActionListener(a);
	}

	public void addDeleteFromCartButtonListener(ActionListener a) {
		deleteFromCart.addActionListener(a);
	}

	public void addOrderButtonListener(ActionListener a) {
		order.addActionListener(a);
	}

	public void disableIDSelection() {
		this.clientID.setEnabled(false);
	}

	public void enableIDSelection() {
		this.clientID.setEnabled(true);
	}

	public Long getClientId() {
		return Long.valueOf(clientID.getSelectedItem().toString());
	}

	public String getProductName() {
		return prodName.getSelectedItem().toString();
	}

	public void insertIntoCart() {
		Long q = Long.valueOf(quantity.getText());

		ProductBLL p = new ProductBLL();
		DefaultTableModel model = (DefaultTableModel) cart.getModel();
		Product pr = p.findByName(prodName.getSelectedItem().toString());

		if (q <= pr.getQuantity()) {
			Vector<String> rowData = new Vector<String>();
			rowData.add(pr.getId().toString());
			rowData.add(pr.getName());
			rowData.add(q.toString());
			rowData.add(pr.getPrice().toString());
			model.addRow(rowData);
		} else {
			JOptionPane.showMessageDialog(null, "Product understock!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void deleteFromCart() {
		int index = cart.getSelectedRow();
		DefaultTableModel model = (DefaultTableModel) cart.getModel();
		model.removeRow(index);
	}

	public void submitOrder() {
		// put info from cart into real tables
		Long idc = Long.valueOf(clientID.getSelectedItem().toString());
		String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

		Order o = new Order();
		o.setTotalpayment(Double.valueOf(0));
		o.setDate(date);
		o.setIdclient(idc);

		for (int i = 0; i < cart.getRowCount(); i++) {
			Long q = Long.valueOf(cart.getValueAt(i, 2).toString());
			Double price = Double.valueOf(cart.getValueAt(i, 3).toString());
			o.setTotalpayment(o.getTotalpayment() + q * price);
		}
		OrderBLL ord = new OrderBLL();
		Long ido = ord.insertOrder(o);

		List<Product> prodList = new ArrayList<Product>();

		for (int i = 0; i < cart.getRowCount(); i++) {
			Long q = Long.valueOf(cart.getValueAt(i, 2).toString());
			Long idp = Long.valueOf(cart.getValueAt(i, 0).toString());

			OrderProducts op = new OrderProducts();
			op.setIdorder(ido);
			op.setIdproduct(idp);
			op.setQuantity(q);

			OrderProductsBLL ordprod = new OrderProductsBLL();
			ordprod.insertOrderProducts(op);

			// display orders table
			displayOrdersTable();

			// decrease quantity of each product
			ProductBLL prod = new ProductBLL();
			Product p = prod.findProductByID(idp);
			p.setQuantity(p.getQuantity() - q);
			prod.updateProduct(p);

			// used for the pdf
			p.setQuantity(q);
			p.setPrice(p.getPrice() * q);
			prodList.add(p);
		}

		ClientBLL cbll = new ClientBLL();
		Client c = cbll.findClientByID(idc);
		generatePDF(c, ido.toString(), prodList);
	}

	public boolean checkFields() {
		if (quantity.getText().isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean isProductSelected() {
		if (cart.getSelectedRow() != -1) {
			return true;
		}
		return false;
	}

	public boolean isTableEmpty() {
		if (cart.getModel().getRowCount() == 0) {
			return true;
		}
		return false;
	}

	public void resetCart() {
		DefaultTableModel model = (DefaultTableModel) cart.getModel();
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
	}

	private void displayOrdersTable() {
		OrderBLL o = new OrderBLL();
		List<Order> list = o.findAllOrders();
		Order[] ord = new Order[list.size()];
		ord = list.toArray(ord);
		DefaultTableModel model = (DefaultTableModel) orders.getModel();
		model.setRowCount(ord.length);
		int i = -1;
		for (Order aux : ord) {
			i++;
			model.setValueAt(aux.getId(), i, 0);
			model.setValueAt(aux.getDate(), i, 1);
			model.setValueAt(aux.getTotalpayment(), i, 2);
			model.setValueAt(aux.getIdclient(), i, 3);
		}
	}

	private void generatePDF(Client c, String idorder, List<Product> products) {
		String s = "order " + idorder + "_" + c.getId().toString();
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(s + ".pdf"));
			document.open();
			Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
			Paragraph para1 = new Paragraph("\nOrder #" + idorder + "\n\n\n\n\n");
			para1.setAlignment(Element.ALIGN_CENTER);
			Paragraph para2 = new Paragraph(
					"CUSTOMER INFORMATION\n\nID:" + c.getId() + "\nName: " + c.getFullname() + "\nAddress: "
							+ c.getAddress() + "\nEmail: " + c.getEmail() + "\nPhone: " + c.getPhone() + "\n\n\n");

			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 1, 5, 2, 3 });
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell();
			cell.setVerticalAlignment(com.itextpdf.text.Rectangle.ALIGN_CENTER);
			cell.setHorizontalAlignment(com.itextpdf.text.Rectangle.ALIGN_CENTER);

			Phrase p1 = new Phrase("Purchased Products");
			cell.setPhrase(p1);
			cell.setColspan(4);
			cell.setPaddingTop(10);
			cell.setPaddingBottom(10);
			table.addCell(cell);

			cell.setColspan(1);

			cell.setPhrase(new Phrase("Number"));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Product name"));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Quantity"));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Price"));
			table.addCell(cell);

			cell.setPadding(3);

			DecimalFormat df = new DecimalFormat("#.##");

			int i = 1;
			Double sum = Double.valueOf(0);
			for (Product p : products) {
				cell.setPhrase(new Phrase(Integer.toString(i)));
				table.addCell(cell);
				cell.setPhrase(new Phrase(p.getName()));
				table.addCell(cell);
				cell.setPhrase(new Phrase(p.getQuantity().toString()));
				table.addCell(cell);
				cell.setPhrase(new Phrase(p.getPrice().toString()));
				table.addCell(cell);
				i++;
				sum += p.getPrice();
			}

			Paragraph para3 = new Paragraph("\n\nTotal: " + df.format(sum) + " RON    ");
			para3.setAlignment(Element.ALIGN_RIGHT);

			document.add(para1);
			document.add(para2);
			document.add(table);
			document.add(para3);

			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
