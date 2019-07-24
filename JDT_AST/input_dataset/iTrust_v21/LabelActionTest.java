package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.LabelBean;
import edu.ncsu.csc.itrust.action.LabelAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests adding sleep entries to the sleep diary
 */
public class LabelActionTest extends TestCase {

	private LabelAction action;
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen;
	private LabelBean labelBean;
	private EvilDAOFactory evil = new EvilDAOFactory();
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();	
		labelBean = new LabelBean();
		labelBean.setEntryID(1);
		labelBean.setPatientID(1);
		labelBean.setLabelName("Test");
		labelBean.setLabelColor("#FFFFFF");
	}
	
	/**
	 * Clears all of the tables.
	 */
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/**
	 * Tests that a patient can add a new label.
	 * Log in as the patient Random Person who has no prior labels.
	 * @throws FormValidationException 
	 */
	@Test
	public void testAddNewLabel() throws FormValidationException {
		action = new LabelAction(factory, 1);
			String result = action.addLabel(labelBean);
			assertEquals("Success: Test was added "
					+ "successfully!", result);
			try {
				List<LabelBean> labels = action.getLabels(1);
				assertEquals(1, labels.size());
				LabelBean bean = labels.get(0);
				assertEquals(1, bean.getEntryID());
				assertEquals(1, bean.getPatientID());
				assertEquals("Test", bean.getLabelName());
				assertEquals("#FFFFFF", bean.getLabelColor());
			} catch (ITrustException e) {
				fail(e.getMessage());
			}
	}
	
	/**
	 * Tests that a patient can add a new label.
	 * Log in as the patient Random Person who has no prior labels.
	 * @throws FormValidationException 
	 */
	@Test
	public void testEditLabel() throws FormValidationException {
		action = new LabelAction(factory, 1);
			String result = action.addLabel(labelBean);
			assertEquals("Success: Test was added "
					+ "successfully!", result);
			labelBean.setLabelName("Updated Test");
			try {
				action.editLabel(labelBean);
			} catch (ITrustException e1) {
				e1.printStackTrace();
			}
			try {
				List<LabelBean> labels = action.getLabels(1);
				assertEquals(1, labels.size());
				LabelBean bean = labels.get(0);
				assertEquals(1, bean.getEntryID());
				assertEquals(1, bean.getPatientID());
				assertEquals("Updated Test", bean.getLabelName());
				assertEquals("#FFFFFF", bean.getLabelColor());
			} catch (ITrustException e) {
				fail(e.getMessage());
			}
	}
	
	/**
	 * Tests that a patient can add a new label.
	 * Log in as the patient Random Person who has no prior labels.
	 * @throws FormValidationException 
	 */
	@Test
	public void testDeleteLabel() throws FormValidationException {
		action = new LabelAction(factory, 1);
		long entryID = labelBean.getEntryID();
			String result = action.addLabel(labelBean);
			assertEquals("Success: Test was added "
					+ "successfully!", result);
			try {
				action.deleteLabel(entryID);
			} catch (ITrustException e1) {
				e1.printStackTrace();
			}
			try {
				LabelBean label = action.getLabel(entryID);
				assertEquals(null, label);
			} catch (ITrustException e) {
				fail(e.getMessage());
			}
	}
	
	/**
	 * Tests that a patient can add a new label.
	 * Log in as the patient Random Person who has no prior labels.
	 */
	@Test
	public void testBadGetLabel() {
		action = new LabelAction(evil, 1);
		long entryID = labelBean.getEntryID();
		try {
			action.getLabel(entryID);
		} catch (ITrustException e) {
			assertEquals("Error retrieving Label", e.getMessage());
		} 
	}
	
	/**
	 * Tests that a patient can add a new label.
	 * Log in as the patient Random Person who has no prior labels.
	 */
	@Test
	public void testBadGetLabels() {
		action = new LabelAction(evil, 1);
		long entryID = labelBean.getEntryID();
		try {
			action.getLabels(entryID);
		} catch (ITrustException e) {
			assertEquals("Error retrieving Labels", e.getMessage());
		} 
	}
	
	/**
	 * Tests that a patient can add a new label.
	 * Log in as the patient Random Person who has no prior labels.
	 * @throws FormValidationException 
	 */
	@Test
	public void testBadAddLabel() throws FormValidationException {
		action = new LabelAction(evil, 1);
		String result = action.addLabel(labelBean);
		assertEquals("A database exception has occurred. Please see "
					+ "the log in the console for stacktrace", result);
	}
	
	/**
	 * Tests that a patient can add a new label.
	 * Log in as the patient Random Person who has no prior labels.
	 * @throws FormValidationException 
	 */
	@Test
	public void testBadEditLabel() throws FormValidationException {
		action = new LabelAction(evil, 1);
		try {
			action.editLabel(labelBean);
		} catch (ITrustException e) {
			assertEquals("Error updating Label", e.getMessage());
		} 
	}
	
	/**
	 * Tests that a patient can add a new label.
	 * Log in as the patient Random Person who has no prior labels.
	 */
	@Test
	public void testBadDeleteLabel() {
		action = new LabelAction(evil, 1);
		long entryID = labelBean.getEntryID();
		try {
			action.deleteLabel(entryID);
		} catch (ITrustException e) {
			assertEquals("Error deleting Label", e.getMessage());
		} 
	}
	
}
