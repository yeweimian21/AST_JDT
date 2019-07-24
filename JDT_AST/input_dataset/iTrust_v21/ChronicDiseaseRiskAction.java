package edu.ncsu.csc.itrust.action;

import java.util.List;
import edu.ncsu.csc.itrust.action.base.PatientBaseAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.NoHealthRecordsException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.risk.ChronicDiseaseMediator;
import edu.ncsu.csc.itrust.risk.RiskChecker;

/**
 * 
 * Used for chronicDiseaseRisks.jsp. Passes most of the logic off to {@link ChronicDiseaseMediator}, and the
 * various subclasses of {@link RiskChecker}.
 * 
 * 
 */
public class ChronicDiseaseRiskAction extends PatientBaseAction {
	private AuthDAO authDAO;
	private ChronicDiseaseMediator diseaseMediator;

	/**
	 * 
	 * @param factory
	 * @param loggedInMID
	 * @param pidString
	 *            The patient ID to be validated and used
	 * @throws ITrustException
	 * @throws DBException
	 * @throws NoHealthRecordsException
	 *             This is thrown if a patient is added without any health records to be checked. Try to avoid
	 *             having this exception be thrown in a normal flow of events.
	 */
	public ChronicDiseaseRiskAction(DAOFactory factory, long loggedInMID, String pidString)
			throws ITrustException, DBException, NoHealthRecordsException {
		super(factory, pidString);
		this.authDAO = factory.getAuthDAO();
		this.diseaseMediator = new ChronicDiseaseMediator(factory, pid);
	}

	/**
	 * Returns the ID of the patient to be checked.
	 * 
	 * @return patient ID whose risk we are checking
	 */
	public long getPatientID() {
		return pid;
	}

	/**
	 * Gives the full name of the patient
	 * 
	 * @return Full name of the patient who we are checking
	 * @throws DBException
	 * @throws ITrustException
	 */
	public String getUserName() throws DBException, ITrustException {
		return authDAO.getUserName(pid);
	}

	/**
	 * Returns the risks for which this patient is at risk for. All logic has been passed to
	 * {@link ChronicDiseaseMediator} and the subclasses of {@link RiskChecker}.
	 * 
	 * @return List of risks
	 * @throws ITrustException
	 * @throws DBException
	 */
	public List<RiskChecker> getDiseasesAtRisk() throws ITrustException, DBException {
		return diseaseMediator.getDiseaseAtRisk();
	}
}
