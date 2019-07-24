package edu.ncsu.csc.itrust.unit.action;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.AddApptRequestAction;
import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.ApptRequestBean;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class AddApptRequestActionTest extends TestCase {

	private AddApptRequestAction action;
	private TestDataGenerator gen = new TestDataGenerator();
	
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.apptRequestConflicts();
		action = new AddApptRequestAction(TestDAOFactory.getTestInstance());
	}

	public void testAddApptRequest() throws Exception {
		ApptBean b = new ApptBean();
		b.setApptType("General Checkup");
		b.setHcp(9000000010L);
		b.setPatient(2L);
		
		String time = "01:45 PM";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat fo = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 7);
		time = fo.format(cal.getTime()) + " " + time;
		fo = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		Date d = fo.parse(time);
		
		b.setDate(new Timestamp(d.getTime()));
		ApptRequestBean req = new ApptRequestBean();
		req.setRequestedAppt(b);
		String expected = "The appointment you requested conflicts with other existing appointments.";
		assertEquals(expected, action.addApptRequest(req));
		expected = "Your appointment request has been saved and is pending.";
		cal.set(2012, 7, 20, 18, 45, 0);
		req.getRequestedAppt().setDate(new Timestamp(cal.getTimeInMillis()));
		assertEquals(expected, action.addApptRequest(req));
	}

	public void testGetNextAvailableAppts() throws SQLException, ParseException, DBException {
		ApptBean b = new ApptBean();
		b.setApptType("General Checkup");
		b.setHcp(9000000000L);
		b.setPatient(2L);
		
		String time = "01:45 PM";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat fo = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 7);
		@SuppressWarnings("unused")
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		time = fo.format(cal.getTime()) + " " + time;
		fo = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		Date d = fo.parse(time);
		
		b.setDate(new Timestamp(d.getTime()));
		List<ApptBean> next = action.getNextAvailableAppts(3, b);
		assertEquals(3, next.size());
		
		cal.clear();
		time = "03:30 PM";
		cal = Calendar.getInstance();
		fo = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 7);
		sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		time = fo.format(cal.getTime()) + " " + time;
		fo = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		d = fo.parse(time);
		cal.clear(Calendar.MILLISECOND);
		
		Timestamp e1 = new Timestamp(d.getTime());
		
		cal.clear();
		time = "05:30 PM";
		cal = Calendar.getInstance();
		fo = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 7);
		sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		time = fo.format(cal.getTime()) + " " + time;
		fo = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		d = fo.parse(time);
		cal.clear(Calendar.MILLISECOND);
		
		Timestamp e2 = new Timestamp(d.getTime());

		cal.clear();
		time = "06:15 PM";
		cal = Calendar.getInstance();
		fo = new SimpleDateFormat("MM/dd/yyyy");
		cal.add(Calendar.DAY_OF_YEAR, 7);
		sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		time = fo.format(cal.getTime()) + " " + time;
		fo = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
		d = fo.parse(time);
		cal.clear(Calendar.MILLISECOND);
		
		Timestamp e3 = new Timestamp(d.getTime());
		assertEquals(e1, next.get(0).getDate());
		assertEquals(e2, next.get(1).getDate());
		assertEquals(e3, next.get(2).getDate());
	}

}
