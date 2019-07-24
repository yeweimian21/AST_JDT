package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.LabelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.LabelDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class LabelAction {

	private LabelDAO labelDAO;
	private PatientDAO patientDAO;
	private long loggedInMID;

	/**
	 * Uses the factory to construct labelDAO and patientDAO
	 * 
	 * @param factory
	 *            DAO factory to use
	 * @param loggedInMID
	 *            who is currently logged in
	 */
	public LabelAction(DAOFactory factory, long loggedInMID) {
		this.patientDAO = factory.getPatientDAO();
		this.labelDAO = factory.getLabelDAO();
		this.loggedInMID = loggedInMID;
	}

	/**
	 * Returns a single label
	 * @param entryID ID of the label in the database
	 * @return a LabelBean if there exist any labels
	 * @throws ITrustException
	 */
	public LabelBean getLabel(long entryID) throws ITrustException {
		try {
				return labelDAO.getLabel(entryID);
		} catch (DBException d) {
			throw new ITrustException("Error retrieving Label");
		}
	}
	
	/**
	 * Takes in which patient you want to view the labels for, and then returns
	 * all of the labels for that patient. It first checks to ensure that the
	 * person requesting the labels is a patient
	 * 
	 * @param patientMID
	 *            the id of the patient whose food diary we want
	 * @return a list of the patient's labels
	 */
	public List<LabelBean> getLabels(long patientMID) throws ITrustException {
		try {
			if ((patientDAO.checkPatientExists(loggedInMID) && loggedInMID == patientMID)) {

				// if true, then it is a patient, so show own labels
				return labelDAO.getLabels(patientMID);
			} else {
				throw new ITrustException("You do not have permission to "
						+ "access those labels!");
			}
		} catch (DBException d) {
			throw new ITrustException("Error retrieving Labels");
		}
	}
	
	public String addLabel(LabelBean label) throws FormValidationException {
		try {
			label.setPatientID(loggedInMID);
			labelDAO.addLabel(label);
			return "Success: " + label.getLabelName()
					+ " was added successfully!";
		} catch (DBException e) {
			return e.getMessage();
		} catch (ITrustException d) {
			return d.getMessage();
		}
	}
	
	/**
	 * Edits the label of a patient. It first checks to make sure that the
	 * patient has the ability to edit this label because it belongs to 
	 * them. Patients should only be able to edit entries that belong to them.
	 * @param label the bean to be updated
	 * @return the number of rows updated (0 means nothing happened,
	 * -1 means the logged in user cannot edit this food entry, and 
	 * anything else is the number of rows updated which should never
	 * exceed 1)
	 * @throws ITrustException
	 */
	public int editLabel(LabelBean label) 
			throws ITrustException, FormValidationException {
		int numUpdated;
		if (label.getPatientID() != loggedInMID) {
			numUpdated = -1; //this user does not "own" this food entry
		} else {
			try {
				numUpdated = labelDAO.editLabel(label.getEntryID(), loggedInMID, label);
			} catch (DBException d) {
				throw new ITrustException("Error updating Label");
			}
		}
		return numUpdated;
	}

	/**
	 * Deletes a label from the database
	 * 
	 * @param entryID
	 *            the label to delete
	 * @return the number of rows deleted (should never exceed 1)
	 * @throws ITrustException
	 */
	public int deleteLabel(long entryID) throws ITrustException {
		try {
			int numDeleted = labelDAO.deleteLabel(entryID, loggedInMID);
			return numDeleted;
		} catch (DBException d) {
			throw new ITrustException("Error deleting Label");
		}
	}
}