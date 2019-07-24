package edu.ncsu.csc.itrust.action.base;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * An abstract class which contains functionalities common among all actions relating to inserting and editing
 * office visits. Because all office visits are with a certain patient, OfficeVisitBaseAction extends
 * PatientBaseAction.
 * 
 * Use this class whenever you have a page which not only requires a patient MID, but an office visit ID. Pass
 * those IDs to the constructor, and an exception will be thrown if they are not valid IDs (which should kick
 * the user out to the home page).
 * 
 * The concrete methods created by this class allow for its association with the unique identifier of a given
 * office visit. These identifiers can also be verified for their correctness and existence.
 * 
 * Very similar to {@link PatientBaseAction} and {@link PersonnelBaseAction}
 */
abstract public class OfficeVisitBaseAction extends PatientBaseAction {
	private static final long UNSAVED_VISIT_ID = -1;

	/**
	 * A database access object for dealing with office visits.
	 */
	private OfficeVisitDAO ovDAO;

	/**
	 * The unique identifier of the office visit this action is associated with.
	 */
	protected long ovID;

	/**
	 * The default constructor.
	 * 
	 * @param factory
	 *            A database access object factory for supplying a runtime context.
	 * @param pidString
	 *            The patient's MID as a String, to be passed to the super constructor (for PatientBaseAction)
	 * @param ovIDString
	 *            The unique identifier of the office visit as a String.
	 * @throws ITrustException
	 *             If any of the supplied parameters is incorrect or there is a DB problem.
	 */
	public OfficeVisitBaseAction(DAOFactory factory, String pidString, String ovIDString)
			throws ITrustException {
		super(factory, pidString);
		this.ovDAO = factory.getOfficeVisitDAO();
		this.ovID = checkOfficeVisitID(ovIDString);
	}
	
	
	/**
	 * Constructs an action that is initially unsaved.  Like the three-argument 
	 * constructor except that the office visit id is a sentinel value and does 
	 * not represent a valid office visit.
	 * 
	 * @param factory
	 * @param pidString
	 * @throws ITrustException
	 */
	public OfficeVisitBaseAction(DAOFactory factory, String pidString)
			throws ITrustException {
		super(factory, pidString);
		this.ovDAO = factory.getOfficeVisitDAO();
		this.ovID = UNSAVED_VISIT_ID;
	}

	/**
	 * Asserts whether this unique office visit identifier both exists and is associated with the patient in
	 * the database.
	 * 
	 * @param input
	 *            The presumed identifier as a String.
	 * @return The same identifier as a long of the existing office visit.
	 * @throws ITrustException
	 *             If the visit does not exist or if there is a DB problem.
	 */
	private long checkOfficeVisitID(String input) throws ITrustException {
		try {
			encode(input);
			long ovID = Long.valueOf(input);

			if (ovDAO.checkOfficeVisitExists(ovID, pid))
				return ovID;
			else
				throw new ITrustException("Office Visit " + ovID + " with Patient MID " + pid
						+ " does not exist");
		} catch (NumberFormatException e) {
			throw new ITrustException("Office Visit ID is not a number: " + e.getMessage());
		}
	}
	
	/**
	 * Indicates if the office visit has been saved or not.
	 * @return boolean that indicates if this has been saved. 
	 */
	public boolean isUnsaved() {
		return ovID == UNSAVED_VISIT_ID;
	}
	
	/**
	 * Raises an exception if the office visit has not been saved.  Otherwise, 
	 * does nothing. 
	 * @throws ITrustException
	 */
	protected void verifySaved() throws ITrustException {
		if (isUnsaved()) {
			throw new ITrustException("Cannot perform action.  OfficeVisit is not saved.");
		}
	}

	/**
	 * For obtaining the unique identifier of the office visit this action is associated with.
	 * 
	 * @return A long of the identifier.
	 */
	public long getOvID() {
		return ovID;
	}

	/**
	 * Converts all characters of the input string to their HTML special characters equivalent representation.
	 * Explicitly, the less than symbol becomes lt, the greater than symbol becomes gt and a newline feed
	 * becomes br.
	 * 
	 * @param input
	 *            The string to encode.
	 * @return The encoded string.
	 */
	public String encode(String input) {
		String str = input.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\n", "<br />");
		return str;
	}
}
