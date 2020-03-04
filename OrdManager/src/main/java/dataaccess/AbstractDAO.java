package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.Statement;

import java.lang.reflect.*;
import java.beans.*;

import connection.ConnectionFactory;

public class AbstractDAO<T> {

	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	private String createSelectIdQuery(T t) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT id FROM `" + type.getSimpleName() + "` WHERE ");
		Field[] fields = t.getClass().getDeclaredFields();
		int first = 1;
		try {
			for (Field f : fields) {
				PropertyDescriptor pd = new PropertyDescriptor(f.getName(), type);
				Method method = pd.getReadMethod();
				Object v = method.invoke(t);
				if (!f.getName().equals("id")) {
					if (first == 0) {
						sb.append(" and ");
					}
					sb.append(f.getName() + "='" + v.toString() + "'");
					first = 0;
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	public Long selectId(T t) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		String query = createSelectIdQuery(t);
		try {
			conn = ConnectionFactory.getConnection();
			stat = conn.prepareStatement(query);
			res = stat.executeQuery();
			res.next();
			return res.getLong(1);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(stat);
			ConnectionFactory.close(conn);
		}
		return Long.valueOf(0);
	}

	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM `" + type.getSimpleName() + "`");
		if (field != null) {
			sb.append(" WHERE " + field + " =?");
		}
		return sb.toString();
	}

	public T findByID(Long id) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		String query = createSelectQuery(type.getDeclaredFields()[0].getName());
		try {
			conn = ConnectionFactory.getConnection();
			stat = conn.prepareStatement(query);
			stat.setLong(1, id);
			res = stat.executeQuery();
			if (res.next()) {
				res.previous();
				return createObjects(res).get(0);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(res);
			ConnectionFactory.close(stat);
			ConnectionFactory.close(conn);
		}
		return null;
	}

	public List<T> findAll() {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		String query = createSelectQuery(null);
		try {
			conn = ConnectionFactory.getConnection();
			stat = conn.prepareStatement(query);
			res = stat.executeQuery();
			return createObjects(res);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(res);
			ConnectionFactory.close(stat);
			ConnectionFactory.close(conn);
		}
		return null;
	}

	private String createInsertQuery(T t) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO `" + type.getSimpleName() + "` (");
		Field[] fieldsArr = type.getDeclaredFields();

		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		int count = -1;
		try {
			for (Field aux : fieldsArr) {
				PropertyDescriptor pd = new PropertyDescriptor(aux.getName(), type);
				Object f = (Object) aux.getName();
				Method method = pd.getReadMethod();
				Object v = method.invoke(t);
				if (!aux.getName().equals("id")) {
					count++;
					fields.add(count, f.toString());
					values.add(count, v.toString());
					System.out.println(fields.get(count) + " " + values.get(count));
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		int first = 1;
		for (String s : fields) {
			if (first == 0) {
				sb.append(", ");
			}
			sb.append(s);
			first = 0;
		}
		sb.append(") VALUES (");
		first = 1;
		for (String s : values) {
			if (first == 0) {
				sb.append(", ");
			}
			sb.append("'" + s + "'");
			first = 0;
		}
		sb.append(")");
		System.out.println(sb.toString() + "\n");
		return sb.toString();
	}

	public Long insert(T t) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		String query = createInsertQuery(t);
		try {
			conn = ConnectionFactory.getConnection();
			stat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stat.executeUpdate();
			res = stat.getGeneratedKeys();
			if (res.next()) {
				return res.getLong(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(stat);
			ConnectionFactory.close(conn);
		}
		return Long.valueOf(0);
	}

	private String createUpdateQuery(T t) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE `" + type.getSimpleName() + "` SET ");
		Field[] fieldsArr = type.getDeclaredFields();

		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		int count = -1;
		int first = 1;
		String id = "";
		try {
			for (Field aux : fieldsArr) {
				PropertyDescriptor pd = new PropertyDescriptor(aux.getName(), type);
				Object f = (Object) aux.getName();
				System.out.println(f.toString());
				Method method = pd.getReadMethod();
				Object v = method.invoke(t);
				System.out.println(v.toString());
				if (first == 1) {
					id = f.toString() + "='" + v.toString() + "'";
					first = 0;
				}
				if (!aux.getName().equals("id")) {
					count++;
					fields.add(count, f.toString());
					values.add(count, v.toString());
					System.out.println(fields.get(count) + " " + values.get(count));
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		first = 1;
		for (int i = 0; i <= count; i++) {
			if (first == 0) {
				sb.append(", ");
			}
			sb.append(fields.get(i) + "='" + values.get(i) + "'");
			first = 0;
		}
		sb.append(" WHERE " + id);
		return sb.toString();
	}

	public void update(T t) {
		Connection conn = null;
		PreparedStatement stat = null;
		String query = createUpdateQuery(t);
		System.out.println(query);
		try {
			conn = ConnectionFactory.getConnection();
			stat = conn.prepareStatement(query);
			stat.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(stat);
			ConnectionFactory.close(conn);
		}
	}

	public List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		try {
			while (resultSet.next()) {
				T instance = type.newInstance();
				for (Field field : type.getDeclaredFields()) {
					Object value = resultSet.getObject(field.getName());
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	private String createDeleteQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM `" + type.getSimpleName() + "` WHERE " + field + " =?");
		return sb.toString();
	}

	public void delete(Long id) {
		Connection conn = null;
		PreparedStatement stat = null;
		String query = createDeleteQuery(type.getDeclaredFields()[0].getName());
		try {
			conn = ConnectionFactory.getConnection();
			stat = conn.prepareStatement(query);
			stat.setLong(1, id);
			stat.execute();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(stat);
			ConnectionFactory.close(conn);
		}
	}

}
