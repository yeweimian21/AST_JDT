package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.EditProceduresAction;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * Test procedure actions
 * testAddProcedure
 * test Edit Procedure
 * test delete Procedure
 *
 */
public class EditProceduresActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditProceduresAction action;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testGetProcedures() throws Exception {
		action = new EditProceduresAction(factory, 9000000000L, "2", "955");
		List<ProcedureBean> list = action.getProcedures();
		assertEquals(1, list.size());
		assertEquals("1270F", list.get(0).getCPTCode());

		action = new EditProceduresAction(factory, 9000000000L, "1", "11");
		assertEquals(0, action.getProcedures().size());

		// An EditPrescriptionAction without an ovID returns an empty list.
		action = new EditProceduresAction(factory, 9000000000L, "2");
		assertEquals(0, action.getProcedures().size());
	}

	/**
	 * testAddProcedure
	 * @throws Exception
	 */
	public void testAddProcedure() throws Exception {
		action = new EditProceduresAction(factory, 9000000000L, "1", "11");
		assertEquals(0, action.getProcedures().size());
		ProcedureBean bean = new ProcedureBean("1270F", "Injection procedure", null, "9000000000");
		bean.setVisitID(11);
		action.addProcedure(bean);
		assertEquals(1, action.getProcedures().size());
		assertEquals("1270F", action.getProcedures().get(0).getCPTCode());
	}

	/**
	 * testEditProcedure
	 * @throws Exception
	 */
	public void testEditProcedure() throws Exception {
		action = new EditProceduresAction(factory, 9000000000L, "2", "955");
		ProcedureBean bean = action.getProcedures().get(0);
		assertEquals("1270F", bean.getCPTCode());
		bean.setCPTCode("87");
		bean.setVisitID(955);
		bean.setHcpid("9000000000");
		action.editProcedure(bean);
		bean = action.getProcedures().get(0);
		assertEquals("87", bean.getCPTCode());
	}

	/**
	 * testDeleteProcedure
	 * @throws Exception
	 */
	public void testDeleteProcedure() throws Exception {
		action = new EditProceduresAction(factory, 9000000000L, "2", "955");
		assertEquals(1, action.getProcedures().size());
		action.deleteProcedure(action.getProcedures().get(0));
		assertEquals(0, action.getProcedures().size());
	}

	/**
	 * testGetProcedureCodes
	 * @throws Exception
	 */
	public void testGetProcedureCodes() throws Exception {
		action = new EditProceduresAction(factory, 9000000000L, "2", "955");
		List<ProcedureBean> list = action.getProcedureCodes();
		assertEquals(2, list.size());
		
		// It can also be retrieved for an undefined office visit
		action = new EditProceduresAction(factory, 9000000000L, "1");
		list = action.getProcedureCodes();
		assertEquals(2, list.size());
	}

}
