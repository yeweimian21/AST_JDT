package edu.ncsu.csc.itrust.unit.dao.druginteraction;

import java.util.List;

import edu.ncsu.csc.itrust.beans.DrugInteractionBean;
import edu.ncsu.csc.itrust.dao.mysql.DrugInteractionDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class DrugInteractionDAOTest extends TestCase {
	private DrugInteractionDAO interactionDAO = TestDAOFactory.getTestInstance().getDrugInteractionDAO();
	private TestDataGenerator gen;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.admin1();
	}
	
	public void testReportInteraction() throws Exception {
		interactionDAO.reportInteraction("619580501", "081096", "May increase the risk and severity of nephrotoxicity due to additive effects on the kidney.");
		List<DrugInteractionBean> testList = interactionDAO.getInteractions("619580501");
		DrugInteractionBean interaction = testList.get(0);
		assertEquals("619580501", interaction.getFirstDrug());
		assertEquals("081096", interaction.getSecondDrug());
	}
	
	public void testReportInteractionThatExists() throws Exception {
		gen.drugInteractions();
		try{
			interactionDAO.reportInteraction("009042407", "548680955", "This is not allowed.");
			fail("Drug interaction already exists for these drugs.");
		} catch(Exception e){
			//Good job, it works
		}
	}
	
	public void testReportInteractionThatExistsReverseOrder() throws Exception {
		gen.drugInteractions();
		try{
			interactionDAO.reportInteraction("548680955", "009042407", "This is not allowed.");
			fail("Drug interaction already exists for these drugs.");
		} catch(Exception e){
			//Good job, it works
		}
	}
	
	public void testDeleteInteraction() throws Exception {
		gen.drugInteractions();
		interactionDAO.deleteInteraction("009042407", "548680955");
		List<DrugInteractionBean> testList = interactionDAO.getInteractions("548680955");
		assertTrue(testList.isEmpty());
	}
	
}
