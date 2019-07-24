package edu.ncsu.csc.itrust.server;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import edu.ncsu.csc.itrust.XmlGenerator;
import edu.ncsu.csc.itrust.action.GroupReportGeneratorAction;
import edu.ncsu.csc.itrust.dao.DAOFactory;

/**
 * GroupReportGeneratorServlet is a generic servlet that serves a xml document
 * 
 *
 */
public class GroupReportGeneratorServlet extends HttpServlet{

	/**
	 * GroupReportGeneratorAction to generate the report for download
	 */
	GroupReportGeneratorAction grga;
	
	/**
	 * factor for the GroupReportGeneratorAction to use
	 */
	protected DAOFactory factory = DAOFactory.getProductionInstance();
	
	/**
	 * Document to be served to client
	 */
	Document doc;
	
	/**
	 * Randomly generated servlet ID
	 */
	private static final long serialVersionUID = 4343961065799365553L;

	/**
	 * doPost method that takes a user request and serves them a downloadable xml based on their defined search criteria.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		grga = new GroupReportGeneratorAction(factory, request);
		
		try{
			grga.generateReport();
			doc = XmlGenerator.generateXml(grga.getReportHeaders(), grga.getReportData());
			
			//Set the headers.
			response.setContentType("application/x-download"); 
			response.setHeader("Content-Disposition", "attachment; filename=patientReport-" + Calendar.getInstance().getTimeInMillis() + ".xml");
			DOMSource source = new DOMSource(doc);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Writer writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
			StreamResult result = new StreamResult(writer);
			transformer.transform(source, result);
		}catch(Exception e){
			System.out.println(e);
		}			
	}
}
