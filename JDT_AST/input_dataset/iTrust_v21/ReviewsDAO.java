package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.ReviewsBean;
import edu.ncsu.csc.itrust.beans.loaders.ReviewsBeanLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * Provide a way to handle database queries for the reviews table,
 * store HCP review information.
 */
public class ReviewsDAO {
  private DAOFactory factory;
  private ReviewsBeanLoader loader;
  
  /**
   * The basic constructor for the ReviewsDAO object.
   * @param factory DAOFactory entry param
   */
  public ReviewsDAO(DAOFactory factory){
	  this.factory = factory;
	  loader = new ReviewsBeanLoader();
  }

  
  
  /**
   * Based on the information from the ReviewsBean, add 
   * a review for an HCP (given by HCP in the bean) into the reviews table.
   * @param bean containing the rating 
   * @return true if the review was added successfully and false otherwise
   * @throws DBException 
   */
  public boolean addReview(ReviewsBean bean) throws DBException{
	    if(bean == null){
	    	return false;
	    }
	    Connection conn = null;
		PreparedStatement ps = null;
		int numInserted = 0;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO reviews(mid, pid, reviewdate, descriptivereview, rating, title)  VALUES (?,?,(CURRENT_TIMESTAMP),?,?,?)");
			ps = loader.loadParameters(ps, bean);
			numInserted = ps.executeUpdate();
			return (numInserted > 0);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
  }
  /**
   * Get a list of all reviews for a given HCP with id matching 
   * input param pid.
   * @param pid ID of the HCP whose reviews to return
   * @return list of all reviews, null if there aren't any
   * @throws DBException 
   */
  public List<ReviewsBean> getAllReviews(long pid) throws DBException{
	    Connection conn = null;
		PreparedStatement ps = null;
		List<ReviewsBean> records = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM reviews WHERE pid=?");
			ps.setLong(1, pid);
			ResultSet rs;
			rs = ps.executeQuery();
			records = loader.loadList(rs);
			rs.close();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return records;
  }
  
  /**
   * Get a list of all reviews for a given HCP with id matching 
   * input param pid.
   * @param pid ID of the HCP whose reviews to return
   * @return list of all reviews, null if there aren't any
   * @throws DBException 
   */
  public List<ReviewsBean> getAllReviews() throws DBException{
	    Connection conn = null;
		PreparedStatement ps = null;
		List<ReviewsBean> records = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM reviews");
			ResultSet rs;
			rs = ps.executeQuery();
			records = loader.loadList(rs);
			rs.close();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return records;
  }
  
  /**
   * Get a list of all reviews for a given HCP with id matching 
   * input param pid.
   * @param pid ID of the HCP whose reviews to return
   * @return list of all reviews, null if there aren't any
   * @throws DBException 
   */
  public List<ReviewsBean> getReviews(long pid) throws DBException{
	    Connection conn = null;
		PreparedStatement ps = null;
		List<ReviewsBean> records = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM reviews WHERE pid=?");
			ps.setLong(1, pid);
			ResultSet rs;
			rs = ps.executeQuery();
			records = loader.loadList(rs);
			rs.close();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
		return records;
  }
  
  /**
   * Get average rating for the given HCP overall, all categories and all reviews.
   * @param pid HCP with ratings wanted 
   * @return the average rating of all the overall ratings 
   * @throws DBException 
   */
  public double getTotalAverageRating(long pid) throws DBException{
	    double avgrating = 0;
	    Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT AVG(rating) FROM reviews WHERE pid=?");
			ps.setLong(1, pid);
			rs = ps.executeQuery();
			if(rs.next()){
				avgrating = (rs.getDouble("AVG(rating)"));
			}
			rs.close();
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					throw new DBException(e);
				}
		}
	  return avgrating;
  }
  
  
  /**
   * This checks the appointment table in the DB to see if the there is an appointment entry
   * for the input mid(patient_id) and pid(doctor_id) params.  
   * It returns true if the patient has seen the given doctor, false otherwise.
   * @param mid Patient ID
   * @param pid HCP ID
   * @return true if the patient has had an appointment with the HCP, false otherwise
   * @throws DBException
   */
  public boolean isRateable(long mid, long pid) throws DBException{
	    Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isRateable = false;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM appointment WHERE patient_id =? AND doctor_id=?");
			ps.setLong(1, mid);
			ps.setLong(2, pid);
			rs = ps.executeQuery();
			isRateable = rs.next();
		} catch (SQLException e){
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					throw new DBException(e);
				}
		}
		if(!isRateable){
			try {
				conn = factory.getConnection();
				ps = conn.prepareStatement("SELECT * FROM officevisits WHERE PatientID=? AND HCPID=?");
				ps.setLong(1, mid);
				ps.setLong(2, pid);
				rs = ps.executeQuery();
				isRateable =  rs.next();
				rs.close();
			} catch (SQLException e) {
				throw new DBException(e);
			} finally {
				DBUtil.closeConnection(conn, ps);
				if(rs != null)
					try {
						rs.close();
					} catch (SQLException e) {
						throw new DBException(e);
					}
			}
		}
	  return isRateable;
  }
  
  
}
