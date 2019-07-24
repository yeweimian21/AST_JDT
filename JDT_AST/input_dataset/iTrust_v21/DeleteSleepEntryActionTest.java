package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.action.AddSleepEntryAction;
import edu.ncsu.csc.itrust.action.DeleteSleepEntryAction;
import edu.ncsu.csc.itrust.action.ViewSleepEntryAction;
import edu.ncsu.csc.itrust.beans.SleepEntryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests that a user can add and delete entries in his
 * sleep diary. Entries are known by their id.
 *
 */
public class DeleteSleepEntryActionTest extends TestCase {

	private DeleteSleepEntryAction action;
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
	 * Tests that an appropriate message is displayed if a user
	 * tries to delete from an empty diary (it will be the same
	 * as trying to delete with the wrong id)
	 */
	@Test
	public void testDeleteInvalidID() {
		action = new DeleteSleepEntryAction(factory, 1);
		viewAction = new ViewSleepEntryAction(factory, 1);
		try {
			List<SleepEntryBean> diary = viewAction.getDiary(1);
			assertEquals(2, diary.size());
			int numDeleted = action.deleteEntry(1);
			assertEquals(0, numDeleted);
		} catch (ITrustException e) {
			fail("Unexpected exception");
		}
	}
	
	/**
	 * Tests that a user can delete a sleep entry he just added.
	 */
	@Test
	public void testNewEntry() {
		action = new DeleteSleepEntryAction(factory, 1);
		viewAction = new ViewSleepEntryAction(factory, 1);
		addAction = new AddSleepEntryAction(factory, 1);
		try {
			addAction.addEntry(sleepBean);
			SleepEntryBean bean = viewAction.getDiary(1).get(0);
			assertEquals(3, viewAction.getDiary(1).size());
			int numDeleted = action.deleteEntry(bean.getEntryID());
			assertEquals(1, numDeleted);
			assertEquals(2, viewAction.getDiary(1).size());
		} catch (Exception e) {
			fail("Unexpected exception");
		}
	}
	
	/**
	 * Tests that a user cannot delete a sleep entry from another user.
	 */
	@Test
	public void testDeleteFromOtherUser() {
		action = new DeleteSleepEntryAction(factory, 2);
		
		addAction = new AddSleepEntryAction(factory, 1);
		viewAction = new ViewSleepEntryAction(factory, 1);
		try {
			addAction.addEntry(sleepBean);
			addAction.addEntry(sleepBean);
			
			assertEquals(4, viewAction.getDiary(1).size());
			long id = viewAction.getDiary(1).get(0).getEntryID();
			int numDeleted = action.deleteEntry(id);
			assertEquals(0, numDeleted);
			assertEquals(4, viewAction.getDiary(1).size());
		} catch (Exception e) {
			fail("Unexpected exception");
		}
	}
	
	/**
	 * Tests deleting from an evil database.
	 */
	@Test
	public void testEvilDelete() {
		EvilDAOFactory evil = new EvilDAOFactory(0);
		action = new DeleteSleepEntryAction(evil, 1);
		try {
			action.deleteEntry(2);
			fail("Using evil factory. Should have failed");
		} catch (ITrustException e) {
			assertTrue(e.getMessage().contains("Error deleting entry from "
					+ "Sleep Diary"));
		}
	}

}
