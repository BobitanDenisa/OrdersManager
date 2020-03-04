package businesslogic;

import java.util.List;
import java.util.NoSuchElementException;

import dataaccess.OrderDAO;
import model.Order;

public class OrderBLL {

	private OrderDAO orderDAO;

	public OrderBLL() {
		this.orderDAO = new OrderDAO();
	}

	public List<Order> findAllOrders() {
		return orderDAO.findAll();
	}

	public Order findOrderByID(Long id) {
		Order o = orderDAO.findByID(id);
		if (o == null) {
			throw new NoSuchElementException("The order with id=" + id + " was not found!");
		}
		return o;
	}

	public Long insertOrder(Order o) {
		return orderDAO.insert(o);
	}

	public void updateOrder(Order o) {
		orderDAO.update(o);
	}

	public void deleteOrder(Long id) {
		orderDAO.delete(id);
	}

}
