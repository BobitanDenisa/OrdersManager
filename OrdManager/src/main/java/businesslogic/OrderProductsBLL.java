package businesslogic;

import java.util.List;
import java.util.NoSuchElementException;

import dataaccess.OrderProductsDAO;
import model.OrderProducts;

public class OrderProductsBLL {

	private OrderProductsDAO orderProductsDAO;

	public OrderProductsBLL() {
		this.orderProductsDAO = new OrderProductsDAO();
	}

	public List<OrderProducts> findAllOrderpPoducts() {
		return orderProductsDAO.findAll();
	}

	public OrderProducts findOrderProductsByID(Long id) {
		OrderProducts op = orderProductsDAO.findByID(id);
		if (op == null) {
			throw new NoSuchElementException("The client with id=" + id + " was not found!");
		}
		return op;
	}

	public Long insertOrderProducts(OrderProducts op) {
		return orderProductsDAO.insert(op);
	}

	public void updateOrderProducts(OrderProducts op) {
		orderProductsDAO.update(op);
	}

	public void deleteOrderProducts(Long id) {
		orderProductsDAO.delete(id);
	}

}
