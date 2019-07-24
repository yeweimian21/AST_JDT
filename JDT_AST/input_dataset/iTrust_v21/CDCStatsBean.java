package edu.ncsu.csc.itrust.beans;

/**
 * A bean for storing CDC statistics data.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters 
 * to create these easily)
 */
public class CDCStatsBean {
	private int sex;
	private float age;
	private double L;
	private double M;
	private double S;
	
	/**
	 * Constructs a CDCStatsBean with all null values for the fields
	 */
	public CDCStatsBean() {
	}
	
	/**
	 * Gets the gender associated with the cdc statistic
	 * @return the gender associated with the cdc statistic. 1 stands for male. 2 stands for female.
	 */
	public int getSex() {
		return sex;
	}
	
	/**
	 * Sets the gender for the cdc statistic
	 * @param sex integer for the gender of the cdc statistic. 1 for male. 2 for female.
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	/**
	 * Gets the age in months
	 * @return the age of the cdc statistic in months
	 */
	public float getAge() {
		return age;
	}
	
	/**
	 * Sets the age in months for the cdc statistic
	 * @param age the age in months as a float
	 */
	public void setAge(float age) {
		this.age = age;
	}
	
	/**
	 * Gets the Box-Cox transformation or L value
	 * @return the Box-Cox transformation or L value 
	 */
	public double getL() {
		return L;
	}
	
	/**
	 * Sets the Box-Cox transformation or L value
	 * @return L double for the Box-Cox transformation or L value
	 */
	public void setL(double L) {
		this.L = L;
	}
	
	/**
	 * Gets the median or the M value
	 * @return the median
	 */
	public double getM() {
		return M;
	}
	
	/**
	 * Sets the median or the M value
	 * @param M double value for the median
	 */
	public void setM(double M) {
		this.M = M;
	}
	
	/**
	 * Gets the generalized coefficient of variation or S value
	 * @return the generalized coefficient of variation or S value
	 */
	public double getS() {
		return S;
	}
	
	/**
	 * Sets the generalized coefficient of variation or S Value
	 * @param S double for the generalized coefficient of variation
	 */
	public void setS(double S) {
		this.S = S;
	}
	
}
