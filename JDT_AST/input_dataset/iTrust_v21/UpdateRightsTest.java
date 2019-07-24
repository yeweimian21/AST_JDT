package edu.ncsu.csc.itrust.unit.dao.labprocedure;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * 
 * 
 */
public class UpdateRightsTest extends TestCase {
	private LabProcedureDAO lpDAO = TestDAOFactory.getTestInstance().getLabProcedureDAO();

	private TestDataGenerator gen;
	private LabProcedureBean l;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.labProcedures();
		l = new LabProcedureBean();
		l.setPid(1L);
		l.setOvID(905L);
		l.setLoinc("10763-8");
		l.statusPending();
		l.setCommentary("Awaiting results");
		l.setResults("");
		l.restrict();
	}

	public void testUpdateRights() throws Exception {
		long id = lpDAO.addLabProcedure(l);
		LabProcedureBean procedures = lpDAO.getLabProcedure(id);
		assertEquals(1L, procedures.getPid());
		l.allow();
		l.setProcedureID(id);
		lpDAO.updateRights(l);
		LabProcedureBean updprocedures = lpDAO.getLabProcedure(id);
		assertEquals(LabProcedureBean.Allow, updprocedures.getRights());
	}

}
