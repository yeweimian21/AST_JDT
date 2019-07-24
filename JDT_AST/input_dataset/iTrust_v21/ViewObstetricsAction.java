package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.FlagsBean;
import edu.ncsu.csc.itrust.beans.ObstetricsRecordBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FlagsDAO;
import edu.ncsu.csc.itrust.dao.mysql.ObstetricsRecordDAO;
import edu.ncsu.csc.itrust.enums.FlagValue;
import edu.ncsu.csc.itrust.enums.PregnancyStatus;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * ViewObstetricsAction is a class to help the Initialize Obstetrics Record page to get all the records that should
 * be displayed on the page (by MID), and the View Obstetrics Record page to get a record when selected (by OID).
 */
public class ViewObstetricsAction {

    /**obstetricsDAO is the DAO that retrieves the obstetrics records from the database*/
    private ObstetricsRecordDAO obstetricsDAO;
    private FlagsDAO flagsDAO;
    /**loggedInMID is the HCP that is logged in.*/
    private long loggedInMID;
    private EventLoggingAction loggingAction;

    /**
     * ViewObstetricsAction is the constructor for this action class. It simply initializes the
     * instance variables.
     * @param factory The factory used to get the obstetricsDAO.
     * @param loggedInMID The MID of the logged in user.
     * @param MID The MID 
     * @param OID the OID
     * @throws ITrustException When there is a bad user.
     */
    public ViewObstetricsAction(DAOFactory factory, long loggedInMID) //, long MID, long OID)
            throws ITrustException {
        this.obstetricsDAO = factory.getObstetricsRecordDAO();
        this.flagsDAO = factory.getFlagsDAO();
        this.loggedInMID = loggedInMID;
        this.loggingAction = new EventLoggingAction(factory);
    }
    
    /**
     * getViewableObstetricsRecordsByMID returns a list of obstetrics record beans for past obstetrics care
     * @return The list of obstetrics records.
     * @throws ITrustException When there is a bad user.
     */
    public List<ObstetricsRecordBean> getViewableObstetricsRecordsByMID(long mid) throws ITrustException{
        List<ObstetricsRecordBean> result;
        result = obstetricsDAO.getObstetricsRecordsByMID(mid);
        return result;
    }
    
    /**
     * getViewableObstetricsRecordsByMID returns a list of obstetrics record beans for past obstetrics care
     * @return The list of obstetrics records.
     * @throws ITrustException When there is a bad user.
     */
    public List<ObstetricsRecordBean> getViewableObstetricsRecordsByMIDType(long mid, PregnancyStatus type)
    		throws ITrustException{
        List<ObstetricsRecordBean> result;
        result = obstetricsDAO.getObstetricsRecordsByMIDPregStatus(mid, type.toString());
        return result;
    }
    
    /**
     * Retrieves a ObstetricsRecordBean 
     * 
     * @return ObstetricsRecordBean
     * @throws ITrustException
     */
    public ObstetricsRecordBean getViewableObstetricsRecords(long oid) throws ITrustException {
        ObstetricsRecordBean record = obstetricsDAO.getObstetricsRecord(oid);
        if (record != null && record.getPregnancyStatus().equals(PregnancyStatus.Initial)) {
        	loggingAction.logEvent(TransactionType.parse(6301), loggedInMID, 
					record.getMid(), "Initial Obstetrics Record " +  oid + " viewed by " + loggedInMID);
        }
        else if (record != null && record.getPregnancyStatus().equals(PregnancyStatus.Office)) {
        	loggingAction.logEvent(TransactionType.parse(6401), loggedInMID, 
					record.getMid(), "Obstetrics Office Visit " +  oid + " viewed by " + loggedInMID);
        }
        return record;
    }
    
    public FlagsBean getFlagForRecord(ObstetricsRecordBean p, FlagValue v) throws ITrustException {
    	FlagsBean bean = new FlagsBean();
    	bean.setMid(p.getMid());
    	bean.setPregId(p.getPregId());
    	bean.setFlagValue(v);
    	return flagsDAO.getFlag(bean);
    }
}