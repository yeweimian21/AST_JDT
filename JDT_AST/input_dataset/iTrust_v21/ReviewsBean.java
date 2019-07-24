package edu.ncsu.csc.itrust.beans;

import java.util.Date;

/**
 * A bean for storing data about a review of a physician.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters
 * to create these easily)
 */
public class ReviewsBean 
{
	private long MID;
	private Date dateOfReview;
	private long PID;
	private int rating;
	private String descriptiveReview;
	private String title;
	
	/**
	 * Gets the MID of the reviewer.
	 * @return MID
	 */
	public long getMID() 
	{
		return MID;
	}
	
	/**
	 * Gets the title of the review.
	 * @return title
	 */
	public String getTitle() 
	{
		return title;
	}

	/**
	 * Sets the title of the review.
	 * @param title 
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Set the MID of the reviewer
	 * @param MID The MID of the reviwer.
	 */
	public void setMID(long mID) 
	{
		MID = mID;
	}
	
	/**
	 * Gets the date that the review was made.
	 * @return Date
	 */
	public Date getDateOfReview() 
	{
		return new Date(dateOfReview.getTime());
	}
	
	/**
	 * Sets the date that the review was made.
	 * @param dateOfReview The date of the review.
	 */
	public void setDateOfReview(Date dateOfReview) 
	{
		this.dateOfReview = new Date(dateOfReview.getTime());
	}
	
	/**
	 * Gets the ID of the physician who is being reviewed. 
	 * @return
	 */
	public long getPID() 
	{
		return PID;
	}
	
	/**
	 * Sets the ID of the physician who is being reviewed. 
	 * @param PID
	 */
	public void setPID(long pID) 
	{
		PID = pID;
	}
	
	/**
	 * Gets the number of stars that were given to the phyician.
	 * @return Rating
	 */
	public int getRating() 
	{
		return rating;
	}
	
	/**
	 * Sets the rating associated with a review.
	 * @param rating String value 1-5.
	 */
	public void setRating(int rating) 
	{
		this.rating = rating;
	}
	
	/**
	 * Gets the descriptive review of the Physician.
	 * @return Descriptive Review
	 */
	public String getDescriptiveReview() 
	{
		return descriptiveReview;
	}
	
	/**
	 * Sets the descriptive review attribute.
	 * @param descriptiveReview
	 */
	public void setDescriptiveReview(String descriptiveReview) 
	{
		this.descriptiveReview = descriptiveReview;
	}
	
	
}
