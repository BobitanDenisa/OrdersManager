package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import connection.ConnectionFactory;
import model.Product;

public class ProductDAO extends AbstractDAO<Product> {

	public Product findByName(String name) {
		Product p = new Product();
		String query = "SELECT * FROM " + p.getClass().getSimpleName() + " WHERE name=?";
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try {
			conn = ConnectionFactory.getConnection();
			stat = conn.prepareStatement(query);
			stat.setString(1, name);
			res = stat.executeQuery();
			if (res.next()) {
				res.previous();
				return createObjects(res).get(0);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, p.getClass().getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(res);
			ConnectionFactory.close(stat);
			ConnectionFactory.close(conn);
		}
		return null;
	}

}
