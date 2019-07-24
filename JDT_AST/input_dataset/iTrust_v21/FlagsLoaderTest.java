package edu.ncsu.csc.itrust.unit.bean;

import static org.easymock.classextension.EasyMock.createControl;
import static org.junit.Assert.*;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;

import static org.easymock.EasyMock.expect;

import org.easymock.classextension.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.FlagsBean;
import edu.ncsu.csc.itrust.beans.loaders.FlagsLoader;
import edu.ncsu.csc.itrust.enums.FlagValue;

/**
 * Test of the ReviewsBeanLoader, for creating/building a ReviewsBean 
 */
public class FlagsLoaderTest {
    private IMocksControl ctrl;
    private ResultSet rs;
    private FlagsLoader load;
    private FlagsBean bean;
    private static final long TFID = 2, TMID = 4;
    private static final String fVal = "Twins";
        
	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new FlagsLoader();
		bean = new FlagsBean();
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
		List<FlagsBean> l = load.loadList(rs);
		assertEquals(0, l.size());	  
   }

	/**
	 * Test the ReviewsBeanLoader loadSingle() method.
	 */
	@Test
	public void testLoadSingle() {
		try {
		      //set-up the ResultSet 
			  expect(rs.getLong("FID")).andReturn(TFID).once();
			  expect(rs.getLong("MID")).andReturn(TMID).once();
			  expect(rs.getString("flagValue")).andReturn(fVal).once();
			  ctrl.replay();
			  //load the bean 
			  bean = load.loadSingle(rs);
			  //check all fields in bean are loaded properly
			  assertEquals(TMID, bean.getMid());
			  assertEquals(TFID, bean.getFid());
			  assertEquals(FlagValue.valueOf(fVal), bean.getFlagValue());
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
