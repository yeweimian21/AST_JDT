package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddSleepEntryAction;
import edu.ncsu.csc.itrust.action.ViewSleepEntryAction;
import edu.ncsu.csc.itrust.beans.SleepEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests adding sleep entries to the sleep diary
 *
 */
public class AddSleepEntryActionTest extends TestCase {

	private AddSleepEntryAction action;
	private ViewSleepEntryAction viewAction;
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private SleepEntryBean sleepBean;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();	
		sleepBean = new SleepEntryBean();
		sleepBean.setStrDate("12/12/2012");
		sleepBean.setSleepType("Nap");
		sleepBean.setHoursSlept(1);
	}
	
	/**
	 * Clears all of the tables.
	 */
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/**
	 * Tests that a patient can add a new sleep entry to an empty diary.
	 * Log in as the patient Random Person who has no prior sleep entries.
	 */
	@Test
	public void testAddNewSleepEntryToEmptyDiary() {
		action = new AddSleepEntryAction(factory, 1);
		viewAction = new ViewSleepEntryAction(factory, 1);
		try {
			String result = action.addEntry(sleepBean);
			assertEquals("Success: Entry for 12/12/2012 was added "
					+ "successfully!", result);
			try {
				List<SleepEntryBean> sleepDiary = viewAction.getDiary(1);
				assertEquals(3, sleepDiary.size());
				SleepEntryBean bean = sleepDiary.get(0);
				assertEquals(78, bean.getEntryID());
				assertEquals("12/14/2012", bean.getStrDate().toString());
				assertEquals("Nap", bean.getSleepType().name());
				assertEquals(1.0, bean.getHoursSlept());
				
				List<SleepEntryBean> sleepTotals = 
						viewAction.getDiaryTotals(1);
				assertEquals(2, sleepTotals.size());
				SleepEntryBean total1 = sleepTotals.get(0);
				assertEquals("12/14/2012", total1.getStrDate());
				assertEquals(1.0, total1.getHoursSlept());
				SleepEntryBean total = sleepTotals.get(1);
				assertEquals("12/12/2012", total.getStrDate());
				assertEquals(3.0, total.getHoursSlept());
			} catch (ITrustException e) {
				fail(e.getMessage());
			}
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Tests that a patient can add a new sleep entry to a diary that already
	 * has some entries in it.
	 */
	@Test
	public void testAddSleepEntryToNonEmptyDiary() {
		action = new AddSleepEntryAction(factory, 1);
		viewAction = new ViewSleepEntryAction(factory, 1);
		
		String result;
		try {
			result = action.addEntry(sleepBean);
			sleepBean.setStrDate("12/11/2012");
			
			assertEquals(viewAction.getDiary(1).size(), 3);
		} catch (Exception e1) {
			fail(e1.getMessage());
		}
		try {
			result = action.addEntry(sleepBean);
			assertEquals("Success: Entry for 12/11/2012 was added "
					+ "successfully!", result);
			try {
				List<SleepEntryBean> sleepDiary = viewAction.getDiary(1);
				assertEquals(4, sleepDiary.size());
				SleepEntryBean bean = sleepDiary.get(0);
				assertEquals("12/14/2012", bean.getStrDate().toString());
				assertEquals("Nap", bean.getSleepType().name());
				assertEquals(1.0, bean.getHoursSlept());
				
				SleepEntryBean bean2 = sleepDiary.get(3);
				assertEquals("12/11/2012", bean2.getStrDate().toString());
				assertEquals("Nap", bean2.getSleepType().name());
				assertEquals(1.0, bean.getHoursSlept());
				
				List<SleepEntryBean> totals = 
						viewAction.getDiaryTotals(1);
				assertEquals(3, totals.size());
				SleepEntryBean prevTotal = totals.get(2);
				assertEquals("12/11/2012", prevTotal.getStrDate());
				assertEquals(1.0, prevTotal.getHoursSlept());
			} catch (ITrustException e) {
				fail(e.getMessage());
			}
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Tests that dates must be entered in the correct format.
	 */
	@Test
	public void testSleepEntryInvalidDateFormat() {
		action = new AddSleepEntryAction(factory, 1);
		sleepBean.setStrDate("2014/04/04");
		try {
			action.addEntry(sleepBean);
			fail("Date is in the wrong format");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. "
					+ "The following field are not properly filled in: "
					+ "[Date Slept: MM/DD/YYYY]", e.getMessage());
		}
	}
	
	/**
	 * Tests that dates in the future cannot be entered in.
	 * Eventually this test will fail because I am hardcoding in the date.
	 * However, since it is in the future 100 years, I do not expect this to be
	 * a problem (I spent about 30 minutes trying to use the Calendar class to 
	 * add one day to today's date, but I could'nt get it to format correctly).
	 */
	@Test
	public void testSleepEntryFutureDate() {
		action = new AddSleepEntryAction(factory, 1);
		String date = "03/17/2115";
		sleepBean.setStrDate(date);
		try {
			action.addEntry(sleepBean);
			fail("Date is in the future");
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("The Date Slept must be before "
					+ "or on today's Date."));
		}
	}
	
	/**
	 * Ensures that only breakfast, lunch, dinner, or snack is allowed
	 * as the sleep type
	 */
	@Test
	public void testInvalidSleepType() {
		action = new AddSleepEntryAction(factory, 1);
		try {
			sleepBean.setSleepType("Brunch");
			action.addEntry(sleepBean);
			fail("Invalid sleep type");
		} catch (IllegalArgumentException d) {
			assertEquals("Sleep Type Brunch does not exist", d.getMessage());
		} catch (FormValidationException e) {
			fail("Setting sleep type to brunch should have failed");
		}
	}
	
	/**
	 * Test that the number of hours worked must be a positive number
	 */
	@Test
	public void testNumHoursPositive() {
		action = new AddSleepEntryAction(factory, 1);
		sleepBean.setHoursSlept(0);
		try {
			action.addEntry(sleepBean);
			fail("Hours are 0");
		} catch (FormValidationException e) {
			assertTrue(e.getMessage().contains("Number of Hours"));
			sleepBean.setHoursSlept(-1);
			try {
				action.addEntry(sleepBean);
				fail("Hours are negative");
			} catch (FormValidationException d) {
				assertTrue(d.getMessage().contains("Number of Hours"));
			}
		}
	}
	
	/**
	 * Test with an evil factory
	 */
	@Test
	public void testEvilFactory() {
		EvilDAOFactory evil = new EvilDAOFactory();
		action = new AddSleepEntryAction(evil, 1);
		try {
			String result = action.addEntry(sleepBean);
			assertEquals("A database exception has occurred. Please see "
					+ "the log in the console for stacktrace", result);
		} catch (FormValidationException d) {
			fail(d.getMessage());
		}
	}
	
}
