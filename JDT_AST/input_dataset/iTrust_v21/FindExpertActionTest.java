package edu.ncsu.csc.itrust.unit.action;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.action.FindExpertAction;
import edu.ncsu.csc.itrust.beans.CustomComparator;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class FindExpertActionTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();
	private FindExpertAction fea;
	private PersonnelBean person, person1;
	
		
	protected void setUp() throws Exception {
		fea = new FindExpertAction(factory);
		gen.clearAllTables();
		person = new PersonnelBean();
		person1 = new PersonnelBean();
		person1.setMID(40L);
		person.setMID(900000000L);
		
	}
	
	/**
	 * Tests that you can find all of the experts you should
	 * be able to
	 */
	public void testFindExperts(){
		List<HospitalBean> hospitals = new ArrayList<HospitalBean>();
		
		HospitalBean realHospital = new HospitalBean();
		HospitalBean anotherHospital = new HospitalBean();

		hospitals.add(realHospital);
		hospitals.add(anotherHospital);

	
	
	
		//Test a single result
		assertTrue(fea.findExperts(hospitals, "ob/gyn").size() == 1);

		CustomComparator c = new CustomComparator();
		int apple = c.compare(person, person1);
		//tests compares two PersonnelBeans from the CustomComparator class using the compare method
		assertTrue((900000000 - 40) == apple);
	
		
	}
	
	/**
	 * Tests that you can filter out the hospitals
	 */
	public void testFilterHospitals(){
		HospitalBean hospitalZero = new HospitalBean();
		HospitalBean hospitalOne = new HospitalBean();
		HospitalBean blankHospital = new HospitalBean();
		HospitalBean nullHospital = new HospitalBean();

		List<HospitalBean> hospitals = new ArrayList<HospitalBean>();
		hospitalZero.setHospitalZip("00000");
		hospitalOne.setHospitalZip("11111");
	
		hospitalOne.setHospitalZip(null);
		
		hospitals.add(hospitalZero);
		hospitals.add(hospitalOne);
		hospitals.add(blankHospital);
		hospitals.add(nullHospital);
		
		assertTrue(fea.filterHospitals(hospitals, "00000", 5).size() == 1);
		assertTrue(fea.filterHospitals(hospitals, "00001", 4).size() == 1);
		assertTrue(fea.filterHospitals(hospitals, "00011", 3).size() == 1);
		assertTrue(fea.filterHospitals(hospitals, "00111", 2).size() == 1);
		assertTrue(fea.filterHospitals(hospitals, "01111", 1).size() == 1);
	}
	
	/**
	 * Tests that you can find hospitals by their specialty
	 */
	public void testFindHospitalsBySpecialty() {
		assertTrue(fea.findHospitalsBySpecialty("ob/gyn", "00000", 5).size() == 0);
	}
}
