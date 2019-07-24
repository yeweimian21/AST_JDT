package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.LabProcHCPAction;
import edu.ncsu.csc.itrust.beans.Email;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FakeEmailDAO;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class LabProcHCPActionTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private LabProcedureDAO lpDAO = factory.getLabProcedureDAO();
	private OfficeVisitDAO ovDAO = factory.getOfficeVisitDAO();
	private FakeEmailDAO feDAO = factory.getFakeEmailDAO();
	private TestDataGenerator gen;
	LabProcHCPAction action;
	LabProcHCPAction action2;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.transactionLog();
		gen.patient1();
		gen.patient2();
		gen.patient3();
		gen.patient4();
		gen.hcp0();
		gen.hcp3();
		gen.labProcedures();
		action = new LabProcHCPAction(factory, 9000000000L);
	}

	public void testUpdateProcedure() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-1");
		lp.setCommentary("This is a routine procedure");
		lp.setOvID(902L);
		lp.setPid(2L);
		lp.setResults("");
		lp.allow();
		lp.statusPending();
		long id = lpDAO.addLabProcedure(lp);
		lp.setProcedureID(id);
		lp.statusComplete();
		lp.setResults("No abnormal results");
		gen.fakeEmail();
		action.updateProcedure(lp);
		LabProcedureBean procedures = lpDAO.getLabProcedure(id);
		assertEquals(LabProcedureBean.Completed, procedures.getStatus());
		assertEquals("No abnormal results", procedures.getResults());
		List<Email> list = feDAO.getAllEmails();
		assertTrue(list.get(0).getBody().contains("Dear Andy Programmer,"));
		assertTrue(list.get(0).getBody().contains("Your Lab Procedure (10763-1) has a new updated status of Completed. Log on to iTrust to view."));
	}

	public void testViewProcedure() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-1");
		lp.setCommentary("This is a routine procedure");
		lp.setOvID(902L);
		lp.setPid(3L);
		lp.setResults("");
		lp.allow();
		lp.statusPending();
		long id = lpDAO.addLabProcedure(lp);
		List<LabProcedureBean> procedures = action.viewProcedures(3L);
		assertEquals(1, procedures.size());
		assertEquals(id, procedures.get(0).getProcedureID());
	}

	public void testGetLabProcedureForLHCPForNextMonth() throws Exception {
		List<LabProcedureBean> procedures = action.getLabProcForNextMonth();
		assertEquals(1, procedures.size());
		assertEquals("10763-1", procedures.get(0).getLoinc());
		assertEquals(4, procedures.get(0).getPid());
	}

	public void testUpdateRights() throws Exception {
		OfficeVisitBean ov = new OfficeVisitBean();
		ov.setHcpID(9000000000L);
		long ovid = ovDAO.add(ov);
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-5");
		lp.setCommentary("This is a routine procedure");
		lp.setOvID(ovid);
		lp.setPid(2L);
		lp.setResults("");
		lp.allow();
		lp.statusPending();
		long id = lpDAO.addLabProcedure(lp);
		assertEquals(LabProcedureBean.Allow, lpDAO.getLabProcedure(id).getRights());
		action.changePrivacy(id);
		LabProcedureBean procedures = lpDAO.getLabProcedure(id);
		assertEquals(LabProcedureBean.Restrict, procedures.getRights());
	}

	public void testSortByLOINC() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("12933-5");
		lp.setCommentary("This is a routine procedure");
		lp.setOvID(952);
		lp.setPid(2L);
		lp.setResults("");
		lp.allow();
		lp.statusPending();
		lpDAO.addLabProcedure(lp);

		LabProcedureBean lp2 = new LabProcedureBean();
		lp2.setLoinc("00000-0");
		lp2.setCommentary("This is a routine procedure");
		lp2.setOvID(953);
		lp2.setPid(2L);
		lp2.setResults("");
		lp2.allow();
		lp2.statusPending();
		lpDAO.addLabProcedure(lp2);

		LabProcedureBean lp3 = new LabProcedureBean();
		lp3.setLoinc("10543-5");
		lp3.setCommentary("This is a routine procedure");
		lp3.setOvID(954);
		lp3.setPid(2L);
		lp3.setResults("");
		lp3.allow();
		lp3.statusPending();
		lpDAO.addLabProcedure(lp3);

		List<LabProcedureBean> lplist = action.sortByLOINC(2);
		assertEquals(953, lplist.get(0).getOvID());
		assertEquals("00000-0", lplist.get(0).getLoinc());

	}
	public void testGetLabProcedureForLHCPForNextMonth1() throws Exception {
		action2 = new LabProcHCPAction(factory, 9000000003L);
		List<LabProcedureBean> procedures = action2.getLabProcForNextMonth();
		assertNotNull(procedures);
	}
}
