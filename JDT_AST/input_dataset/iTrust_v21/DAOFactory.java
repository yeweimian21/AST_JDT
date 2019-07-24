package edu.ncsu.csc.itrust.dao;

import java.sql.Connection;
import java.sql.SQLException;

import edu.ncsu.csc.itrust.dao.mysql.*;

/**
 * The central mediator for all Database Access Objects. The production instance uses the database connection
 * pool provided by Tomcat (so use the production instance when doing stuff from JSPs in the "real code").
 * Both the production and the test instance parses the context.xml file to get the JDBC connection.
 * 
 * Also, @see {@link EvilDAOFactory} and @see {@link TestDAOFactory}.
 * 
 * Any DAO that is added to the system should be added in this class, in the same way that all other DAOs are.
 * 
 *  
 * 
 */
public class DAOFactory {
	private static DAOFactory productionInstance = null;
	private IConnectionDriver driver;

	/**
	 * 
	 * @return A production instance of the DAOFactory, to be used in deployment (by Tomcat).
	 */
	public static DAOFactory getProductionInstance() {
			productionInstance = new DAOFactory();
		return productionInstance;
	}

	/**
	 * Protected constructor. Call getProductionInstance to get an instance of the DAOFactory
	 */
	protected DAOFactory() {
		this.driver = new ProductionConnectionDriver();
	}

	/**
	 * 
	 * @return this DAOFactory's Connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return driver.getConnection();
	}

	/**
	 * 
	 * @return this DAOFactory's AccessDAO
	 */
	public AccessDAO getAccessDAO() {
		return new AccessDAO(this);
	}
	
