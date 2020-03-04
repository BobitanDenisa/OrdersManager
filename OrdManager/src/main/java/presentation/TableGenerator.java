package presentation;

import java.lang.reflect.Field;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableGenerator<T> {

	public JTable createTable(T t) {
		JTable table = new JTable();
		Field[] fields = t.getClass().getDeclaredFields();
		String[] s = new String[fields.length];
		int i = -1;
		for (Field f : fields) {
			i++;
			s[i] = f.getName();
			System.out.println(s[i]);
		}
		DefaultTableModel model = new DefaultTableModel(s, 0);
		table.setModel(model);
		return table;
	}

}
