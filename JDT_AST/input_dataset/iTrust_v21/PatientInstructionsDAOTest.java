package edu.ncsu.csc.itrust.unit.dao.patientinstructions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PatientInstructionsBean;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientInstructionsDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class PatientInstructionsDAOTest extends TestCase {
	private PatientInstructionsDAO dao = TestDAOFactory.getTestInstance().getPatientInstructionsDAO();
	private OfficeVisitDAO ovdao = TestDAOFactory.getTestInstance().getOfficeVisitDAO();
	private TestDataGenerator gen;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
	}
	
	public void testGetList() throws Exception {
		// empty database
		List<PatientInstructionsBean> list = dao.getList(1);
		assertEquals(0, list.size());
		
		// load one patient instructions record
		gen.uc44_acceptance_scenario_2();
		list = dao.getList(44100);
		assertEquals(1, list.size());
		// should still return none when there are no records with the given id 
		list = dao.getList(10);
		assertEquals(0, list.size());
	}
	
	public void testCreate() throws Exception {
		// empty database
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/DD HH:mm:ss");
		Date date = dateFormat.parse("2011/01/15 12:11:10");
		PatientInstructionsBean bean = new PatientInstructionsBean();
		bean.setName("name");
		bean.setComment("comment");
		bean.setModified(date);
		bean.setVisitID(1);
		dao.add(bean);
		
		List<PatientInstructionsBean> list = dao.getList(1);
		assertEquals(1, list.size());
		bean = list.get(0);
		assertEquals("name", bean.getName());
		assertEquals("comment", bean.getComment());
		assertEquals(date.getTime(), bean.getModified().getTime());
		assertEquals(1, bean.getVisitID());
	}
	
	public void testEdit() throws Exception {
		// empty database
		// Editing a non existent record doesn't do anything.
		PatientInstructionsBean bean = new PatientInstructionsBean();
		bean.setId(1);
		bean.setName("name");
		bean.setComment("comment");
		bean.setModified(new Date());
		bean.setVisitID(2);
		dao.edit(bean);
		
		assertEquals(0, dao.getList(2).size()); // nothing created
		assertEquals(0, dao.getList(1).size());
		
		// load one patient instructions record
		gen.uc44_acceptance_scenario_2();
		bean = dao.getList(44100).get(0);
		assertEquals("http://www.webmd.com/cold-and-flu/flu-guide/what-to-eat-when-you-have-the-flu", bean.getUrl());
		bean.setUrl("http://www.google.com/");
		dao.edit(bean);
		
		assertEquals(1, dao.getList(44100).size());
		assertEquals("http://www.google.com/", dao.getList(44100).get(0).getUrl());
	}
	
	public void testDelete0() throws Exception {
		assertEquals(0, dao.getList(3).size());
		dao.remove(1);
		assertEquals(0, dao.getList(3).size());
	}
	
	public void testGetOfficeVisitsWithInstructions() throws Exception {
		// Empty database.
		assertEquals(0, dao.getOfficeVisitsWithInstructions(1).size());

		// Patient 5 has one office visit with instructions.
		OfficeVisitBean ovbean = new OfficeVisitBean();
		ovbean.setPatientID(5);
		ovbean.setHospitalID("1");
		ovbean.setNotes("notes");
		ovbean.setVisitDateStr("1/1/2011");
		long ovid1 = ovdao.add(ovbean);
		ovbean.setPatientID(6);
		long ovid2 = ovdao.add(ovbean);
		
		PatientInstructionsBean bean = new PatientInstructionsBean();
		bean.setName("name");
		bean.setComment("comment");
		bean.setModified(new Date());
		bean.setVisitID(ovid1);
		dao.add(bean);
		bean.setVisitID(ovid2);
		dao.add(bean);
		
		// Ensure it only returns the one office visit with patient 
		// instructions assigned to patient 5.
	    List<OfficeVisitBean> list = dao.getOfficeVisitsWithInstructions(5);
	    assertEquals(1, list.size());
	    assertEquals(5, list.get(0).getPatientID());
	    assertEquals(ovid1, list.get(0).getID());

		// Patient 5 has two office visits, one with instructions.
		ovbean.setPatientID(5);
		long ovid3 = ovdao.add(ovbean);
		
	    list = dao.getOfficeVisitsWithInstructions(5);
	    assertEquals(1, list.size());
	    assertEquals(ovid1, list.get(0).getID());
		
		// Patient 5 has two office visits, both with instructions
		bean = new PatientInstructionsBean();
		bean.setName("name");
		bean.setComment("comment");
		bean.setModified(new Date());
		bean.setVisitID(ovid3);
		dao.add(bean);

		// Ensure it returns both offices visit with patient instructions 
		// assigned to patient 5.
	    list = dao.getOfficeVisitsWithInstructions(5);
	    assertEquals(2, list.size());
	    assertEquals(ovid1, list.get(0).getID());
	    assertEquals(ovid3, list.get(1).getID());
	    
	    // Patient 7 has an office visit, but no patient instructions.
		ovbean.setPatientID(7);
		ovdao.add(ovbean);

		assertEquals(0, dao.getOfficeVisitsWithInstructions(7).size());
	    
	    // Check that empty list is returned with bad id even when items are in DB.
		assertEquals(0, dao.getOfficeVisitsWithInstructions(123321).size());
	}
	
}
