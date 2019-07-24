package edu.ncsu.csc.itrust.unit.dao.standards;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.CPTCodesDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.DBBuilder;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class CPTCodeTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DAOFactory evilFactory = EvilDAOFactory.getEvilInstance();
	private CPTCodesDAO cptDAO = factory.getCPTCodesDAO();
	private CPTCodesDAO evilCptDAO = evilFactory.getCPTCodesDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.cptCodes();
	}

	// order by code asc but field isn't numerical, so codes will NOT be
	// in NUMERICAL ascending order...
	// (unless codes are switched to fixed width)
	public void testGetAllCPT() throws Exception {
		List<ProcedureBean> codes = cptDAO.getAllCPTCodes();
		assertEquals(17, codes.size());
		assertEquals("Injection procedure", codes.get(0).getDescription());
		assertEquals("87", codes.get(1).getCPTCode());
	}

	public void testGetCPTCode() throws DBException {
		ProcedureBean proc = cptDAO.getCPTCode("87");
		assertEquals("87", proc.getCPTCode());
		assertEquals("Diagnostic Radiology", proc.getDescription());
	}

	public void testGetAllFromEmptyTable() throws Exception {
		clearCPTCodes();
		assertEquals(0, cptDAO.getAllCPTCodes().size());
	}

	public void testGetCPTCodeFromEmptyTable() throws Exception {
		clearCPTCodes();
		assertNull(cptDAO.getCPTCode("87"));
	}

	public void testAddCPTCode() throws DBException, ITrustException {
		final String code = "9999F";
		final String desc = "testAddCPTCode description";
		genericAdd(code, desc);
		List<ProcedureBean> allCodes = cptDAO.getAllCPTCodes();
		assertEquals(code, allCodes.get(allCodes.size() - 1).getCPTCode());
		assertEquals(desc, allCodes.get(allCodes.size() - 1).getDescription());
	}

	public void testAddDupe() throws SQLException, DBException, ITrustException {
		final String code = "0000A";
		final String descrip0 = "testAddDupe description";
		ProcedureBean proc = genericAdd(code, descrip0);
		try {
			proc.setDescription("");
			cptDAO.addCPTCode(proc);
			fail("CPTCodeTest.testAddDupe failed to catch dupe");
		} catch (ITrustException e) {
			assertEquals("Error: Code already exists.", e.getMessage());
			proc = cptDAO.getCPTCode(code);
			assertEquals(descrip0, proc.getDescription());
		}
	}

	private ProcedureBean genericAdd(String code, String desc) throws DBException, ITrustException {
		ProcedureBean proc = new ProcedureBean(code, desc);
		assertTrue(cptDAO.addCPTCode(proc));
		assertEquals(desc, cptDAO.getCPTCode(code).getDescription());
		return proc;
	}

	public void testUpdateDescription() throws DBException, ITrustException {
		final String code = "7777D";
		final String desc = "short description code";
		ProcedureBean proc = genericAdd(code, "");
		proc.setDescription(desc);
		assertEquals(1, cptDAO.updateCode(proc));
		proc = cptDAO.getCPTCode(code);
		assertEquals("short description code", proc.getDescription());
	}

	public void testUpdateNonExistent() throws SQLException, DBException {
		final String code = "0000F";
		ProcedureBean proc = new ProcedureBean(code, "");
		assertEquals(0, cptDAO.updateCode(proc));
		assertEquals(17, cptDAO.getAllCPTCodes().size());
	}
	
	public void testgetImmunizationCPTCodes() throws DBException {
		List<ProcedureBean> pBeans = cptDAO.getImmunizationCPTCodes();
		assertEquals(15, pBeans.size());
	}
	
	public void testgetImmunizationCPTCodes2(){
		try {
		evilCptDAO.getImmunizationCPTCodes();
		fail("Should have thrown DBException");
		} catch(DBException e) {
			//success
		}
	}

	private void clearCPTCodes() throws SQLException {
		new DBBuilder().executeSQL(Arrays.asList("DELETE FROM cptcodes;"));
	}
}
