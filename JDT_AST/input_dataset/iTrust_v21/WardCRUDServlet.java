package edu.ncsu.csc.itrust.server;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.ncsu.csc.itrust.beans.WardBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.WardDAO;

public class WardCRUDServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * WardDao for doing DAO Operations
	 */
	protected WardDAO wardDAO = new WardDAO(DAOFactory.getProductionInstance());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		try{
			String reqSpecialty = request.getParameter("requiredSpecialty");
			long inHospital = Long.parseLong(request.getParameter("inHospital"));
			WardBean ward = new WardBean(0, reqSpecialty, inHospital);
			wardDAO.addWard(ward);
		} catch(RuntimeException e){
			//Send error parameter back to page
			response.sendRedirect("");
			return;
		} catch(Exception e){
			//Send error parameter back to page
			response.sendRedirect("");
			return;
		}
		
		//Redirect back to page
		response.sendRedirect("");
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try{
			long wardID = Long.parseLong(request.getParameter("wardID"));
			String reqSpecialty = request.getParameter("requiredSpecialty");
			long inHospital = Long.parseLong(request.getParameter("inHospital"));
			WardBean ward = new WardBean(wardID, reqSpecialty, inHospital);
			wardDAO.updateWard(ward);
		} catch(RuntimeException e){
			//Send error parameter back to page
			response.sendRedirect("");
			return;
		} catch(Exception e){
			//Send error parameter back to page
			response.sendRedirect("");
			return;
		}
		
		//Redirect back to page
		response.sendRedirect("");
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try{
			wardDAO.removeWard(Long.parseLong(request.getParameter("wardID")));
		} catch(Exception e){
			//Send error parameter back to page
			response.sendRedirect("");
			return;
		}
		
		//Redirect back to page
		response.sendRedirect("");
	}
	
}
