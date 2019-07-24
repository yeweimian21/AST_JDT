package edu.ncsu.csc.itrust.unit.charts;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.category.DefaultCategoryDataset;

import de.laures.cewolf.DatasetProduceException;
import edu.ncsu.csc.itrust.beans.DiagnosisStatisticsBean;
import edu.ncsu.csc.itrust.charts.DiagnosisTrendData;
import edu.ncsu.csc.itrust.unit.datagenerators.TestDataGenerator;
import junit.framework.TestCase;

public class DiagnosisTrendDataTest extends TestCase {
	
	private DiagnosisTrendData chart;
	TestDataGenerator gen = new TestDataGenerator();
	
	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.influenza_epidemic();
		gen.malaria_epidemic();
	}
	
	public void testProduceDataset() {
		
		chart = new DiagnosisTrendData();
		
		String diagnosisName = "Influenza";
		
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		
		cal.set(2011, 10, 31); //start week
		cal2.set(2011, 11, 06); //end week
    	
		DiagnosisStatisticsBean dsBean = new DiagnosisStatisticsBean();
		DiagnosisStatisticsBean avgBean = new DiagnosisStatisticsBean();
		
		dsBean.setZipStats(700);
		dsBean.setRegionStats(700);
		dsBean.setStartDate(cal.getTime());
		dsBean.setEndDate(cal2.getTime());
		
		avgBean.setZipStats(700);
		avgBean.setRegionStats(700);
		dsBean.setStartDate(cal.getTime());
		dsBean.setEndDate(cal.getTime());
		
		Map<String, String> params = new HashMap<String, String>();
		
		try {
			
			chart.initializeAvgDiagnosisStatistics(avgBean, dsBean, diagnosisName);
			DefaultCategoryDataset data = (DefaultCategoryDataset)chart.produceDataset(params);
			assertEquals(700.0, data.getValue(diagnosisName, "Current Week Zipcode Cases"));
			assertEquals(700.0, data.getValue(diagnosisName, "Average Prior Zipcode Cases"));
			assertEquals(700.0, data.getValue(diagnosisName, "Current Week Region Cases"));
			assertEquals(700.0, data.getValue(diagnosisName, "Average Prior Region Cases"));
			assertTrue(chart.hasData());
			
		} catch (DatasetProduceException e) {
			
			
			fail();
			
		}
		
	}
	
	public void testProduceDataset2() {
		
		chart = new DiagnosisTrendData();
		String diagnosisName = "Malaria";
		
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		
		cal.set(2011, 10, 31); //start week
		cal2.set(2011, 11, 06); //end week
    	
		DiagnosisStatisticsBean dsBean = new DiagnosisStatisticsBean();
		DiagnosisStatisticsBean avgBean = new DiagnosisStatisticsBean();
		
		dsBean.setZipStats(700);
		dsBean.setRegionStats(700);
		dsBean.setStartDate(cal.getTime());
		dsBean.setEndDate(cal2.getTime());
		
		avgBean.setZipStats(700);
		avgBean.setRegionStats(700);
		dsBean.setStartDate(cal.getTime());
		dsBean.setEndDate(cal.getTime());
		
		Map<String, String> params = new HashMap<String, String>();
		
		try {
			
			chart.initializeAvgDiagnosisStatistics(avgBean, dsBean, diagnosisName);
			DefaultCategoryDataset data = (DefaultCategoryDataset)chart.produceDataset(params);
			assertEquals(700.0, data.getValue(diagnosisName, "Current Week Zipcode Cases"));
			assertEquals(700.0, data.getValue(diagnosisName, "Average Prior Zipcode Cases"));
			assertEquals(700.0, data.getValue(diagnosisName, "Current Week Region Cases"));
			assertEquals(700.0, data.getValue(diagnosisName, "Average Prior Region Cases"));
			assertTrue(chart.hasData());
			
		} catch (DatasetProduceException e) {
			
			
			fail();
			
		}
	
	}
	
	public void testProduceDataset3() {
		
		chart = new DiagnosisTrendData();
		String diagnosisName = "Mumps";
		
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		
		cal.set(2011, 10, 31); //start week
		cal2.set(2011, 11, 06); //end week
    	
		DiagnosisStatisticsBean dsBean = new DiagnosisStatisticsBean();
		
		dsBean.setZipStats(500);
		dsBean.setRegionStats(500);
		dsBean.setStartDate(cal.getTime());
		dsBean.setEndDate(cal2.getTime());
		
		Map<String, String> params = new HashMap<String, String>();
		
		try {
			
			chart.initializeDiagnosisStatistics(dsBean, diagnosisName);
			DefaultCategoryDataset data = (DefaultCategoryDataset)chart.produceDataset(params);
			assertEquals(500.0, data.getValue(diagnosisName, "Zipcode Cases"));
			assertEquals(500.0, data.getValue(diagnosisName, "Region Cases"));
			assertTrue(chart.hasData());
			
		} catch (DatasetProduceException e) {
			
			
			fail();
			
		}
	
	}
	
}
