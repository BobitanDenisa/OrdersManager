package businesslogic.validators;

import javax.swing.JOptionPane;

import model.Client;

public class ClientNameValidator implements Validator<Client> {

	private static final String NAME_PATTERN = "^[a-zA-Z\\s]*$";

	public int validate(Client t) {
		if (!t.getFullname().isEmpty() && !t.getFullname().matches(NAME_PATTERN)) {
			JOptionPane.showMessageDialog(null, "Wrong name!", "Wrong input", JOptionPane.ERROR_MESSAGE);
			return 1;
		}
		return 0;
	}

}
