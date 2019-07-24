package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.LabProcLTAction;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class LabProcLTActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private LabProcedureDAO lpDAO = factory.getLabProcedureDAO();
	private TestDataGenerator gen;
	LabProcLTAction action;
	LabProcLTAction action2;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.transactionLog();
		gen.ltData0();
		gen.ltData1();
		gen.ltData2();
		gen.hcp0();
		gen.labProcedures();
		action = new LabProcLTAction(factory);
	}

	public void testUpdateProcedure() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-1");
		lp.setCommentary("");
		lp.setOvID(902L);
		lp.setPid(2L);
		lp.setResults("");
		lp.allow();
		lp.statusInTransit();
		lp.setLTID(5000000005L);
		long id = lpDAO.addLabProcedure(lp);
		lp.setProcedureID(id);
		
		LabProcedureBean procedures = lpDAO.getLabProcedure(id);
		
		List<LabProcedureBean> beans = action.viewInTransitProcedures(5000000005L);
		assertEquals(1, beans.size());
		LabProcedureBean lpBean = beans.get(0);
		
		assertEquals(procedures.getLoinc(), lpBean.getLoinc());
		assertEquals(procedures.getOvID(), lpBean.getOvID());
		assertEquals(procedures.getLoinc(), lpBean.getLoinc());
		assertEquals("In Transit", lpBean.getStatus());
		assertEquals(procedures.getProcedureID(), lpBean.getProcedureID());
	}

	public void testViewReceivedProcedures() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-1");
		lp.setCommentary("");
		lp.setOvID(902L);
		lp.setPid(2L);
		lp.setResults("");
		lp.allow();
		lp.statusReceived();
		lp.setLTID(5000000005L);
		long id = lpDAO.addLabProcedure(lp);
		lp.setProcedureID(id);
		
		LabProcedureBean procedures = lpDAO.getLabProcedure(id);
		
		List<LabProcedureBean> beans = action.viewReceivedProcedures(5000000005L);
		assertEquals(1, beans.size());
		LabProcedureBean lpBean = beans.get(0);
		
		assertEquals(procedures.getLoinc(), lpBean.getLoinc());
		assertEquals(procedures.getOvID(), lpBean.getOvID());
		assertEquals(procedures.getLoinc(), lpBean.getLoinc());
		assertEquals("Received", lpBean.getStatus());
		assertEquals(procedures.getProcedureID(), lpBean.getProcedureID());
	}

	public void testViewTestingProcedures() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-1");
		lp.setCommentary("");
		lp.setOvID(902L);
		lp.setPid(2L);
		lp.setResults("");
		lp.allow();
		lp.statusTesting();
		lp.setLTID(5000000005L);
		long id = lpDAO.addLabProcedure(lp);
		lp.setProcedureID(id);
		
		LabProcedureBean procedures = lpDAO.getLabProcedure(id);
		
		List<LabProcedureBean> beans = action.viewTestingProcedures(5000000005L);
		assertEquals(1, beans.size());
		LabProcedureBean lpBean = beans.get(0);
		
		assertEquals(procedures.getLoinc(), lpBean.getLoinc());
		assertEquals(procedures.getOvID(), lpBean.getOvID());
		assertEquals(procedures.getLoinc(), lpBean.getLoinc());
		assertEquals("Testing", lpBean.getStatus());
		assertEquals(procedures.getProcedureID(), lpBean.getProcedureID());
	}
	
	public void testGetLabProcedure() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-1");
		lp.setCommentary("");
		lp.setOvID(902L);
		lp.setPid(2L);
		lp.setResults("");
		lp.allow();
		lp.statusTesting();
		lp.setLTID(5000000005L);
		long id = lpDAO.addLabProcedure(lp);
		lp.setProcedureID(id);
		
		LabProcedureBean proc = lpDAO.getLabProcedure(id);
		
		assertEquals(lp.getOvID(), proc.getOvID());

	}

	public void testGetHCPName() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-1");
		lp.setCommentary("");
		lp.setOvID(902L);
		lp.setPid(2L);
		lp.setResults("");
		lp.allow();
		lp.statusTesting();
		lp.setLTID(5000000005L);
		long id = lpDAO.addLabProcedure(lp);
		lp.setProcedureID(id);
		
		lpDAO.getLabProcedure(id);
		
		assertEquals("Kelly Doctor", action.getHCPName(902L));
		
	}

	/**
	 * testSubmiteResults
	 * @throws Exception
	 */
	public void testSubmitResults() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-1");
		lp.setCommentary("");
		lp.setOvID(902L);
		lp.setPid(2L);
		lp.setResults("");
		lp.allow();
		lp.statusReceived();
		lp.setLTID(5000000005L);
		long id = lpDAO.addLabProcedure(lp);
		lp.setProcedureID(id);
		
		assertTrue(action.submitResults(""+id, "12", "grams", "13", "14"));
		
		LabProcedureBean procedures = lpDAO.getLabProcedure(id);
		
		assertEquals("Pending", procedures.getStatus());
		assertEquals("12", procedures.getNumericalResult());
		assertEquals("grams", procedures.getNumericalResultUnit());
		assertEquals("13", procedures.getUpperBound());
		assertEquals("14", procedures.getLowerBound());

	}
	
	/**
	 * testSubmitReceived
	 * @throws Exception
	 */
	public void testSubmitReceived() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-1");
		lp.setCommentary("");
		lp.setOvID(902L);
		lp.setPid(2L);
		lp.setResults("");
		lp.allow();
		lp.statusInTransit();
		lp.setLTID(5000000005L);
		long id = lpDAO.addLabProcedure(lp);
		lp.setProcedureID(id);
		
		assertTrue(action.submitReceived(""+id));
		
		LabProcedureBean procedures = lpDAO.getLabProcedure(id);
		
		assertEquals("Received", procedures.getStatus());
	}
	
	/**
	 * testSetToTesting
	 * @throws Exception
	 */
	public void testSetToTesting() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-1");
		lp.setCommentary("");
		lp.setOvID(902L);
		lp.setPid(2L);
		lp.setResults("");
		lp.allow();
		lp.statusReceived();
		lp.setLTID(5000000005L);
		long id = lpDAO.addLabProcedure(lp);
		lp.setProcedureID(id);
		
		assertTrue(action.setToTesting(id));
		
		LabProcedureBean procedures = lpDAO.getLabProcedure(id);
		
		assertEquals("Testing", procedures.getStatus());
	}
	
	/**
	 * testGetLabProc
	 * @throws Exception
	 */
	public void testGetLabProc() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		lp.setPid(2L);
		lp.setLoinc("10763-1");
		lp.setCommentary("This is a test");
		lp.setOvID(902L);
		lp.setResults("Test Result");
		lp.allow();
		lp.statusReceived();
		lp.setLTID(5000000005L);
		long id = lpDAO.addLabProcedure(lp);
		lp.setProcedureID(id);
		
		assertEquals(lp.getCommentary(), action.getLabProcedure(id).getCommentary());
		assertEquals(lp.getPid(), action.getLabProcedure(id).getPid());
		assertEquals(lp.getLoinc(), action.getLabProcedure(id).getLoinc());
		assertEquals(lp.getOvID(), action.getLabProcedure(id).getOvID());
		assertEquals(lp.getResults(), action.getLabProcedure(id).getResults());
		assertEquals(lp.getLTID(), action.getLabProcedure(id).getLTID());
		assertEquals(lp.getStatus(), action.getLabProcedure(id).getStatus());
		assertEquals(lp.getRights(), action.getLabProcedure(id).getRights());
	}
	
	/**
	 * testSubmiteResultsWronIDNumberFormat
	 * @throws FormValidationException
	 */
	public void testSubmitResultsWrongIDNumberFormat() throws FormValidationException {
		
		boolean successfulSubmit = action.submitResults("Test", "99", "99", "100", "0");
		assertFalse(successfulSubmit);
	}
	
	/**
	 * testSudmitReceivedWrongIDNubmerFormat
	 * @throws DBException
	 */
	public void testSubmitReceivedWrongIDNumberFormat() throws DBException {
		
		boolean successfulSubmit = action.submitReceived("Test");
		assertFalse(successfulSubmit);
	}
	
	/**
	 * testSubmitResultsEvilFactory
	 * @throws Exception
	 */
	public void testSubmitResultsEvilFactory() throws Exception {
		action = new LabProcLTAction(new EvilDAOFactory());
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-1");
		lp.setCommentary("");
		lp.setOvID(902L);
		lp.setPid(2L);
		lp.setResults("");
		lp.allow();
		lp.statusReceived();
		lp.setLTID(5000000005L);
		long id = lpDAO.addLabProcedure(lp);
		lp.setProcedureID(id);
		
		assertFalse(action.submitResults(""+id, "12", "grams", "13", "14"));
	}
}
