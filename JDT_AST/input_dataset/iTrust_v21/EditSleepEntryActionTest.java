package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddSleepEntryAction;
import edu.ncsu.csc.itrust.action.EditSleepEntryAction;
import edu.ncsu.csc.itrust.action.ViewSleepEntryAction;
import edu.ncsu.csc.itrust.beans.SleepEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * Tests different abilities for editing a sleep entry.
 *
 */
public class EditSleepEntryActionTest extends TestCase {
	
	private EditSleepEntryAction action;
	private ViewSleepEntryAction viewAction;
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private SleepEntryBean sleepBean;
	private AddSleepEntryAction addAction;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		sleepBean = new SleepEntryBean();
		sleepBean.setStrDate("12/12/2012");
		sleepBean.setSleepType("Nap");
		sleepBean.setHoursSlept(1);
		sleepBean.setPatientID(1);
	}
	
	/**
	 * Clears all of the tables.
	 */
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/**
	 * Tests that nothing happens when a user tries to udpate
	 * a nonexistent sleep entry. Random Person (1) has no entries.
	 */
	@Test
	public void testEditNoEntry() {
		action = new EditSleepEntryAction(factory, 1);
		viewAction = new ViewSleepEntryAction(factory, 1);
		try {
			sleepBean.setEntryID(100);
			assertEquals(0, action.editEntry(sleepBean));
		} catch (ITrustException e) {
			fail(e.getMessage());
		} catch (FormValidationException d) {
			fail(d.getMessage());
		}
	}
	
	/**
	 * Tests that a user can edit their own sleep entries.
	 */
	@Test
	public void testEditExistingEntry() {
		addAction = new AddSleepEntryAction(factory, 1);
		action = new EditSleepEntryAction(factory, 1);
		viewAction = new ViewSleepEntryAction(factory, 1);
		try {
			addAction.addEntry(sleepBean);
			
			List<SleepEntryBean> sleepDiary = viewAction.getDiary(1);
			assertEquals(3, sleepDiary.size());
			SleepEntryBean firstEntry = sleepDiary.get(0);
			assertEquals(1.0, firstEntry.getHoursSlept());
			firstEntry.setHoursSlept(2.0);
			int numUpdated = action.editEntry(firstEntry);
			assertEquals(1, numUpdated);
			assertEquals(3, viewAction.getDiary(1).size());
			firstEntry = viewAction.getDiary(1).get(0);
			assertEquals(2.0, firstEntry.getHoursSlept());
		} catch (ITrustException d) {
			fail(d.getMessage());
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
		
	}
	
	/**
	 * Tests that users cannot edit other people sleep entries
	 */
	@Test
	public void testOtherUsersEntry() {
		addAction = new AddSleepEntryAction(factory, 1);
		action = new EditSleepEntryAction(factory, 2);
		viewAction = new ViewSleepEntryAction(factory, 1);
		try {
			addAction.addEntry(sleepBean);
			
			List<SleepEntryBean> sleepDiary = viewAction.getDiary(1);
			assertEquals(3, sleepDiary.size());
			SleepEntryBean firstEntry = sleepDiary.get(0);
			assertEquals(1.0, firstEntry.getHoursSlept());
			firstEntry.setHoursSlept(2.0);
			int numUpdated = action.editEntry(firstEntry);
			assertEquals(-1, numUpdated);
			assertEquals(3, viewAction.getDiary(1).size());
			firstEntry = viewAction.getDiary(1).get(0);
			assertEquals(1.0, firstEntry.getHoursSlept());
		} catch (ITrustException d) {
			fail(d.getMessage());
		} catch (FormValidationException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test that you can add an entry and then turn around and edit it.
	 */
	@Test
	public void testAddEditEntry() {
		addAction = new AddSleepEntryAction(factory, 1);
		action = new EditSleepEntryAction(factory, 1);
		viewAction = new ViewSleepEntryAction(factory, 1);
		try {
			addAction.addEntry(sleepBean);
			
			assertEquals(3, viewAction.getDiary(1).size());
			SleepEntryBean bean = viewAction.getDiary(1).get(0);
			assertEquals("12/14/2012", bean.getStrDate());
			assertEquals("Nap", bean.getSleepType().getName());
			assertEquals(1.0, bean.getHoursSlept());
			
			bean.setStrDate("12/11/2012");
			bean.setSleepType("Nightly");
			bean.setHoursSlept(2.0);
			action.editEntry(bean);
			
			assertEquals(3, viewAction.getDiary(1).size());
			
			bean = viewAction.getDiary(1).get(2);
			assertEquals("12/11/2012", bean.getStrDate());
			assertEquals("Nightly", bean.getSleepType().getName());
			assertEquals(2.0, bean.getHoursSlept());
		} catch (FormValidationException e) {
			fail(e.getMessage());
		} catch (ITrustException d) {
			fail(d.getMessage());
		}
	}
	
	/**
	 * Tests that the information included still has to be correct and pass
	 * the same validation as adding a new one
	 */
	@Test
	public void testEditInvalidServings() {
		addAction = new AddSleepEntryAction(factory, 1);
		action = new EditSleepEntryAction(factory, 1);
		viewAction = new ViewSleepEntryAction(factory, 1);
		sleepBean.setHoursSlept(2.0);
		try {
			addAction.addEntry(sleepBean);
			
			SleepEntryBean bean = viewAction.getDiary(1).get(0);
			assertEquals(1.0, bean.getHoursSlept());
			bean.setHoursSlept(-2.0);
			action.editEntry(bean);
			fail("Invalid number of hours");
		} catch (FormValidationException d) {
			assertTrue(d.getMessage().contains("Number of Hours must be "
					+ "greater than 0"));
		} catch (ITrustException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test with an evil factory
	 */
	@Test
	public void testEvilFactory() {
		EvilDAOFactory evil = new EvilDAOFactory(0);
		addAction = new AddSleepEntryAction(factory, 1);
		action = new EditSleepEntryAction(evil, 1);
		viewAction = new ViewSleepEntryAction(factory, 1);
		try {
			addAction.addEntry(sleepBean);
			
			SleepEntryBean bean = viewAction.getDiary(1).get(0);
			bean.setHoursSlept(2.0);
			action.editEntry(bean);
			fail("Using evil factory. Should have failed");
		} catch (ITrustException e) {
			assertTrue(e.getMessage().contains("Error updating entry from "
					+ "Sleep Diary"));
		} catch (FormValidationException d) {
			fail(d.getMessage());
		}
	}
	

}
