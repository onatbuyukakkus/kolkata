package core;

import java.util.HashMap;

import merchant.Merchant;
import user.User;

/**
 * This class is used to store users, merchants and barcodes.
 * 
 */
public final class Storage {

	private static final Storage INSTANCE = new Storage();
	private static HashMap<String, User> userStorage;
	private static HashMap<String, String> barcodeStorage;
	private static HashMap<String, String> usedBarcodeStorage;
	private static HashMap<String, Merchant> merchantStorage;

	/**
	 * Constructor
	 */
	private Storage() {
		userStorage = new HashMap<>();         /* Storage containing all users */
		barcodeStorage = new HashMap<>();      /* Storage containing users bar codes */
		usedBarcodeStorage = new HashMap<>();  /* Storage containing all used bar codes */
		merchantStorage = new HashMap<>();     /* Storage containing all merchants */
	}

	/**
	 * Adding user to database/hash map storage of users.
	 * If new user, the user is added to storage and given a bar code, if user already exists, nothing happens.
	 * @param receivedInput Array of {@link User} objects, each user has CPR number, first and last name
	 * @return Message describing the outcome. Possible object is {@link String}
	 */
	public static String addUser(String[] receivedInput) {
		if (!userStorage.containsKey(receivedInput[1])) {
			User newUser = new User(receivedInput[1], receivedInput[2], receivedInput[3]);
			userStorage.put(receivedInput[1], newUser);
			return("New user created.");
		} else {
			return("User already exists.");
		}
	}

	/**
	 * Add user barcode to barcode storage.
	 * @param user The {@link User} object for the user whose barcode is to be added to the storage 
	 */
	public static void addBarcode(User user) {
		barcodeStorage.put(user.getBarcode(), user.getCpr());
	}

	/**
	 * Adding a user's old barcode to the old barcode storage for history.
	 * @param user {@link User} whose barcode we want to add to the storage
	 */
	public static void addUsedBarcode(User user) {
		usedBarcodeStorage.put(user.getBarcode(), user.getCpr());
	}

	/**
	 * Adding a merchant to the database/hash map storage of merchants.
	 * @param cvr The 8 digit identifier of the merchant {@link String} 
	 * @param name Name of a user. Allowed object {@link String}
	 * @return Message depending on whether user already exists or not. Possible object is {@link String}
	 */
	public static String addMerchant(String cvr, String name) {
		if(!merchantStorage.containsKey(cvr)) {
			merchantStorage.put(cvr, new Merchant(cvr, name));
			return("New merchant created.");
		}
		else {
			return("Merchant already exists.");
		}
	}

	/**
	 * Returning instance of Storage.
	 * @return Possible object is {@link Storage}
	 */
	public static Storage getInstance() {
		return INSTANCE;
	}

	/**
	 * Getting a user from the user storage by associated CPR number.
	 * @param cpr The CPR number associated with the specific {@link User} object
	 * @return Possible object is {@link Storage}
	 */
	public static User getUserFromCpr(String cpr) {
		return userStorage.get(cpr);
	}

	/**
	 * Get full storage of current users.
	 * @return Possible object is {@link Storage}
	 */
	public static HashMap<String, User> getUserStorage() {
		return userStorage;
	}

	/**
	 * Get storage of active barcodes.
	 * @return Possible object is {@link Storage}
	 */
	public static HashMap<String, String> getBarcodeStorage() {
		return barcodeStorage;
	}

	/**
	 * Removing given barcode from the storage of active barcodes.
	 * @param barcode The barcode associated with a user. Allowed object is {@link String}
	 */
	public static void removeBarcodeFromBarcodeStorage(String barcode) {
		barcodeStorage.remove(barcode);
	}

	/**
	 * Return storage of used barcodes.
	 * @return Possible object is {@link Storage}
	 */
	public static HashMap<String, String> getUsedBarcodeStorage() {
		return usedBarcodeStorage;
	}

	/**
	 * Return merchant storage.
	 * @return Possible object is {@link Storage}
	 */
	public static HashMap<String, Merchant> getMerchantStorage() {
		return merchantStorage;
	}

	/**
	 * Get merchant from CVR number.
	 * @param cvr The merchant identifier CVR number. Allowed object is {@link String}
	 * @return Possible object is {@link Merchant}
	 */
	public static Merchant getMerchant(String cvr) {
		return merchantStorage.get(cvr);
	}

	/**
	 * Clear all storages.
	 * Method used for testing purposes in order to reset all storages to be empty.
	 */
	public static void clear() {
		userStorage.clear();
		usedBarcodeStorage.clear();
		merchantStorage.clear();
		barcodeStorage.clear();
	}

}
