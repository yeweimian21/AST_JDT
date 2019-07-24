package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.DrugInteractionAction;
import edu.ncsu.csc.itrust.beans.DrugInteractionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class DrugInteractionActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private DAOFactory evilFactory = EvilDAOFactory.getEvilInstance();
	private DrugInteractionAction action;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		action = new DrugInteractionAction(factory);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testReportInteraction() throws Exception {
		gen.ndCodes();
		String response = action.reportInteraction("548684985", "081096", "May potentiate the risk of bleeding in patients.");
		assertSame(response, "Interaction recorded successfully");		
	}

	/**
	 * testDeleteInteraction
	 * @throws Exception
	 */
	public void testDeleteInteraction() throws Exception{
		gen.drugInteractions();
		String response = action.deleteInteraction("009042407", "548680955");
		assertSame(response, "Interaction deleted successfully");
	}

	/**
	 * testGetInteractions
	 * @throws Exception
	 */
	public void testGetInteractions() throws Exception {
		gen.drugInteractions();
		List<DrugInteractionBean> beans = action.getInteractions("009042407");
		assertEquals(beans.size(), 1);
		assertTrue(beans.get(0).getDescription().equals("May increase the risk of pseudotumor cerebri, or benign intracranial hypertension."));
		assertTrue(beans.get(0).getFirstDrug().equals("009042407"));
		assertTrue(beans.get(0).getSecondDrug().equals("548680955"));
			
	}

	/**
	 * testGetInteractions2
	 * @throws Exception
	 */
	public void testGetInteractions2() throws Exception {
		gen.standardData();
		action.reportInteraction("009042407", "081096", "Tetra and Aspirin");
		action.reportInteraction("009042407", "647641512", "Tetra and Prio");
		action.reportInteraction("548684985", "647641512", "Cital and Prio");
		List<DrugInteractionBean> beans = action.getInteractions("647641512");
		assertEquals(beans.size(), 2);
		assertTrue(beans.get(0).getDescription().equals("Tetra and Prio"));
		assertTrue(beans.get(0).getFirstDrug().equals("009042407"));
		assertTrue(beans.get(0).getSecondDrug().equals("647641512"));
		assertTrue(beans.get(1).getDescription().equals("Cital and Prio"));
		assertTrue(beans.get(1).getFirstDrug().equals("548684985"));
		assertTrue(beans.get(1).getSecondDrug().equals("647641512"));
			
	}
	
	/**
	 * testReportSameDrugsInteraction
	 * @throws Exception
	 */
	public void testReportSameDrugsInteraction() throws Exception {
		gen.ndCodes();
		String response = action.reportInteraction("548684985", "548684985", "Double dose");
		assertEquals("Interactions can only be recorded between two different drugs", response);
	}
	
	/**
	 * testReportAlreadyAdded
	 * @throws Exception
	 */
	public void testReportAlreadyAdded() throws Exception {
		gen.ndCodes();
		try {
			String response = action.reportInteraction("548684985", "081096", "May potentiate the risk of bleeding in patients.");
			assertSame(response, "Interaction recorded successfully");
			action.reportInteraction("548684985", "081096", "May possibly potentiate the risk of bleeding in patients.");
		} catch (ITrustException e){
			assertSame(e.getMessage(), "Error: Interaction between these drugs already exists.");
		}
			
	}
	
	/**
	 * testEvilDAOFactory
	 * @throws Exception
	 */
	public void testEvilDAOFactory() throws Exception {
		DrugInteractionAction actionEvil = new DrugInteractionAction(evilFactory);
		gen.drugInteractions();
		try {
			actionEvil.deleteInteraction("009042407", "548680955");
			fail();
		} catch (ITrustException e){
			assertSame("A database exception has occurred. Please see the log in the "
					+ "console for stacktrace", e.getMessage());
		}
		
		try {
			actionEvil.getInteractions("009042407");
			fail();
		} catch (ITrustException e){
			assertSame("A database exception has occurred. Please see the log in the "
					+ "console for stacktrace", e.getMessage());
		}
	}
}
