package edu.ncsu.csc.itrust.server;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.ncsu.csc.itrust.beans.WardRoomBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.WardDAO;

public class WardRoomCRUDServlet extends HttpServlet  {

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
			long inWard = Long.parseLong(request.getParameter("inWard"));
			String roomName = request.getParameter("roomName");
			String status = request.getParameter("status");
			WardRoomBean wardRoom = new WardRoomBean(0, 0, inWard, roomName, status);
			wardDAO.addWardRoom(wardRoom);
		} catch (RuntimeException e){
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
			long occupiedBy = Long.parseLong(request.getParameter("occupiedBy"));
			long inWard = Long.parseLong(request.getParameter("inWard"));
			String roomName = request.getParameter("roomName");
			String status = request.getParameter("status");
			WardRoomBean wardRoom = new WardRoomBean(0, occupiedBy, inWard, roomName, status);
			wardDAO.updateWardRoom(wardRoom);
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
			wardDAO.removeWardRoom(Long.parseLong(request.getParameter("roomID")));
		} catch(Exception e){
			//Send error parameter back to page
			response.sendRedirect("");
			return;
		}
		
		//Redirect back to page
		response.sendRedirect("");
	}
	
}
