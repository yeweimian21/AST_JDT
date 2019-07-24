package edu.ncsu.csc.itrust.unit.bean;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.easymock.classextension.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.LabelBean;
import edu.ncsu.csc.itrust.beans.loaders.LabelLoader;
import junit.framework.TestCase;

public class LabelLoaderTest extends TestCase
{
	private IMocksControl ctrl;
	private ResultSet rs;
	private LabelLoader load;

	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new LabelLoader();
	}

	@Test
	public void testLoadList() throws SQLException {
		List<LabelBean> l = load.loadList(rs);
		assertEquals(0, l.size());
		
	}

	@Test
	public void testLoadSingle() {

		try {
			//this just sets the value for a method call (kinda hard coding I assume)
			expect(rs.getLong("EntryID")).andReturn((long) 1);
			expect(rs.getLong("PatientID")).andReturn((long) 1);
			expect(rs.getString("LabelName")).andReturn("Test");
			expect(rs.getString("LabelColor")).andReturn("#FFFFFF");
			ctrl.replay();
	
			LabelBean r = load.loadSingle(rs);
			assertEquals((long) 1, r.getEntryID());
			assertEquals((long) 1, r.getPatientID());
			assertEquals("Test", r.getLabelName());
			assertEquals("#FFFFFF", r.getLabelColor());			
		} catch (SQLException e) {
			//TODO
		}
	}

	
}
