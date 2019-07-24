package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.exception.DBException;

public class ViewMyLabProceduresAction {
	private LabProcedureDAO labProcedureDAO;
	long patient;
	
	public ViewMyLabProceduresAction(DAOFactory factory, long loggedInMID) {
		labProcedureDAO = factory.getLabProcedureDAO();
		patient = loggedInMID;
	}
	
	public List<LabProcedureBean> getLabProcedures() throws DBException {
		return labProcedureDAO.getLabProceduresForPatient(patient);
	}

	/**
	 * Get the number of unviewed lab procedures that the current user has.
	 * 
	 * @return The number of unviewed lab procedures.
	 * @throws SQLException
	 */
	public int getUnviewedCount() throws DBException {
		return labProcedureDAO.getPatientUnviewedCount(patient);
	}
}
