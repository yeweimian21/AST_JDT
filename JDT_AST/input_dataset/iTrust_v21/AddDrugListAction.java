package edu.ncsu.csc.itrust.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.StringTokenizer;

import edu.ncsu.csc.itrust.beans.MedicationBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.NDCodesDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class AddDrugListAction {
	 private DrugStrategy strategy;
	 private DAOFactory factory;
	 private EventLoggingAction loggingAction;
	 private long loggedInMID;
	 
	 public AddDrugListAction (DrugStrategy uploadStrategy, DAOFactory factory, EventLoggingAction loggingAction, long loggedInMID) {
		 this.strategy = uploadStrategy;
		 this.factory = factory;
		 this.loggingAction = loggingAction;
		 this.loggedInMID = loggedInMID;
	 }
	 
	/**
	 * Loads the given file input stream into the drug database.
	 * @param fileContent
	 * @throws IOException
	 */
	public void loadFile(InputStream fileContent) throws IOException, DBException {
		strategy.loadFile(fileContent, factory, loggingAction, loggedInMID);
	}
	
	public interface DrugStrategy {
		void loadFile(InputStream fileContent, DAOFactory factory, EventLoggingAction loggingAction, long loggedInMID) throws IOException, DBException;
	}
	

	public static class SkipDuplicateDrugStrategy implements DrugStrategy {
		public void loadFile(InputStream fileContent, DAOFactory factory, EventLoggingAction loggingAction, long loggedInMID) throws IOException, DBException {
			NDCodesDAO ndcodesDAO = factory.getNDCodesDAO();
	        Scanner fileScanner = new Scanner(fileContent, "UTF-8");
			while(fileScanner.hasNextLine()) {
				String ndCodeWithDash;
				MedicationBean bean = new MedicationBean();
				StringTokenizer tok = new StringTokenizer(fileScanner.nextLine(), "\t");
				ndCodeWithDash = tok.nextToken();
				String parts[] = ndCodeWithDash.split("-");
				
				//Skip drug type field
				tok.nextToken();
				
				bean.setNDCode(parts[0].concat(parts[1]));
				bean.setDescription(tok.nextToken());
				try {
					ndcodesDAO.addNDCode(bean);
					loggingAction.logEvent(TransactionType.DRUG_CODE_ADD, loggedInMID, 0,
							"" + bean.getNDCode() + bean.getDescription());
				} catch (ITrustException e) {
					//We just want to skip duplicate-entries. Let it pass.
					e.printStackTrace();
				}
			}
			fileScanner.close();
		}
	}
	
	
	public static class OverwriteDuplicateDrugStrategy implements DrugStrategy {
		public void loadFile(InputStream fileContent, DAOFactory factory, EventLoggingAction loggingAction, long loggedInMID) throws IOException, DBException {
			NDCodesDAO ndcodesDAO = factory.getNDCodesDAO();
	        Scanner fileScanner = new Scanner(fileContent, "UTF-8");
			while(fileScanner.hasNextLine()) {
				String ndCodeWithDash;
				MedicationBean bean = new MedicationBean();
				StringTokenizer tok = new StringTokenizer(fileScanner.nextLine(), "\t");
				ndCodeWithDash = tok.nextToken();
				String parts[] = ndCodeWithDash.split("-");
				
				//Skip drug type field
				tok.nextToken();
				
				bean.setNDCode(parts[0].concat(parts[1]));
				bean.setDescription(tok.nextToken());
				try {
					ndcodesDAO.addNDCode(bean);
					loggingAction.logEvent(TransactionType.DRUG_CODE_ADD, loggedInMID, 0,
							"" + bean.getNDCode() + " - " + bean.getDescription());
				} catch (ITrustException e) {
					//Overwrite duplicate entries
					ndcodesDAO.updateCode(bean);
					loggingAction.logEvent(TransactionType.DRUG_CODE_EDIT, loggedInMID, 0,
							"" + bean.getNDCode() + " - " + bean.getDescription());
				}
			}
			fileScanner.close();
		}
	}

}

