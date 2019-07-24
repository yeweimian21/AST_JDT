package edu.ncsu.csc.itrust.validate;

import edu.ncsu.csc.itrust.action.EditPatientAction;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.exception.ErrorList;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import org.apache.commons.validator.CreditCardValidator;
import java.util.Date;

/**
 * Validates a patient bean, from {@link EditPatientAction}
 * 
 *  
 * 
 */
public class PatientValidator extends BeanValidator<PatientBean> {
	/**
	 * The default constructor.
	 */
	public PatientValidator() {
	}

	@Override
	public void validate(PatientBean p) throws FormValidationException{
		ErrorList errorList = new ErrorList();
		
		//First name, last name, and email are all required
		errorList.addIfNotNull(checkFormat("First name", p.getFirstName(), ValidationFormat.NAME, false));
		errorList.addIfNotNull(checkFormat("Last name", p.getLastName(), ValidationFormat.NAME, false));
		errorList.addIfNotNull(checkFormat("Email", p.getEmail(), ValidationFormat.EMAIL, false));

		if(!p.getDateOfBirthStr().isEmpty()){
			errorList.addIfNotNull(checkFormat("Date of Birth", p.getDateOfBirthStr(), ValidationFormat.DATE, false));
		}
		
		if(!p.getDateOfDeathStr().isEmpty()){
			errorList.addIfNotNull(checkFormat("Date of Death", p.getDateOfDeathStr(), ValidationFormat.DATE, true));
		}
		
		try {
			if ("".equals(p.getDateOfDeathStr()) || p.getDateOfDeath() == null) {
				if (!p.getCauseOfDeath().equals("")){
					errorList.addIfNotNull("Cause of Death cannot be specified without Date of Death!");
				}
			} else {
				if (p.getDateOfDeath().before(p.getDateOfBirth()))
					errorList.addIfNotNull("Death date cannot be before birth date!");
				if( p.getDateOfDeath().after(new Date())){
					errorList.addIfNotNull("Death date cannot be in the future!");
				}
			}
		
			if( p.getDateOfBirth().after(new Date())){
				errorList.addIfNotNull("Birth date cannot be in the future!");
			}
		} catch (NullPointerException e) {
			// ignore this
		}

		
		if(!p.getCauseOfDeath().isEmpty()){
			boolean deathCauseNull = (null == p.getDateOfDeathStr() || p.getDateOfDeathStr().equals(""));
			errorList.addIfNotNull(checkFormat("Cause of Death", p.getCauseOfDeath(), ValidationFormat.ICD9CM, deathCauseNull));
		}
		
		if(!p.getStreetAddress1().isEmpty()){
			errorList.addIfNotNull(checkFormat("Street Address 1", p.getStreetAddress1(), ValidationFormat.ADDRESS, false));
		}
		
		if(!p.getStreetAddress2().isEmpty()){
			errorList.addIfNotNull(checkFormat("Street Address 2", p.getStreetAddress2(), ValidationFormat.ADDRESS, true));
		}
		
		if(!p.getCity().isEmpty()){
			errorList.addIfNotNull(checkFormat("City", p.getCity(), ValidationFormat.CITY, false));
		}
		
		if(!p.getState().isEmpty()){
			errorList.addIfNotNull(checkFormat("State", p.getState(), ValidationFormat.STATE, false));
		}
		
		if(!p.getZip().isEmpty()){
			errorList.addIfNotNull(checkFormat("Zip Code", p.getZip(), ValidationFormat.ZIPCODE, false));
		}
		
		if(!p.getPhone().isEmpty()){
			errorList.addIfNotNull(checkFormat("Phone Number", p.getPhone(), ValidationFormat.PHONE_NUMBER, false));
		}
		
		if(!p.getEmergencyName().isEmpty()){
			errorList.addIfNotNull(checkFormat("Emergency Contact Name", p.getEmergencyName(), ValidationFormat.NAME, false));
		}
		
		if(!p.getEmergencyPhone().isEmpty()){
			errorList.addIfNotNull(checkFormat("Emergency Contact Phone", p.getEmergencyPhone(), ValidationFormat.PHONE_NUMBER, false));
		}
		
		if(!p.getIcName().isEmpty()){
			errorList.addIfNotNull(checkFormat("Insurance Company Name", p.getIcName(), ValidationFormat.NAME, false));
		}
		
		if(!p.getIcAddress1().isEmpty()){
			errorList.addIfNotNull(checkFormat("Insurance Company Address 1", p.getIcAddress1(), ValidationFormat.ADDRESS, false));
		}
		
		if(!p.getIcAddress2().isEmpty()){
			errorList.addIfNotNull(checkFormat("Insurance Company Address 2", p.getIcAddress2(), ValidationFormat.ADDRESS, true));
		}
		
		if(!p.getIcCity().isEmpty()){
			errorList.addIfNotNull(checkFormat("Insurance Company City", p.getIcCity(), ValidationFormat.CITY, false));
		}
		
		if(!p.getIcState().isEmpty()){
			errorList.addIfNotNull(checkFormat("Insurance Company State", p.getIcState(), ValidationFormat.STATE, false));
		}
		
		if(!p.getIcZip().isEmpty()){
			errorList.addIfNotNull(checkFormat("Insurance Company Zip", p.getIcZip(), ValidationFormat.ZIPCODE, false));
		}
		
		if(!p.getIcPhone().isEmpty()){
			errorList.addIfNotNull(checkFormat("Insurance Company Phone", p.getIcPhone(), ValidationFormat.PHONE_NUMBER, false));
		}
		
		if(!p.getIcID().isEmpty()){
			errorList.addIfNotNull(checkFormat("Insurance Company ID", p.getIcID(), ValidationFormat.INSURANCE_ID, false));
		}
		
		if(!p.getMotherMID().isEmpty()){
			errorList.addIfNotNull(checkFormat("Mother MID", p.getMotherMID(), ValidationFormat.NPMID, true));
		}
		
		if(!p.getFatherMID().isEmpty()){
			errorList.addIfNotNull(checkFormat("Father MID", p.getFatherMID(), ValidationFormat.NPMID, true));
		}
		
		if(!p.getTopicalNotes().isEmpty()){
			errorList.addIfNotNull(checkFormat("Topical Notes", p.getTopicalNotes(), ValidationFormat.NOTES, true));
		}
		
		
		/* This block was added for Theme 5 by Tyler Arehart */
		
		if(!p.getCreditCardNumber().isEmpty()){
			if (!(p.getCreditCardNumber().equals("") && p.getCreditCardType().equals(""))) {
		
				String s = null;
				CreditCardValidator c;
				int type = -1;
				if (p.getCreditCardType().equals("VISA")) type = CreditCardValidator.VISA;
				if (p.getCreditCardType().equals("MASTERCARD")) type = CreditCardValidator.MASTERCARD;
				if (p.getCreditCardType().equals("DISCOVER")) type = CreditCardValidator.DISCOVER;
				if (p.getCreditCardType().equals("AMEX")) type = CreditCardValidator.AMEX;
			
				if (type != -1) {	
					c = new CreditCardValidator(type);
					if (!c.isValid(p.getCreditCardNumber())) {
						s = "Credit Card Number";
					}
				}
				else {
					s = "Credit Card Type";
				}
				errorList.addIfNotNull(s);
			}
		}
		
		if(!p.getDirectionsToHome().isEmpty()){
			errorList.addIfNotNull(checkFormat("Directions to Home", p.getDirectionsToHome(), ValidationFormat.COMMENTS, true));
		}
		
		if(!p.getReligion().isEmpty()){
			errorList.addIfNotNull(checkFormat("Religion", p.getReligion(), ValidationFormat.NAME, true));
		}
		
		if(!p.getLanguage().isEmpty()){
			errorList.addIfNotNull(checkFormat("Language", p.getLanguage(), ValidationFormat.NAME, true));
		}
		
		if(!p.getSpiritualPractices().isEmpty()){
			errorList.addIfNotNull(checkFormat("Spiritual Practices", p.getSpiritualPractices(), ValidationFormat.COMMENTS, true));
		}
		
		if(!p.getAlternateName().isEmpty()){
			errorList.addIfNotNull(checkFormat("Alternate Name", p.getAlternateName(), ValidationFormat.NAME, true));
		}
		
		if (errorList.hasErrors())
			throw new FormValidationException(errorList);
	}
}
