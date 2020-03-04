package presentation;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.JComboBox;

public class ComboBoxGenerator<T> {

	public JComboBox<String> createComboBox(List<T> list, int fieldIndex) {
		String[] ids = new String[list.size()];
		Field field = list.get(0).getClass().getDeclaredFields()[fieldIndex];
		int i = 0;
		for (T el : list) {
			try {
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), el.getClass());
				Method method = pd.getReadMethod();
				Object o = method.invoke(el);
				ids[i] = o.toString();
				i++;

			} catch (IntrospectionException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		JComboBox<String> box = new JComboBox<String>(ids);
		return box;
	}

}
