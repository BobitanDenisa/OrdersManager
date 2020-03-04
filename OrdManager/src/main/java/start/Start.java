package start;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;

import businesslogic.ClientBLL;
import businesslogic.ProductBLL;
import model.Client;
import model.Product;
import presentation.ClientView;
import presentation.OrderView;
import presentation.ProductView;
import presentation.View;

public class Start {

	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());
	private View view;
	private ClientView cv;
	private ProductView pv;
	private OrderView ov;

	public Start() {

		this.view = new View();
		this.view.setVisible(true);
		this.view.addClientsButtonListener(new ClientsButtonListener());
		this.view.addOrdersButtonListener(new OrdersButtonListener());
		this.view.addProductsButtonListener(new ProductsButtonListener());

	}

	/* 
	 * -----------------------------------------------------------------------------
	 * main view listeners
	 * -----------------------------------------------------------------------------
	 */

	class ClientsButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cv = new ClientView();
			cv.addInsertButtonListener(new InsertClientButtonListener());
			cv.addUpdateButtonListener(new UpdateClientButtonListener());
			cv.addDeleteButtonListener(new DeleteClientButtonListener());
			cv.addFindButtonListener(new FindClientButtonListener());
			cv.addDisplayButtonListener(new DisplayClientButtonListener());
			cv.setVisible(true);
		}
	}

	class ProductsButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			pv = new ProductView();
			pv.addInsertButtonListener(new InsertProductButtonListener());
			pv.addUpdateButtonListener(new UpdateProductButtonListener());
			pv.addDeleteButtonListener(new DeleteProductButtonListener());
			pv.addFindButtonListener(new FindProductButtonListener());
			pv.addDisplayButtonListener(new DisplayProductButtonListener());
			pv.setVisible(true);
		}
	}

	class OrdersButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ov = new OrderView();
			ov.addInsertToCartButtonListener(new AddToCartButtonListener());
			ov.addDeleteFromCartButtonListener(new DeleteFromCartButtonListener());
			ov.addOrderButtonListener(new SubmitOrderButtonListener());
			ov.setVisible(true);
		}
	}

	/*
	 * -----------------------------------------------------------------------------
	 * client view listeners
	 * -----------------------------------------------------------------------------
	 */

	class InsertClientButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (cv.checkFields() == 0) {
				ClientBLL c = new ClientBLL();
				Client cl = new Client(cv.getFullname(), cv.getEmail(), cv.getPhone(), cv.getAddress());
				if (c.checkValidators(cl) == 0) {
					c.insertClient(cl);
					cv.setTableInfo();
				}
			}
		}
	}

	class UpdateClientButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ClientBLL c = new ClientBLL();
			Client cl = cv.getSelectedClient();
			if (cl != null) {
				String name = cv.getFullname();
				String email = cv.getEmail();
				String phone = cv.getPhone();
				String address = cv.getAddress();
				boolean ok = false;
				if (!name.isEmpty()) {
					cl.setFullname(name);
					ok = true;
				}
				if (!email.isEmpty()) {
					cl.setEmail(email);
					ok = true;
				}
				if (!phone.isEmpty()) {
					cl.setPhone(phone);
					ok = true;
				}
				if (!address.isEmpty()) {
					cl.setAddress(address);
					ok = true;
				}
				if (ok) {
					if (c.checkValidators(cl) == 0) {
						c.updateClient(cl);
						cv.setTableInfo();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Nothing to update!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please select a client from the list", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class DeleteClientButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ClientBLL c = new ClientBLL();
			Client cl = cv.getSelectedClient();
			if (cl != null) {
				c.deleteClient(cl.getId());
				cv.setTableInfo();
			} else if (cv.checkIDField() == 0) {
				Long id = cv.getId();
				System.out.println(id);
				if (c.findClientByID(id) != null) {
					c.deleteClient(cv.getId());
					cv.setTableInfo();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please select a client from the list or enter an id", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class FindClientButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (cv.checkIDField() == 0) {
				ClientBLL c = new ClientBLL();
				Long id = cv.getId();
				if (id > 0) {
					cv.setTableInfo(id);
				} else {
					JOptionPane.showMessageDialog(null, "Please enter a valid id", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	class DisplayClientButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cv.setTableInfo();
		}
	}

	/*
	 * -----------------------------------------------------------------------------
	 * product view listeners
	 * -----------------------------------------------------------------------------
	 */

	class InsertProductButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (pv.checkFields() == 0) {
				ProductBLL p = new ProductBLL();
				Product pr = new Product(pv.getName(), pv.getQuantity(), pv.getPrice());
				p.insertProduct(pr);
				pv.setTableInfo();
			}
		}
	}

	class UpdateProductButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ProductBLL p = new ProductBLL();
			Product pr = pv.getSelectedProduct();
			if (pr != null) {
				String name = pv.getName();
				Long quantity = Long.valueOf(pv.getQuantity());
				Double price = Double.valueOf(pv.getPrice());
				boolean ok = false;
				if (!name.isEmpty()) {
					pr.setName(name);
					ok = true;
				}
				if (quantity != 0) {
					pr.setQuantity(quantity);
					ok = true;
				}
				if (price != 0) {
					pr.setPrice(price);
					ok = true;
				}
				if (ok) {
					p.updateProduct(pr);
					pv.setTableInfo();
				} else {
					JOptionPane.showMessageDialog(null, "Nothing to update!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please select a product from the list", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class DeleteProductButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ProductBLL p = new ProductBLL();
			Product pr = pv.getSelectedProduct();
			if (pr != null) {
				p.deleteProduct(pr.getId());
				pv.setTableInfo();
			} else if (pv.checkIDField() == 0) {
				Long id = pv.getId();
				System.out.println(id);
				if (p.findProductByID(id) != null) {
					p.deleteProduct(pv.getId());
					pv.setTableInfo();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please select a product from the list or enter an id", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class FindProductButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (pv.checkIDField() == 0) {
				ProductBLL p = new ProductBLL();
				Long id = pv.getId();
				if (id > 0) {
					pv.setTableInfo(id);
				} else {
					JOptionPane.showMessageDialog(null, "Please enter a valid id", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	class DisplayProductButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			pv.setTableInfo();
		}
	}

	/*
	 * -----------------------------------------------------------------------------
	 * order view listeners
	 * -----------------------------------------------------------------------------
	 */

	class AddToCartButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (ov.checkFields()) {
				ov.disableIDSelection();
				ov.insertIntoCart();
			} else {
				JOptionPane.showMessageDialog(null, "Please enter the quantity", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class DeleteFromCartButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (ov.isProductSelected() && !ov.isTableEmpty()) {
				ov.deleteFromCart();
			} else {
				JOptionPane.showMessageDialog(null, "Please select a product to delete", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class SubmitOrderButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!ov.isTableEmpty()) {
				ov.submitOrder();
			} else {
				JOptionPane.showMessageDialog(null, "Please enter products", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			ov.enableIDSelection();
			ov.resetCart();
		}
	}
	
	public static void main(String[] args) {
		Start start = new Start();
	}

}
