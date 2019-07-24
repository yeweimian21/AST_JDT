package edu.ncsu.csc.itrust.beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import edu.ncsu.csc.itrust.enums.BloodType;
import edu.ncsu.csc.itrust.enums.Ethnicity;
import edu.ncsu.csc.itrust.enums.Gender;

/**
 * A bean for storing data about a patient.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class PatientBean implements Serializable, Comparable<PatientBean> {
	private static final long serialVersionUID = -6474182977342257877L;
	
	private long MID = 0;
	private String firstName = "";
	private String lastName = "";
	private String email = "";
	private String securityQuestion = "";
	private String securityAnswer = "";
	private String password = "";
	private String confirmPassword = "";
	private String streetAddress1 = "";
	private String streetAddress2 = "";
	private String city = "";
	private String state = "AK";
	private String zip = "";
	private String phone = "";
	private String emergencyName = "";
	private String emergencyPhone = "";
	private String icName = "";
	private String icAddress1 = "";
	private String icAddress2 = "";
	private String icCity = "";
	private String icState = "AK";
	private String icZip = "";
	private String icPhone = "";
	private String icID = "";
	private String creditCardType = "";
	private String creditCardNumber = "";
	// Topical Health Information
	private String dateOfBirthStr = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
	private String dateOfDeathStr = "";
	private String causeOfDeath = "";
	private String motherMID = "0";
	private String fatherMID = "0";
	private BloodType bloodType = BloodType.NS;
	private Ethnicity ethnicity = Ethnicity.NotSpecified;
	private Gender gender = Gender.NotSpecified;
	private String topicalNotes = "";
	private String directionsToHome = "";
	private String religion = "";
	private String language = "";
	private String spiritualPractices = "";
	private String alternateName = "";
	private String dateOfDeactivationStr = "";


	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodTypeStr(String bloodType) {
		this.bloodType = BloodType.parse(bloodType);
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

	public String getCauseOfDeath() {
		return causeOfDeath;
	}

	public void setCauseOfDeath(String causeOfDeath) {
		this.causeOfDeath = causeOfDeath;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDateOfBirthStr() {
		return dateOfBirthStr;
	}

	public Date getDateOfBirth() {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(dateOfBirthStr);
		} catch (ParseException e) {
			
			return null;
		}
	}

	public Date getDateOfDeath() {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(dateOfDeathStr);
		} catch (ParseException e) {
			
			return null;
		}
	}

	public void setDateOfBirthStr(String dateOfBirthStr) {
		this.dateOfBirthStr = dateOfBirthStr;
	}

	public int getAge() {
		try {
			long ageInMs = System.currentTimeMillis()
					- new SimpleDateFormat("MM/dd/yyyy").parse(dateOfBirthStr).getTime();
			long age = ageInMs / (1000L * 60L * 60L * 24L * 365L);
			return (int) age;
		} catch (ParseException e) {
			
			return -1;
		}
	}
	
	public long getAgeInDays() {
		
		long age;
		long ageInMs;

		try {
			ageInMs = System.currentTimeMillis()
					- new SimpleDateFormat("MM/dd/yyyy").parse(dateOfBirthStr).getTime();
			age = ageInMs / (1000L * 60L * 60L * 24L);
	
		}
		catch (ParseException e) {
			
			return -1;
		}
		
		return age;
	}

	public long getAgeInWeeks() {
		
		long age;
		long ageInMs;

		try {
			ageInMs = System.currentTimeMillis()
					- new SimpleDateFormat("MM/dd/yyyy").parse(dateOfBirthStr).getTime();
			age = ageInMs / (1000L * 60L * 60L * 24L * 7L);
	
		}
		catch (ParseException e) {
			
			return -1;
		}
		
		return age;
	}
	
	public String getDateOfDeathStr() {
		return dateOfDeathStr;
	}

	public void setDateOfDeathStr(String dateOfDeathStr) {
		this.dateOfDeathStr = dateOfDeathStr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmergencyName() {
		return emergencyName;
	}

	public void setEmergencyName(String emergencyName) {
		this.emergencyName = emergencyName;
	}

	public Ethnicity getEthnicity() {
		return ethnicity;
	}

	public void setEthnicityStr(String ethnicity) {
		this.ethnicity = Ethnicity.parse(ethnicity);
	}

	public void setEthnicity(Ethnicity ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getFatherMID() {
		return fatherMID;
	}

	public void setFatherMID(String fatherMID) {
		this.fatherMID = fatherMID;
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGenderStr(String gender) {
		this.gender = Gender.parse(gender);
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getIcAddress1() {
		return icAddress1;
	}

	public void setIcAddress1(String icAddress1) {
		this.icAddress1 = icAddress1;
	}

	public String getIcAddress2() {
		return icAddress2;
	}

	public void setIcAddress2(String icAddress2) {
		this.icAddress2 = icAddress2;
	}

	// Composition of city, state, and zip
	public String getIcAddress3() {
		return getIcCity() + ", " + getIcState() + " " + getIcZip();
	}

	public String getIcCity() {
		return icCity;
	}

	public void setIcCity(String icCity) {
		this.icCity = icCity;
	}

	public String getIcID() {
		return icID;
	}

	public void setIcID(String icID) {
		this.icID = icID;
	}
	
	public String getCreditCardType() {
		return creditCardType;
	}
	
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getIcName() {
		return icName;
	}

	public void setIcName(String icName) {
		this.icName = icName;
	}

	public String getIcZip() {
		return icZip;
	}

	public void setIcZip(String icZip) {
		this.icZip = icZip;
	}

	public String getIcState() {
		return icState;
	}

	public void setIcState(String icState) {
		this.icState = icState;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getMID() {
		return MID;
	}

	public void setMID(long mid) {
		MID = mid;
	}

	public String getMotherMID() {
		return motherMID;
	}

	public void setMotherMID(String motherMID) {
		this.motherMID = motherMID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreetAddress1() {
		return streetAddress1;
	}

	public void setStreetAddress1(String streetAddress1) {
		this.streetAddress1 = streetAddress1;
	}

	public String getStreetAddress2() {
		return streetAddress2;
	}

	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}

	// Composition of the city, state, zip
	public String getStreetAddress3() {
		return getCity() + ", " + getState() + " " + getZip();
	}

	public String getTopicalNotes() {
		return topicalNotes;
	}

	public void setTopicalNotes(String topicalNotes) {
		this.topicalNotes = topicalNotes;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getEmergencyPhone() {
		return emergencyPhone;
	}

	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}

	public String getIcPhone() {
		return icPhone;
	}

	public void setIcPhone(String icPhone) {
		this.icPhone = icPhone;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDirectionsToHome() {
		return directionsToHome;
	}

	public void setDirectionsToHome(String directionsToHome) {
		this.directionsToHome = directionsToHome;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSpiritualPractices() {
		return spiritualPractices;
	}

	public void setSpiritualPractices(String spiritualPractices) {
		this.spiritualPractices = spiritualPractices;
	}

	public String getAlternateName() {
		return alternateName;
	}

	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}
	
	public String getDateOfDeactivationStr() {
		return dateOfDeactivationStr;
	}

	public void setDateOfDeactivationStr(String dateOfDeactivationStr) {
		this.dateOfDeactivationStr = dateOfDeactivationStr;
	}

	public int compareTo(PatientBean o) {
		return (int)(o.MID-this.MID);
	}
	
	
	public int equals(PatientBean o) {
		return (int)(o.MID-this.MID);
	}
	
	public int hashCode() {
		assert false : "hashCode not designed";
		return 42; // any arbitrary constant will do
	}
	
}
