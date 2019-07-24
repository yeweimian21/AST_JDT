package edu.ncsu.csc.itrust.action;

/**
 * Edits the designated personnel Used by admin/editPersonnel.jsp, staff/editMyDemographics.jsp,
 * editPersonnel.jsp
 */
public class ViewHelperAction {
	
	/**
	 * calculateColor
	 * @param primaryColor primaryColor
	 * @param secondaryColor secondaryColor
	 * @param ratio ratio
	 * @return string
	 */
	public static String calculateColor(String primaryColor, String secondaryColor, double ratio) {
		double primeRed = Integer.parseInt(primaryColor.substring(0, 2), 16);
		double primeGreen = Integer.parseInt(primaryColor.substring(2, 4), 16);
		double primeBlue = Integer.parseInt(primaryColor.substring(4, 6), 16);
		
		double secondRed = Integer.parseInt(secondaryColor.substring(0, 2), 16);
		double secondGreen = Integer.parseInt(secondaryColor.substring(2, 4), 16);
		double secondBlue = Integer.parseInt(secondaryColor.substring(4, 6), 16);
		
		int newRed = (int) (secondRed*ratio + primeRed*(1-ratio));
		int newGreen = (int) (secondGreen*ratio + primeGreen*(1-ratio));
		int newBlue = (int) (secondBlue*ratio + primeBlue*(1-ratio));
		
		return String.format("%06X", (newRed << 16) + (newGreen << 8) + newBlue);
	}
}
