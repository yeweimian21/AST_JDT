package edu.ncsu.csc.itrust.unit.dao.officevisit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.RequiredProceduresDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class RequiredProceduresDAOTest {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private RequiredProceduresDAO requiredDAO = factory.getRequiredProceduresDAO();

	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}
	
	@Test
	public void testGetAllImmunizations() throws Exception {
		//Adam Sandler, MID 300
		assertTrue(requiredDAO.getAllImmunizations(300).size() == 8);
		assertEquals("90696", requiredDAO.getAllImmunizations(300).get(0).getCPTCode());
		
		//Will Smith, MID 302
		assertTrue(requiredDAO.getAllImmunizations(302).size() == 3);
		assertEquals("90696", requiredDAO.getAllImmunizations(302).get(0).getCPTCode());
		assertEquals("90707", requiredDAO.getAllImmunizations(302).get(1).getCPTCode());
		assertEquals("90371", requiredDAO.getAllImmunizations(302).get(2).getCPTCode());
	}
	
	@Test
	public void testGetRequiredImmunizations() throws Exception {
		//Adam Sandler, MID 300
		assertTrue(requiredDAO.getRequiredImmunizations(300, 0).size() == 6);
		assertEquals("90696", requiredDAO.getRequiredImmunizations(300, 0).get(0).getCptCode());
		assertEquals("90712", requiredDAO.getRequiredImmunizations(300, 0).get(1).getCptCode());
		assertEquals("90707", requiredDAO.getRequiredImmunizations(300, 0).get(2).getCptCode());
		assertEquals("90645", requiredDAO.getRequiredImmunizations(300, 0).get(3).getCptCode());
		assertEquals("90371", requiredDAO.getRequiredImmunizations(300, 0).get(4).getCptCode());
		assertEquals("90396", requiredDAO.getRequiredImmunizations(300, 0).get(5).getCptCode());
		
		//Charlie Chaplin, MID 308
		assertTrue(requiredDAO.getRequiredImmunizations(308, 2).size() == 4);
		assertEquals("90696", requiredDAO.getRequiredImmunizations(308, 2).get(0).getCptCode());
		assertEquals("90712", requiredDAO.getRequiredImmunizations(308, 2).get(1).getCptCode());
		assertEquals("90707", requiredDAO.getRequiredImmunizations(308, 2).get(2).getCptCode());
		assertEquals("90371", requiredDAO.getRequiredImmunizations(308, 2).get(3).getCptCode());
	}
	
	@Test
	public void testGetNeededImmunizations() throws Exception {
		//Adam Sandler, MID 300
		assertTrue(requiredDAO.getNeededImmunizations(300, 0).size() == 0);
		
		//Charlie Chaplin, MID 308
		assertTrue(requiredDAO.getNeededImmunizations(308, 2).size() == 4);
		assertEquals("90696", requiredDAO.getNeededImmunizations(308, 2).get(0).getCptCode());
		assertEquals("90712", requiredDAO.getNeededImmunizations(308, 2).get(1).getCptCode());
		assertEquals("90707", requiredDAO.getNeededImmunizations(308, 2).get(2).getCptCode());
		assertEquals("90371", requiredDAO.getNeededImmunizations(308, 2).get(3).getCptCode());
	}
}
