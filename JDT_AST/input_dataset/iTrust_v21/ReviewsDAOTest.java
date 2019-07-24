package edu.ncsu.csc.itrust.unit.dao.reviews;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.ReviewsBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.ReviewsDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.EvilDAOFactory;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

public class ReviewsDAOTest {
    /** ReviewsDAO instance for testing */
    private ReviewsDAO rdao, evil2;
    private EvilDAOFactory evil;
    /** test instance of beans for testing */
    private ReviewsBean beanValid, beanInvalid;
    private static final long PID1 = 9000000000L, PID2 = 9000000003L, MID = 106L;
	private static final Date REVDATE = new java.sql.Date(new Date().getTime());


	
	/**
	 * Provide setup for the rest of the tests; initialize all globals.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.uc61reviews();
		DAOFactory factory = TestDAOFactory.getTestInstance();
		rdao = new ReviewsDAO(factory);
		evil = new EvilDAOFactory(0);
		evil2= new ReviewsDAO(evil);
		beanValid = new ReviewsBean();
		beanValid.setMID(MID);
		beanValid.setPID(PID1);
		beanValid.setDescriptiveReview("oh well");
		beanValid.setDateOfReview(REVDATE);
		beanValid.setRating(2);
		
		beanInvalid = new ReviewsBean();
		beanInvalid.setMID(MID);
		beanInvalid.setPID(PID2);
		beanInvalid.setDateOfReview(REVDATE);
		beanInvalid.setDescriptiveReview("oh well");
		beanInvalid.setRating(2);
	
	}
	

	@After
	public void tearDown() throws Exception {
	
	}

	/**
	 * Tests adding a review to the reviewsTable by comparing sizes of 
	 * lists.
	 * Pre-condition: assuming the ReviewsDAO.getAllReviews() works AND
	 * setup is run between ea test.
	 */
	@Test
	public final void testAddReviewValid() {
	    try {
	    	//sanity check for the initial size of the entries in the reviews table
	    	List<ReviewsBean> l = rdao.getAllReviews();
	    	assertEquals(6, l.size());
	    	//try adding a valid bean
	    	assertTrue(rdao.addReview(beanValid));
			//check the number of reviews table entries went up by 1
			l = rdao.getAllReviews();
			assertEquals(7, l.size());
			
			assertEquals(false, rdao.addReview(null));
					
		
		} catch (DBException e) {
			fail();
		}
	    
	}
	
	
	/**
	 * Tests getting reviews from a current database for a 
	 * given HCP.
	 * @throws DBException 
	 */
	@Test(expected=DBException.class)
	public final void testGetReviews() throws DBException {
			List<ReviewsBean> l = rdao.getAllReviews(PID1);
			//test getting reviews for Kelly Doctor
			assertEquals(4, l.size());
			
			//test getting reviews for Gandolf Stormcloud
			l=rdao.getReviews(PID2);
			assertEquals(2, l.size());
			
			evil2.getAllReviews();
			
	}
	
	/**
	 * Tests that ALL in table reviews are retrieved when called.
	 */
	@Test
	public final void testGetAllReviews(){
		List<ReviewsBean> l;
		try {
		
			l = rdao.getAllReviews(PID1);
			assertEquals(4, l.size());
			l.clear(); 
			assertEquals(0, l.size());
			
			
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * Tests that overall rating averages are returned for ea
	 * HCP.
	 */
	@Test
	public final void testGetTotalAverageRating() {
	    /** expected average rating for Kelly Doctor */
		final double PID1AVG = 2.5;
		/** expected average rating for Gandolf Stomcloud */
		final double PID2AVG = 4.5;
	    try {
	    	assertTrue(PID2AVG != rdao.getTotalAverageRating(PID1));
			assertTrue(PID1AVG == rdao.getTotalAverageRating(PID1));
			assertTrue(PID2AVG == rdao.getTotalAverageRating(PID2));
			
			
		} catch (DBException e) {
			fail();
		}
	}

	/**
	 * Tests that a Patient can ONLY review a Physician they have previously seen.
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 */
	@Test 
	public final void testIsRateable() throws FileNotFoundException, SQLException, IOException {
		try {
			//Tests when it should not work, 
			//for Patient Tom Nook and HCP Gandolf Stormcloud
			assertFalse(rdao.isRateable(MID, PID2));
			//Tests when it should be added, 
			//for Patient Tom Nook and HCP Kelly Doctor
			assertTrue(rdao.isRateable(MID, PID1));
			
		} catch (DBException e) {
			fail();
		}
	}
	
	/**
	 * Tests adding an invalid review
	 */
	@Test
	public void testAddInvalidReview() {
		try {
			beanInvalid.setMID(-1);
			rdao.addReview(beanInvalid);
			fail("Should have thrown an exception");
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the "
					+ "log in the console for stacktrace", e.getMessage());
		}
	}
	
	/**
	 * Tests getting reviews with invalid dao
	 */
	@Test
	public void testGetReviewsEvilDAO() {
		try {
			evil2.getReviews(1);
			fail("Should have thrown an exception");
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the "
					+ "log in the console for stacktrace", e.getMessage());
		}
	}
	
	/**
	 * Tests getting all of the reviews with invalid dao
	 */
	@Test
	public void testGetAllReviewsEvilDAO() {
		try {
			evil2.getAllReviews(1);
			fail("Should have thrown an exception");
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the "
					+ "log in the console for stacktrace", e.getMessage());
		}
	}
	
	/**
	 * Tests getting the total average rating with invalid dao
	 */
	@Test
	public void testAverageRatingEvilDAO() {
		try {
			evil2.getTotalAverageRating(1);
			fail("Should have thrown an exception");
		} catch (DBException e) {
			assertEquals("A database exception has occurred. Please see the "
					+ "log in the console for stacktrace", e.getMessage());
		}
	}

}
