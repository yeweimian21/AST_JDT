package edu.ncsu.csc.itrust.server;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.ncsu.csc.itrust.action.ViewRecordsReleaseAction;
import edu.ncsu.csc.itrust.beans.HealthRecord;
import edu.ncsu.csc.itrust.beans.RecordsReleaseBean;

import java.io.*;
import java.util.List;

public class HealthRecordsXMLGeneratorServlet extends HttpServlet  {

	private static final long serialVersionUID = 1L;
	
	private ViewRecordsReleaseAction viewAction;
	private RecordsReleaseBean release;
	
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		try{
			//Get the ViewRecordsReleaseAction object
			viewAction = (ViewRecordsReleaseAction)request.getSession().getAttribute("viewAction");
			//Get the release request
			int index = Integer.parseInt(request.getParameter("index"));
			List<RecordsReleaseBean> releaseRequests = (List<RecordsReleaseBean>)request.getSession().getAttribute("recRequests");
			release = releaseRequests.get(index);
		} catch(NullPointerException e){
			e.printStackTrace();
		}
		
		try {
			printHealthRecordsXML(release, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void printHealthRecordsXML(RecordsReleaseBean release, HttpServletResponse response) throws Exception {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		//Get all of the patient's health records from the specified hospital
		List<HealthRecord> records = viewAction.getRequestedHealthRecords(release);
		
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation impl = builder.getDOMImplementation();
		document = impl.createDocument(null, "MedicalHealthRecords", null);
		//Get the root element
		Element rootElement = document.getDocumentElement();
		
		//Create a release information element
		Element releaseInfo = document.createElement("ReleaseInformation");
		rootElement.appendChild(releaseInfo);
		//Define the request date
		Element child = document.createElement("requestDate");
		child.appendChild(document.createTextNode(release.getDateRequestedStr()));
		releaseInfo.appendChild(child);
		//Define the patient name
		child = document.createElement("patientName");
		child.appendChild(document.createTextNode(viewAction.getPatientName(release.getPid())));
		releaseInfo.appendChild(child);
		//Define the release hospital
		child = document.createElement("releaseHospital");
		child.appendChild(document.createTextNode(viewAction.getHospitalName(release.getReleaseHospitalID())));
		releaseInfo.appendChild(child);
		//Define the doctor to release to's name
		child = document.createElement("recDoctor");
		child.appendChild(document.createTextNode(release.getDocFirstName() + " " + release.getDocLastName()));
		releaseInfo.appendChild(child);
		//Define the doctor to release to's contact information
		child = document.createElement("recDoctorPhone");
		child.appendChild(document.createTextNode(release.getDocPhone()));
		releaseInfo.appendChild(child);
		child = document.createElement("recDoctorEmail");
		child.appendChild(document.createTextNode(release.getDocEmail()));
		releaseInfo.appendChild(child);
		//Define the hospital to release to
		child = document.createElement("recHospital");
		child.appendChild(document.createTextNode(release.getRecHospitalName()));
		releaseInfo.appendChild(child);
		//Define the hospital to release to's address
		child = document.createElement("recHospitalAddress");
		child.appendChild(document.createTextNode(release.getRecHospitalAddress()));
		releaseInfo.appendChild(child);
		
		
		for (int i = 0; i < records.size(); i++) {
			//Define health record elements
			Element healthRecord = document.createElement("HealthRecord");
			rootElement.appendChild(healthRecord);

			//Define visit date elements
			child = document.createElement("visitDate");
			child.appendChild(document.createTextNode(records.get(i).getVisitDateStr()));
			healthRecord.appendChild(child);

			//Define height elements
			child = document.createElement("height");
			child.appendChild(document.createTextNode(String.valueOf(records.get(i).getHeight()) + " in"));
			healthRecord.appendChild(child);

			//Define weight elements
			child = document.createElement("weight");
			child.appendChild(document.createTextNode(String.valueOf(records.get(i).getWeight()) + " lbs"));
			healthRecord.appendChild(child);

			//Define blood pressure elements
			child = document.createElement("bloodPressure");
			String bpString = records.get(i).getBloodPressureSystolic() + "/" 
							+ records.get(i).getBloodPressureDiastolic() + " mmHg";
			child.appendChild(document.createTextNode(bpString));
			healthRecord.appendChild(child);
			
			//Define patient smoking status elements
			child = document.createElement("smokeStatus");
			child.appendChild(document.createTextNode(String.valueOf(records.get(i).getSmokingStatusDesc())));
			healthRecord.appendChild(child);
			
			//Define household smoke status elements
			child = document.createElement("houseSmokeStatus");
			child.appendChild(document.createTextNode(String.valueOf(records.get(i).getHouseholdSmokingStatusDesc())));
			healthRecord.appendChild(child);
		
			//Define hdl elements
			child = document.createElement("hdl");
			child.appendChild(document.createTextNode(String.valueOf(records.get(i).getCholesterolHDL()) + " mg/dL"));
			healthRecord.appendChild(child);
			
			//Define ldl elements
			child = document.createElement("ldl");
			child.appendChild(document.createTextNode(String.valueOf(records.get(i).getCholesterolLDL()) + " mg/dL"));
			healthRecord.appendChild(child);
			
			//Define triglycerides elements
			child = document.createElement("triglycerides");
			child.appendChild(document.createTextNode(String.valueOf(records.get(i).getCholesterolTri()) + " mg/dL"));
			healthRecord.appendChild(child);
			
			//Define doctor elements
			child = document.createElement("doctor");
			String docName = viewAction.getDoctorName(records.get(i).getPersonnelID());
			child.appendChild(document.createTextNode(docName));
			healthRecord.appendChild(child);
		}
		
		//Serialisation through Transform and print to html
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(out);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "users.dtd");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.transform(domSource, streamResult);
		
	}
	
}