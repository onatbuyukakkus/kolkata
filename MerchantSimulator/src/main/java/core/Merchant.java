package core;

/**
 * Object used for sending rest body.
 * 
 */
public class Merchant {
	
	private String cvr;
	private String name;
	
	public Merchant() {}
	public Merchant(String cvr, String name) {
		this.cvr = cvr;
		this.name = name;
	}
	
	public String getCvr() {
		return this.cvr;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setCvr(String cvr) {
		this.cvr = cvr;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
