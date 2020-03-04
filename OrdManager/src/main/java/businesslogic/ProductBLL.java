package businesslogic;

import java.util.List;
import java.util.NoSuchElementException;

import businesslogic.validators.Validator;
import dataaccess.ProductDAO;
import model.Client;
import model.Product;

public class ProductBLL {

	private ProductDAO productDAO;

	public ProductBLL() {
		this.productDAO = new ProductDAO();
	}

	public List<Product> findAllProducts() {
		return productDAO.findAll();
	}

	public Product findByName(String name) {
		return productDAO.findByName(name);
	}

	public Product findProductByID(Long id) {
		Product p = productDAO.findByID(id);
		if (p == null) {
			throw new NoSuchElementException("The product with id=" + id + " was not found!");
		}
		return p;
	}

	public Long insertProduct(Product p) {
		return productDAO.insert(p);
	}

	public void updateProduct(Product p) {
		productDAO.update(p);
	}

	public void deleteProduct(Long id) {
		productDAO.delete(id);
	}

}
