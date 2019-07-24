package edu.ncsu.csc.itrust.unit.bean;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.easymock.classextension.IMocksControl;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.WardRoomBean;

import edu.ncsu.csc.itrust.beans.loaders.WardRoomBeanLoader;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createControl;

/**
 * WardRoomBeanLoader
 */
@SuppressWarnings("unused")
public class WardRoomBeanLoaderTest extends TestCase{
	
	private IMocksControl ctrl;
	java.util.List<WardRoomBean> list = new ArrayList<WardRoomBean>();
	ResultSet rs;
	WardRoomBeanLoader wbl = new WardRoomBeanLoader();
	
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
			expect(rs.getLong("RoomID")).andReturn(1L).once();
			expect(rs.getLong("OccupiedBy")).andReturn(1L).once();
			expect(rs.getLong("InWard")).andReturn(1L).once();
			expect(rs.getString("roomName")).andReturn("CleanRoom").once();
			expect(rs.getString("Status")).andReturn("Clean").once();
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
		} catch(Exception e) {
			//TODO
		}
		
		assertTrue(true);
	}

}
