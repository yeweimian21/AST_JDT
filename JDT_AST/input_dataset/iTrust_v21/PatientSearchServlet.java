package edu.ncsu.csc.itrust.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import edu.ncsu.csc.itrust.action.SearchUsersAction;
import edu.ncsu.csc.itrust.beans.PatientBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;

/**
 * Servlet implementation class PateintSearchServlet
 */
public class PatientSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SearchUsersAction sua;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PatientSearchServlet() {
        super();
        //We don't ever use the second parameter, so we don't need to give it meaning.
        sua = new SearchUsersAction(DAOFactory.getProductionInstance(), -1);
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    protected PatientSearchServlet(DAOFactory factory) {
        super();
        //We don't ever use the second parameter, so we don't need to give it meaning.
        sua = new SearchUsersAction(factory, -1);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("q");
		if(query == null ){
			return;
		}
		boolean isAudit = request.getParameter("isAudit") != null && request.getParameter("isAudit").equals("true");
		boolean deactivated = request.getParameter("allowDeactivated") != null && request.getParameter("allowDeactivated").equals("checked");
		String forward = request.getParameter("forward");
		List<PatientBean> search = null;
		if(query.isEmpty() && deactivated){
			search = sua.getDeactivated();
		} else {
			search = sua.fuzzySearchForPatients(query, deactivated);
		}
		StringBuffer result = new StringBuffer("<span class=\"searchResults\">Found " + search.size() + " Records</span>");
		if(isAudit){
			result.append("<table class='fTable' width=80%><tr><th width=10%>MID</th><th width=20%>First Name</th><th width=20%>Last Name</th><th width=30%>Status</th><th width=20%>Action</th></tr>");
			for(PatientBean p : search){
				boolean isActivated = p.getDateOfDeactivationStr() == null || p.getDateOfDeactivationStr().isEmpty();
				String change = isActivated ? "Deactivate" : "Activate";
				result.append("<tr>");
				result.append("<td>" + p.getMID() + "</td>");
				result.append("<td>" + p.getFirstName() + "</td>");
				result.append("<td>" + p.getLastName() + "</td>");
				if(isActivated){
					result.append("<td>" + p.getFirstName() + " " + p.getLastName() + " is activated.</td>");
				} else {
					result.append("<td>" + p.getFirstName() + " " + p.getLastName() + " deactivated on: " + p.getDateOfDeactivationStr() + "</td>");
				}
				result.append("<td>");
				result.append("<input type='button' style='width:100px;' onclick=\"parent.location.href='getPatientID.jsp?UID_PATIENTID=" + StringEscapeUtils.escapeHtml("" + p.getMID()) + "&forward=" + StringEscapeUtils.escapeHtml("" + forward ) + "';\" value=" + StringEscapeUtils.escapeHtml("" + change) + " />");
				result.append("</td></tr>");
			}
			result.append("<table>");
		} else {
			result.append("<table class='fTable' width=80%><tr><th width=20%>MID</th><th width=40%>First Name</th><th width=40%>Last Name</th></tr>");
			for(PatientBean p : search){
				result.append("<tr>");
				result.append("<td>");
				result.append("<input type='button' style='width:100px;' onclick=\"parent.location.href='getPatientID.jsp?UID_PATIENTID=" + StringEscapeUtils.escapeHtml("" + p.getMID()) + "&forward=" + StringEscapeUtils.escapeHtml("" + forward ) +"';\" value=" + StringEscapeUtils.escapeHtml("" + p.getMID()) + " />");
				result.append("</td>");
				result.append("<td>" + p.getFirstName() + "</td>");
				result.append("<td>" + p.getLastName() + "</td>");
				result.append("</tr>");
			}
			result.append("</table>");
		}
		response.setContentType("text/plain");
		PrintWriter resp = response.getWriter();
		resp.write(result.toString());
	}

}
