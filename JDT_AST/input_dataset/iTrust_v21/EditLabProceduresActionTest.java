package edu.ncsu.csc.itrust.unit.action;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.action.EditLabProceduresAction;
import edu.ncsu.csc.itrust.beans.LOINCbean;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * EditLabProceduresActionTest
 */
public class EditLabProceduresActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditLabProceduresAction action;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	/**
	 * testGetLabProcedures
	 * @throws Exception
	 */
	public void testGetLabProcedures() throws Exception {
		action = new EditLabProceduresAction(factory, 9000000000L, "2", "955");
		List<LabProcedureBean> list = action.getLabProcedures();
		assertEquals(3, list.size());
		assertEquals("10763-1", list.get(0).getLoinc());

		action = new EditLabProceduresAction(factory, 9000000000L, "2", "953");
		assertEquals(0, action.getLabProcedures().size());

		// An EditLabProceduresAction without an ovID returns an empty list.
		action = new EditLabProceduresAction(factory, 9000000000L, "2");
		assertEquals(0, action.getLabProcedures().size());
	}
	
	/**
	 * testGetLabProcedure
	 * @throws Exception
	 */
	public void testGetLabProcedure() throws Exception {
		gen.officeVisit5();
		action = new EditLabProceduresAction(factory, 9000000003L, "5", "380");
		LabProcedureBean bean = action.getLabProcedure(1380);
		assertEquals("13495-7", bean.getLoinc());
	}

	/**
	 * testAddLabProcedure
	 * @throws Exception
	 */
	public void testAddLabProcedure() throws Exception {
		action = new EditLabProceduresAction(factory, 9000000000L, "2", "953");
		assertEquals(0, action.getLabProcedures().size());
		LabProcedureBean bean = new LabProcedureBean();
		bean.setLoinc("10763-1");
		bean.setOvID(953);
		bean.setPid(2);
		action.addLabProcedure(bean);
		assertEquals(1, action.getLabProcedures().size());
		assertEquals("10763-1", action.getLabProcedures().get(0).getLoinc());
	}

	/**
	 * testEditLabProcedure
	 * @throws Exception
	 */
	public void testEditLabProcedure() throws Exception {
		action = new EditLabProceduresAction(factory, 9000000000L, "2", "952");
		assertEquals(1, action.getLabProcedures().size());
		LabProcedureBean bean = action.getLabProcedures().get(0);
		assertEquals("Performed the procedure", bean.getCommentary());
		bean.setCommentary("But I don't want to be a pirate!");
		bean.setOvID(952);
		bean.setPid(2);
		action.editLabProcedure(bean);
		assertEquals(1, action.getLabProcedures().size());
		bean = action.getLabProcedures().get(0);
		assertEquals("But I don't want to be a pirate!", bean.getCommentary());
	}

	/**
	 * testDeleteLabProcedure
	 * @throws Exception
	 */
	public void testDeleteLabProcedure() throws Exception {
		action = new EditLabProceduresAction(factory, 9000000000L, "2", "952");
		assertEquals(1, action.getLabProcedures().size());
		action.deleteLabProcedure(action.getLabProcedures().get(0));
		assertEquals(0, action.getLabProcedures().size());
	}

	/**
	 * testGetLabProcedureCodes
	 * @throws Exception
	 */
	public void testGetLabProcedureCodes() throws Exception {
		action = new EditLabProceduresAction(factory, 9000000000L, "2", "955");
		List<LOINCbean> list = action.getLabProcedureCodes();
		assertEquals(4, list.size());
		
		// It can also be retrieved for an undefined office visit
		action = new EditLabProceduresAction(factory, 9000000000L, "1");
		list = action.getLabProcedureCodes();
		assertEquals(4, list.size());
	}
	
	/**
	 * testAutoAssignLabTech
	 * @throws Exception
	 */
	public void testAutoAssignLabTech() throws Exception {
		//setup: assign lab procs to techs
		//the lab tech with the least amount of work should get the assignment
	}
	
	/**
	 * testGetLabTechs
	 * @throws Exception
	 */
	public void testGetLabTechs() throws Exception {
		action = new EditLabProceduresAction(factory, 9000000000L, "1", "11");
		List<PersonnelBean> list = action.getLabTechs();
		assertEquals(3, list.size());
		ArrayList<String> names = new ArrayList<String>();
		for (PersonnelBean bean: list) {
			names.add(bean.getFullName());
		}
		assertTrue(names.contains("Lab Dude"));
		assertTrue(names.contains("Nice Guy"));
		assertTrue(names.contains("Cool Person"));
	}
	
	/**
	 * testGetLabTechName
	 * @throws Exception
	 */
	public void testGetLabTechName() throws Exception {
		action = new EditLabProceduresAction(factory, 9000000000L, "1", "11");
		assertEquals("Lab Dude", action.getLabTechName(5000000001L));
		assertEquals("", action.getLabTechName(5432100001L));
	}
	
	/**
	 * testGetLabTechQueueSize
	 * @throws Exception
	 */
	public void testGetLabTechQueueSize() throws Exception {
		action = new EditLabProceduresAction(factory, 9000000000L, "1", "11");
		assertEquals(19, action.getLabTechQueueSize(5000000001L));
		assertEquals(26, action.getLabTechQueueSize(5000000002L));
		assertEquals(0, action.getLabTechQueueSize(5000000003L));
		assertEquals(0, action.getLabTechQueueSize(5432100001L)); // bad id
	}
	
	/**
	 * testGetLabTechQueueSizeByPriority
	 * @throws Exception
	 */
	public void testGetLabTechQueueSizeByPriority() throws Exception {
		action = new EditLabProceduresAction(factory, 9000000000L, "1", "11");
		int[] sizes = action.getLabTechQueueSizeByPriority(5000000001L);
		assertEquals(3, sizes[1]);
		assertEquals(0, sizes[2]);
		assertEquals(16, sizes[3]);
	}

}
