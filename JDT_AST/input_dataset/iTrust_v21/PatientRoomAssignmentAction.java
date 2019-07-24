package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.beans.WardRoomBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.WardDAO;
import edu.ncsu.csc.itrust.exception.DBException;

public class PatientRoomAssignmentAction {
	/**
	 * DAOFactory to use with the WardDAO
	 */
	WardDAO wardDAO = null;
	
	public PatientRoomAssignmentAction(DAOFactory factory){
		wardDAO = new WardDAO(factory);
	}
	
	public void assignPatientToRoom(WardRoomBean wardRoom, long patientMID) throws DBException{
		wardRoom.setOccupiedBy(patientMID);
		wardDAO.updateWardRoomOccupant(wardRoom);
	}
	
	public void assignPatientToRoom(WardRoomBean wardRoom, PatientBean patient) throws DBException{
		assignPatientToRoom(wardRoom, patient.getMID());
	}
	
	public void removePatientFromRoom(WardRoomBean wardRoom, String reason) throws DBException{
		long mid = wardRoom.getOccupiedBy();
		wardDAO.checkOutPatientReason(mid, reason);
		wardRoom.setOccupiedBy(null);
		wardDAO.updateWardRoomOccupant(wardRoom);
	}
}
