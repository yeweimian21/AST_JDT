package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import edu.ncsu.csc.itrust.action.EditImmunizationsAction;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class EditImmunizationsActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditImmunizationsAction action;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testGetImmunizations() throws Exception {
		action = new EditImmunizationsAction(factory, 9000000000L, "5", "1000");
		List<ProcedureBean> list = action.getImmunizations();
		assertEquals(1, list.size());
		assertEquals("90371", list.get(0).getCPTCode());

		action = new EditImmunizationsAction(factory, 9000000000L, "1", "11");
		assertEquals(0, action.getImmunizations().size());

		// An EditImmunizationsAction without an ovID returns an empty list.
		action = new EditImmunizationsAction(factory, 9000000000L, "2");
		assertEquals(0, action.getImmunizations().size());
	}

	public void testAddImmunization() throws Exception {
		action = new EditImmunizationsAction(factory, 9000000000L, "2", "955");
		assertEquals(0, action.getImmunizations().size());
		ProcedureBean bean = new ProcedureBean();
		bean.setCPTCode("90707");
		bean.setVisitID(955);
		bean.setHcpid("9000000000");
		action.addImmunization(bean);
		assertEquals(1, action.getImmunizations().size());
		assertEquals("90707", action.getImmunizations().get(0).getCPTCode());
	}

	public void testEditImmunization() throws Exception {
		action = new EditImmunizationsAction(factory, 9000000000L, "5", "1000");
		ProcedureBean bean = action.getImmunizations().get(0);
		assertEquals("90371", bean.getCPTCode());
		bean.setCPTCode("90707");
		bean.setVisitID(1000);
		bean.setHcpid("9000000000");
		action.editImmunization(bean);
		bean = action.getImmunizations().get(0);
		assertEquals("90707", bean.getCPTCode());
	}

	public void testDeleteImmunization() throws Exception {
		action = new EditImmunizationsAction(factory, 9000000000L, "5", "1000");
		assertEquals(1, action.getImmunizations().size());
		action.deleteImmunization(action.getImmunizations().get(0));
		assertEquals(0, action.getImmunizations().size());
	}

	public void testGetImmunizationCodes() throws Exception {
		action = new EditImmunizationsAction(factory, 9000000000L, "2", "955");
		List<ProcedureBean> list = action.getImmunizationCodes();
		assertEquals(15, list.size());
		
		// It can also be retrieved for an undefined office visit
		action = new EditImmunizationsAction(factory, 9000000000L, "1");
		list = action.getImmunizationCodes();
		assertEquals(15, list.size());
	}

}
