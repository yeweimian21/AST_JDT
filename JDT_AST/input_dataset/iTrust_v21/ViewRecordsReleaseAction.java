package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.RecordsReleaseDAO;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.HospitalBean;
import edu.ncsu.csc.itrust.beans.RecordsReleaseBean;

public class ViewRecordsReleaseAction {
	
	/** PersonnelDAO object for accessing the patients in the database*/
	private PersonnelDAO personnelDAO;
	/** RecordsReleaseDAO object for accessing record release requests in the database*/
	private RecordsReleaseDAO releaseDAO;
	/** AuthDAO for determining the type of user the user is */
	private AuthDAO authDAO;
	/** PatientDAO for working with patient objects in the database*/
	private PatientDAO patDAO;
	/** HospitalsDAO for getting hospital objects from the database*/
	private HospitalsDAO hosDAO;
	/** HealthRecordsDAO for getting health records from the database*/
	private HealthRecordsDAO hrDAO;
	
	/** Long for storing the logged in hcp/uap's mid */
	private long loggedInMID;
	
	/**
	 * Constructor for RequestRecordsReleaseAction. Gets and initializes all necessary DAOs for
	 * working with requesting a patient's records.
	 */
	public ViewRecordsReleaseAction(DAOFactory factory, long loggedInMID) {
		personnelDAO = factory.getPersonnelDAO();
		releaseDAO = new RecordsReleaseDAO(factory);
		authDAO = factory.getAuthDAO();
		patDAO = factory.getPatientDAO();
		hosDAO = factory.getHospitalsDAO();
		hrDAO = factory.getHealthRecordsDAO();
		this.loggedInMID = loggedInMID;
	}
	
	public String getPatientName(long pid) {
		String name = "";
		try {
			name = patDAO.getName(pid);
		} catch (DBException e1) {
			e1.printStackTrace();
		} catch (ITrustException e2) {
			e2.printStackTrace();
		}
		
		return name;
	}
	
	public String getHospitalName(String hospitalID) {
		String name = "";
		try {
			HospitalBean hospital = hosDAO.getHospital(hospitalID);
			if (hospital != null)
				name = hospital.getHospitalName();
		} catch (DBException e1) {
			e1.printStackTrace();
		}
		
		return name;
	}
	
	
	public String getDoctorName(long mid) throws ITrustException {
		String name = "";
		try {
			//Try to get the doctor's name with the specified mid
			name = personnelDAO.getName(mid);
		} catch (DBException e) {
			//If a DBException is thrown, print a stack trace and return a blank name
			e.printStackTrace();
		}
		
		return name;
	}
	
	public List<RecordsReleaseBean> getHospitalReleaseRequests() throws ITrustException {
		//List for holding records release requests
		List<RecordsReleaseBean> releases = new ArrayList<RecordsReleaseBean>();
		//List of hospitals that the hcp/uap is associated with
		List<HospitalBean> hospitals;
		//Get the logged in user's role
		Role userRole;
		
		try {
			//Check the user's role and get a list of hospitals depending on that
			userRole = authDAO.getUserRole(loggedInMID);
			if (userRole.getUserRolesString().equals("hcp")) {
				hospitals = personnelDAO.getHospitals(loggedInMID);
			} else if (userRole.getUserRolesString().equals("uap")) {
				hospitals = personnelDAO.getUAPHospitals(loggedInMID);
			} else {
				return new ArrayList<RecordsReleaseBean>();
			}
			
			//Add to the list of records release requests from each associated hospital
			for (int i = 0; i < hospitals.size(); i++) {
				//Get each hospital's id
				String hospitalID = hospitals.get(i).getHospitalID();
				releases.addAll(releaseDAO.getAllRecordsReleasesByHospital(hospitalID));
			}
		} catch (DBException e) {
			//If there is a DBException return an empty list
			return new ArrayList<RecordsReleaseBean>();
		}
		
		return releases;
	}
	
	public List<RecordsReleaseBean> filterPendingRequests(List<RecordsReleaseBean> requests) {
		List<RecordsReleaseBean> pendingRequests = new ArrayList<RecordsReleaseBean>();
		for (int i = 0; i < requests.size(); i++) {
			if (requests.get(i).getStatus() == 0)
				pendingRequests.add(requests.get(i));
		}
		
		return pendingRequests;
	}
	
	public int getNumPendingRequests(List<RecordsReleaseBean> requests) {
		int numOfPendingRequests = 0;
		for(int i = 0; i < requests.size(); i++){
			if(requests.get(i).getStatus() == 0) {
				numOfPendingRequests++;
			}
		}
		
		return numOfPendingRequests;
	}
	
	public boolean approveRequest(RecordsReleaseBean request) {
		//If the request status is not pending, don't update
		if (request.getStatus() != 0)
			return false;
		
		//Set the request to approved status
		request.setStatus(1);
		try {
			//Update the records release object
			releaseDAO.updateRecordsRelease(request);
		} catch (DBException e) {
			//If a DBException is caught reset the status and return false
			request.setStatus(0);
			return false;
		}
		
		return true;
	}
	
	public boolean denyRequest(RecordsReleaseBean request) {
		//If the request status is not pending, don't update
		if (request.getStatus() != 0)
			return false;
		
		//Set the request to denied status
		request.setStatus(2);
		
		try {
			//Update the records release object
			releaseDAO.updateRecordsRelease(request);
		} catch (DBException e) {
			//If a DBException is caught reset the status and return false
			request.setStatus(0);
			return false;
		}
		
		return true;
	}
	
	public List<HealthRecord> getRequestedHealthRecords(RecordsReleaseBean request) {
		//Get the patient's mid
		long pid = request.getPid();
		//Get the hospital id of the request
		String hospitalID = request.getReleaseHospitalID();
		
		//List of health records from the requested hospital
		List<HealthRecord> records = new ArrayList<HealthRecord>();
		
		try {
			//Get all of the patient's health records from the specified hospital
			records = hrDAO.getAllPatientHealthRecordsByHospital(pid, hospitalID);
		} catch (DBException e) {
			// If a DBException is thrown print a stack trace and return an empty list
			e.printStackTrace();
		}
		
		return records;
	}
}
