package merchant;

/**
 * Merchant represents the employee in a store who scans the barcode and carries out the purchase. 
 * 
 * 
 * 
 */

public class Merchant {
	
	private String cvr;
	private String name;
	
	/**
	 * Constructor of class {@link Merchant}.
	 * @param cvr Danish registration number for companies. Allowed object is {@link String }. 
	 * @param name allowed object is {@link String }.
	 */
	
	public Merchant(String cvr, String name) {
		this.cvr = cvr;
		this.name = name;
	}
	
	public Merchant() {
		
	}
	
	/**
	 * Returns the Cvr of the merchant.
	 * @return possible object is {@link String }
	 */

	public String getCvr() {
		return this.cvr;
	}
	
	/**
	 * Returns the name of the merchant.
	 * @return possible object is {@link String }
	 */
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * Changes the cvr to the given param
	 * @param cvr allowed value {@link String}
	 */
	public void setCvr(String cvr) {
		this.cvr = cvr;
	}

	/**
	 * Changes the name to the given param
	 * @param name allowed value {@link String}
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
