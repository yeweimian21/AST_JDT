package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.ViewPatientAction;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ViewPatientActionTest {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private ViewPatientAction action;

	@Before
	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.patient2();
		action = new ViewPatientAction(factory, 2L, "2");
	}

	@Test
	public void testGetViewablePatients() {
		List<PatientBean> list = null;
		try {
			list = action.getViewablePatients();
		} catch (ITrustException e) {
			fail();
		}
		if(list == null){
			fail();
		} else {
			assertEquals(1, list.size());
		}
	}
	
	@Test
	public void testGetPatient() {
		PatientBean bean1 = null;
		try {
			bean1 = action.getPatient("2");
		} catch (ITrustException e) {
			fail();
		}
		if (bean1 == null)
			fail();
		assertEquals(bean1.getMID(), 2L); //and just test that the right record came back
		
		PatientBean bean2 = null;
		try {
			bean2 = action.getPatient("1");
		} catch (ITrustException e) {
			assertNull(bean2);
		}
		
		PatientBean bean3 = null;
		try {
			bean3 = action.getPatient("a");
		} catch (ITrustException e) {
			assertNull(bean3);
		}
	}

}
