package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.FlagsBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.ObstetricsRecordBean;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.dao.mysql.FlagsDAO;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.ObstetricsRecordDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.PreExistingConditionsDAO;
import edu.ncsu.csc.itrust.enums.DeliveryType;
import edu.ncsu.csc.itrust.enums.FlagValue;
import edu.ncsu.csc.itrust.enums.PregnancyStatus;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.ObstetricsRecordValidator;
/**
 * Used for add obstetrics record page (addObstetricsRecord.jsp). 
 * 
 * Very similar to {@link AddOfficeVisitAction}
 * 
 * 
 */
public class AddObstetricsAction {
	private ObstetricsRecordDAO obstetricsDAO;
	private FlagsDAO flagsDAO;
	private AllergyDAO allergyDAO;
	private PreExistingConditionsDAO conditionsDAO;
	private PatientDAO patientDAO;
	private long loggedInMID;
	private EventLoggingAction loggingAction;
	private HealthRecordsDAO hDao;
	private DAOFactory prodDAO;
	
	/**
	 * Just the factory and logged in MID
	 * 
	 * @param factory
	 * @param loggedInMID
	 */
	public AddObstetricsAction(DAOFactory factory, long loggedInMID) {
		this.obstetricsDAO = factory.getObstetricsRecordDAO();
		this.flagsDAO = factory.getFlagsDAO();
		this.allergyDAO = factory.getAllergyDAO();
		this.conditionsDAO = factory.getPreExistingConditionsDAO();
		this.patientDAO = factory.getPatientDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
		this.hDao = new HealthRecordsDAO(factory);
		this.prodDAO = factory;
	}
	
