package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.action.EditDiagnosesAction;
import junit.framework.TestCase;

public class EditDiagnosesActionTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditDiagnosesAction action;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testGetDiagnoses() throws Exception {
		action = new EditDiagnosesAction(factory, 9000000000L, "2", "955");
		List<DiagnosisBean> list = action.getDiagnoses();
		assertEquals(1, list.size());
		assertEquals("250.10", list.get(0).getICDCode());

		action = new EditDiagnosesAction(factory, 9000000000L, "2", "952");
		assertEquals(0, action.getDiagnoses().size());

		// An EditDiagnosesAction without an ovID returns an empty list.
		action = new EditDiagnosesAction(factory, 9000000000L, "2");
		assertEquals(0, action.getDiagnoses().size());
	}

	public void testAddDiagnosis() throws Exception {
		action = new EditDiagnosesAction(factory, 9000000000L, "2", "952");
		assertEquals(0, action.getDiagnoses().size());
		DiagnosisBean bean = new DiagnosisBean();
		bean.setICDCode("250.10");
		bean.setVisitID(952);
		action.addDiagnosis(bean);
		assertEquals(1, action.getDiagnoses().size());
		assertEquals("250.10", action.getDiagnoses().get(0).getICDCode());
	}

	public void testEditDiagnosis() throws Exception {
		action = new EditDiagnosesAction(factory, 9000000000L, "2", "955");
		DiagnosisBean bean = action.getDiagnoses().get(0);
		assertEquals("250.10", bean.getICDCode());
		bean.setICDCode("84.50");
		action.editDiagnosis(bean);
		bean = action.getDiagnoses().get(0);
		assertEquals("84.50", bean.getICDCode());
	}

	public void testDeleteDiagnosis() throws Exception {
		action = new EditDiagnosesAction(factory, 9000000000L, "2", "955");
		assertEquals(1, action.getDiagnoses().size());
		action.deleteDiagnosis(action.getDiagnoses().get(0));
		assertEquals(0, action.getDiagnoses().size());
	}

	public void testGetDiagnosisCodes() throws Exception {
		action = new EditDiagnosesAction(factory, 9000000000L, "2", "955");
		List<DiagnosisBean> list = action.getDiagnosisCodes();
		assertEquals(19, list.size());
		
		// It can also be retrieved for an undefined office visit
		action = new EditDiagnosesAction(factory, 9000000000L, "1");
		list = action.getDiagnosisCodes();
		assertEquals(19, list.size());
	}

}
