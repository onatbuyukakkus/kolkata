package UUID;

import java.math.BigInteger;
import java.util.UUID;

/**
 * 
 */

public class RandomNumberGenerator {

	/**
	 * Generates a UUID.
	 * @return {@link String}
	 */
	public static String GenerateUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Convert UUID to decimal.
	 * @param uuid {@link String}
	 * @return {@link String}
	 */
	public static String UUIDtoDec(String uuid) {
		// Convert random number to hex rep
		uuid = uuid.replace("-", "");
		uuid = uuid.toUpperCase();

		// Convert hex random number to decimal
		BigInteger decimalRandomNumber = new BigInteger(uuid, 16);
		String decimalRandomNumberString = decimalRandomNumber.toString();

		// Create 40 digit number by adding zeros
		int diff = 40 - decimalRandomNumberString.length();
		for (int i = 0; i < diff; i++) {
			decimalRandomNumberString = "0" + decimalRandomNumberString;
		}
		return decimalRandomNumberString;
	}

	/**
	 * Convert decimal to UUID.
	 * @param decimal {@link String}
	 * @return {@link String}
	 */
	public static String decToUUID(String decimal) {
		// Convert decimal to hex
		BigInteger hexInteger = new BigInteger(decimal, 10);
		String hex = hexInteger.toString(16);

		// Create UUID string
		hex = hex.toLowerCase();
		hex = hex.substring(0, 8) + "-" + hex.substring(8, 12) + "-" + hex.substring(12, 16) + "-"
				+ hex.substring(16, 20) + "-" + hex.substring(20);
		return hex;
	}
}
