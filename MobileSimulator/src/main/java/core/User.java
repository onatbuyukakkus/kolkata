package core;

/**
 * Object used for sending rest body.
 * 
 */
public class User {
	private String firstName;
	private String lastName;
	private String cpr;
	private String barcode;
	public User() {}
	public User(String cpr, String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.cpr = cpr;
		this.barcode = "";
	}
	
	public String getfirstName() {
		return firstName;
	}
	
	public void setfirstName(String name) {
		this.firstName = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String name) {
		this.lastName = name;
	}
	
	public String getCpr() {
		return cpr;
	}
	
	public void setCpr(String cpr) {
		this.cpr = cpr;
	}
	
}

