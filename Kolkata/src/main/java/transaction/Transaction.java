/**
 * 
 */
package transaction;

/**
 * 
 *
 */
public class Transaction {
	private String amount, cvr,  UUID;
	
	public Transaction() {}
	public Transaction(String amount, String cvr, String UUID) {
		this.amount = amount;
		this.cvr = cvr;
		this.UUID = UUID;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCvr() {
		return cvr;
	}
	public void setCvr(String cvr) {
		this.cvr = cvr;
	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	
}
