/**
 * 
 */
package edu.ncsu.csc.itrust.unit.dao.billing;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.BillingBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.BillingDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 */
public class BillingDAOTest {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private EvilDAOFactory evil;
	private BillingDAO evil2;
	private BillingDAO billingDAO = factory.getBillingDAO();
	private BillingBean b1;
	private BillingBean b2;
	private BillingBean bean;
	private static final long PATIENT_MID = 42L;
	private static final int OV_ID = 3;
	private static final long DOCTOR_MID = 9000000000L;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		evil = new EvilDAOFactory(0);
		evil2 = evil.getBillingDAO();
		DBBuilder tables = new DBBuilder();
		tables.dropTables();
		tables.createTables();
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		b1 = new BillingBean();
		b1.setAmt(40);
		b1.setApptID(OV_ID);
		b1.setBillID(1);
		b1.setBillingAddress("123 somewhere drive");
		b1.setCcHolderName("dad");
		b1.setCcNumber("123456789");
		b1.setCcType("Visa");
		b1.setCvv("123");
		b1.setHcp(DOCTOR_MID);
		b1.setInsAddress1("123 else drive");
		b1.setInsAddress2(" ");
		b1.setInsCity("Durham");
		b1.setInsHolderName("dad");
		b1.setInsID("1234");
		b1.setInsPhone("333-333-3333");
		b1.setInsProviderName("insurance");
		b1.setInsState("NC");
		b1.setInsZip("27607");
		b1.setPatient(PATIENT_MID);
		b1.setStatus("Unsubmitted");
		b2 = new BillingBean();
		b2.setAmt(40);
		b2.setApptID(OV_ID);
		b2.setBillID(1);
		b2.setBillingAddress("123 somewhere drive");
		b2.setCcHolderName("dad");
		b2.setCcNumber("987654321");
		b2.setCcType("Visa");
		b2.setCvv("123");
		b2.setHcp(DOCTOR_MID);
		b2.setInsAddress1("123 else drive");
		b2.setInsAddress2(" ");
		b2.setInsCity("Durham");
		b2.setInsHolderName("dad");
		b2.setInsID("1234");
		b2.setInsPhone("333-333-3333");
		b2.setInsProviderName("insurance");
		b2.setInsState("NC");
		b2.setInsZip("27607");
		b2.setPatient(PATIENT_MID);
		b2.setStatus("Unsubmitted");
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.dao.mysql.BillingDAO#addBill(BillingBean)}.
	 * @throws DBException 
	 */
	@Test
	public void testAddBill() throws DBException {
		List<BillingBean> billing = billingDAO.getBills(PATIENT_MID);
		assertEquals(0, billing.size());
		billingDAO.addBill(b1);
		billing = billingDAO.getBills(PATIENT_MID);
		assertEquals(1, billing.size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.dao.mysql.BillingDAO#getBillWithOVId(long)}.
	 * @throws DBException 
	 */
	@Test
	public void testGetBillWithOVId() throws DBException {
		billingDAO.addBill(b1);
		BillingBean billing = billingDAO.getBillWithOVId(OV_ID);
		assertEquals(40, billing.getAmt());
		assertEquals(OV_ID, billing.getApptID());
		assertEquals(1, billing.getBillID());
		assertEquals("123 somewhere drive", billing.getBillingAddress());
		assertEquals("dad", billing.getCcHolderName());
		assertEquals("123456789", billing.getCcNumber());
		assertEquals("Visa", billing.getCcType());
		assertEquals("123", billing.getCvv());
		assertEquals(DOCTOR_MID, billing.getHcp());
		assertEquals("123 else drive", billing.getInsAddress1());
		assertEquals(" ", billing.getInsAddress2());
		assertEquals("Durham", billing.getInsCity());
		assertEquals("dad", billing.getInsHolderName());
		assertEquals("1234", billing.getInsID());
		assertEquals("333-333-3333", billing.getInsPhone());
		assertEquals("insurance", billing.getInsProviderName());
		assertEquals("NC", billing.getInsState());
		assertEquals("27607", billing.getInsZip());
		assertEquals(PATIENT_MID, billing.getPatient());
		assertEquals("Unsubmitted", billing.getStatus());
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.dao.mysql.BillingDAO#getBills(long)}.
	 * @throws DBException 
	 */
	@Test
	public void testGetBills() throws DBException {
		billingDAO.addBill(b1);
		List<BillingBean> billing = billingDAO.getBills(PATIENT_MID);
		assertEquals(40, billing.get(0).getAmt());
		assertEquals(3, billing.get(0).getApptID());
		assertEquals(1, billing.get(0).getBillID());
		assertEquals("123 somewhere drive", billing.get(0).getBillingAddress());
		assertEquals("dad", billing.get(0).getCcHolderName());
		assertEquals("123456789", billing.get(0).getCcNumber());
		assertEquals("Visa", billing.get(0).getCcType());
		assertEquals("123", billing.get(0).getCvv());
		assertEquals(DOCTOR_MID, billing.get(0).getHcp());
		assertEquals("123 else drive", billing.get(0).getInsAddress1());
		assertEquals(" ", billing.get(0).getInsAddress2());
		assertEquals("Durham", billing.get(0).getInsCity());
		assertEquals("dad", billing.get(0).getInsHolderName());
		assertEquals("1234", billing.get(0).getInsID());
		assertEquals("333-333-3333", billing.get(0).getInsPhone());
		assertEquals("insurance", billing.get(0).getInsProviderName());
		assertEquals("NC", billing.get(0).getInsState());
		assertEquals("27607", billing.get(0).getInsZip());
		assertEquals(PATIENT_MID, billing.get(0).getPatient());
		assertEquals("Unsubmitted", billing.get(0).getStatus());
	}
	/**
	 * Tests that you get the correct number of unpaid bills.
	 * @throws DBException
	 */
	@Test
	public void testGetUnpaidBills() throws DBException{
		List<BillingBean> bill = billingDAO.getUnpaidBills(PATIENT_MID);
		
		assertEquals(0, bill.size());					
	}
	
	@Test
	public void testGetBillID() throws DBException{
		List<BillingBean> billing = billingDAO.getBills(PATIENT_MID);
		assertEquals(0, billing.size());
		billingDAO.addBill(b1);
		billing = billingDAO.getBills(PATIENT_MID);
		assertEquals(1, billing.size());
		bean = billingDAO.getBillId(1);
	
		assertEquals(40, bean.getAmt());
	}
	
	
	@Test
	public void testGetInsuranceBills() throws DBException{
		b1.setBillID((int) billingDAO.addBill(b1));
		b1.setSubmissions(b1.getSubmissions() + 1);
		b1.setInsurance(true);
		billingDAO.editBill(b1);
		b1.setInsID("2NDID");
		b1.setBillID((int)billingDAO.addBill(b1));
		b1.setSubmissions(b1.getSubmissions() + 1);
		b1.setInsurance(true);
		billingDAO.editBill(b1);
		
		List<BillingBean> list = billingDAO.getInsuranceBills();
		
		assertEquals(2, list.size());
		assertEquals(1, list.get(0).getSubmissions());
		assertEquals(2, list.get(1).getSubmissions());
	}
	
	/**
	 * Test method for {@link edu.ncsu.csc.itrust.dao.mysql.BillingDAO#editBill(BillingBean)}.
	 * @throws DBException 
	 */
	@Test(expected=DBException.class)
	public void testEditBill() throws DBException {
		List<BillingBean> billing = billingDAO.getBills(PATIENT_MID);
		assertEquals(0, billing.size());
		billingDAO.addBill(b1);
		billing = billingDAO.getBills(PATIENT_MID);
		assertEquals(1, billing.size());
		billingDAO.editBill(b2);
		billing = billingDAO.getBills(PATIENT_MID);
		assertEquals(1, billing.get(0).getBillID());
		assertEquals(40, billing.get(0).getAmt());
		assertEquals("987654321", billing.get(0).getCcNumber());
		evil2.addBill(b1);
	}
	
	/**
	 * Test method for {@link edu.ncsu.csc.itrust.dao.mysql.BillingDAO#RemoveBill(BillingBean)}.
	 * @throws DBException 
	 */
	@Test(expected=DBException.class)
	public void testRemoveBill() throws DBException {
		List<BillingBean> billing = billingDAO.getBills(PATIENT_MID);
		assertEquals(0, billing.size());
		billingDAO.addBill(b1);
		billing = billingDAO.getBills(PATIENT_MID);
		assertEquals(1, billing.size());
		billingDAO.removeBill(b1);
		billing = billingDAO.getBills(PATIENT_MID);
		assertEquals(0, billing.size());
		evil2.removeBill(b1);
	}
	
	/**
	 * Test method for {@link edu.ncsu.csc.itrust.dao.mysql.BillingDAO#getPendingNum()}.
	 * @throws DBException 
	 */
	@Test
	public void testGetPendingNum(){
		b1.setStatus(BillingBean.PENDING);
		try{
			billingDAO.addBill(b1);
			assertEquals(1, billingDAO.getPendingNum());
		} catch(DBException e){
			fail();
		}
	}
	/**
	 * Test method for {@link edu.ncsu.csc.itrust.dao.mysql.BillingDAO#getDeniedNum(long)}.
	 * @throws DBException 
	 */
	@Test
	public void testGetDeniedNum(){
		b1.setStatus(BillingBean.DENIED);
		try{
			billingDAO.addBill(b1);
			assertEquals(1, billingDAO.getDeniedNum(PATIENT_MID));
		} catch(DBException e){
			fail();
		}
	}

	/**
	 * Test method for {@link edu.ncsu.csc.itrust.dao.mysql.BillingDAO#getApprovedNum(long)}.
	 * @throws DBException 
	 */
	@Test
	public void testGetApprovedNum(){
		b1.setStatus(BillingBean.APPROVED);
		try{
			billingDAO.addBill(b1);
			assertEquals(1, billingDAO.getApprovedNum(PATIENT_MID));
		} catch(DBException e){
			fail();
		}
	}
	
	
	

}
