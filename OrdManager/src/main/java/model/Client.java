package model;

public class Client {

	private Long id;
	private String fullname;
	private String email;
	private String phone;
	private String address;

	public Client() {
	}

	public Client(String fullname, String email, String phone, String address) {
		this.fullname = fullname;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	public Client(Long id, String fullname, String email, String phone, String address) {
		this.id = id;
		this.fullname = fullname;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String toString() {
		return ("Client with id=" + id + ": " + fullname + ", " + email + ", " + phone + ", " + address);
	}

}
