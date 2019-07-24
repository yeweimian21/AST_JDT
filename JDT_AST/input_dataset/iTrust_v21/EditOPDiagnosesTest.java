package edu.ncsu.csc.itrust.unit.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.OphthalmologyDiagnosisBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.validate.OphthalmologyDiagnosisBeanValidator;
import edu.ncsu.csc.itrust.action.EditOPDiagnosesAction;
import junit.framework.TestCase;

public class EditOPDiagnosesTest extends TestCase {
	
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private EditOPDiagnosesAction action;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	//Test actions related to getting Diagnoses
	public void testGetDiagnoses() throws Exception {
		testAddDiagnosis();
		action = new EditOPDiagnosesAction(factory, "1");
		List<OphthalmologyDiagnosisBean> list = action.getDiagnoses();
		assertEquals(1, list.size());
		assertEquals("35.30", list.get(0).getICDCode());


		// An EditDiagnosesAction without an ovID returns an empty list.
		action = new EditOPDiagnosesAction(factory, "105");
		assertEquals(0, action.getDiagnoses().size());
	}

	//test adding various sorts of diagnosis.
	public void testAddDiagnosis() throws Exception {
		action = new EditOPDiagnosesAction(factory, "1");
		assertEquals(0, action.getDiagnoses().size());
		OphthalmologyDiagnosisBean bean = new OphthalmologyDiagnosisBean();
		bean.setICDCode("35.30");
		bean.setVisitID(1);
		bean.setDescription("Age-Related Macular Degeneration");
		action.addDiagnosis(bean);
		assertEquals(1, action.getDiagnoses().size());
		assertEquals("35.30", action.getDiagnoses().get(0).getICDCode());
		assertEquals("Age-Related Macular Degeneration", action.getDiagnoses().get(0).getDescription());
		assertEquals("yes",action.getDiagnoses().get(0).getClassification());
	}

	public void testEditDiagnosis() throws Exception {
		testAddDiagnosis();
		action = new EditOPDiagnosesAction(factory, "1");
		OphthalmologyDiagnosisBean bean = action.getDiagnoses().get(0);
		assertEquals("35.30", bean.getICDCode());
		bean.setICDCode("35.00");
		bean.setDescription("Amblyopia");
		bean.setURL("http://google.com");
		OphthalmologyDiagnosisBeanValidator validator = new OphthalmologyDiagnosisBeanValidator();
		validator.validate(bean);
		action.editDiagnosis(bean);
		bean = action.getDiagnoses().get(0);
		assertEquals("35.00", bean.getICDCode());
	}

	public void testDeleteDiagnosis() throws Exception {
		testAddDiagnosis();
		action = new EditOPDiagnosesAction(factory, "1");
		assertEquals(1, action.getDiagnoses().size());
		action.deleteDiagnosis(action.getDiagnoses().get(0));
		assertEquals(0, action.getDiagnoses().size());
	}
	

	public void testGetDiagnosisCodes() throws Exception {
		action = new EditOPDiagnosesAction(factory, "1");
		List<OphthalmologyDiagnosisBean> list = action.getDiagnosisCodes();
		assertEquals(4, list.size());
		
		// It can also be retrieved for an undefined office visit
		action = new EditOPDiagnosesAction(factory, "0");
		list = action.getDiagnosisCodes();
		assertEquals(4, list.size());
	}
	
	public void testBadFactory() throws Exception {
		testAddDiagnosis();
		EvilDAOFactory badFact = new EvilDAOFactory(1);
		action = new EditOPDiagnosesAction(badFact,"1");
		boolean except=false;
		try{
		OphthalmologyDiagnosisBean bean =action.getDiagnoses().get(0);
		action.deleteDiagnosis(bean);
		}
		catch(DBException e){
			except=true;
		}
		
		assertTrue(except);
		
	}

}
