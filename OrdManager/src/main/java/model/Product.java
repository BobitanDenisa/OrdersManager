package model;

public class Product {

	private Long id;
	private String name;
	private Long quantity;
	private Double price;

	public Product() {
	}

	public Product(String name, Long quantity, Double price) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	public Product(Long id, String name, Long quantity, Double price) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String toString() {
		return "Product with id=" + id + ": " + name + ", " + quantity + ", " + price;
	}

}
