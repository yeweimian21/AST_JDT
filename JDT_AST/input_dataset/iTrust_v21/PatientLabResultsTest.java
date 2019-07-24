package edu.ncsu.csc.itrust.unit.charts;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jfree.data.category.DefaultCategoryDataset;

import de.laures.cewolf.DatasetProduceException;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.LabProcedureBean;
import edu.ncsu.csc.itrust.charts.PatientLabResults;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;

public class PatientLabResultsTest extends TestCase {
	
	private PatientLabResults chart;
	TestDataGenerator gen = new TestDataGenerator();
	
	@Override
	protected void setUp() throws Exception {
		chart = new PatientLabResults();
		gen.clearAllTables();
		gen.standardData();
		gen.patientLabProcedures();
	}
	
	public void testProduceDataset()
	{
		String labProcedureName = "Microscopic Observation";
		Calendar mytime = Calendar.getInstance();
    	int quarter = (mytime.get(Calendar.MONTH)/3)+1;
    	
		List<LabProcedureBean> lpBeans = new LinkedList<LabProcedureBean>();
		LabProcedureBean lp = new LabProcedureBean();
		lp.setLoinc("10763-1");
		lp.setNumericalResult("73");
		lp.setTimestamp(new Timestamp(mytime.getTime().getTime()));
		lpBeans.add(lp);
		
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yy");
		
		try {
			chart.initializeLabProcedures(lpBeans, labProcedureName);
			DefaultCategoryDataset data = (DefaultCategoryDataset)chart.produceDataset(params);
			assertEquals(73.0, data.getValue(labProcedureName, "Q" + quarter + " '" + sdf.format(mytime.getTime())));
			assertTrue(chart.hasData());
		} catch (DatasetProduceException e) {
			
			fail();
		}
		
	}
	
}
