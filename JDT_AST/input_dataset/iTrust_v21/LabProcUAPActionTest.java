package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.LabProcUAPAction;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FakeEmailDAO;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class LabProcUAPActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private LabProcedureDAO lpDAO = factory.getLabProcedureDAO();
	private TestDataGenerator gen;
	private FakeEmailDAO feDAO = factory.getFakeEmailDAO();
	LabProcUAPAction action;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient2();
		gen.uap1();
		gen.labProcedures();
		action = new LabProcUAPAction(factory, 8000000009L);
	}
	
	public void testUpdateProcedure() throws Exception
	{
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-1");
		lp.setCommentary("This is a routine procedure");
		lp.setOvID(902L);
		lp.setPid(2L);
		lp.setResults("");
		lp.allow();
		lp.statusPending();
		long id =lpDAO.addLabProcedure(lp);
		lp.setProcedureID(id);
		lp.statusComplete();
		lp.setResults("No abnormal results");
		action.updateProcedure(lp);
		LabProcedureBean procedures = lpDAO.getLabProcedure(id);
		assertEquals(LabProcedureBean.Completed, procedures.getStatus());
		assertEquals("No abnormal results", procedures.getResults());
		List<Email> list = feDAO.getAllEmails(); 
		assertTrue(list.get(0).getBody().contains("Dear Andy Programmer,"));
		assertTrue(list.get(0).getBody().contains("Your Lab Procedure (10763-1) has a new updated status of Completed. Log on to iTrust to view."));

	}
	
	public void testViewProcedure() throws Exception
	{
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-1");
		lp.setCommentary("This is a routine procedure");
		lp.setOvID(902L);
		lp.setPid(3L);
		lp.setResults("");
		lp.allow();
		lp.statusPending();
		long id = lpDAO.addLabProcedure(lp);
		List <LabProcedureBean> procedures = action.viewProcedures(3L);
		assertEquals(1, procedures.size());
		assertEquals(id, procedures.get(0).getProcedureID());
	}

	
}
