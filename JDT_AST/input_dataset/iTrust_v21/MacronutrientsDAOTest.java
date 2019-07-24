package edu.ncsu.csc.itrust.unit.dao.macronutrients;

import java.io.IOException;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.junit.Test;

import edu.ncsu.csc.itrust.beans.MacronutrientsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.MacronutrientsDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Tests that the dao throws exceptions when created with bad factory
 *
 */
public class MacronutrientsDAOTest extends TestCase {
	
	private TestDataGenerator gen = new TestDataGenerator();
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private MacronutrientsDAO macronutrientsDAO;
	
	/**
	 * Clears all of the tables, sets the standard data and includes 
	 * the data for use cases 68 and 69
	 */
	@Override
	public void setUp() throws SQLException, IOException {
		gen.clearAllTables();
		gen.standardData();
		macronutrientsDAO = new MacronutrientsDAO(factory);
	}
	
	/**
	 * Clears all of the tables
	 */
	@Override
	public void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	@Test
	public void testGetMsj() {
				
		try {
			MacronutrientsBean macronutrients = macronutrientsDAO.getMsj(341);
			assertTrue(1336.25 == macronutrients.getMsj());
			assertEquals(40, macronutrients.getYears());
		} catch (DBException e) {
			
			e.printStackTrace();
		}
	}
}
