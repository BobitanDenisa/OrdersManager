package businesslogic;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;

import businesslogic.validators.ClientEmailValidator;
import businesslogic.validators.ClientNameValidator;
import businesslogic.validators.ClientPhoneValidator;
import businesslogic.validators.Validator;
import dataaccess.ClientDAO;
import model.Client;

public class ClientBLL {

	private List<Validator<Client>> validators;
	private ClientDAO clientDAO;

	public ClientBLL() {
		validators = new ArrayList<Validator<Client>>();
		validators.add(new ClientNameValidator());
		validators.add(new ClientEmailValidator());
		validators.add(new ClientPhoneValidator());

		clientDAO = new ClientDAO();
	}

	public List<Client> findAllClients() {
		return clientDAO.findAll();
	}

	public Client findClientByID(Long id) {
		Client c = clientDAO.findByID(id);
		if (c == null) {
			JOptionPane.showMessageDialog(null, "The client with id " + id + " was not found!", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
		return c;
	}

	public Long insertClient(Client c) {
		return clientDAO.insert(c);
	}

	public void updateClient(Client c) {
		clientDAO.update(c);
	}

	public void deleteClient(Long id) {
		clientDAO.delete(id);
	}

	public int checkValidators(Client c) {
		for (Validator<Client> v : validators) {
			if (v.validate(c) != 0) {
				return 1;
			}
		}
		return 0;
	}

}
