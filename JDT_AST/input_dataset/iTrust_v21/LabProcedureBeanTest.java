package edu.ncsu.csc.itrust.unit.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;

public class LabProcedureBeanTest  extends TestCase{
	LabProcedureBean l;
	@Override
	protected void setUp() throws Exception {
		l = new LabProcedureBean();
		l.setPid(0000000001);
		l.setProcedureID(10);
		l.setLoinc("12345-6");
		l.statusInTransit();
		l.setCommentary("Their blood is purple and orange.");
		l.setResults("Please call us for your results.");
		l.setOvID(10023);
		Date date = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse("03/28/2008 12:00");
		l.setTimestamp(new java.sql.Timestamp(date.getTime()));
		l.allow();
	}
	
	public void testBaseCaseBean() throws Exception {
		assertEquals(0000000001, l.getPid());
		assertEquals(10, l.getProcedureID());
		assertEquals("12345-6", l.getLoinc());
		assertEquals(LabProcedureBean.In_Transit, l.getStatus());
		assertEquals("Their blood is purple and orange.", l.getCommentary());
		assertEquals("Please call us for your results.", l.getResults());
		assertEquals(10023, l.getOvID());
		Date date = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse("03/28/2008 12:00");
		assertEquals(date.getTime(), l.getTimestamp().getTime());
		assertEquals(LabProcedureBean.Allow, l.getRights());
	}
	
	public void testViewedByPatient() throws Exception {
		// In order to set the LP as viewed by patient, the status must be completed.
		LabProcedureBean lp = new LabProcedureBean();
		lp.setStatus(LabProcedureBean.In_Transit);
		assertEquals(false, lp.isViewedByPatient());
		lp.setViewedByPatient(true);
		assertEquals(false, lp.isViewedByPatient());
		lp.setStatus(LabProcedureBean.Completed);
		lp.setViewedByPatient(true);
		assertEquals(true, lp.isViewedByPatient());
	}
	
	/**
	 * testGetNumericalResultUnit
	 * @throws Exception
	 */
	public void testGetNumericalResultUnit() throws Exception {
		
		LabProcedureBean lp = new LabProcedureBean();
		assertEquals("", lp.getNumericalResultUnit());
		
		lp.setNumericalResultUnit("grams");
		assertEquals("grams", lp.getNumericalResultUnit());
		
		lp.setNumericalResultUnit("ml");
		assertEquals("ml", lp.getNumericalResultUnit());
		
	}
	
	/**
	 * testGetNumericalResultAsDouble
	 * @throws Exception
	 */
	public void testGetNumericalResultAsDouble() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		assertEquals("", lp.getNumericalResult());
		assertEquals(Double.NaN, lp.getNumericalResultAsDouble(), 1e-7);
		
		lp.setNumericalResult("0.0");
		assertEquals("0.0", lp.getNumericalResult());
		assertEquals(0.0, lp.getNumericalResultAsDouble(), 1e-7);
		
		lp.setNumericalResult("0.1");
		assertEquals("0.1", lp.getNumericalResult());
		assertEquals(0.1, lp.getNumericalResultAsDouble(), 1e-7);
		
		lp.setNumericalResult("-5.1");
		assertEquals("-5.1", lp.getNumericalResult());
		assertEquals(-5.1, lp.getNumericalResultAsDouble(), 1e-7);
	}
	
	/**
	 * testGetUpperBoundAsDouble
	 * @throws Exception
	 */
	public void testGetUpperBoundAsDouble() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		assertEquals("", lp.getUpperBound());
		assertEquals(Double.NaN, lp.getUpperBoundAsDouble(), 1e-7);
		
		lp.setUpperBound("0.0");
		assertEquals("0.0", lp.getUpperBound());
		assertEquals(0.0, lp.getUpperBoundAsDouble(), 1e-7);
		
		lp.setUpperBound("0.1");
		assertEquals("0.1", lp.getUpperBound());
		assertEquals(0.1, lp.getUpperBoundAsDouble(), 1e-7);
		
		lp.setUpperBound("-5.1");
		assertEquals("-5.1", lp.getUpperBound());
		assertEquals(-5.1, lp.getUpperBoundAsDouble(), 1e-7);
	}
	
	/**
	 * testGetLowerBoundAsDouble
	 * @throws Exception
	 */
	public void testGetLowerBoundAsDouble() throws Exception {
		LabProcedureBean lp = new LabProcedureBean();
		assertEquals("", lp.getLowerBound());
		assertEquals(Double.NaN, lp.getLowerBoundAsDouble(), 1e-7);
		
		lp.setLowerBound("0.0");
		assertEquals("0.0", lp.getLowerBound());
		assertEquals(0.0, lp.getLowerBoundAsDouble(), 1e-7);
		
		lp.setLowerBound("0.1");
		assertEquals("0.1", lp.getLowerBound());
		assertEquals(0.1, lp.getLowerBoundAsDouble(), 1e-7);
		
		lp.setLowerBound("-5.1");
		assertEquals("-5.1", lp.getLowerBound());
		assertEquals(-5.1, lp.getLowerBoundAsDouble(), 1e-7);
	}
}
