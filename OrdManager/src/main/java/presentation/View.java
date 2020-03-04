package presentation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class View extends JFrame {

	private JButton clients;
	private JButton products;
	private JButton orders;

	public View() {

		this.clients = new JButton("Clients");
		this.clients.setPreferredSize(new Dimension(100, 50));
		this.products = new JButton("Products");
		this.products.setPreferredSize(new Dimension(100, 50));
		this.orders = new JButton("Orders");
		this.orders.setPreferredSize(new Dimension(100, 50));

		this.setTitle("Orders Management");
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
		this.setSize(new Dimension(650, 200));

		this.add(clients);
		this.add(products);
		this.add(orders);

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void addClientsButtonListener(ActionListener a) {
		clients.addActionListener(a);
	}

	public void addProductsButtonListener(ActionListener a) {
		products.addActionListener(a);
	}

	public void addOrdersButtonListener(ActionListener a) {
		orders.addActionListener(a);
	}

}
