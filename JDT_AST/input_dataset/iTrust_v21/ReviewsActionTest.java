package edu.ncsu.csc.itrust.unit.action;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.action.ReviewsAction;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.beans.ReviewsBean;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.unit.testutils.TestDAOFactory;

/**
 * Verify the effectiveness of the ReviewsAction class.
 */
public class ReviewsActionTest {
	/** constant id's for testing */
    private static final long PID1 = 9000000000L, PID2 = 9000000003L, MID = 106L;
    private static ReviewsAction act;
    private static ReviewsBean beanValid, beanInvalid;
    private static final String REVDESC = "I don't know what just happened.";
    private static final Date REVDATE = new java.sql.Date(new Date().getTime());
    private static final double AVGRATING = 2.5;
    private static final int LISTLEN = 4;
    
    /**
     * Set up for the rest of the tests
     * @throws Exception
     */
	@Before
	public void setUp() throws Exception {
		act = new ReviewsAction(TestDAOFactory.getTestInstance(), MID);
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.standardData();
		gen.uc61reviews();
		beanValid = new ReviewsBean();
		beanValid.setMID(MID);
		beanValid.setPID(PID1);
		beanValid.setDescriptiveReview(REVDESC);
		beanValid.setDateOfReview(REVDATE);
		beanValid.setRating(2);
		
		beanInvalid = new ReviewsBean();
		beanInvalid.setMID(MID);
		beanInvalid.setPID(PID2);
		beanInvalid.setDescriptiveReview(REVDESC);
		beanInvalid.setDateOfReview(REVDATE);
		beanInvalid.setRating(2);
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests that when a ReviewsBean is added it truly is valid.
	 */
	@Test
	public final void testAddReviewValid() {
		try {
			assertTrue(act.addReview(beanValid));
		} catch (DBException e) {
			fail();
		}
	}
	
	/**
	 * Test to make sure that you are able to get a correct ReviewsBean.
	 */
	@Test
	public final void testGetReviews() {
		//check to make sure that the list size is the same 
		try {
			List<ReviewsBean> l = act.getReviews(PID1);
			assertEquals(LISTLEN, l.size());
		} catch (DBException e) {
			fail();
		}
	}

	/**
	 * Test to make sure that you are able to get a correct Rating for average rating.
	 */
	@Test
	public final void testGetAverageRating() {
		try {
			assertTrue(AVGRATING == act.getAverageRating(PID1));
		} catch (DBException e) {
			fail();
		}
	}
	
	/**
	 * Test to ensure you can get a physician
	 */
	@Test
	public void testGetPhysician() {
		try {
			PersonnelBean physician = act.getPhysician(9000000000L);
			assertEquals("Kelly", physician.getFirstName());
			assertEquals("Doctor", physician.getLastName());
			assertEquals("hcp", physician.getRoleString());
		} catch (DBException e) {
			fail("Physician exists");
		}
		
	}
	
	/**
	 * Tests that a patient can rate his physician
	 */
	@Test
	public void testIsAbleToRate() {
		try {
			assertTrue(act.isAbleToRate(PID1));
		} catch (DBException d) {
			fail("DBException");
		}
	}
	

}
