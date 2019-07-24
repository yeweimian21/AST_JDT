package edu.ncsu.csc.itrust.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ncsu.csc.itrust.action.SearchUsersAction;
import edu.ncsu.csc.itrust.beans.PersonnelBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;

/**
 * Servlet implementation class FindExpertServlet
 */
public class FindExpertServlet extends HttpServlet {
	private SearchUsersAction sua;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindExpertServlet() {
        super();
        //We never use the mid, so we don't need it.
        sua = new SearchUsersAction(DAOFactory.getProductionInstance(), -1);
    }
    
    protected FindExpertServlet(DAOFactory factory){
        super();
    	sua = new SearchUsersAction(factory, -1);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		String q = request.getParameter("query");
		PrintWriter pw = response.getWriter();
		List<PersonnelBean> exp = sua.fuzzySearchForExperts(q);
		StringBuffer resp = new StringBuffer("<table class=\"fTable\" width=\"80%\"><tr>"
				+ "<th width=\"30%\">Name</th>"
				+ "<th width=\"30%\">Specialty</th>"
				+ "<th width=\"25%\">Reviews</th></tr>");
		for(int i = 0; i < exp.size(); i++){
			resp.append("<tr><td>");
			resp.append(exp.get(i).getFirstName() + " " + exp.get(i).getLastName());
			resp.append("</td><td>");
			resp.append(exp.get(i).getSpecialty() == null ? "N/A" : exp.get(i).getSpecialty());
			resp.append("</td><td>");
			resp.append("<a href='reviewsPage.jsp?expertID=" + exp.get(i).getMID() + "'>View Reviews</a>");
			resp.append("</td></tr>");
		}
		resp.append("</table>");
		pw.write(resp.toString());
		pw.close();
	}
}
