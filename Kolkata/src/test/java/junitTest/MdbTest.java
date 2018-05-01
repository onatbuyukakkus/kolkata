package junitTest;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import core.Storage;	

/**
 * 
 */
public class MdbTest {

	private String[] addUserMessage1;
	private String[] addUserMessage2;

	private String RESTinput1          = "addUser/120211-3455/Alan/Turing";
	private String RESTinput2          = "addUser/120260-3457/Donald/Knuth";
	private int userStorageSize        = Storage.getUserStorage().size();
	private int barcodeStorageSize     = Storage.getBarcodeStorage().size();

	@Before
	public void setUp() {
		addUserMessage1  = RESTinput1.split("/");
		addUserMessage2 = RESTinput2.split("/");
	}

	@Test
	public void testOnMessage() {
		assertEquals("[addUser, 120211-3455, Alan, Turing]", Arrays.toString(addUserMessage1));
		assertEquals("[addUser, 120260-3457, Donald, Knuth]", Arrays.toString(addUserMessage2));
	}

	@Test
	//Try to add the same user twice (expected only to be added once)
	public void testAddUser() throws Exception {
		
		Storage.addUser(addUserMessage1);
		Storage.addUser(addUserMessage1);
		
		assertEquals(userStorageSize + 1, Storage.getUserStorage().size());
		assertEquals(barcodeStorageSize + 1, Storage.getBarcodeStorage().size());
		
		// Add another new user
		Storage.addUser(addUserMessage2);
		
		assertEquals(userStorageSize + 2 , Storage.getUserStorage().size());
		assertEquals(barcodeStorageSize + 2, Storage.getBarcodeStorage().size());
	}
	
	@Test
	public void resetStorage() {
		
		Storage.clear();
		assertEquals(0, Storage.getUserStorage().size());
		assertEquals(0, Storage.getMerchantStorage().size());
		assertEquals(0, Storage.getBarcodeStorage().size());
		assertEquals(0, Storage.getUsedBarcodeStorage().size());
	}
}
