package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.UpdateNDCodeListAction;
import edu.ncsu.csc.itrust.beans.MedicationBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * UpdateNDCodeListActionTest
 */
public class UpdateNDCodeListActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private UpdateNDCodeListAction action;
	private final static long performingAdmin = 9000000001L;
	private TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		action = new UpdateNDCodeListAction(factory, performingAdmin);
		gen.clearAllTables();
		gen.admin1();
		gen.ndCodes();
	}

	private String getAddCodeSuccessString(MedicationBean proc) {
		return "Success: " + proc.getNDCode() + " - " + proc.getDescription() + " added";
	}

	/**
	 * testEvilFactory
	 */
	public void testEvilFactory() {
		action = new UpdateNDCodeListAction(EvilDAOFactory.getEvilInstance(), 0L);
		MedicationBean mb = new MedicationBean();
		mb.setDescription("description");
		mb.setNDCode("3657");
		try {
			String x = action.addNDCode(mb);
			assertEquals(
					"A database exception has occurred. Please see the log in the console for stacktrace", x);
		} catch (Exception e) {
			//TODO
		}

		try {
			String x = action.updateInformation(mb);
			assertEquals(
					"A database exception has occurred. Please see the log in the console for stacktrace", x);
		} catch (Exception e) {
			//TODO
		}
		
		try {
			String x = action.removeNDCode(mb);
			assertEquals(
					"A database exception has occurred. Please see the log in the console for stacktrace", x);
		} catch (Exception e) {
			//TODO
		}
	}

	private void addEmpty(String code) throws Exception {
		MedicationBean med = new MedicationBean(code, " ");
		assertEquals(getAddCodeSuccessString(med), action.addNDCode(med));
		med = factory.getNDCodesDAO().getNDCode(code);
		assertEquals(" ", med.getDescription());
	}

	/**
	 * testAddNDCode
	 * @throws Exception
	 */
	public void testAddNDCode() throws Exception {
		final String code = "999999999";
		final String desc = "UpdateNDCodeListActionTest testAddNDCode";
		MedicationBean proc = new MedicationBean(code, desc);
		assertEquals(getAddCodeSuccessString(proc), action.addNDCode(proc));
		proc = factory.getNDCodesDAO().getNDCode(code);
		assertEquals(desc, proc.getDescription());
	}

	/**
	 * testAddDuplicate
	 * @throws DBException
	 * @throws FormValidationException
	 */
	public void testAddDuplicate() throws DBException, FormValidationException {
		final String code = "999999999";
		final String descrip0 = "description 0";
		MedicationBean proc = new MedicationBean(code, descrip0);
		assertEquals(getAddCodeSuccessString(proc), action.addNDCode(proc));
		proc.setDescription("description 1");
		assertEquals("Error: Code already exists.", action.addNDCode(proc));
		proc = factory.getNDCodesDAO().getNDCode(code);
		assertEquals(descrip0, proc.getDescription());
	}

	/**
	 * testUpdateNDInformation0
	 * @throws Exception
	 */
	public void testUpdateNDInformation0() throws Exception {
		final String code = "888888888";
		final String desc = "new descrip 0";
		MedicationBean proc = new MedicationBean(code);
		addEmpty(code);
		proc.setDescription(desc);
		assertEquals("Success: 1 row(s) updated", action.updateInformation(proc));
		proc = factory.getNDCodesDAO().getNDCode(code);
		assertEquals(desc, proc.getDescription());
	}

	/**
	 * testUpdateNonExistent
	 * @throws Exception
	 */
	public void testUpdateNonExistent() throws Exception {
		final String code = "999999999";
		MedicationBean proc = new MedicationBean(code, "shouldnt be here");
		assertEquals("Error: Code not found.", action.updateInformation(proc));
		assertEquals(null, factory.getNDCodesDAO().getNDCode(code));
		assertEquals(5, factory.getNDCodesDAO().getAllNDCodes().size());
	}
	
	/**
	 * testRemoveNDCode
	 * @throws Exception
	 */
	public void testRemoveNDCode() throws Exception {
		final String code = "999999999";
		final String desc = "UpdateNDCodeListActionTest testAddNDCode";
		MedicationBean proc = new MedicationBean(code, desc);
		assertEquals(getAddCodeSuccessString(proc), action.addNDCode(proc));
		proc = factory.getNDCodesDAO().getNDCode(code);
		assertEquals(desc, proc.getDescription());
		
		action.removeNDCode(proc);
		assertNull(factory.getNDCodesDAO().getNDCode(code));
	}
	
	/**
	 * testRemoveNonExistent
	 * @throws Exception
	 */
	public void testRemoveNonExistent() throws Exception {
		final String code = "999999999";
		MedicationBean proc = new MedicationBean(code, "shouldnt be here");
		assertEquals("Drug does not exist or already has been removed from the database.", action.removeNDCode(proc));
		assertEquals(null, factory.getNDCodesDAO().getNDCode(code));
		assertEquals(5, factory.getNDCodesDAO().getAllNDCodes().size());
	}
}
