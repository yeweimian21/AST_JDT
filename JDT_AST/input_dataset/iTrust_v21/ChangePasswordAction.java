package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.exception.ITrustException;


/**
 * Manages resetting the password Used by resetPassword.jsp
 * 
 * 
 */
public class ChangePasswordAction {

	private AuthDAO authDAO;

	/**
	 * Set up defaults
	 * @param factory The DAOFactory used to create the DAOs used in this action.
	 */
	public ChangePasswordAction(DAOFactory factory) {
		this.authDAO = factory.getAuthDAO();
	}

	/**
	 * Changes the password for the given mid
	 * 
	 * @param mid of the user to have their password reset
	 * @param oldPass their old password
	 * @param newPass their desired password
	 * @param confirmPass their desired password again
	 * @return status message
	 * @throws FormValidationException
	 * @throws DBException
	 * @throws ITrustException
	 */
	public String changePassword(long mid, String oldPass, String newPass, String confirmPass) throws FormValidationException, DBException,
	ITrustException {
		String containsLetter = "[a-zA-Z0-9]*[a-zA-Z]+[a-zA-Z0-9]*";
		String containsNumber = "[a-zA-Z0-9]*[0-9]+[a-zA-Z0-9]*";
		String fiveAlphanumeric = "[a-zA-Z0-9]{5,20}";
		
		//Make sure old password is valid
		if(!authDAO.authenticatePassword(mid, oldPass)) {
			return "Invalid password change submission.";
		}
		
		//Make sure new passwords match
		if (!newPass.equals(confirmPass)) {
			return "Invalid password change submission.";
		}	
			
		//Validate password. Must contain a letter, contain a number, and be a string of 5-20 alphanumeric characters
		if(newPass.matches(containsLetter) && newPass.matches(containsNumber) && newPass.matches(fiveAlphanumeric)){
			//Change the password
			authDAO.resetPassword(mid, newPass);
			return "Password Changed.";
		} else {
			return "Invalid password change submission.";
		} 
	}
	
	/**
	 * Generate a new more secure hashed and randomly salted password based on the users 
	 * new desired password passed in as a String.
	 * @param newpas String, desired new plain text password
	 * @return 
	 
	private String genPassword(String newpas){
		String pas = "";
		SecureRandom rand = new SecureRandom();
		
		//TODO change the capacity in the byte array to match that of the original password
		byte newbie[] = new byte[32];
		sr.
	    return pas;
	}
	*/
	//TODO: note, increasing password security will mean changing also how passwords are stored and retrieved to also include the salts for that hash
	
	//generate a new salt for each time a user account is made 
}
