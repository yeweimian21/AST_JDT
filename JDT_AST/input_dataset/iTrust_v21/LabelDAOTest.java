package edu.ncsu.csc.itrust.unit.dao.labels;

import java.io.IOException;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.LabelBean;
import edu.ncsu.csc.itrust.dao.mysql.LabelDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;

/**
 * Tests that the dao throws exceptions when created with bad factory
 *
 */
public class LabelDAOTest extends TestCase {
	
	private TestDataGenerator gen = new TestDataGenerator();
	private EvilDAOFactory evil;
	private LabelDAO labelDAO;
	
	
	/**
	 * Clears all of the tables, sets the standard data and includes 
	 * the data for use cases 68 and 69
	 */
	@Override
	public void setUp() throws SQLException, IOException {
		gen.clearAllTables();
		gen.standardData();
		evil = new EvilDAOFactory(0);
		labelDAO = new LabelDAO(evil);
	}
	
	/**
	 * Clears all of the tables
	 */
	@Override
	public void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/**
	 * Testing when you are using an evil dao.
	 * looking for it to throw exceptions
	 */
	@Test
	public void testGetLabelsWithEvilDAO() {
		try {
			labelDAO.getLabels(1);
			fail("Should have thrown an exception");
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the "
					+ "log in the console for stacktrace", e.getMessage());
		}
	}
	
	/**
	 * Testing when you are using an evil dao.
	 * looking for it to throw exceptions
	 */
	@Test
	public void testGetLabelWithEvilDAO() {
		try {
			labelDAO.getLabel(1);
			fail("Should have thrown an exception");
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the "
					+ "log in the console for stacktrace", e.getMessage());
		}
	}

	/**
	 * Testing when you are using an evil dao.
	 * looking for it to throw exceptions
	 */
	@Test
	public void testAddToLabelsWithEvilDAO() {
		try {
			LabelBean bean = new LabelBean();
			bean.setLabelName("Test");
			bean.setLabelColor("#FFFFFF");
			labelDAO.addLabel(bean);
			fail("Should have thrown an exception");
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the "
					+ "log in the console for stacktrace", e.getMessage());
		} catch (ITrustException d) {
			fail("Should not have been an ITrustException");
		}
	}
	
	/**
	 * Testing when you are using an evil dao.
	 * looking for it to throw exceptions
	 */
	@Test
	public void testEditLabelWithEvilDAO() {
		try {
			LabelBean bean = new LabelBean();
			bean.setEntryID(1);
			bean.setPatientID(1);
			bean.setLabelName("Test");
			bean.setLabelColor("#FFFFFF");
			labelDAO.editLabel(1, 1, bean);
			fail("Should have thrown an exception");
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the "
					+ "log in the console for stacktrace", e.getMessage());
		}
	}
	
	/**
	 * Testing when you are using an evil dao.
	 * looking for it to throw exceptions
	 */
	@Test
	public void testDeleteLabelWithEvilDAO() {
		try {
			LabelBean bean = new LabelBean();
			bean.setEntryID(1);
			bean.setPatientID(1);
			bean.setLabelName("Test");
			bean.setLabelColor("#FFFFFF");
			labelDAO.deleteLabel(1, 1);
			fail("Should have thrown an exception");
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the "
					+ "log in the console for stacktrace", e.getMessage());
		}
	}
}
