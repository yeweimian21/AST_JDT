package edu.ncsu.csc.itrust.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import edu.ncsu.csc.itrust.CSVParser;
import edu.ncsu.csc.itrust.RandomPassword;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.enums.Role;
import edu.ncsu.csc.itrust.exception.AddPatientFileException;
import edu.ncsu.csc.itrust.exception.CSVFormatException;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.validate.AddPatientValidator;
import edu.ncsu.csc.itrust.validate.PatientValidator;

/**
 * Used for Upload Patient File page (uploadPatientFile.jsp).
 */
public class AddPatientFileAction {
	
	/**
	 * Holds the accumulated list of errors from the CSVParser and this class
	 */
	private ErrorList errors;
	/**
	 * Holds the CSV header from the CSVParser
	 */
	private ArrayList<String> CSVHeader;
	/**
	 * Holds the CSV data from the CSVParser
	 */
	private ArrayList<ArrayList<String>> CSVData;
	/**
	 * Holds the list of PatientBeans for passing back to the UI
	 */
	private ArrayList<PatientBean> patients=new ArrayList<PatientBean>();
	
	/**
	 * List of fields required to be in the CSV
	 */
	private String[] requiredFields={"firstName", "lastName", "email"};
	/**
	 * List of valid fields which can be included in the CSV
	 */
	private String[] validFields={"streetAddress1", "streetAddress2", "city", "state", "zip", "phone",
								"motherMID", "fatherMID", "creditCardType", "creditCardNumber"};
	
	/**
	 * Array to map the required field lists above to the uploaded CSV header list (which may be in any order)
	 */
	private Integer requiredFieldsMapping[] = new Integer[3];
	/**
	 * Array to map the valid field lists above to the uploaded CSV header list (which may be in any order)
	 */
	private Integer validFieldsMapping[] = new Integer[13];
	
	/**
	 * PatientDAO used to add patients to the DB
	 */
	private PatientDAO patientDAO;
	/**
	 * AuthDAO to provide authorization for the patient actions
	 */
	private AuthDAO authDAO;
	/**
	 * MID of the HCP performing the request
	 */
	private long loggedInMID;
	

	/**
	 * Accepts the DAO factory and the CSV stream from the view and parses it.
	 * 
	 * @param factory The DAO factory
	 * @param loggedInMID The MID of the HCP
	 * @param CSVStream The CSV stream uploaded by the user
	 * @throws CSVFormatException
	 */
	public AddPatientFileAction(InputStream CSVStream, DAOFactory factory, long loggedInMID) throws CSVFormatException, AddPatientFileException {
		if(factory!=null){
			this.patientDAO = factory.getPatientDAO();
			this.loggedInMID = loggedInMID;
			this.authDAO = factory.getAuthDAO();
		}
		CSVParser parser = new CSVParser(CSVStream);
		CSVHeader = parser.getHeader();
		CSVData = parser.getData();
		errors = parser.getErrors();
		buildMappings(CSVHeader);
		try{
			createPatients();
		}catch(DBException e){
			throw new AddPatientFileException("Database error while adding new patients!");
		}
	}
	
	/**
	 * Gets the patient list
	 * 
	 * @return ArrayList<PatientBean> The patients from the parsed file
	 */
	public ArrayList<PatientBean> getPatients(){
		return patients;
	}
	
	/**
	 * Gets the error list
	 * 
	 * @return ErrorList All errors encountered while parsing
	 */
	public ErrorList getErrors(){
		return errors;
	}
	
