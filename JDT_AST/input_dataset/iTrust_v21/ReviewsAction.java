package edu.ncsu.csc.itrust.action;

import java.util.List;

import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.ReviewsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO;
import edu.ncsu.csc.itrust.dao.mysql.ReviewsDAO;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * This class forms the action for the HCP Reviewing system, each category 
 * rated on a scale from 1 (lowest) to 5 (highest), and providing an overall rating 
 * for the HCP.  
 */
public class ReviewsAction {
  private ReviewsDAO dao;
  private PersonnelDAO personnelDAO;
  private long loggedInMID;
  
  public ReviewsAction(DAOFactory factory, long mid){
       	  this.dao = factory.getReviewsDAO();
       	  this.personnelDAO = factory.getPersonnelDAO();
       	  this.loggedInMID = mid;
  }
  
  /**
   * Add a review based on the input params for input of a bean.  
   * @return true if added and false otherwise
   */
  public boolean addReview(ReviewsBean b) throws DBException{
        return dao.addReview(b);
  }
  
  /**
   * Method that returns physician based on a mid.
   * @param mid of physician
   * @return
   * @throws DBException
   */
  public PersonnelBean getPhysician(long mid) throws DBException
  {
	  return personnelDAO.getPersonnel(mid);
  }
  
  /**
   * Get all reviews for a given HCP (ie pid input param), 
   * return as a Java ArrayList
   * @param pid HCP under review's ID
   * @return java.utils.ArrayList of all reviews for the HCP
   * @throws DBException 
   */
  public List<ReviewsBean> getReviews(long pid) throws DBException{
	return dao.getReviews(pid);
  }
  
  /**
   * Checks whether a patient can post a review for a physician.
   * @param pid
   * @return
   * @throws DBException 
   */
  public boolean isAbleToRate(long pid) throws DBException
  {
	  
	 return dao.isRateable(loggedInMID, pid);
  }
  
  /**
   * Get total average rating for a given HCP.
   * @param pid Long ID of the HCP under review
   * @return average int "rating" for the information 
   * @throws DBException 
   */
  public double getAverageRating(long pid) throws DBException{
	return dao.getTotalAverageRating(pid);
  }
}
