package edu.ncsu.csc.itrust.beans;

import edu.ncsu.csc.itrust.enums.Role;
import java.io.Serializable;
import java.util.List;

/**
 * A bean for storing data about a hospital employee.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class PersonnelBean implements Serializable {
	private static final long serialVersionUID = 6575544592646001050L;
	
	private long MID = 0;
	private long AMID = 0;
	private String roleString;
	private String firstName = "";
	private String lastName = "";
	private String password = "";
	private String confirmPassword = "";
	private String securityQuestion = "";
	private String securityAnswer = "";
	private String streetAddress1 = "";
	private String streetAddress2 = "";
	private String city = "";
	private String state = "";
	private String zip = "";
	private String phone = "";
	private String email = "";
	private String specialty = "";

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getAMID() {
		return AMID;
	}

	public void setAMID(long amid) {
		AMID = amid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	public long getMID() {
		return MID;
	}

	public void setMID(long mid) {
		MID = mid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoleString() {
		return roleString;
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getSpecialty() {
		return specialty;
	}
	
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	public int getIndexIn(List<PersonnelBean> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).MID == this.MID) return i;
		}
		return -1;
	}

	public void setRoleString(String role) {
		this.roleString = role;
	}

	public Role getRole() {
		return Role.parse(roleString);
	}

	public void setRole(Role role) {		
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null || getClass() != o.getClass())
			return false;
		return this.MID == ((PersonnelBean) o).MID;
	}
	
	public int hashCode() {
		assert false : "hashCode not designed";
		return 0;
	}

}
