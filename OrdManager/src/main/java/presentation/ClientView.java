package presentation;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import businesslogic.ClientBLL;
import model.Client;

@SuppressWarnings("serial")
public class ClientView extends JFrame {

	private JLabel idL; 
	private JLabel fullnameL;
	private JLabel emailL;
	private JLabel phoneL;
	private JLabel addressL;
	private JTextField id;
	private JTextField fullname;
	private JTextField email;
	private JTextField phone;
	private JTextField address;
	private JButton insert;
	private JButton update;
	private JButton delete;
	private JButton find;
	private JButton display;
	private JTable table;
	private JScrollPane sp;

	public ClientView() {

		this.fullnameL = new JLabel("Name");
		this.fullnameL.setBounds(40, 40, 50, 20);
		this.fullname = new JTextField();
		this.fullname.setBounds(110, 40, 150, 20);

		this.emailL = new JLabel("Email");
		this.emailL.setBounds(40, 90, 50, 20);
		this.email = new JTextField();
		this.email.setBounds(110, 90, 150, 20);

		this.phoneL = new JLabel("Phone");
		this.phoneL.setBounds(40, 140, 50, 20);
		this.phone = new JTextField();
		this.phone.setBounds(110, 140, 150, 20);

		this.addressL = new JLabel("Address");
		this.addressL.setBounds(40, 190, 50, 20);
		this.address = new JTextField();
		this.address.setBounds(110, 190, 150, 20);

		this.insert = new JButton("Insert");
		this.insert.setBounds(40, 240, 100, 30);

		this.update = new JButton("Update");
		this.update.setBounds(160, 240, 100, 30);

		this.idL = new JLabel("ID:");
		this.idL.setBounds(40, 310, 50, 20);
		this.id = new JTextField();
		this.id.setBounds(110, 310, 150, 20);

		this.delete = new JButton("Delete");
		this.delete.setBounds(40, 360, 100, 30);

		this.find = new JButton("Find");
		this.find.setBounds(160, 360, 100, 30);

		this.display = new JButton("Display All Clients");
		this.display.setBounds(40, 420, 220, 30);

		TableGenerator<Client> tg = new TableGenerator<Client>();
		Client c = new Client();
		this.table = tg.createTable(c);
		this.sp = new JScrollPane(table);
		this.sp.setBounds(300, 40, 600, 410);
		this.sp.setSize(new Dimension(600, 410));

		this.setTitle("Clients");
		this.setLayout(null);

		this.add(idL);
		this.add(id);
		this.add(fullnameL);
		this.add(fullname);
		this.add(emailL);
		this.add(email);
		this.add(phoneL);
		this.add(phone);
		this.add(addressL);
		this.add(address);
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
		if (fullname.getText().isEmpty() || email.getText().isEmpty() || phone.getText().isEmpty()
				|| address.getText().isEmpty()) {
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

		ClientBLL c = new ClientBLL();
		List<Client> list = new ArrayList<Client>();
		Client client = c.findClientByID(id);
		if (client != null) {
			list.add(client);
			Client[] clients = new Client[list.size()];
			clients = list.toArray(clients);
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(clients.length);
			int i = -1;
			for (Client cl : clients) {
				i++;
				model.setValueAt(cl.getId(), i, 0);
				model.setValueAt(cl.getFullname(), i, 1);
				model.setValueAt(cl.getEmail(), i, 2);
				model.setValueAt(cl.getPhone(), i, 3);
				model.setValueAt(cl.getAddress(), i, 4);
			}
		}

	}

	public void setTableInfo() {

		ClientBLL c = new ClientBLL();
		List<Client> list = c.findAllClients();
		Client[] clients = new Client[list.size()];
		clients = list.toArray(clients);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(clients.length);
		int i = -1;
		for (Client cl : clients) {
			i++;
			model.setValueAt(cl.getId(), i, 0);
			model.setValueAt(cl.getFullname(), i, 1);
			model.setValueAt(cl.getEmail(), i, 2);
			model.setValueAt(cl.getPhone(), i, 3);
			model.setValueAt(cl.getAddress(), i, 4);
		}

	}

	public Client getSelectedClient() {
		Client c = new Client();
		int row = table.getSelectedRow();
		if (row != -1) {
			c.setId(Long.valueOf(table.getValueAt(row, 0).toString()));
			c.setFullname(table.getValueAt(row, 1).toString());
			c.setEmail(table.getValueAt(row, 2).toString());
			c.setPhone(table.getValueAt(row, 3).toString());
			c.setAddress(table.getValueAt(row, 4).toString());
			return c;
		}
		return null;
	}

	public Long getId() {
		return Long.valueOf(id.getText());
	}

	public String getFullname() {
		return fullname.getText();
	}

	public String getEmail() {
		return email.getText();
	}

	public String getPhone() {
		return phone.getText();
	}

	public String getAddress() {
		return address.getText();
	}

}
