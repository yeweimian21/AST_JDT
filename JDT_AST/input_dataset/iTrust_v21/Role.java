package edu.ncsu.csc.itrust.enums;

/**
 * The iTrust user roles: Patient, ER, HCP, UAP, LT, PHA, Administrator and Tester.
 * Consult the requirements for the contextual meanings of these individual choices.
 */
public enum Role {
	/**PATIENT*/
	PATIENT("patient", "Patients", 0L), 
	/**ER*/
	ER("er", "Personnel", 9L), 
	/**HCP*/
	HCP("hcp", "Personnel", 9L), 
	/**UAP*/
	UAP("uap", "Personnel", 8L),
	/**LT*/
	LT("lt", "Personnel", 5L),
	/**ADMIN*/
	ADMIN("admin", "Personnel", 0L),
	/**PHA*/
	PHA("pha", "Personnel", 7L),
	/**TESTER*/
	TESTER("tester", "", 0L);
	
	private String userRolesString;
	private String dbTable;
	private long midFirstDigit;

	/**
	 * Role
	 * @param userRolesString userRolesString
	 * @param dbTable dbTable
	 * @param midFirstDigit midFirstDigit
	 */
	Role(String userRolesString, String dbTable, long midFirstDigit) {
		this.userRolesString = userRolesString;
		this.dbTable = dbTable;
		this.midFirstDigit = midFirstDigit;
	}

	/**
	 * getDBTable
	 * @return dbTable
	 */
	public String getDBTable() {
		return dbTable;
	}

	/**
	 * getUserRolesString
	 * @return userRolesString
	 */
	public String getUserRolesString() {
		return userRolesString;
	}

	/**
	 * getMidFirstDigit
	 * @return midFirstDigit
	 */
	public long getMidFirstDigit() {
		return midFirstDigit;
	}

	/**
	 * parse
	 * @param str str
	 * @return role
	 */
	public static Role parse(String str) {
		for (Role role : values()) {
			if (role.userRolesString.toLowerCase().equals(str.toLowerCase()))
				return role;
		}
		throw new IllegalArgumentException("Role " + str + " does not exist");
	}
}
