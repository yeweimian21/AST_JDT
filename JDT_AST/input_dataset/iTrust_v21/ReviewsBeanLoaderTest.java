package edu.ncsu.csc.itrust.unit.bean;

import static org.easymock.classextension.EasyMock.createControl;
import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.ResultSet;



import java.sql.SQLException;
import java.util.List;

import static org.easymock.EasyMock.expect;
import org.easymock.classextension.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.ReviewsBean;
import edu.ncsu.csc.itrust.beans.loaders.ReviewsBeanLoader;

/**
 * Test of the ReviewsBeanLoader, for creating/building a ReviewsBean 
 */
public class ReviewsBeanLoaderTest {
    private IMocksControl ctrl;
    private ResultSet rs;
    private ReviewsBeanLoader load;
    private ReviewsBean bean;
    private static final long TPID = 90000001, TMID = 2;
    private static final int TRATING = 5;
    private static final Date TREVDATE = new java.sql.Date(new Date(0).getTime());
    private static final String TDESCREV = "This doctor was fantifirrific!";
    
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new ReviewsBeanLoader();
		bean = new ReviewsBean();
	}

	@After
	public void tearDown() throws Exception {
		//unused currently
	}

	
	/**
	 * Test the ReviewsBeanLoader's loadList() method
	 * @throws SQLException 
	 */
	@Test
	public void testLoadList() throws SQLException 
	{
		List<ReviewsBean> l = load.loadList(rs);
		assertEquals(0, l.size());
	
	  
   }

	/**
	 * Test the ReviewsBeanLoader loadSingle() method.
	 */
	@Test
	public void testLoadSingle() {
		try {
		      //set-up the ResultSet 
			  expect(rs.getLong("mid")).andReturn(TMID).once();
			  expect(rs.getLong("pid")).andReturn(TPID).once();
			  expect(rs.getInt("rating")).andReturn(TRATING).once();
			  expect(rs.getDate("reviewdate")).andReturn(TREVDATE).once();
			  expect(rs.getString("descriptivereview")).andReturn(TDESCREV).once();
			  expect(rs.getString("title")).andReturn("Bad Service").once();
			  ctrl.replay();
			  //load the bean 
			  bean = load.loadSingle(rs);
			  //check all fields in bean are loaded properly
			  assertEquals(TMID, bean.getMID());
			  assertEquals(TPID, bean.getPID());
			  assertEquals(TRATING, bean.getRating());
			  assertEquals(TREVDATE, bean.getDateOfReview());
			  assertEquals(TDESCREV, bean.getDescriptiveReview());
			  assertEquals("Bad Service", bean.getTitle());
		   } catch (SQLException e) {
			fail();
		  }
	}

	/**
	 * Tests the ReviewsBeanLoader loadParameter() method.
	 */
	@Test
	public void testLoadParameters() {
		try{
			
			load.loadParameters(null, null);
			fail();
		} catch(Exception e) {
		}
	}

}
