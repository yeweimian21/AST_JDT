package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.ViewSleepEntryAction;
import edu.ncsu.csc.itrust.beans.SleepEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests for viewing a patient's sleep diary
 *
 */
public class ViewSleepEntryActionTest extends TestCase {
	private ViewSleepEntryAction action;
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private EvilDAOFactory evil = new EvilDAOFactory();
	
	/**
	 * Clears all of the tables, gets the standard data, 
	 * and includes the data for use cases 68 and 69
	 */
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * Clears all of the tables
	 */
	@Override
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	/**
	 * Log in as the HCP Duyu Ivanlyft who does have the specialty of
	 * trainer, so she should be able to view sleep entries. View
	 * the Sleep Entries for Patient Random Person who has some
	 * sleep entries already in their diary.
	 */
	@Test
	public void testViewingSleepDiaryWithEntriesAsHCP() {
		action = new ViewSleepEntryAction(factory, 9000000001L);
		try {
			List<SleepEntryBean> sleepDiary = action.getDiary(1);
			assertEquals(2, sleepDiary.size());
			SleepEntryBean entry1 = sleepDiary.get(0);
			SleepEntryBean entry2 = sleepDiary.get(1);
			//now that we know we have 2 of them, make sure they are the 
			//right ones
			assertEquals("12/14/2012", entry1.getStrDate().toString());
			assertEquals("Nap", entry1.getSleepType().name());
			assertEquals(1.0, entry1.getHoursSlept());
			assertEquals(1, entry1.getPatientID());
			
			assertEquals("12/12/2012", entry2.getStrDate().toString());
			assertEquals("Nightly", entry2.getSleepType().name());
			assertEquals(2.0, entry2.getHoursSlept());
			assertEquals(1, entry1.getPatientID());
			
			//now check the totals
			List<SleepEntryBean> totals = action.getDiaryTotals(1);
			assertEquals(2, totals.size());
			SleepEntryBean total = totals.get(0);
			assertEquals(1.0, total.getHoursSlept(), .001);
			SleepEntryBean total2 = totals.get(1);
			assertEquals(2.0, total2.getHoursSlept(), .001);
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
			
	}
	
	/**
	 * Test that a patient can view their sleep diary.
	 */
	@Test
	public void testViewingSleepDiaryAsPatient() {
		action = new ViewSleepEntryAction(factory, 1);
		try {
			List<SleepEntryBean> sleepDiary = action.getDiary(1);
			assertEquals(2, sleepDiary.size());
			SleepEntryBean entry1 = sleepDiary.get(0);
			SleepEntryBean entry2 = sleepDiary.get(1);
			//now that we know we have 2 of them, 
			//make sure they are the right ones
			assertEquals("12/14/2012", entry1.getStrDate().toString());
			assertEquals("Nap", entry1.getSleepType().name());
			assertEquals(1.0, entry1.getHoursSlept());
			assertEquals(1, entry1.getPatientID());
			
			assertEquals("12/12/2012", entry2.getStrDate().toString());
			assertEquals("Nightly", entry2.getSleepType().name());
			assertEquals(2.0, entry2.getHoursSlept());
			assertEquals(1, entry1.getPatientID());
			
			//now check the totals
			List<SleepEntryBean> totals = action.getDiaryTotals(1);
			assertEquals(2, totals.size());
			SleepEntryBean total = totals.get(0);
			assertEquals(1.0, total.getHoursSlept(), .001);
			SleepEntryBean total2 = totals.get(1);
			assertEquals(2.0, total2.getHoursSlept(), .001);
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Ensures a patient can still view their sleep diary even when it is empty.
	 */
	@Test
	public void testViewingEmptySleepDiaryAsPatient() {
		action = new ViewSleepEntryAction(factory, 2);
		try {
			List<SleepEntryBean> sleepDiary = action.getDiary(2);
			assertEquals(0, sleepDiary.size());
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Ensures HCPs with specialty of trainer can still view a sleep diary
	 * even when the sleep diary is empty.
	 */
	@Test
	public void testViewingEmptySleepDiaryAsHCP() {
		action = new ViewSleepEntryAction(factory, 9000000001L);
		try {
			List<SleepEntryBean> sleepDiary = action.getDiary(2);
			assertEquals(0, sleepDiary.size());
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Ensure patients can't view the sleep diary of other patients.
	 */
	@Test
	public void testViewSleepDiaryOfOtherPatient() {
		action = new ViewSleepEntryAction(factory, 1);
		try {
			action.getDiary(2);
			fail("You should not be able to view ottheir patient's sleep diary.");
		} catch (ITrustException e) {
			assertEquals("You do not have permission to view the Sleep Diary!",
					e.getMessage());
		}
	}
	
	/**
	 * Testing that we can get the diary when we are searching for range of dates
	 */
	@Test
	public void testViewSleepDiaryDateRange() {
		action = new ViewSleepEntryAction(factory, 1);
		try {
			List<SleepEntryBean> sleepDiary = 
					action.getBoundedDiary("12/12/2012", "12/12/2012", 1);
			assertEquals(1, sleepDiary.size());
			SleepEntryBean entry1 = sleepDiary.get(0);
			//now that we know we have 1 of them, make sure they are the 
			//right ones
			assertEquals("12/12/2012", entry1.getStrDate().toString());
			assertEquals("Nightly", entry1.getSleepType().toString());
			assertEquals(2.0, entry1.getHoursSlept(), .001);
			
			
			//now check the totals
			List<SleepEntryBean> totals = 
					action.getBoundedDiaryTotals("12/12/2012", "12/12/2012", 1);
			assertEquals(1, totals.size());
			SleepEntryBean total = totals.get(0);
			assertEquals(2.0, total.getHoursSlept(), .001);
		} catch (ITrustException e) {
			fail(e.getMessage());
		} catch (FormValidationException d) {
			fail("Do not want an exception");
		}
	}
	
	/**
	 * Test bounded diary with bad dates
	 */
	@Test
	public void testSleepDiaryBadDates() {
		action = new ViewSleepEntryAction(factory, 1);
		try {
			action.getBoundedDiary("", "", 1);
			fail("Bad dates");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. "
					+ "The following field are not properly "
					+ "filled in: [Enter dates in MM/dd/yyyy]", 
					e.getMessage());
		} catch (ITrustException d) {
			fail("Wanted bad dates");
		}
	}
	
	/**
	 * Test with a bad factory
	 */
	@Test
	public void testSleepDiaryBadFactoryBoundedDates() {
		action = new ViewSleepEntryAction(evil, 1);
		try {
			action.getBoundedDiary("", "", 1);
		} catch (ITrustException d) {
			assertEquals("Error retrieving Sleep Diary", d.getMessage());
		} catch (FormValidationException e) {
			fail("Made it with evil factory");
		}
	}
	
	/**
	 * Test with a bad factory
	 */
	@Test
	public void testSleepDiaryBadFactoryBoundedDatesTotals() {
		action = new ViewSleepEntryAction(evil, 1);
		try {
			action.getBoundedDiaryTotals("", "", 1);
		} catch (ITrustException d) {
			assertEquals("Error retrieving Sleep Diary", d.getMessage());
		} catch (FormValidationException e) {
			fail("Made it with evil factory");
		}
	}
	
	/**
	 * Test with a bad factory
	 */
	@Test
	public void testSleepDiaryBadFactory() {
		action = new ViewSleepEntryAction(evil, 1);
		try {
			action.getDiary(1);
		} catch (ITrustException d) {
			assertEquals("Error retrieving Sleep Diary", d.getMessage());
		}
	}
	
	/**
	 * Test with a bad factory
	 */
	@Test
	public void testSleepDiaryBadFactoryTotals() {
		action = new ViewSleepEntryAction(evil, 1);
		try {
			action.getDiaryTotals(1);
		} catch (ITrustException d) {
			assertEquals("Error retrieving Sleep Diary", d.getMessage());
		}
	}
	
	/**
	 * Try to get a bounded exercise diary of other patient.
	 */
	@Test
	public void testBoundedDiaryOtherPatient() {
		action = new ViewSleepEntryAction(factory, 1);
		try {
			action.getBoundedDiary("", "", 2);
			fail("You should not be able to view other patient's exercise diary.");
		} catch (ITrustException e) {
			assertEquals("You do not have permission to view the Sleep Diary!",
					e.getMessage());
		} catch (FormValidationException d) {
			fail("Wrong patient");
		}
	}
	
	/**
	 * Try to get a bounded exercise diary totals of other patient.
	 */
	@Test
	public void testBoundedDiaryTotalsOtherPatient() {
		action = new ViewSleepEntryAction(factory, 1);
		try {
			action.getBoundedDiaryTotals("", "", 2);
			fail("You should not be able to view other patient's exercise diary.");
		} catch (ITrustException e) {
			assertEquals("You do not have permission to view the Sleep Diary!",
					e.getMessage());
		} catch (FormValidationException d) {
			fail("Wrong patient");
		}
	}
	
	/**
	 * Enter in start date after end date
	 */
	@Test
	public void testStartAfterEnd() {
		action = new ViewSleepEntryAction(factory, 1);
		try {
			action.getBoundedDiary("12/12/2012", "12/10/2012", 1);
			fail("Wrong date order");
		} catch (FormValidationException d) {
			assertEquals("This form has not been validated correctly. "
					+ "The following field are not properly filled in: "
					+ "[Start date must be before end date!]", 
					d.getMessage());
		} catch (ITrustException e) {
			fail("Wrong ordering of dates");
		}
	}
	
	/**
	 * Enter in start date after end date for the totals
	 */
	@Test
	public void testStartAfterEndTotals() {
		action = new ViewSleepEntryAction(factory, 1);
		try {
			action.getBoundedDiaryTotals("12/12/2012", "12/10/2012", 1);
			fail("Wrong date order");
		} catch (FormValidationException d) {
			assertEquals("This form has not been validated correctly. "
					+ "The following field are not properly filled in: "
					+ "[Start date must be before end date!]", 
					d.getMessage());
		} catch (ITrustException e) {
			fail("Wrong ordering of dates");
		}
	}
	

}
