package edu.ncsu.csc.itrust.unit.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.action.*;

/**
 * UpdateICDCodeListActionTest
 */
public class UpdateICDCodeListActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private UpdateICDCodeListAction action;
	private TestDataGenerator gen;
	private final static long performingAdmin = 9000000001L;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.admin1();
		action = new UpdateICDCodeListAction(factory, performingAdmin);
	}

	/**
	 * testEvilFactory
	 */
	public void testEvilFactory() {
		action = new UpdateICDCodeListAction(EvilDAOFactory.getEvilInstance(), 0L);
		DiagnosisBean db = new DiagnosisBean("10.3", "a crazy diagnosis", "true");

		try {
			String x = action.addICDCode(db);
			assertEquals(
					"A database exception has occurred. Please see the log in the console for stacktrace", x);
		} catch (Exception e) {
			//TODO
		}

		try {
			String x = action.updateInformation(db);
			assertEquals(
					"A database exception has occurred. Please see the log in the console for stacktrace", x);
		} catch (Exception e) {
			//TODO
		}
	}

	private String getAddCodeSuccessString(DiagnosisBean proc) {
		return "Success: " + proc.getICDCode() + " - " + proc.getDescription() + " added";
	}

	/**
	 * testAddICDCode
	 * @throws Exception
	 */
	public void testAddICDCode() throws Exception {
		gen.icd9cmCodes();
		final String code = "999.99";
		final String desc = "testAddICDCode";
		final String classification = "Long Term";
		DiagnosisBean proc = new DiagnosisBean(code, desc, classification);
		assertEquals(getAddCodeSuccessString(proc), action.addICDCode(proc));
		proc = factory.getICDCodesDAO().getICDCode(code);
		assertEquals(desc, proc.getDescription());
	}
	
	/**
	 * testAddICDCodeURL
	 * @throws Exception
	 */
	public void testAddICDCodeURL() throws Exception {
		gen.icd9cmCodes();
		final String code = "999.99";
		final String desc = "testAddICDCode";
		final String classification = "Long Term";
		DiagnosisBean proc = new DiagnosisBean(code, desc, classification,"");
		proc.setURL("www.google.com");
		assertEquals(getAddCodeSuccessString(proc), action.addICDCode(proc));
		proc = factory.getICDCodesDAO().getICDCode(code);
		assertEquals(desc, proc.getDescription());
	}

	/**
	 * testAddDuplicate
	 * @throws Exception
	 */
	public void testAddDuplicate() throws Exception {
		gen.icd9cmCodes();
		final String code = "000.00";
		final String descrip0 = "description 0";
		DiagnosisBean proc = new DiagnosisBean(code, descrip0, "Short Term");
		assertEquals(getAddCodeSuccessString(proc), action.addICDCode(proc));
		proc.setDescription("description 1");
		assertEquals("Error: Code already exists.", action.addICDCode(proc));
		proc = factory.getICDCodesDAO().getICDCode(code);
		assertEquals(descrip0, proc.getDescription());
	}

	/**
	 * testUpdateICDInformation0
	 * @throws Exception
	 */
	public void testUpdateICDInformation0() throws Exception {
		final String code = "888.88";
		final String desc = "new descrip 0";
		final String desc_new = "new descrip 1";
		final String classification = "Long Term";
		DiagnosisBean proc = new DiagnosisBean(code, desc, classification);
		action.addICDCode(proc);
		proc.setDescription(desc_new);
		assertEquals("Success: 1 row(s) updated", action.updateInformation(proc));
		proc = factory.getICDCodesDAO().getICDCode(code);
		assertEquals(desc_new, proc.getDescription());
	}

	/**
	 * testUpdateNonExistent
	 * @throws Exception
	 */
	public void testUpdateNonExistent() throws Exception {
		gen.icd9cmCodes();
		DiagnosisBean proc = new DiagnosisBean("999.99", "shouldnt be here", "Long Term");
		assertEquals("Error: Code not found.", action.updateInformation(proc));
		assertEquals(null, factory.getICDCodesDAO().getICDCode("999.99"));
		assertEquals(13, factory.getICDCodesDAO().getAllICDCodes().size());
	}
	
	/**
	 * testUpdateNonExistentAllCodes
	 * @throws Exception
	 */
	public void testUpdateNonExistentAllCodes1() throws Exception {
		gen.icd9cmCodes();
		DiagnosisBean proc = new DiagnosisBean("999.99", "shouldnt be here", "Long Term");
		assertEquals("Error: Code not found.", action.updateInformation(proc));
		assertEquals(null, factory.getICDCodesDAO().getICDCode("999.99"));
		assertEquals(17, factory.getICDCodesDAO().getAllCodes().size());
	}

	
	/**
	 * testDiagnosisBeanStringConstructor
	 * @throws Exception
	 */
	public void testDiagnosisBeanStringConstructor() throws Exception {
		DiagnosisBean bean = new DiagnosisBean("999.99", "Test OK", "Long Term");
		assertEquals("999.99", bean.getICDCode());
		assertEquals("Test OK", bean.getDescription());
		bean = new DiagnosisBean("0", "Test OK", "Long Term");
		assertEquals("0", bean.getICDCode());
		assertEquals("Test OK", bean.getDescription());
	}
}
