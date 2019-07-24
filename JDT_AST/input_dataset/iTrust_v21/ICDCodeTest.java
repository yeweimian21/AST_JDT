package edu.ncsu.csc.itrust.unit.dao.standards;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.dao.mysql.ICDCodesDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ICDCodeTest extends TestCase{
	private ICDCodesDAO icdDAO = TestDAOFactory.getTestInstance().getICDCodesDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.icd9cmCodes();
	}
	
	public void testGetAllICD() throws Exception {
		List<DiagnosisBean> codes = icdDAO.getAllICDCodes();
		assertEquals(13, codes.size());
		assertEquals("Tuberculosis of the lung", codes.get(0).getDescription());
		assertEquals("15.00", codes.get(1).getICDCode());
	}
}
