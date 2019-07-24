package edu.ncsu.csc.itrust.action;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.itrust.beans.FlagsBean;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.ObstetricsRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FlagsDAO;
import edu.ncsu.csc.itrust.dao.mysql.HealthRecordsDAO;
import edu.ncsu.csc.itrust.dao.mysql.ObstetricsRecordDAO;
import edu.ncsu.csc.itrust.enums.FlagValue;
import edu.ncsu.csc.itrust.enums.PregnancyStatus;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.validate.ObstetricsRecordValidator;

public class EditObstetricsAction {
    private ObstetricsRecordDAO obstetricsDAO;
    private FlagsDAO flagsDAO;
    private long loggedInMID;
    private EventLoggingAction loggingAction;
    private HealthRecordsDAO hDao;
    
    /**
	 * Just the factory and logged in MID
	 * 
	 * @param factory
	 * @param loggedInMID
	 */
	public EditObstetricsAction(DAOFactory factory, long loggedInMID) {
		this.obstetricsDAO = factory.getObstetricsRecordDAO();
		this.flagsDAO = factory.getFlagsDAO();
		this.loggedInMID = loggedInMID;
		this.loggingAction = new EventLoggingAction(factory);
		this.hDao = new HealthRecordsDAO(factory);
	}
    
	public void updateFhrFlag(ObstetricsRecordBean p) throws DBException {
		List<ObstetricsRecordBean> lst = obstetricsDAO.getObstetricsRecordsByMID(loggedInMID);
		
		//Get the current flag bean
		FlagsBean f = new FlagsBean();
		f.setMid(p.getMid());
		f.setPregId(p.getPregId());
		f.setFlagValue(FlagValue.AbnormalFHR);
		f = flagsDAO.getFlag(f);
		
		boolean setFlag = false;
		for (ObstetricsRecordBean r : lst) {
			if(r.getFhr() > FlagsBean.FHR_MAX_TRIGGER || r.getFhr() < FlagsBean.FHR_MIN_TRIGGER) {
				setFlag = true;
			}
		}
		
		f.setFlagged(setFlag);
		flagsDAO.setFlag(f);
	}
	
    public void editObstetricsRecord(long oid, ObstetricsRecordBean p, ArrayList<FlagsBean> flags)
    		throws FormValidationException, ITrustException {
    	if (p != null) {
    		List<ObstetricsRecordBean> initialBeans = obstetricsDAO.getObstetricsRecordsByMIDPregStatus(
					p.getMid(), PregnancyStatus.Initial.toString());
			if (initialBeans == null || initialBeans.isEmpty()) {
				throw new ITrustException("Invalid office visit to edit.");
			}
			else {
				p.setPregId(initialBeans.get(0).getPregId());
			}
    		new ObstetricsRecordValidator().validate(p);
    		//TODO set the calculation flags

	    	obstetricsDAO.editObstetricsRecord(p.getOid(), p);
	    	
			boolean isTwins = false;
			
			//set the manual flags first to load from later and pull if twins is set
			for (FlagsBean flag : flags) {
				flag.setPregId(p.getPregId());
				flagsDAO.setFlag(flag);
				if (flag.getFlagValue() == FlagValue.Twins)
					isTwins = flag.isFlagged();
			}
	    	
	    	//Download the list of office visits for this patient
	    	List<ObstetricsRecordBean> visits = obstetricsDAO.getObstetricsRecordsByMIDPregStatus(
					p.getMid(), PregnancyStatus.Office.toString());
	    	
	    	//Get FHR flag
			FlagsBean fhrFlag = new FlagsBean();
			fhrFlag.setMid(p.getMid());
			fhrFlag.setPregId(p.getPregId());
			fhrFlag.setFlagValue(FlagValue.AbnormalFHR);
			fhrFlag = flagsDAO.getFlag(fhrFlag);
			fhrFlag.setFlagged(false);
			
			//Get BP flag
			FlagsBean bpFlag = new FlagsBean();
			bpFlag.setMid(p.getMid());
			bpFlag.setPregId(p.getPregId());
			bpFlag.setFlagValue(FlagValue.HighBloodPressure);
			bpFlag = flagsDAO.getFlag(bpFlag);
			bpFlag.setFlagged(false);
			
			//Weight flag processing
			boolean setWeightFlag = false;
			List<HealthRecord> records = hDao.getAllRecordsBeforeOVDate(p.getMid(), p.getLmp());
			//can only do BMI calculations if a normal office visit (with height/weight) exists before LMP
			if (records.isEmpty()) {
				records = hDao.getAllHealthRecords(p.getMid());
			} 
			
			//check if any record trigges any flags
			for (ObstetricsRecordBean bean : visits){	
				
				//Do weight gain stuff
				if (!records.isEmpty() && !setWeightFlag) {
					double height = records.get(0).getHeight();
					double origWeight = records.get(0).getWeight();
					double newWeight = p.getWeight();
					
					//caluclate the original BMI
					double origBMI = HealthRecord.calculateBMI(height, origWeight);
					double weightChange = newWeight - origWeight;
					double scaler = ((double)(p.getDateVisit().getTime() - p.getLmp().getTime())) / ((double)(p.getEdd().getTime() - p.getLmp().getTime()));
					
					//find the expected weight offsets in BMI/weight tables
					for (int i = 0; i < FlagsBean.WEIGHTGAINBMIBOUNDS.length; i++) {
						//This is the correct upper bound
						if (FlagsBean.WEIGHTGAINBMIBOUNDS[i] > origBMI) {
							if (isTwins) {
								//If not in expected twins range
								if (FlagsBean.WeightGainTwinsBMIMin[i] * scaler > weightChange ||
										FlagsBean.WeightGainTwinsBMIMax[i] < weightChange) {
									setWeightFlag = true;								
								}
							} else {
								//If not in expected range
								if (FlagsBean.WEIGHTGAINBMIMIN[i] * scaler > weightChange ||
										FlagsBean.WeightGainBMIMax[i] < weightChange) {
									setWeightFlag = true;								
								}
							}					
							break; //exit after computed
						}
					}
				}
				
				//do fhr stuff
				if (!fhrFlag.isFlagged()) {
					if(bean.getFhr() > FlagsBean.FHR_MAX_TRIGGER || bean.getFhr() < FlagsBean.FHR_MIN_TRIGGER) {
						fhrFlag.setFlagged(true);
					}
				}
				
				//do bp stuff
				if (!bpFlag.isFlagged()) {
					if(bean.getBloodPressureS() > FlagsBean.SYS_BP_MAX_TRIGGER || bean.getBloodPressureD() > FlagsBean.DIA_BP_MAX_TRIGGER) {
						bpFlag.setFlagged(true);
						
					}
				}
	    	}
			flagsDAO.setFlag(fhrFlag);
			flagsDAO.setFlag(bpFlag);
			
			//Set weight flag if needed
			FlagsBean wFlag = new FlagsBean();
			wFlag.setMid(p.getMid());
			wFlag.setPregId(p.getPregId());
			wFlag.setFlagValue(FlagValue.WeightChange);
			wFlag = flagsDAO.getFlag(wFlag);
			wFlag.setFlagged(setWeightFlag);
			flagsDAO.setFlag(wFlag);
			
	    	loggingAction.logEvent(TransactionType.parse(6402), loggedInMID, 
					p.getMid(), "Obstetrics Office Visit " +  p.getOid() + " edited by " + loggedInMID);
    	}
    	else {
			throw new ITrustException("Cannot edit a null Obstetrics Record.");
		}
    }
}
