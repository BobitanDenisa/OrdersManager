package businesslogic.validators;

import javax.swing.JOptionPane;

import model.Client;

public class ClientPhoneValidator implements Validator<Client> {

	private static final String PHONE_PATTERN = "^[0-9] {10}";

	public int validate(Client t) {
		if (!t.getPhone().isEmpty() && !t.getPhone().matches(PHONE_PATTERN) && t.getPhone().length() != 10) {
			JOptionPane.showMessageDialog(null, "Wrong phone!", "Wrong input", JOptionPane.ERROR_MESSAGE);
			return 1;
		}
		return 0;

	}

}
