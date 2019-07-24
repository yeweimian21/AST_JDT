package edu.ncsu.csc.itrust.unit.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import edu.ncsu.csc.itrust.action.AddApptAction;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class AddApptActionTest extends TestCase {
	
	private AddApptAction action;
	private DAOFactory factory;
	private long mid = 1L;
	private long hcpId = 9000000000L;
	TestDataGenerator gen;
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		
		this.factory = TestDAOFactory.getTestInstance();
		this.action = new AddApptAction(this.factory, this.hcpId);
	}
	
	public void testAddAppt() throws FormValidationException, SQLException, DBException {
		ApptBean b = new ApptBean();
		b.setApptType("General Checkup");
		b.setHcp(hcpId);
		b.setPatient(mid);
		b.setDate(new Timestamp(System.currentTimeMillis()+(10*60*1000)));
		b.setComment(null);
		
		assertTrue(action.addAppt(b, true).startsWith("Success"));
	}
	
	public void testAddAppt2() throws FormValidationException, SQLException, DBException {
		ApptBean b = new ApptBean();
		b.setApptType("General Checkup");
		b.setHcp(hcpId);
		b.setPatient(mid);
		Timestamp t = new Timestamp(System.currentTimeMillis()-(10*60*1000));
		b.setDate(t);
		b.setComment("Test Appiontment");
		
		assertEquals("The scheduled date of this Appointment ("+t+") has already passed.", action.addAppt(b, true));
	}
	
	public void testGetName() throws ITrustException {
		assertEquals("Kelly Doctor", action.getName(hcpId));
	}
	
	public void testGetName2() throws ITrustException {
		assertEquals("Random Person", action.getName(mid));
	}
	public void testAddConflicts() throws SQLException, FormValidationException, FileNotFoundException, IOException, DBException{
		gen.clearAllTables();
		gen.appointmentType();
		ApptBean a = new ApptBean();
		a.setApptType("General Checkup");
		a.setHcp(hcpId);
		a.setPatient(mid);
		Timestamp t = new Timestamp(System.currentTimeMillis()+(10*60*1000));
		a.setDate(t);
		a.setComment("Test Appiontment");
		
		ApptBean b = new ApptBean();
		b.setApptType("General Checkup");
		b.setHcp(hcpId);
		b.setPatient(mid);
		Timestamp t2 = new Timestamp(System.currentTimeMillis()+(20*60*1000));
		b.setDate(t2);
		b.setComment("Test Appiontment");
		
	
		String resultA = action.addAppt(a, false);
		String resultB = action.addAppt(b, false);
	
		assertTrue(resultA.contains("Success"));
		assertTrue(resultB.contains("conflict"));
	}
	
	public void testGetConflicts() throws SQLException, FormValidationException, FileNotFoundException, IOException, DBException{
		gen.clearAllTables();
		gen.appointmentType();
		ApptBean a = new ApptBean();
		a.setApptType("General Checkup");
		a.setHcp(hcpId);
		a.setPatient(mid);
		Timestamp t = new Timestamp(System.currentTimeMillis()+(10*60*1000));
		a.setDate(t);
		a.setComment("Test Appiontment");
		
		ApptBean b = new ApptBean();
		b.setApptType("General Checkup");
		b.setHcp(hcpId);
		b.setPatient(mid);
		Timestamp t2 = new Timestamp(System.currentTimeMillis()+(20*60*1000));
		b.setDate(t2);
		b.setComment("Test Appiontment");
		
	
		action.addAppt(a, true);
		action.addAppt(b, true);
	
	
		List<ApptBean> conflicts = action.getAllConflicts(hcpId);
		assertEquals(2, conflicts.size());
		
		List<ApptBean> conflictForA;
		
		conflictForA = action.getConflictsForAppt(hcpId, a);
		
		assertEquals(2, conflictForA.size());
	}
}
