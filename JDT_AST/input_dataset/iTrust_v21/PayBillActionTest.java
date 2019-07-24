package edu.ncsu.csc.itrust.unit.action;


import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.PayBillAction;
import edu.ncsu.csc.itrust.action.ViewMyBillingAction;
import edu.ncsu.csc.itrust.beans.BillingBean;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class PayBillActionTest extends TestCase{
	private ViewMyBillingAction assistantAction;
	private long mid = 311L; //Sean Ford
	private PayBillAction action;
	private BillingBean billBean;

	public void setUp() throws Exception {
		super.setUp();
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.uc60();
		
		assistantAction = new ViewMyBillingAction(TestDAOFactory.getTestInstance(), this.mid);

		billBean = assistantAction.getAllMyBills().get(1);
		
		action = new PayBillAction(TestDAOFactory.getTestInstance(), billBean.getBillID());
	}
	
	public void testPayBillWithCC() throws Exception{
		String testCC = "4539592576502361";
		String thirtyOne = "1234567890123456789012345678901";

		assertEquals("The field for Credit Card Type must be filled.", action.payBillWithCC(null, null, null, null, null));
		assertEquals("Invalid Credit Card number.", action.payBillWithCC("11", null, "Visa", null, null));
		assertEquals("The field for Credit Card Holder must be filled.", action.payBillWithCC(testCC, null, "Visa", null, null));
		assertEquals("The Credit Card Holder must be 30 characters or shorter.", action.payBillWithCC(testCC, thirtyOne, "Visa", null, null));
		assertEquals("The field for Credit Card Type must be filled.", action.payBillWithCC(testCC, "Bob", "null", null, null));
		assertEquals("The field for the Credit Card Type must be 20 or shorter.", action.payBillWithCC(testCC, "Bob", thirtyOne, null, null));
		assertEquals("The field for Billing Address must be filled.", action.payBillWithCC(testCC, "Bob", "Visa", null, null));
		assertEquals("The fields for Billing Address must be 120 characters or shorter.", action.payBillWithCC(testCC, "Bob", "Visa", thirtyOne + thirtyOne + thirtyOne + thirtyOne + thirtyOne, null));
		assertEquals("The field for CVV must be filled.", action.payBillWithCC(testCC, "Bob", "Visa", "123 Fake St", null));
		assertEquals("Invalid CVV code.", action.payBillWithCC(testCC, "Bob", "Visa", "123 Fake St", "abc"));
		assertNull(action.payBillWithCC(testCC, "Bob", "Visa", "123 Fake St.", "111"));
		billBean = action.getBill();
		assertFalse(billBean.isInsurance());
		assertEquals("Submitted", billBean.getStatus());
		
	}
	
	public void testPayBillWithIns() throws Exception{
		String eT = "555-555-5555";
		String t1 = "123456789012345678901";
		assertEquals("The field for Insurance Holder must be filled.", action.payBillWithIns(null, null, null, null, null, null, null, null, null));
		assertEquals("The field for Insurance Provider must be filled.", action.payBillWithIns("Bob", null, null, null, null, null, null, null, null));
		assertEquals("The Insurance Provider must be 20 characters or shorter.", action.payBillWithIns("Bob", t1, null, null, null, null, null, null, null));
		assertEquals("The field for Insurance Policy ID must be filled.", action.payBillWithIns("Bob", "Blue Cross", null, null, null, null, null, null, null));
		assertEquals("Insurance IDs must consist of alphanumeric characters.", action.payBillWithIns("Bob", "Blue Crosss", "!@#%?", null, null, null, null, null, null));
		assertEquals("The field for Insurance Address 1 must be filled.", action.payBillWithIns("Bob", "Blue Cross", "132345", null, null, null, null, null, null));
		assertEquals("The field for Insurnace Address 1 must be 20 characters or shorter.", action.payBillWithIns("Bob", "Blue Cross", "132345", t1, null, null, null, null, null));
		assertEquals("The field for Insurance Address 2 must be filled.", action.payBillWithIns("Bob", "Blue Cross", "132345", "123 Fake St.", null, null, null, null, null));
		assertEquals("The field for Insurnace Address 2 must 20 characters or shorter.", action.payBillWithIns("Bob", "Blue Cross", "132345", "123 Fake St.", t1, null, null, null, null));
		assertEquals("The field for Insurance City must be filled.", action.payBillWithIns("Bob", "Blue Cross", "132345", "123 Fake St.", "123 Faker St.", null, null, null, null));
		assertEquals("The field for Insurance City must be 20 characters or shorter.", action.payBillWithIns("Bob", "Blue Cross", "132345", "123 Fake St.", "123 Faker St.", t1, null, null, null));
		assertEquals("The field for Insurance State must be filled.", action.payBillWithIns("Bob", "Blue Cross", "132345", "123 Fake St.", "123 Faker St.", "Raleigh", null, null, null));
		assertEquals("The field for Insurance State must be 2 characters.", action.payBillWithIns("Bob", "Blue Cross", "132345", "123 Fake St.", "123 Faker St.", "Raleigh", "NCS", null, null));
		assertEquals("The field for Insurance Zip must be filled.", action.payBillWithIns("Bob", "Blue Cross", "132345", "123 Fake St.", "123 Faker St.", "Raleigh", "NC", null, null));
		assertEquals("The field for Insurance Phone must be filled.", action.payBillWithIns("Bob", "Blue Cross", "132345", "123 Fake St.", "123 Faker St.", "Raleigh", "NC", "27606", null));
		assertEquals("Insurance Phone Number must match the form \"XXX-XXX-XXXX\"", action.payBillWithIns("Bob", "Blue Cross", "132345", "123 Fake St.", "123 Faker St.", "Raleigh", "NC", "27606", "1"));
		assertNull(action.payBillWithIns("Bob", "Blue Cross", "132345", "123 Fake St.", "123 Faker St.", "Raleigh", "NC", "27606", eT));
		billBean = action.getBill();
		assertEquals(1, billBean.getSubmissions());
		assertTrue(billBean.isInsurance());
		assertEquals("Pending", billBean.getStatus());
	}

}
