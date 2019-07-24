package edu.ncsu.csc.itrust.unit.action;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.action.EditApptAction;
import edu.ncsu.csc.itrust.action.ViewMyApptsAction;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class EditApptActionTest extends TestCase {
	private EditApptAction editAction;
	private EditApptAction evilAction;
	private ViewMyApptsAction viewAction;
	private DAOFactory factory;
	private DAOFactory evilFactory;
	private long hcpId = 9000000000L;
	
	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.hcp0();
		gen.patient42();
		gen.appointment();
		gen.appointmentType();
		gen.uc22();
		
		this.factory = TestDAOFactory.getTestInstance();
		this.evilFactory = EvilDAOFactory.getEvilInstance();
		this.evilAction = new EditApptAction(this.evilFactory, this.hcpId);
		this.editAction = new EditApptAction(this.factory, this.hcpId);
		this.viewAction = new ViewMyApptsAction(this.factory, this.hcpId);
	}
	
	public void testRemoveAppt() throws Exception {
		List<ApptBean> appts = viewAction.getMyAppointments();
		int size = appts.size();
		assertEquals("Success: Appointment removed", editAction.removeAppt(appts.get(0)));
		assertEquals(size-1, viewAction.getMyAppointments().size());
		editAction.removeAppt(appts.get(0));
	}
	
	public void testGetAppt() throws Exception , DBException{
		List<ApptBean> appts = viewAction.getMyAppointments();
		ApptBean b1 = appts.get(0);
		ApptBean b2 = editAction.getAppt(b1.getApptID());
		ApptBean b3 = new ApptBean();
		
		b3 = editAction.getAppt(1234567891);
		
		assertTrue(b3 == null);
		
		assertEquals(b1.getApptID(), b2.getApptID());
		assertEquals(b1.getApptType(), b2.getApptType());
		assertEquals(b1.getComment(), b2.getComment());
		assertEquals(b1.getHcp(), b2.getHcp());
		assertEquals(b1.getPatient(), b2.getPatient());
		assertEquals(b1.getClass(), b2.getClass());
		assertEquals(b1.getDate(), b2.getDate());
		
		try{
			
			evilAction.getAppt(b1.getApptID());
		}catch(DBException e){
			//success!
		}
		
		try{
			
			evilAction.removeAppt(b1);
		}catch(DBException e){
			//success!
		}
	}
	
	/**
	 * testGetName
	 * @throws ITrustException
	 */
	public void testGetName() throws ITrustException {
		assertEquals("Kelly Doctor", editAction.getName(hcpId));
		assertEquals("Bad Horse", editAction.getName(42));
	}
	
	/**
	 * testEditAppt
	 * @throws DBException
	 * @throws SQLException
	 * @throws FormValidationException
	 */
	public void testEditAppt() throws DBException, SQLException, FormValidationException {
		List<ApptBean> appts = viewAction.getAllMyAppointments();
		ApptBean orig = appts.get(0);
		ApptBean b = new ApptBean();
		b.setApptID(orig.getApptID());
		b.setDate(orig.getDate());
		b.setApptType(orig.getApptType());
		b.setHcp(orig.getHcp());
		b.setPatient(orig.getPatient());
		b.setComment("New comment!");
		
		String s = editAction.editAppt(b, true);
		assertTrue(s.contains("The scheduled date of this appointment"));
		assertTrue(s.contains("has already passed"));
		
		Date d = new Date();
		boolean changed = false;
		for (ApptBean aBean : appts) {
			b = new ApptBean();
			b.setApptID(aBean.getApptID());
			b.setDate(aBean.getDate());
			b.setApptType(aBean.getApptType());
			b.setHcp(aBean.getHcp());
			b.setPatient(aBean.getPatient());
			b.setComment("New comment!");
			d.setTime(aBean.getDate().getTime());
			if (d.after(new Date())) {
				s = editAction.editAppt(b, true);
				assertEquals("Success: Appointment changed", s);
				changed = true;
				break;
			}
		}
		
		if (!changed)
			fail();
	}
	
	/**
	 * testEditApptConflict
	 * @throws Exception
	 */
	public void testEditApptConflict() throws Exception {
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 12);
		c.set(Calendar.HOUR, 9);
		c.set(Calendar.AM_PM, Calendar.AM);
		c.set(Calendar.MINUTE, 45);
		
		List<ApptBean> appts = viewAction.getMyAppointments();
		ApptBean orig = appts.get(0);
		ApptBean b = new ApptBean();
		b.setApptID(orig.getApptID());
		b.setDate(new Timestamp(c.getTimeInMillis()));
		b.setApptType(orig.getApptType());
		b.setHcp(orig.getHcp());
		b.setPatient(orig.getPatient());
		
		String s = editAction.editAppt(b, false);
		assertTrue(s.contains("conflict"));
		
	}

	/**
	 * testEvilFactory
	 * @throws DBException
	 * @throws SQLException
	 * @throws FormValidationException
	 */
	public void testEvilFactory() throws DBException, SQLException, FormValidationException {
		this.editAction = new EditApptAction(EvilDAOFactory.getEvilInstance(), this.hcpId);
		List<ApptBean> appts = viewAction.getMyAppointments();
		ApptBean x = appts.get(0);
		try{
		
			editAction.editAppt(x, true);
		}catch(DBException e){
			//this should pass if DBexception is thrown
		}
		
		try{
		
			editAction.editAppt(x, true);
		}catch(DBException e){
			//this should pass if DBexception is thrown
		}
		try{
			assertEquals(null, editAction.getAppt(x.getApptID()));
		}catch(DBException e){
			//this should pass if DBexception is thrown
		}
	}
}
