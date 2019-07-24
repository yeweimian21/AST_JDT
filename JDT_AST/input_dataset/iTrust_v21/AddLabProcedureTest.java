package edu.ncsu.csc.itrust.unit.dao.labprocedure;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * 
 * 
 */
public class AddLabProcedureTest extends TestCase {
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
		l.setLoinc("10763-1");
		l.statusComplete();
		l.setCommentary("Awaiting results");
		l.setResults("Good");
	}

	public void testAddLabProcedure() throws Exception {
		long id = lpDAO.addLabProcedure(l);
		LabProcedureBean procedures = lpDAO.getLabProcedure(id);
		assertEquals(LabProcedureBean.Completed, procedures.getStatus());
		assertEquals(1L, procedures.getPid());
		assertEquals(905L, l.getOvID());
		assertEquals("10763-1", procedures.getLoinc());
		assertEquals("Awaiting results", procedures.getCommentary());
		assertEquals("Good", procedures.getResults());
	}

	public void testFailLabProcedure() throws Exception {
		l.setPid(0);
		try {
			lpDAO.addLabProcedure(l);
			fail();
		} catch (DBException e) {
			assertEquals("PatientMID cannot be null", e.getExtendedMessage());
		}
	}

}
