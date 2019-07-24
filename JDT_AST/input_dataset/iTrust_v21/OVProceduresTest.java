package edu.ncsu.csc.itrust.unit.dao.officevisit;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.dao.mysql.ProceduresDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Test client OV procedure
 */
public class OVProceduresTest extends TestCase{
	private ProceduresDAO procDAO = TestDAOFactory.getTestInstance().getProceduresDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.cptCodes();
		gen.officeVisit1();
	}
	
	/**
	 * testAddRemoveOneOVProcedure
	 * @throws Exception
	 */
	public void testAddRemoveOneOVProcedure() throws Exception {
		assertEquals("no current procedures on office vist 1", 0, procDAO.getList(1).size());
		ProcedureBean bean = new ProcedureBean();
		bean.setVisitID(1);
		bean.setCPTCode("1270F");
		long ovPID = procDAO.add(bean);
		List<ProcedureBean> procs = procDAO.getList(1);
		assertEquals("now there's 1", 1, procs.size());
		assertEquals("test the description", "Injection procedure", procs.get(0).getDescription());
		procDAO.remove(ovPID);
		assertEquals("now there's none", 0, procDAO.getList(1).size());
	}
	
	/**
	 * testAddBadProcedure
	 * @throws Exception
	 */
	public void testAddBadProcedure() throws Exception {
		ProcedureBean bean = new ProcedureBean();
		bean.setVisitID(-1);
		bean.setCPTCode(null);
		try {
			procDAO.add(bean);
			fail("expected an exception");
		} catch (DBException e) {
			//TODO
		}
	}
	
	/**
	 * testEditBadProcedure
	 * @throws Exception
	 */
	public void testEditBadProcedure() throws Exception {
		ProcedureBean bean = new ProcedureBean();
		bean.setVisitID(-1);
		bean.setCPTCode(null);
		try {
			procDAO.edit(bean);
			fail("expected an exception");
		} catch (DBException e) {
			//TODO
		}
	}
	
}
