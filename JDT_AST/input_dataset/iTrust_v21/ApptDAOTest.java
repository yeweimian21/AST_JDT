package edu.ncsu.csc.itrust.unit.dao.appointment;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.ApptBean;
import edu.ncsu.csc.itrust.beans.ApptTypeBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ApptDAO;
import edu.ncsu.csc.itrust.dao.mysql.ApptTypeDAO;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class ApptDAOTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private ApptDAO apptDAO = factory.getApptDAO();

	private ApptBean a1; 
	private ApptBean a2;
	private ApptBean a3;
	
	long patientMID = 42L;
	long doctorMID = 9000000000L;
	
	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.appointmentType();
		
		
		
		a1 = new ApptBean();
		a1.setDate(new Timestamp(new Date().getTime()));
		a1.setApptType("Ultrasound");
		a1.setHcp(doctorMID);
		a1.setPatient(patientMID);
		
		a2 = new ApptBean();
		a2.setDate(new Timestamp(new Date().getTime()+1000*60*15));	//15 minutes later
		a2.setApptType("Ultrasound");
		a2.setHcp(doctorMID);
		a2.setPatient(patientMID);
		
		a3 = new ApptBean();
		a3.setDate(new Timestamp(new Date().getTime()+1000*60*45));	//45 minutes later
		a3.setApptType("Ultrasound");
		a3.setHcp(doctorMID);
		a3.setPatient(patientMID);
	}

	public void testAppointment() throws Exception {
		
		long doctorMID = 9000000000L;
		
		
		List<ApptBean> conflicts = apptDAO.getAllConflictsForDoctor(doctorMID);
		assertEquals(0, conflicts.size());
		
		apptDAO.scheduleAppt(a1);
		apptDAO.scheduleAppt(a3);
		
		conflicts = apptDAO.getAllConflictsForDoctor(doctorMID);
		assertEquals(0, conflicts.size());

	}
	
	public void testAppointmentConflict() throws Exception {
		
		long doctorMID = 9000000000L;		
		
		List<ApptBean> conflicts = apptDAO.getAllConflictsForDoctor(doctorMID);
		assertEquals(0, conflicts.size());
		
		apptDAO.scheduleAppt(a1);
		apptDAO.scheduleAppt(a2);
		
		conflicts = apptDAO.getAllConflictsForDoctor(doctorMID);
		assertEquals(2, conflicts.size());

	}
	
	public void testAppointmentPatientConflict() throws Exception {
		
				
		List<ApptBean> conflicts = apptDAO.getAllConflictsForPatient(patientMID);
		assertEquals(0, conflicts.size());
		
		apptDAO.scheduleAppt(a1);
		apptDAO.scheduleAppt(a2);
		
		conflicts = apptDAO.getAllConflictsForPatient(patientMID);
		assertEquals(2, conflicts.size());

	}
	
	public void testGetConflictForAppointment() throws Exception {
		
		List<ApptBean> conflicts = apptDAO.getAllHCPConflictsForAppt(doctorMID, a1);
		assertEquals(0, conflicts.size());
		
		apptDAO.scheduleAppt(a1);
		
		conflicts = apptDAO.getAllHCPConflictsForAppt(doctorMID, a1);
		assertEquals(1, conflicts.size());
		
		ApptBean a1new = conflicts.get(0);
		
		conflicts = apptDAO.getAllHCPConflictsForAppt(doctorMID, a1new);
		assertEquals(0, conflicts.size());
		
		apptDAO.scheduleAppt(a2);
		
		conflicts = apptDAO.getAllHCPConflictsForAppt(doctorMID, a1new);
		assertEquals(1, conflicts.size());

	}
	
	public void testGetPatientConflictForAppointment() throws Exception {
		
		List<ApptBean> conflicts = apptDAO.getAllPatientConflictsForAppt(patientMID, a1);
		assertEquals(0, conflicts.size());
		
		apptDAO.scheduleAppt(a1);
		
		conflicts = apptDAO.getAllPatientConflictsForAppt(patientMID, a1);
		assertEquals(1, conflicts.size());
		
		ApptBean a1new = conflicts.get(0);
		
		conflicts = apptDAO.getAllHCPConflictsForAppt(doctorMID, a1new);
		assertEquals(0, conflicts.size());
		
		apptDAO.scheduleAppt(a2);
		
		conflicts = apptDAO.getAllPatientConflictsForAppt(patientMID, a1new);
		assertEquals(1, conflicts.size());

	}
	
	public void testGetApptType() throws Exception{
		ApptTypeDAO apptTypeDAO = factory.getApptTypeDAO();
		
		ApptTypeBean type = apptTypeDAO.getApptType("Ultrasound");
		
		assertEquals(30, type.getDuration());
		assertEquals("Ultrasound", type.getName());
	}
	
}
