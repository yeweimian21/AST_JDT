package edu.ncsu.csc.itrust.unit.dao.patient;

import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ProcedureBean;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class GetProceduresTest extends TestCase {
	private PatientDAO patientDAO = TestDAOFactory.getTestInstance().getPatientDAO();
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.cptCodes();
		gen.patient2();
	}
	
	public void testGetProcedures() throws Exception {
		List<ProcedureBean> list = patientDAO.getProcedures(2L);
		assertEquals(1, list.size());
		ProcedureBean bean = list.get(0);
		assertEquals("1270F", bean.getCPTCode());
		assertEquals("Injection procedure", bean.getDescription());
	}
	
}
