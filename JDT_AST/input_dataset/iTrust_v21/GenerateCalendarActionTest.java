package edu.ncsu.csc.itrust.unit.action;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Date;

import edu.ncsu.csc.itrust.action.AddApptAction;
import edu.ncsu.csc.itrust.action.GenerateCalendarAction;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class GenerateCalendarActionTest extends TestCase {
	private GenerateCalendarAction action;
	private DAOFactory factory;
	private long mId = 2L;
	private long hcpId = 9000000000L;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();

		this.factory = TestDAOFactory.getTestInstance();
		this.action = new GenerateCalendarAction(factory, mId);
	}
	
	public void testGetApptsTable() throws SQLException, DBException {
		Hashtable<Integer, ArrayList<ApptBean>> aTable = action.getApptsTable(Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.YEAR));
		
		assertTrue(aTable.containsKey(5));
		assertTrue(aTable.containsKey(18));
		assertTrue(aTable.containsKey(28));
	}
	
	public void testGetSend() throws SQLException, DBException {
		action.getApptsTable(Calendar.getInstance().get(Calendar.MONTH),
Calendar.getInstance().get(Calendar.YEAR));
		List<ApptBean> aList = action.getSend();
			
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		
		SimpleDateFormat month = new SimpleDateFormat("-MM");
		
		Date now = Calendar.getInstance().getTime();
		
		Timestamp FirstDayOfMonth = Timestamp.valueOf(""+year.format(now)
				+ month.format(now) + "-01 00:00:00");
		Timestamp LastDayOfMonth = Timestamp.valueOf(""+year.format(now)
				+ month.format(now)+ "-31 23:59:59");
				
		for(int i = 0; i < aList.size(); i++)
		{
			assertTrue(aList.get(i).getDate().after(FirstDayOfMonth));
			assertTrue(aList.get(i).getDate().before(LastDayOfMonth));
		}
	}

	/**
	 * testGetConflicts
	 * @throws Exception
	 */
	public void testGetConflicts() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		gen.patient2();
		gen.appointmentType();

		Calendar startDate = Calendar.getInstance();
		startDate.set(Calendar.HOUR, 0);
		startDate.setTimeInMillis(startDate.getTimeInMillis()+1000*60*60*24);
	
		ApptBean b = new ApptBean();
		b.setApptType("General Checkup");
		b.setHcp(hcpId);
		b.setPatient(mId);
		b.setDate(new Timestamp(startDate.getTimeInMillis()+(10*60*1000)));
		b.setComment(null);
		AddApptAction schedAction = new AddApptAction(factory, hcpId);
		try {
			assertTrue(schedAction.addAppt(b, true).startsWith("Success"));
		} catch (FormValidationException e1) {
			fail();
		}
		
		b = new ApptBean();
		b.setApptType("Physical");
		b.setHcp(hcpId);
		b.setPatient(01L);
		b.setDate(new Timestamp(startDate.getTimeInMillis()+(20*60*1000)));
		b.setComment(null);
		try {
			assertTrue(schedAction.addAppt(b, true).startsWith("Success"));
		} catch (FormValidationException e) {
			fail();
		}
		
		b = new ApptBean();
		b.setApptType("Colonoscopy");
		b.setHcp(hcpId);
		b.setPatient(mId);
		b.setDate(new Timestamp(startDate.getTimeInMillis()+(60*60*1000)));
		b.setComment(null);
		try {
			assertTrue(schedAction.addAppt(b, true).startsWith("Success"));
		} catch (FormValidationException e) {
			fail();
		}
		
		this.action = new GenerateCalendarAction(factory, hcpId);	
		action.getApptsTable(startDate.get(Calendar.MONTH), startDate.get(Calendar.YEAR));
		
		boolean conflicts[] = action.getConflicts();		
		assertTrue(conflicts[0]);
		assertTrue(conflicts[1]);
		assertFalse(conflicts[2]);
	}
	
	/**
	 * testGetOfficeVisitsTable
	 * @throws ITrustException
	 */
	public void testGetOfficeVisitsTable() throws ITrustException {
		this.action = new GenerateCalendarAction(factory, mId);
		
		Hashtable<Integer, ArrayList<OfficeVisitBean>> oTable = action.getOfficeVisitsTable(5, 2007);
		
		assertTrue(oTable.containsKey(9));
		assertTrue(oTable.containsKey(10));
	}
	
	/**
	 * testGetLabProceduresTable
	 * @throws ITrustException
	 */
	public void testGetLabProceduresTable() throws ITrustException {
		Hashtable<Integer, ArrayList<LabProcedureBean>> lTable = action.getLabProceduresTable(4, 2007);
		
		assertTrue(lTable.containsKey(19));
	}
	
}