	/**
	 * Builds the mappings between the local arrays and the CSV file
	 * Also checks for missing required, duplicate, and invalid fields
	 * 
	 * @param CSVHeader
	 * @throws AddPatientFileExceptionTest
	 */
	private void buildMappings(ArrayList<String> CSVHeader) throws AddPatientFileException{
		boolean valid;
		for(int i=0; i<CSVHeader.size(); i++){
			valid=false;
			for(int j=0; j<requiredFields.length; j++){
				if(CSVHeader.get(i).equalsIgnoreCase(requiredFields[j])){
					if(requiredFieldsMapping[j]==null){
						valid=true;
						requiredFieldsMapping[j]=i;
					}else{
						throw new AddPatientFileException("Duplicate field \""+CSVHeader.get(i)+"\"!");
					}
				}
			}
			for(int j=0; j<validFields.length; j++){
				if(CSVHeader.get(i).equalsIgnoreCase(validFields[j])){
					if(validFieldsMapping[j]==null){
						valid=true;
						validFieldsMapping[j]=i;
					}else{
						throw new AddPatientFileException("Duplicate field \""+CSVHeader.get(i)+"\"!");
					}
				}
			}
			if(valid == false){
				throw new AddPatientFileException("Field \""+CSVHeader.get(i)+"\" is invalid!");
			}
		}
		for(int i=0; i<requiredFieldsMapping.length; i++){
			if(requiredFieldsMapping[i]==null){
				throw new AddPatientFileException("Required field \""+requiredFields[i]+"\" is missing!");
			}
		}
	}
	
	/**
	 * Creates the patients and adds them to the DB
	 * 
	 * @throws DBException
	 * @throws AddPatientFileExceptionTest
	 */
	private void createPatients() throws DBException, AddPatientFileException{
		for(int i=0; i<CSVData.size(); i++){
			PatientBean temp=new PatientBean();
			
			temp.setFirstName(CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("firstName")]));
			temp.setLastName(CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("lastName")]));
			temp.setEmail(CSVData.get(i).get(requiredFieldsMapping[Arrays.asList(requiredFields).indexOf("email")]));
			
			try{
				temp.setStreetAddress1(CSVData.get(i).get(validFieldsMapping[Arrays.asList(validFields).indexOf("streetAddress1")]));
			}catch(NullPointerException e) {
				//TODO
			}
			try{
				temp.setStreetAddress2(CSVData.get(i).get(validFieldsMapping[Arrays.asList(validFields).indexOf("streetAddress2")]));
			}catch(NullPointerException e) {
				//TODO
			}
			try{
				temp.setCity(CSVData.get(i).get(validFieldsMapping[Arrays.asList(validFields).indexOf("city")]));
			}catch(NullPointerException e) {
				//TODO
			}
			try{
				temp.setState(CSVData.get(i).get(validFieldsMapping[Arrays.asList(validFields).indexOf("state")]));
			}catch(NullPointerException e) {
				//TODO
			}
			try{
				temp.setZip(CSVData.get(i).get(validFieldsMapping[Arrays.asList(validFields).indexOf("zip")]));
			}catch(NullPointerException e) {
				//TODO
			}
			try{
				temp.setPhone(CSVData.get(i).get(validFieldsMapping[Arrays.asList(validFields).indexOf("phone")]));
			}catch(NullPointerException e) {
				//TODO
			}
			try{
				temp.setMotherMID(CSVData.get(i).get(validFieldsMapping[Arrays.asList(validFields).indexOf("motherMID")]));
			}catch(NullPointerException e) {
				//TODO
			}
			try{
				temp.setFatherMID(CSVData.get(i).get(validFieldsMapping[Arrays.asList(validFields).indexOf("fatherMID")]));
			}catch(NullPointerException e) {
				//TODO
			}
			try{
				temp.setCreditCardType(CSVData.get(i).get(validFieldsMapping[Arrays.asList(validFields).indexOf("creditCardType")]));
			}catch(NullPointerException e){
				//TODO
			}
			try{
				temp.setCreditCardNumber(CSVData.get(i).get(validFieldsMapping[Arrays.asList(validFields).indexOf("creditCardNumber")]));
			}catch(NullPointerException e) {
				//TODO
			}
			
			try{
				new AddPatientValidator().validate(temp);
				new PatientValidator().validate(temp);
				if(patientDAO!=null){
					long newMID = patientDAO.addEmptyPatient();
					temp.setMID(newMID);
					String pwd = authDAO.addUser(newMID, Role.PATIENT, RandomPassword.getRandomPassword());
					temp.setPassword(pwd);
					patientDAO.editPatient(temp, loggedInMID);
				}
				patients.add(temp);
			}catch(FormValidationException e){
				for(int j=0; j<e.getErrorList().size(); j++){
					System.out.println(e.getErrorList().get(j));
				}
				errors.addIfNotNull("Input validation failed for patient \""+temp.getFirstName()+" "+temp.getLastName()+"\"!");
			}
		}
	}
}
