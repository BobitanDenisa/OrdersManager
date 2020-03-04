package model;

public class Order {

	private Long id;
	private String date;
	private Double totalpayment;
	private Long idclient;

	public Order() {
	}

	public Order(String date, Double totalpayment, Long idclient) {
		this.date = date;
		this.totalpayment = totalpayment;
		this.idclient = idclient;
	}

	public Order(Long id, String date, Double totalpayment, Long idclient) {
		this.id = id;
		this.date = date;
		this.totalpayment = totalpayment;
		this.idclient = idclient;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getTotalpayment() {
		return totalpayment;
	}

	public void setTotalpayment(Double totalpayment) {
		this.totalpayment = totalpayment;
	}

	public Long getIdclient() {
		return idclient;
	}

	public void setIdclient(Long idclient) {
		this.idclient = idclient;
	}

}
