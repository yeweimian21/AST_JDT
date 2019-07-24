package edu.ncsu.csc.itrust.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.CreditCardValidator;

import edu.ncsu.csc.itrust.beans.BillingBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.BillingDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.ITrustException;

/**
 * This class aids payBill.jsp in paying a bill for a user. This mostly will
 * interact with the BillingDAO and verify user input.
 */
public class PayBillAction {
	/**billingDAO just access the database when I need to.*/
	private BillingDAO billingDAO;
	/**myBill is the bill that we are paying.*/
	private BillingBean myBill;
	private PatientDAO patientRetriever;
	private OfficeVisitDAO visitAccess;
	
	/**The length of a credit card number*/
	public static final int CC_NUMBER_LEN = 16;
	
	/**
	 * PayBillAction is the constructor and it just sets the instance variables.
	 * @param factory The object that makes the BillingDAO.
	 * @param bID The ID of the bill we are paying.
	 */
	public PayBillAction(DAOFactory factory, long bID){
		this.billingDAO = factory.getBillingDAO();
		this.patientRetriever = factory.getPatientDAO();
		this.visitAccess = factory.getOfficeVisitDAO();
		try {
			this.myBill = billingDAO.getBillId(bID);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * getBill returns the billing bean to make jsp stuff easy.
	 * @return the bill we a handling.
	 */
	public BillingBean getBill(){
		return this.myBill;
	}
	
	/**
	 * getPatient gets the patient of the office visit.
	 * @return The name of the patient.
	 */
	public String getPatient(){
		String result = null;
		try {
			result = patientRetriever.getName(myBill.getPatient());
		} catch (ITrustException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * getOVBean returns the office visit bean
	 * associated with this bill.
	 * @return The office visit for this bill.
	 */
	public OfficeVisitBean getOVBean(){
		try {
			return visitAccess.getOfficeVisit(myBill.getApptID());
		} catch (DBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Pay bill with CreditCard validates input, and pays the bill if it can.
	 * @param ccNum The Credit Card number.
	 * @param ccHolder The Credit Card holder.
	 * @param ccType The Credit Card type.
	 * @param billAddress The bill address.
	 * @param cvv The cvv.
	 * @throws Exception It just throws an exception that contains the error message.
	 */
	public String payBillWithCC(String ccNum, String ccHolder, String ccType,
			String billAddress, String cvv) throws Exception{
		Pattern checkCvv = Pattern.compile("[0-9]{3,4}");
		
		if(ccType == null || ccType.equals("null"))
			return ("The field for Credit Card Type must be filled.");
		if(ccType.length() > 20)
			return ("The field for the Credit Card Type must be 20 or shorter.");
		myBill.setCcType(ccType);

		if(ccNum == null || ccNum.equals("null"))
			return ("The field for Credit Card Number must be filled.");
		
		int type = CreditCardValidator.NONE;
		if(ccType.equals("MasterCard")){
			type = CreditCardValidator.MASTERCARD;
		} else if(ccType.equals("Visa")){
			type = CreditCardValidator.VISA;
		} else if(ccType.equals("AmericanExpress")){
			type = CreditCardValidator.AMEX;
		} else if(ccType.equals("Discover")){
			type= CreditCardValidator.DISCOVER; 
		}
		CreditCardValidator c = new CreditCardValidator(type);
		if(ccNum.length() > CC_NUMBER_LEN || !c.isValid(ccNum))
			return ("Invalid Credit Card number.");
		myBill.setCcNumber(ccNum);
		
		if(ccHolder == null || ccHolder.equals("null"))
			return ("The field for Credit Card Holder must be filled.");
		if(ccHolder.length() > 30)
			return ("The Credit Card Holder must be 30 characters or shorter.");
		myBill.setCcHolderName(ccHolder);
		
		if(billAddress == null || billAddress.equals("null"))
			return ("The field for Billing Address must be filled.");
		if(billAddress.length() > 120)
			return ("The fields for Billing Address must be 120 characters or shorter.");
		myBill.setBillingAddress(billAddress);
		
		
		if(cvv == null || cvv.equals("null"))
			return ("The field for CVV must be filled.");

		Matcher verify = checkCvv.matcher(cvv);
		if(!verify.matches())
			return ("Invalid CVV code.");
		myBill.setCvv(cvv);
		myBill.setStatus("Submitted");
		myBill.setInsurance(false);
		billingDAO.editBill(myBill);
		return null;
	}
	
	/**
	 * Pay bill with insurance just pays the bill with insurance information.
	 * @param insHolder The holder of the insurance.
	 * @param insProvider The provider of the insurance.
	 * @param insID The insurance policy id.
	 * @param insAdd1 The insurance address.
	 * @param insAdd2 The insurance address.
	 * @param insCity The insurance city.
	 * @param insState The insurance state.
	 * @param insZip The insurance zip code.
	 * @param insPhone The insurance phone number.
	 * @throws Exception The exception contains the error message.
	 */
	public String payBillWithIns(String insHolder, String insProvider, String insID,
			String insAdd1, String insAdd2, String insCity,
			String insState, String insZip, String insPhone) throws Exception{
		Pattern checkID = Pattern.compile("[0-9a-zA-Z]+");
		Pattern checkPhone = Pattern.compile("[0-9]{3}-[0-9]{3}-[0-9]{4}");

		if(insHolder == null || insHolder.equals("null"))
			return ("The field for Insurance Holder must be filled.");
		myBill.setInsHolderName(insHolder);
		
		if(insProvider == null || insProvider.equals("null"))
			return ("The field for Insurance Provider must be filled.");
		if(insProvider.length() > 20)
			return ("The Insurance Provider must be 20 characters or shorter.");
		myBill.setInsProviderName(insProvider);
		
		if(insID == null || insID.equals("null"))
			return ("The field for Insurance Policy ID must be filled.");
		Matcher verify = checkID.matcher(insID);
		if(!verify.matches())
			return ("Insurance IDs must consist of alphanumeric characters.");
		myBill.setInsID(insID);
		
		if(insAdd1 == null || insAdd1.equals("null"))
			return ("The field for Insurance Address 1 must be filled.");
		if(insAdd1.length() > 20)
			return ("The field for Insurnace Address 1 must be 20 characters or shorter.");
		myBill.setInsAddress1(insAdd1);
		
		if(insAdd2 == null || insAdd2.equals("null"))
			return ("The field for Insurance Address 2 must be filled.");
		if(insAdd2.length() > 20)
			return ("The field for Insurnace Address 2 must 20 characters or shorter.");
		myBill.setInsAddress2(insAdd2);
		
		if(insCity == null || insCity.equals("null"))
			return ("The field for Insurance City must be filled.");
		if(insCity.length() > 20)
			return ("The field for Insurance City must be 20 characters or shorter.");
		myBill.setInsCity(insCity);
		
		if(insState == null || insState.equals("null"))
			return ("The field for Insurance State must be filled.");
		if(insState.length() > 2)
			return ("The field for Insurance State must be 2 characters.");
		myBill.setInsState(insState);
		
		if(insZip == null || insZip.equals("null"))
			return ("The field for Insurance Zip must be filled.");
		myBill.setInsZip(insZip);
		
		if(insPhone == null || insPhone.equals("null"))
			return ("The field for Insurance Phone must be filled.");
		verify = checkPhone.matcher(insPhone);
		if(!verify.matches())
			return ("Insurance Phone Number must match the form \"XXX-XXX-XXXX\"");
		myBill.setInsPhone(insPhone);
		myBill.setStatus("Pending");
		myBill.setSubTime(new Timestamp(new Date().getTime()));
		myBill.setSubmissions(myBill.getSubmissions() + 1);
		myBill.setInsurance(true);
		billingDAO.editBill(myBill);
		
		return null;
	}

}
