package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.UpdateCPTCodeListAction;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;


public class UpdateCPTCodeListActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evil = EvilDAOFactory.getEvilInstance();
	private UpdateCPTCodeListAction action;
	private final static long performingAdmin = 9000000001l;

	@Override
	protected void setUp() throws Exception {
		new TestDataGenerator().admin1();
		action = new UpdateCPTCodeListAction(factory, performingAdmin);
	}

	private String getAddCodeSuccessString(ProcedureBean proc) {
		return "Success: " + proc.getCPTCode() + " - " + proc.getDescription() + " added";
	}

	private void addEmpty(String code) throws Exception {
		ProcedureBean proc = new ProcedureBean(code, " ");
		assertEquals(getAddCodeSuccessString(proc), action.addCPTCode(proc));
		proc = factory.getCPTCodesDAO().getCPTCode(code);
		assertEquals(" ", proc.getDescription());
	}

	public void testAddCPTCode() throws Exception {
		final String code = "9999F";
		final String desc = "testAddCPTCode";
		ProcedureBean proc = new ProcedureBean(code, desc);
		assertEquals(getAddCodeSuccessString(proc), action.addCPTCode(proc));
		proc = factory.getCPTCodesDAO().getCPTCode(code);
		assertEquals(desc, proc.getDescription());
	}
	
	public void testAddCPTCode2() throws Exception {
		final String code = "9999B";
		final String desc = "testAddCPTCode2";
		final String attr = "immunization";
		ProcedureBean proc = new ProcedureBean(code, desc, attr);
		assertEquals(getAddCodeSuccessString(proc), action.addCPTCode(proc));
		proc = factory.getCPTCodesDAO().getCPTCode(code);
		assertEquals(desc, proc.getDescription());
	}
	
	public void testAddCPTCodeEvil() throws FormValidationException {
		action = new UpdateCPTCodeListAction(evil, performingAdmin);
		final String code = "9999F";
		final String desc = "testAddCPTCode";
		ProcedureBean proc = new ProcedureBean(code, desc);
		assertEquals("A database exception has occurred. Please see the log in the console for stacktrace",	action.addCPTCode(proc));


	}

	public void testAddDuplicate() throws Exception {
		final String code = "0000F";
		final String descrip0 = "description 0";
		ProcedureBean proc = new ProcedureBean(code, descrip0);
		assertEquals(getAddCodeSuccessString(proc), action.addCPTCode(proc));
		proc.setDescription("description 1");
		assertEquals("Error: Code already exists.", action.addCPTCode(proc));
		proc = factory.getCPTCodesDAO().getCPTCode(code);
		assertEquals(descrip0, proc.getDescription());
	}

	public void testUpdateCPTInformation0() throws Exception {
		final String code = "8888F";
		final String desc = "new descrip 0";
		ProcedureBean proc = new ProcedureBean(code);
		addEmpty(code);
		proc.setDescription(desc);
		assertEquals("Success: 1 row(s) updated", action.updateInformation(proc));
		proc = factory.getCPTCodesDAO().getCPTCode(code);
		assertEquals(desc, proc.getDescription());
	}

	public void testUpdateNonExistent() throws Exception {
		new TestDataGenerator().clearAllTables();
		final String code = "9999F";
		ProcedureBean proc = new ProcedureBean(code, "shouldnt be here");
		assertEquals("Error: Code not found. To edit an actual code, change the description and add a new code with the old description", action.updateInformation(proc));
		assertEquals(null, factory.getCPTCodesDAO().getCPTCode(code));
		assertEquals(0, factory.getCPTCodesDAO().getAllCPTCodes().size());
	}
}
