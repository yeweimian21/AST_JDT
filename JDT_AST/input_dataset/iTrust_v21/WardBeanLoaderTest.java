package edu.ncsu.csc.itrust.unit.bean;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.easymock.classextension.IMocksControl;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.WardBean;
import edu.ncsu.csc.itrust.beans.loaders.WardBeanLoader;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createControl;

/**
 * WardBeanLoaderTest
 */
@SuppressWarnings("unused")
public class WardBeanLoaderTest extends TestCase{
	
	private IMocksControl ctrl;
	java.util.List<WardBean> list = new ArrayList<WardBean>();
	ResultSet rs;
	WardBeanLoader wbl = new WardBeanLoader();
	
	/**
	 * setUp
	 */
	protected void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
	}
	
	/**
	 * testLoadList
	 */
	@Test
	public void testLoadList() {
		try {
			list = wbl.loadList(rs);
		} catch (SQLException e) {
			//TODO
		}
		
		assertEquals(0, list.size());
	}
	
	/**
	 * testloadSingle
	 */
	public void testloadSingle(){
		try {
			expect(rs.getLong("WardID")).andReturn(1L).once();
			expect(rs.getString("RequiredSpecialty")).andReturn("specialty").once();
			expect(rs.getLong("InHospital")).andReturn(1L).once();
	
			ctrl.replay();
	
			wbl.loadSingle(rs);
		} catch (SQLException e) {
			//TODO
		}
	}
	
	/**
	 * testLoadParameters
	 */
	public void testLoadParameters(){
		try{
			wbl.loadParameters(null, null);
			fail();
		} catch(IllegalStateException e){
			//TODO
		}catch (SQLException e) {
			//TODO
		}
		
		assertTrue(true);
	}

}
