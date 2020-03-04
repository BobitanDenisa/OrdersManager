package model;

public class OrderProducts {

	private Long id;
	private Long quantity;
	private Long idorder;
	private Long idproduct;

	public OrderProducts() {
	}

	public OrderProducts(Long quantity, Long idorder, Long idproduct) {
		this.quantity = quantity;
		this.idorder = idorder;
		this.idproduct = idproduct;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getIdorder() {
		return idorder;
	}

	public void setIdorder(Long idorder) {
		this.idorder = idorder;
	}

	public Long getIdproduct() {
		return idproduct;
	}

	public void setIdproduct(Long idproduct) {
		this.idproduct = idproduct;
	}

}
