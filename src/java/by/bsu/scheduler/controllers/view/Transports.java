package by.bsu.scheduler.controllers.view;

import by.bsu.scheduler.dao.TransportDAO;
import by.bsu.scheduler.models.Transport;
import by.bsu.scheduler.servlets.AdminServlet;
import by.bsu.scheduler.servlets.IAdminController;
import by.bsu.scheduler.utils.DAOException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Transports implements IAdminController {

	public void init(AdminServlet main) {
	}
	
	public void doGet(AdminServlet main, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TransportDAO td = main.df.getTransportDAO();
		ArrayList<Transport> transports = null;
		try {
			transports = td.getAll();
			request.setAttribute("transports", transports);
		} catch (DAOException ex) {
			Logger.getLogger(Transports.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		request.getRequestDispatcher("/WEB-INF/jsp/view/data/transports.jsp").forward(request, response);
	}

	public void doPost(AdminServlet main, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
