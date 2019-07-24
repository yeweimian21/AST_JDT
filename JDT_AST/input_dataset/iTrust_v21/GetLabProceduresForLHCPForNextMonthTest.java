package edu.ncsu.csc.itrust.unit.dao.labprocedure;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class GetLabProceduresForLHCPForNextMonthTest extends TestCase {
	private LabProcedureDAO lpDAO = TestDAOFactory.getTestInstance().getLabProcedureDAO();

	private TestDataGenerator gen;
	private LabProcedureBean l1;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.labProcedures();
		// first procedure
		l1 = new LabProcedureBean();
		l1.setPid(3L);
		l1.setOvID(918L);
		l1.setLoinc("10763-1");
		l1.statusComplete();
		l1.setCommentary("");

	}

	public void testGetLabProceduresForLHCPForNextMonth() throws Exception {
		long id1 = lpDAO.addLabProcedure(l1);
		List<LabProcedureBean> procedures = lpDAO.getLabProceduresForLHCPForNextMonth(918L);
		assertEquals(1, procedures.size());
		assertEquals(id1, procedures.get(0).getProcedureID());
	}

	public void testFailGetLabProcedures() throws Exception {
		try {
			lpDAO.getLabProceduresForLHCPForNextMonth(0L);
			fail();
		} catch (DBException e) {
			assertEquals("OfficeVisitID cannot be null", e.getExtendedMessage());
		}
	}

}
