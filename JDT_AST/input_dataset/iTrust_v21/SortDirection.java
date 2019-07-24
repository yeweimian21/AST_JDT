package edu.ncsu.csc.itrust.enums;

/**
 * Sorting direction.  Used to dynamically build SQL queries.
 */
public enum SortDirection {
	
	ASCENDING("ASC"),
	DESCENDING("DESC");
	
	private String dirString;

	SortDirection(String dirString) {
		this.dirString = dirString;
	}
	public String toString() {
		return dirString;
	}

	public static SortDirection parse(String str) {
		for (SortDirection sort : values()) {
			if (sort.dirString.toLowerCase().equals(str.toLowerCase()))
				return sort;
		}
		if (str.toLowerCase().equals("ascending")) {
			return ASCENDING;
		}
		if (str.toLowerCase().equals("descending")) {
			return DESCENDING;
		}
		throw new IllegalArgumentException("SortDirection " + str + " does not exist");
	}
}
