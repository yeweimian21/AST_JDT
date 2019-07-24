package edu.ncsu.csc.itrust.unit.bean;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.easymock.classextension.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.ZipCodeBean;
import edu.ncsu.csc.itrust.beans.loaders.ZipCodeLoader;
import junit.framework.TestCase;

public class ZipCodeLoaderTest extends TestCase
{
	private IMocksControl ctrl;
	private ResultSet rs;
	private ZipCodeLoader load;

	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new ZipCodeLoader();
	}

	@Test
	public void testLoadList() throws SQLException {
		List<ZipCodeBean> l = load.loadList(rs);
		assertEquals(0, l.size());
		
	}

	@Test
	public void testLoadSingle() {

		try {
			//this just sets the value for a method call (kinda hard coding I assume)
			expect(rs.getString("zip")).andReturn("27587");
			expect(rs.getString("state")).andReturn("NC");
			expect(rs.getString("latitude")).andReturn("65.5");
			expect(rs.getString("longitude")).andReturn("30");
			expect(rs.getString("city")).andReturn("Wake Forest");
			expect(rs.getString("full_state")).andReturn("North Carolina");
			ctrl.replay();
	
			ZipCodeBean r = load.loadSingle(rs);
			assertEquals("27587", r.getZip());
			assertEquals(edu.ncsu.csc.itrust.enums.State.NC, r.getState());
			assertEquals("65.5", r.getLatitude());
			assertEquals("30", r.getLongitude());
			assertEquals("Wake Forest", r.getCity());
			assertEquals("North Carolina", r.getFullState());
			
		} catch (SQLException e) {
			//TODO
		}
	}

	
}