	/**
	 * Add a new obstetrics record
	 * 
	 * @param p ObstetricsRecordBean containing the info for the record to be created
	 * @param flags FlagsBean ArrayList containing all flags manually set on the form that submitted this
	 * @throws FormValidationException if the patient is not successfully validated
	 * @throws ITrustException 
	 */
	public void addObstetricsRecord(ObstetricsRecordBean p, ArrayList<FlagsBean> flags) 
			throws FormValidationException, ITrustException {
		if (p != null) {
			new ObstetricsRecordValidator().validate(p);
			
			List<ObstetricsRecordBean> initialBeans = null;
			
			//if not an office visit
			if (!p.getPregnancyStatus().equals(PregnancyStatus.Office)) {
				ObstetricsRecordBean bean = obstetricsDAO.getObstetricsRecordsByMIDLargestpregId(p.getMid());
				if (bean == null || bean.getPregnancyStatus() == null) {
					p.setPregId(0);
				}
				else {
					p.setPregId(bean.getPregId() + 1);
				}
			}
			//else, it is an office visit
			else {
				initialBeans = obstetricsDAO.getObstetricsRecordsByMIDPregStatus(
						p.getMid(), PregnancyStatus.Initial.toString());
				if (initialBeans == null || initialBeans.isEmpty()) {
					throw new ITrustException("The patient chosen is not a current obstetrics patient");
				}
				else {
					p.setPregId(initialBeans.get(0).getPregId());
				}
			}
			
			//add the obstetrics record to the database
			obstetricsDAO.addObstetricsRecord(p);
			
			
			boolean isTwins = false;
			
			//set the manual flags first to load from later and pull if twins is set
			for (FlagsBean flag : flags) {
				flag.setPregId(p.getPregId());
				flagsDAO.setFlag(flag);
				if (flag.getFlagValue() == FlagValue.Twins)
					isTwins = flag.isFlagged();
			}
				
			//Office Visit flags
			if (p.getPregnancyStatus().equals(PregnancyStatus.Office)) {
				//Weight flag processing
				boolean setWeightFlag = false;
				List<HealthRecord> records = hDao.getAllRecordsBeforeOVDate(p.getMid(), p.getLmp());
				//can only do BMI calculations if a normal office visit (with height/weight) exists before LMP
				if (records.isEmpty()) {
					records = hDao.getAllHealthRecords(p.getMid());
				} 
				if (!records.isEmpty()) {
					double height = records.get(0).getHeight();
					double origWeight = records.get(0).getWeight();
					double newWeight = p.getWeight();
					
					//calculate the original BMI
					double origBMI = HealthRecord.calculateBMI(height, origWeight);
					double weightChange = newWeight - origWeight;
					
					//find the expected weight offsets in BMI/weight tables
					for (int i = 0; i < FlagsBean.WEIGHTGAINBMIBOUNDS.length; i++) {
						//This is the correct upper bound
						if (FlagsBean.WEIGHTGAINBMIBOUNDS[i] > origBMI) {
							if (isTwins) {
								//If not in expected twins range
								if (FlagsBean.WeightGainTwinsBMIMin[i] > weightChange ||
										FlagsBean.WeightGainTwinsBMIMax[i] < weightChange) {
									setWeightFlag = true;								
								}
							} else {
								//If not in expected range
								if (FlagsBean.WEIGHTGAINBMIMIN[i] > weightChange ||
										FlagsBean.WeightGainBMIMax[i] < weightChange) {
									setWeightFlag = true;								
								}
							}					
							break; //exit after computed
						}
					}
				}
				
				//Set weight flag if needed
				FlagsBean wFlag = new FlagsBean();
				wFlag.setMid(p.getMid());
				wFlag.setPregId(p.getPregId());
				wFlag.setFlagValue(FlagValue.WeightChange);
				wFlag = flagsDAO.getFlag(wFlag);
				if (!wFlag.isFlagged()) {
					wFlag.setFlagged(setWeightFlag);
					flagsDAO.setFlag(wFlag);
				}
				
				
				//Set FHR flag if needed
				FlagsBean fhrFlag = new FlagsBean();
				fhrFlag.setMid(p.getMid());
				fhrFlag.setPregId(p.getPregId());
				fhrFlag.setFlagValue(FlagValue.AbnormalFHR);
				fhrFlag = flagsDAO.getFlag(fhrFlag);
				if (!fhrFlag.isFlagged()) {
					if(p.getFhr() > FlagsBean.FHR_MAX_TRIGGER || ((p.getFhr() > 0) && (p.getFhr() < FlagsBean.FHR_MIN_TRIGGER))) {
						fhrFlag.setFlagged(true);
						flagsDAO.setFlag(fhrFlag);
					}
				}
				
				//Set BP flag if needed
				FlagsBean bpFlag = new FlagsBean();
				bpFlag.setMid(p.getMid());
				bpFlag.setPregId(p.getPregId());
				bpFlag.setFlagValue(FlagValue.HighBloodPressure);
				bpFlag = flagsDAO.getFlag(bpFlag);
				if (!bpFlag.isFlagged()) {
					if(p.getBloodPressureS() > FlagsBean.SYS_BP_MAX_TRIGGER || p.getBloodPressureD() > FlagsBean.DIA_BP_MAX_TRIGGER) {
						bpFlag.setFlagged(true);
						flagsDAO.setFlag(bpFlag);
					}
				}
			}
			
			//UC67: Allergies Flag			
			FlagsBean allergiesFlag = new FlagsBean();
			allergiesFlag.setFlagValue(FlagValue.MaternalAllergies);
			allergiesFlag.setMid(p.getMid());
			allergiesFlag.setPregId(p.getPregId());
			allergiesFlag.setFlagged(!allergyDAO.getAllergies(p.getMid()).isEmpty()); //if not empty, have allergies
			flagsDAO.setFlag(allergiesFlag);
			
			//Advanced maternal age flag
			PatientBean chosenPatient = patientDAO.getPatient(p.getMid());
			if (chosenPatient.getAge() > 35) {
				FlagsBean advancedMaternalAge = new FlagsBean();
				advancedMaternalAge.setMid(p.getMid());
				advancedMaternalAge.setPregId(p.getPregId());
				advancedMaternalAge.setFlagValue(FlagValue.AdvancedMaternalAge);
				advancedMaternalAge.setFlagged(true);
				flagsDAO.setFlag(advancedMaternalAge);
			}
			
			//Rh- flag
			if (chosenPatient.getBloodType().toString().contains("-")) {
				FlagsBean rhNeg = new FlagsBean();
				rhNeg.setMid(p.getMid());
				rhNeg.setPregId(p.getPregId());
				rhNeg.setFlagValue(FlagValue.rhNeg);
				rhNeg.setFlagged(true);
			 	flagsDAO.setFlag(rhNeg);
			}
			
			//Genetic miscarriage flag
			ViewObstetricsAction viewObstetrics = new ViewObstetricsAction(prodDAO, loggedInMID);
			List<ObstetricsRecordBean> priorPregnancies = viewObstetrics.getViewableObstetricsRecordsByMIDType(p.getMid(), PregnancyStatus.Complete);
			int priorMiscarriageCount = 0;
			for (ObstetricsRecordBean prior : priorPregnancies) {
				if(prior.getDeliveryType().equals(DeliveryType.Miscarriage)) {
					priorMiscarriageCount++;
				}
			}
			if (priorMiscarriageCount > 1) {
				FlagsBean miscarriage = new FlagsBean();
				miscarriage.setMid(p.getMid());
				miscarriage.setPregId(p.getPregId());
				miscarriage.setFlagValue(FlagValue.GeneticMiscarriage);
				miscarriage.setFlagged(true);
				flagsDAO.setFlag(miscarriage);
			}
			
			//Pre-existing conditions flag
			List<String> conditions = conditionsDAO.getConditionsByMID(p.getMid());
			if (!conditions.isEmpty()) {
				FlagsBean conds = new FlagsBean();
				conds.setMid(p.getMid());
				conds.setPregId(p.getPregId());
				conds.setFlagValue(FlagValue.PreExistingConditions);
				conds.setFlagged(true);
				flagsDAO.setFlag(conds);
			}
			
			if (p.getPregnancyStatus().equals(PregnancyStatus.Initial)) {
				loggingAction.logEvent(TransactionType.parse(6300), loggedInMID, 
						p.getMid(), "Initial Obstetrics Record " +  p.getOid() + " added");
			}
			else if (p.getPregnancyStatus().equals(PregnancyStatus.Office)) {
				loggingAction.logEvent(TransactionType.parse(6400), loggedInMID, 
						p.getMid(), "Obstetrics Office Visit " +  p.getOid() + " added");
			}
		}
		else {
			throw new ITrustException("Cannot add a null Obstetrics Record.");
		}
	}
}
