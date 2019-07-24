package edu.ncsu.csc.itrust.unit.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.ViewClaimsAction;
import edu.ncsu.csc.itrust.beans.BillingBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.BillingDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ViewClaimsActionTest extends TestCase{
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewClaimsAction subject;
	private BillingDAO init = factory.getBillingDAO();
	private BillingBean b1;
	private BillingBean b2;
	private static final long PATIENT_MID = 2L;
	private static final int OV_ID = 3;
	private static final long DOCTOR_MID = 9000000000L;

	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		b1 = new BillingBean();
		b1.setAmt(40);
		b1.setApptID(OV_ID);
		b1.setBillID(1);
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
		b1.setInsurance(true);
		b2 = new BillingBean();
		b2.setAmt(40);
		b2.setApptID(OV_ID);
		b2.setBillID(1);
		b2.setHcp(DOCTOR_MID);
		b2.setInsAddress1("123 else drive");
		b2.setInsAddress2(" ");
		b2.setInsCity("Durham");
		b2.setInsHolderName("dad");
		b2.setInsID("1234");
		b2.setInsPhone("333-333-3333");
		b2.setInsProviderName("Blue Cross");
		b2.setInsState("NC");
		b2.setInsZip("27607");
		b2.setPatient(PATIENT_MID);
		b2.setStatus(BillingBean.PENDING);
		b2.setSubTime(new Timestamp(new Date().getTime()));
		b2.setInsurance(true);
		
		b1.setBillID((int)init.addBill(b1));
		init.editBill(b1);
		b2.setBillID((int)init.addBill(b2));
		init.editBill(b2);
		subject = new ViewClaimsAction(factory);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetClaims() {
		List<BillingBean> list = subject.getClaims();
		assertEquals(2, list.size());
		assertEquals("Cigna", list.get(0).getInsProviderName());
		assertEquals("Blue Cross", list.get(1).getInsProviderName());
	}

	@Test
	public void testGetSubmitter() {
		assertEquals("Andy Programmer", subject.getSubmitter(b1));
		assertEquals("Andy Programmer", subject.getSubmitter(b2));
	}

	@Test
	public void testGetDate() {
		assertEquals(new SimpleDateFormat("MM/dd/YYYY").format(new Date()), subject.getDate(b1).toString());
		assertEquals(new SimpleDateFormat("MM/dd/YYYY").format(new Date()), subject.getDate(b2).toString());
	}
	
	@Test
	public void testEvilAction() {
		EvilDAOFactory evil = new EvilDAOFactory();
		ViewClaimsAction evilAction = new ViewClaimsAction(evil);
		assertEquals(null, evilAction.getClaims());
		assertEquals(null, evilAction.getSubmitter(b1));
	}

}
