/**
 * 
 */

package junitTest;
import UUID.RandomNumberGenerator;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * 
 *
 */
public class UUIDTest {

	@Test
	public void simpleUUIDCompare() {
		String one = RandomNumberGenerator.GenerateUUID();
		String two = RandomNumberGenerator.GenerateUUID();
		
		assertFalse(one.equals(two));
	}
	
	@Test
	public void conversionTest() {
		String uuid = RandomNumberGenerator.GenerateUUID();
		String decimalUUID = RandomNumberGenerator.UUIDtoDec(uuid);
		String convertedUUID = RandomNumberGenerator.decToUUID(decimalUUID);

		assertTrue(uuid.equals(convertedUUID));
	}
}