	/**
	 *
	 * @return this DAOFactory's ZipCodeDAO
	 */
	public ZipCodeDAO getZipCodeDAO()
	{
		return new ZipCodeDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's AllergyDAO
	 */
	public AllergyDAO getAllergyDAO() {
		return new AllergyDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's ApptDAO
	 */
	public ApptDAO getApptDAO() {
		return new ApptDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's ApptRequestDAO
	 */
	public ApptRequestDAO getApptRequestDAO() {
		return new ApptRequestDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's ApptTypeDAO
	 */
	public ApptTypeDAO getApptTypeDAO() {
		return new ApptTypeDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's AuthDAO
	 */
	public AuthDAO getAuthDAO() {
		return new AuthDAO(this);
	}
	
	/**
	 * 
	 * @return this DAOFactory's BillingDAO
	 */
	public BillingDAO getBillingDAO() {
		return new BillingDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's CPTCodesDAO
	 */
	public CPTCodesDAO getCPTCodesDAO() {
		return new CPTCodesDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's DrugInteractionDAO
	 */
	public DrugInteractionDAO getDrugInteractionDAO() {
		return new DrugInteractionDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's FamilyDAO
	 */
	public FamilyDAO getFamilyDAO() {
		return new FamilyDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's HealthRecordsDAO
	 */
	public HealthRecordsDAO getHealthRecordsDAO() {
		return new HealthRecordsDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's HospitalsDAO
	 */
	public HospitalsDAO getHospitalsDAO() {
		return new HospitalsDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's ICDCodesDAO
	 */
	public ICDCodesDAO getICDCodesDAO() {
		return new ICDCodesDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's NDCodesDAO
	 */
	public NDCodesDAO getNDCodesDAO() {
		return new NDCodesDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's OfficeVisitDAO
	 */
	public OfficeVisitDAO getOfficeVisitDAO() {
		return new OfficeVisitDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's PatientDAO
	 */
	public PatientDAO getPatientDAO() {
		return new PatientDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's PersonnelDAO
	 */
	public PersonnelDAO getPersonnelDAO() {
		return new PersonnelDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's ReferralDAO
	 */
	public ReferralDAO getReferralDAO() {
		return new ReferralDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's RiskDAO
	 */
	public RiskDAO getRiskDAO() {
		return new RiskDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's TransactionDAO
	 */
	public TransactionDAO getTransactionDAO() {
		return new TransactionDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's VisitRemindersDAO
	 */
	public VisitRemindersDAO getVisitRemindersDAO() {
		return new VisitRemindersDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's FakeEmailDAO
	 */
	public FakeEmailDAO getFakeEmailDAO() {
		return new FakeEmailDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's ReportRequestDAO
	 */
	public ReportRequestDAO getReportRequestDAO() {
		return new ReportRequestDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's SurveyDAO
	 */
	public SurveyDAO getSurveyDAO() {
		return new SurveyDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's LabProcedureDAO
	 */
	public LabProcedureDAO getLabProcedureDAO() {
		return new LabProcedureDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's LOINCDAO
	 */
	public LOINCDAO getLOINCDAO() {
		return new LOINCDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's SurveyResultDAO
	 */
	public SurveyResultDAO getSurveyResultDAO() {
		return new SurveyResultDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's MessageDAO
	 */
	public MessageDAO getMessageDAO() {
		return new MessageDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's AdverseEventDAO
	 */
	public AdverseEventDAO getAdverseEventDAO() {
		return new AdverseEventDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's RemoteMonitoringDAO
	 */
	public RemoteMonitoringDAO getRemoteMonitoringDAO() {
		return new RemoteMonitoringDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's PrescriptionsDAO
	 */
	public PrescriptionsDAO getPrescriptionsDAO() {
		return new PrescriptionsDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's DiagnosesDAO
	 */
	public DiagnosesDAO getDiagnosesDAO() {
		return new DiagnosesDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's ProceduresDAO
	 */
	public ProceduresDAO getProceduresDAO() {
		return new ProceduresDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's PrescriptionReportDAO
	 */
	public PrescriptionReportDAO getPrescriptionReportDAO() {
		return new PrescriptionReportDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's DrugReactionOverrideCodesDAO
	 */
	public DrugReactionOverrideCodesDAO getORCodesDAO() {
		return new DrugReactionOverrideCodesDAO(this);
	}

	/**
	 * 
	 * @return this DAOFactory's PatientInstructionsDAO
	 */
	public PatientInstructionsDAO getPatientInstructionsDAO() {
		return new PatientInstructionsDAO(this);
	}
	
	/**
	 * Gets the DAO for interaction with database table requiredprocedures.
	 * 
	 * @return this DAOFactory's RequiredProceduresDAO
	 */
	public RequiredProceduresDAO getRequiredProceduresDAO() {
		return new RequiredProceduresDAO(this);
	}

	/**
	 * Gets the DAO for reviews with the DB table reviews.
	 * @return this DAOFactory's ReviewsDAO
	 */
	public ReviewsDAO getReviewsDAO() {
		return new ReviewsDAO(this);
	}

	/**
	 * Gets the DOA for ophthalmology office visits records.
	 * @return this DAO factory's OphthalmologyOVRecordDAO
	 */
	public OphthalmologyOVRecordDAO getOphthalmologyOVRecordDAO(){
		return new OphthalmologyOVRecordDAO(this);
	}
	
	/**
	 * Gets the DOA for ophthalmology scheduled office visit records.
	 * @return this DAO factory's OphthalmologyOVRecordDAO
	 */
	public OphthalmologyScheduleOVDAO getOphthalmologyScheduleOVDAO() {
		return new OphthalmologyScheduleOVDAO(this);
	}
	
	/** 
	 * Gets the DOA for ophthalmology surgery records.
	 * @return this DAO factory's OphthalmologyOVRecordDAO
	 */
	public OphthalmologySurgeryRecordDAO getOphthalmologySurgeryRecordDAO(){
		return new OphthalmologySurgeryRecordDAO(this);
	}
	
	/**
	 * Gets the DAO for obstetrics records.
	 * @return this DAO factory's ObstetricsRecordDAO
	 */
	public ObstetricsRecordDAO getObstetricsRecordDAO() {
		return new ObstetricsRecordDAO(this);
	}
	
	/**
	 * Gets the DAO for setting flags. Currently used with obstetrics.
	 * @return this DAO factory's ObstetricsRecordDAO
	 */
	public FlagsDAO getFlagsDAO() {
		return new FlagsDAO(this);
	}

	public PreExistingConditionsDAO getPreExistingConditionsDAO() {
		return new PreExistingConditionsDAO(this);
	}
	
	/**
	 * Creates a new DAO for all of the Food Entries in a Food Diary
	 * @return this DAO factory's FoodEntryDAO
	 */
	public FoodEntryDAO getFoodEntryDAO() {
		return new FoodEntryDAO(this);
	}
	
	/**
	 * Creates a new DAO for all of the Exercise Entries in an Exercise Diary
	 * @return this DAO factory's ExerciseEntryDAO
	 */
	public ExerciseEntryDAO getExerciseEntryDAO() {
		return new ExerciseEntryDAO(this);
	}
	
	/**
	 * Creates a new DAO for all of the Sleep Entries in an Sleep Diary
	 * @return this DAO factory's SleepEntryDAO
	 */
	public SleepEntryDAO getSleepEntryDAO() {
		return new SleepEntryDAO(this);
	}
	
	/**
	 * Creates a new DAO for all of the Labels for diary entries
	 * @return this DAO factory's LabelsDAO
	 */
	public LabelDAO getLabelDAO() {
		return new LabelDAO(this);
	}
	
	/**
	 * Creates a new DAO for calculating a patient's Mifflin-St Jeor formula
	 * @return this DAO factory's MacronutrientsDAO
	 */
	public MacronutrientsDAO getMacronutrientsDAO() {
		return new MacronutrientsDAO(this);
	}
	
	/**
	 * Creates a new DAO for Ophthalmology Disease interactions
	 * @return this DAO factory's Ophthalmology Disease interactions
	 */
	public OphthalmologyDiagnosisDAO getOPDiagnosisDAO() {
		return new OphthalmologyDiagnosisDAO(this);
	}
}
