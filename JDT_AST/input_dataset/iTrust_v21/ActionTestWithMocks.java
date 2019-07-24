package edu.ncsu.csc.itrust.unit.testutils;

import static org.easymock.EasyMock.expect;
import junit.framework.TestCase;
import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AccessDAO;
import edu.ncsu.csc.itrust.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.dao.mysql.CPTCodesDAO;
import edu.ncsu.csc.itrust.dao.mysql.FakeEmailDAO;
import edu.ncsu.csc.itrust.dao.mysql.FamilyDAO;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.HospitalsDAO;
import edu.ncsu.csc.itrust.dao.mysql.ICDCodesDAO;
import edu.ncsu.csc.itrust.dao.mysql.LOINCDAO;
import edu.ncsu.csc.itrust.dao.mysql.LabProcedureDAO;
import edu.ncsu.csc.itrust.dao.mysql.MessageDAO;
import edu.ncsu.csc.itrust.dao.mysql.NDCodesDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.ReferralDAO;
import edu.ncsu.csc.itrust.dao.mysql.ReportRequestDAO;
import edu.ncsu.csc.itrust.dao.mysql.RiskDAO;
import edu.ncsu.csc.itrust.dao.mysql.SurveyDAO;
import edu.ncsu.csc.itrust.dao.mysql.SurveyResultDAO;
import edu.ncsu.csc.itrust.dao.mysql.TransactionDAO;
import edu.ncsu.csc.itrust.dao.mysql.VisitRemindersDAO;

/**
 * This class is used to create some of the basic mock objects for iTrust. This keeps one control for all unit
 * tests, then resets it for each usage.
 * 
 * To use this class:
 * <ol>
 * <li>Extend this class instead of TestCase</li>
 * <li>Run initMocks in the setUp method</li>
 * <li>Use the mock objects as you wish. You don't need to worry about factory.getDAO-type methods - those are
 * set up to be expected a call any number of times</li>
 * </ol>
 * 
 * By default, control is set up to a "nice" control.
 * 
 * Yes, everything in this class is protected static - which is not typically desirable. However, this is a
 * special test utility that takes advantage of re-using mocks. Mock objects are created once and then reset
 * every time initMocks is used. This has a HUGE performance advantage as creating all of these new Mocks
 * takes 500ms, but resetting them takes 5ms.
 * 
 *   Meneely
 * 
 */
abstract public class ActionTestWithMocks extends TestCase {

	protected static IMocksControl control;
	protected static DAOFactory factory;
	protected static AccessDAO accessDAO;
	protected static AllergyDAO allergyDAO;
	protected static AuthDAO authDAO;
	protected static CPTCodesDAO cptDAO;
	protected static FakeEmailDAO emailDAO;
	protected static FamilyDAO familyDAO;
	protected static HealthRecordsDAO healthDAO;
	protected static HospitalsDAO hospitalDAO;
	protected static ICDCodesDAO icdDAO;
	protected static LabProcedureDAO labDAO;
	protected static LOINCDAO loincDAO;
	protected static MessageDAO messageDAO;
	protected static NDCodesDAO ndDAO;
	protected static OfficeVisitDAO ovDAO;
	protected static PatientDAO patientDAO;
	protected static PersonnelDAO personnelDAO;
	protected static ReferralDAO referralDAO;
	protected static ReportRequestDAO reportRequestDAO;
	protected static RiskDAO riskDAO;
	protected static SurveyDAO surveyDAO;
	protected static SurveyResultDAO surveyResultDAO;
	protected static TransactionDAO transDAO;
	protected static VisitRemindersDAO visitDAO;

	protected static void initMocks() {
		if (control == null)
			control = EasyMock.createNiceControl();
		else
			control.reset();
		createMocks();
		createFactoryExpectations();
	}

	private static void createMocks() {
		factory = control.createMock(DAOFactory.class);
		accessDAO = control.createMock(AccessDAO.class);
		allergyDAO = control.createMock(AllergyDAO.class);
		authDAO = control.createMock(AuthDAO.class);
		cptDAO = control.createMock(CPTCodesDAO.class);
		emailDAO = control.createMock(FakeEmailDAO.class);
		familyDAO = control.createMock(FamilyDAO.class);
		healthDAO = control.createMock(HealthRecordsDAO.class);
		hospitalDAO = control.createMock(HospitalsDAO.class);
		icdDAO = control.createMock(ICDCodesDAO.class);
		labDAO = control.createMock(LabProcedureDAO.class);
		loincDAO = control.createMock(LOINCDAO.class);
		messageDAO = control.createMock(MessageDAO.class);
		ndDAO = control.createMock(NDCodesDAO.class);
		ovDAO = control.createMock(OfficeVisitDAO.class);
		patientDAO = control.createMock(PatientDAO.class);
		personnelDAO = control.createMock(PersonnelDAO.class);
		referralDAO = control.createMock(ReferralDAO.class);
		reportRequestDAO = control.createMock(ReportRequestDAO.class);
		riskDAO = control.createMock(RiskDAO.class);
		surveyDAO = control.createMock(SurveyDAO.class);
		surveyResultDAO = control.createMock(SurveyResultDAO.class);
		transDAO = control.createMock(TransactionDAO.class);
		visitDAO = control.createMock(VisitRemindersDAO.class);
	}

	private static void createFactoryExpectations() {
		expect(factory.getAccessDAO()).andReturn(accessDAO).anyTimes();
		expect(factory.getAllergyDAO()).andReturn(allergyDAO).anyTimes();
		expect(factory.getAuthDAO()).andReturn(authDAO).anyTimes();
		expect(factory.getCPTCodesDAO()).andReturn(cptDAO).anyTimes();
		expect(factory.getFakeEmailDAO()).andReturn(emailDAO).anyTimes();
		expect(factory.getFamilyDAO()).andReturn(familyDAO).anyTimes();
		expect(factory.getHealthRecordsDAO()).andReturn(healthDAO).anyTimes();
		expect(factory.getHospitalsDAO()).andReturn(hospitalDAO).anyTimes();
		expect(factory.getICDCodesDAO()).andReturn(icdDAO).anyTimes();
		expect(factory.getLabProcedureDAO()).andReturn(labDAO).anyTimes();
		expect(factory.getLOINCDAO()).andReturn(loincDAO).anyTimes();
		expect(factory.getMessageDAO()).andReturn(messageDAO).anyTimes();
		expect(factory.getNDCodesDAO()).andReturn(ndDAO).anyTimes();
		expect(factory.getOfficeVisitDAO()).andReturn(ovDAO).anyTimes();
		expect(factory.getPatientDAO()).andReturn(patientDAO).anyTimes();
		expect(factory.getPersonnelDAO()).andReturn(personnelDAO).anyTimes();
		expect(factory.getReferralDAO()).andReturn(referralDAO).anyTimes();
		expect(factory.getReportRequestDAO()).andReturn(reportRequestDAO).anyTimes();
		expect(factory.getRiskDAO()).andReturn(riskDAO).anyTimes();
		expect(factory.getSurveyDAO()).andReturn(surveyDAO).anyTimes();
		expect(factory.getSurveyResultDAO()).andReturn(surveyResultDAO).anyTimes();
		expect(factory.getTransactionDAO()).andReturn(transDAO).anyTimes();
		expect(factory.getVisitRemindersDAO()).andReturn(visitDAO).anyTimes();
	}
}
