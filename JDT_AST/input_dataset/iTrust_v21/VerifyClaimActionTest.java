package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.VerifyClaimAction;
import edu.ncsu.csc.itrust.beans.BillingBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.BillingDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class VerifyClaimActionTest {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private VerifyClaimAction subject;
	private BillingDAO init = factory.getBillingDAO();
	private OfficeVisitDAO init2 = factory.getOfficeVisitDAO();
	private BillingBean b1;
	private OfficeVisitBean ov;
	private static final long PATIENT_MID = 42L;
	private static final long DOCTOR_MID = 9000000000L;
	
	@Before
	public void setUp() throws Exception {
		
		gen.clearAllTables();
		b1 = new BillingBean();
		b1.setAmt(40);
		b1.setInsAddress1("123 else drive");
		b1.setInsAddress2(" ");
		b1.setInsCity("Durham");
		b1.setInsHolderName("dad");
		b1.setInsID("1234");
		b1.setInsPhone("333-333-3333");
		b1.setInsProviderName("Cigna");
		b1.setInsState("NC");
		b1.setInsZip("27607");
		b1.setPatient(PATIENT_MID);
		b1.setStatus(BillingBean.PENDING);
		b1.setSubTime(new Timestamp(new Date().getTime()));
		ov = new OfficeVisitBean();
		ov.setAppointmentType("Mammogram");
		ov.setBilled(true);
		ov.setHcpID(DOCTOR_MID);
		ov.setPatientID(PATIENT_MID);
		int ovID = (int) init2.add(ov);
		b1.setApptID(ovID);
		
		b1.setBillID((int) init.addBill(b1));
		init.editBill(b1);
		subject = new VerifyClaimAction(factory, b1.getBillID());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetBill() {
		assertEquals("123 else drive", subject.getBill().getInsAddress1());
		assertEquals("dad", subject.getBill().getInsHolderName());
		assertEquals(40, subject.getBill().getAmt());
	}

	@Test
	public void testGetOV() {
		assertEquals("Mammogram", subject.getOV().getAppointmentType());
		assertEquals(DOCTOR_MID, subject.getOV().getHcpID());
	}

	@Test
	public void testDenyClaim() {
		subject.denyClaim();
		try {
			b1 = init.getBillId(b1.getBillID());
		} catch (DBException e) {
			e.printStackTrace();
			fail();
		}
		assertEquals(BillingBean.DENIED, b1.getStatus());
	}

	@Test
	public void testApproveClaim() {
		subject.approveClaim();
		try {
			b1 = init.getBillId(b1.getBillID());
		} catch (DBException e) {
			e.printStackTrace();
			fail();
		}
		assertEquals(BillingBean.APPROVED, b1.getStatus());
	}

	@Test
	public void testEvilAction() {
		EvilDAOFactory evil = new EvilDAOFactory();
		VerifyClaimAction evilAction = new VerifyClaimAction(evil, b1.getBillID());
		assertEquals(null, evilAction.getBill());	
	}
}
