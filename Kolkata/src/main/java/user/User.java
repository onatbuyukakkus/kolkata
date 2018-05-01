package user;

import UUID.RandomNumberGenerator;
import core.Storage;

/**
 * 
 */
public class User {

	private String cpr;
	private String firstname;
	private String lastname;

	private String barcode;

	/**
	 * Constructor of class {@link User}.
	 * Upon creation of user object, a barcode is generated for the user and stored
	 * in the barcode storage.
	 * @param cpr Danish civil registration number. Allowed object is {@link String}
	 * @param firstname First name of user. Allowed object is {@link String}
	 * @param lastname Last name of user. Allowed object is {@link String}
	 */
	public User() {}
	public User(String cpr, String firstname, String lastname) {
		this.cpr = cpr;
		this.lastname = lastname;
		this.firstname = firstname;

		this.barcode = RandomNumberGenerator.GenerateUUID();
		Storage.addBarcode(this);
	}

	/**
	 * Creates new barcode for a {@link User} object. Stores the old barcode in the
	 * usedBarcodeStorage. Adds the new barcode to the barcode storage.
	 */
	public String createNewBarcode() {
		Storage.addUsedBarcode(this);
		Storage.removeBarcodeFromBarcodeStorage(barcode);
		barcode = RandomNumberGenerator.GenerateUUID();
		Storage.addBarcode(this);
		return ("Transaction successful.");
	}

	/**
	 * Check whether bar code belongs to user.
	 * @param inputBarcode allowed object is {@link String}
	 * @return true if the inputBarcode represents a String equivalent to the
	 *         barcode of the user object, false otherwise
	 */
	public boolean checkBarcode(String inputBarcode) {
		return inputBarcode.equals(barcode);
	}
	
	/**
	 * Changes the firstname to the given param
	 * @param firstName allowed value {@link String}
	 */
	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}
	
	/**
	 * Changes the lastname to the given param
	 * @param lastName allowed value {@link String}
	 */
	public void setLastName(String lastName) {
		this.lastname = lastName;
	}
	/**
	 * Get the first name of user.
	 * @return {@link String}
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Get the last name of user.
	 * @return {@link String}
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Get the barcode of user.
	 * @return {@link String}
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * Get the CPR of user.
	 * @return {@link String}
	 */
	public String getCpr() {
		return cpr;
	}
	
	/**
	 * Changes the cpr to the given param
	 * @param cpr allowed value {@link String}
	 */
	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

}
