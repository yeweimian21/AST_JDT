package edu.ncsu.csc.itrust.unit.dao.access;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.dao.mysql.DiagnosesDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Test privacy level
 * Test privacy level with access
 * and without no access
 */
public class UpdatePrivacyLevelTest extends TestCase {
	private DiagnosesDAO diagDAO = TestDAOFactory.getTestInstance().getDiagnosesDAO();
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testUpdatePrivacyLevelWithAccess() throws Exception {
		List<DiagnosisBean> diagnoses = getDiagnoses();
		assertEquals(5, diagnoses.size());
		DiagnosisBean d = new DiagnosisBean();
		d.setOvDiagnosisID(diagnoses.get(1).getOvDiagnosisID());
		diagnoses = getDiagnoses();
		assertEquals(5, diagnoses.size());
	}

	public void testUpdatePrivacyLevelWithoutAccess() throws Exception {
		List<DiagnosisBean> diagnoses = getDiagnoses();
		DiagnosisBean d = new DiagnosisBean();
		d.setOvDiagnosisID(diagnoses.get(0).getOvDiagnosisID());
		d.setDescription("My Description");
		d.setICDCode("79.3");
		diagnoses = getDiagnoses();
		assertEquals(5, diagnoses.size());
	}

	private List<DiagnosisBean> getDiagnoses() throws DBException {
		List<DiagnosisBean> diagnoses = diagDAO.getList(960);
		Collections.sort(diagnoses, new Comparator<DiagnosisBean>() {
			public int compare(DiagnosisBean o1, DiagnosisBean o2) {
				return Long.valueOf(o1.getOvDiagnosisID()).compareTo(Long.valueOf(o2.getOvDiagnosisID()));
			}
		});
		return diagnoses;
	}
}
