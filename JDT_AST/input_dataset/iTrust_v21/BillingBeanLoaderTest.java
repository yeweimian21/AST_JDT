package edu.ncsu.csc.itrust.unit.bean;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createControl;
import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.easymock.classextension.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc.itrust.beans.BillingBean;
import edu.ncsu.csc.itrust.beans.loaders.BillingBeanLoader;

public class BillingBeanLoaderTest {
	
	private IMocksControl ctrl;
	private ResultSet rs;
	private BillingBeanLoader load;

	@Before
	public void setUp() throws Exception {
		ctrl = createControl();
		rs = ctrl.createMock(ResultSet.class);
		load = new BillingBeanLoader();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadList() throws SQLException {
		List<BillingBean> l = load.loadList(rs);
		assertEquals(0, l.size());
		
	}

	@Test
	public void testLoadSingle() {

		try {
			//this just sets the value for a method call (kinda hard coding I assume)
			expect(rs.getInt("billID")).andReturn(2).once();
			expect(rs.getInt("appt_id")).andReturn(6).once();
			expect(rs.getLong("PatientID")).andReturn(311L).once();
			expect(rs.getLong("HCPID")).andReturn(90000001L).once();
			expect(rs.getInt("amt")).andReturn(40).once();
			expect(rs.getString("status")).andReturn("Unsubmitted").once();
			expect(rs.getString("ccHolderName")).andReturn("Sean Ford").once();
			expect(rs.getString("billingAddress")).andReturn("123 Fake Street").once();
			expect(rs.getString("ccType")).andReturn("Visa").once();
			expect(rs.getString("ccNumber")).andReturn("1111-1111-1111-1111").once();
			expect(rs.getString("cvv")).andReturn("111").once();
			expect(rs.getString("insHolderName")).andReturn("Sean Ford").once();
			expect(rs.getString("insID")).andReturn("123456").once();
			expect(rs.getString("insProviderName")).andReturn("Blue Cross").once();
			expect(rs.getString("insAddress1")).andReturn("123 Fake St.").once();
			expect(rs.getString("insAddress2")).andReturn("123 Faker St.").once();
			expect(rs.getString("insCity")).andReturn("Raleigh").once();
			expect(rs.getString("insState")).andReturn("NC").once();
			expect(rs.getString("insZip")).andReturn("27606").once();
			expect(rs.getString("insPhone")).andReturn("555-555-5555").once();
			expect(rs.getInt("submissions")).andReturn(1).once();
			expect(rs.getDate("billTimeS")).andReturn(new java.sql.Date( new Date(0).getTime() ));
			expect(rs.getTimestamp("subTime")).andReturn(new Timestamp( new Date(0).getTime() ));
			expect(rs.getBoolean("isInsurance")).andReturn(true);
			ctrl.replay();
	
			BillingBean r = load.loadSingle(rs);
			assertEquals(2, r.getBillID());
			assertEquals(6, r.getApptID());
			assertEquals(311, r.getPatient());
			assertEquals(90000001, r.getHcp());
			assertEquals(40, r.getAmt());
			assertEquals("Unsubmitted", r.getStatus());
			assertEquals("Sean Ford", r.getCcHolderName());
			assertEquals("123 Fake Street", r.getBillingAddress());
			assertEquals("Visa", r.getCcType());
			assertEquals("1111-1111-1111-1111", r.getCcNumber());
			assertEquals("111", r.getCvv());
			assertEquals("Sean Ford", r.getInsHolderName());
			assertEquals("123456", r.getInsID());
			assertEquals("Blue Cross", r.getInsProviderName());
			assertEquals("123 Fake St.", r.getInsAddress1());
			assertEquals("123 Faker St.", r.getInsAddress2());
			assertEquals("Raleigh", r.getInsCity());
			assertEquals("NC", r.getInsState());
			assertEquals("27606", r.getInsZip());
			assertEquals("555-555-5555", r.getInsPhone());
			assertEquals(1, r.getSubmissions());
			assertTrue(r.isInsurance());
			assertEquals(new Timestamp(0).getTime(), r.getBillTime().getTime());
			assertEquals(new Timestamp(0).getTime(), r.getSubTime().getTime());
		} catch (SQLException e) {
			//TODO
		}
	}

	@Test
	public void testLoadParameters() {
		try{
			load.loadParameters(null, null);
			fail();
		} catch (Exception e){
			
		}
	}

}
