package by.bsu.scheduler.controllers.admin.transport;

import by.bsu.scheduler.servlets.AdminServlet;
import by.bsu.scheduler.utils.DAOException;
import by.bsu.scheduler.dao.AdminDAO;
import by.bsu.scheduler.dao.PlaceDAO;
import by.bsu.scheduler.dao.TransportTypeDAO;
import by.bsu.scheduler.models.Place;
import by.bsu.scheduler.models.TransportType;
import by.bsu.scheduler.servlets.IAdminController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import by.bsu.scheduler.utils.CookiesWrapper;
import java.util.ArrayList;

public class Create implements IAdminController {
	
	public void init(AdminServlet main) {
	}

	public void doGet(AdminServlet main, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			AdminDAO ad = main.df.getAdminDAO();
			CookiesWrapper cw = new CookiesWrapper(request.getCookies());
			String adminid = cw.get("adminid");
			String adminpass = cw.get("adminpass");
			if (adminid == null || adminpass == null) {
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "/admin/login");
				return;
			}
			boolean checkCookies = ad.checkCookies(adminid, adminpass);
			if (!checkCookies) {
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "/admin/login");
				return;
			}

			TransportTypeDAO ttd = main.df.getTransportTypeDAO();
			ArrayList<TransportType> transport_types = ttd.getAll();
			request.setAttribute("transport_types", transport_types);
			
			PlaceDAO pd = main.df.getPlaceDAO();
			ArrayList<Place> places = pd.getAll();
			request.setAttribute("places", places);
			
			if(request.getAttribute("from_place_id") == null) {
				request.setAttribute("from_place_id", places.get(0).getId());
			}
			if(request.getAttribute("to_place_id") == null) {
				request.setAttribute("to_place_id", places.get(1).getId());
			}
			
			if(request.getAttribute("transport_type_id") == null) {
				request.setAttribute("transport_type_id", transport_types.get(0).getId());
			}
		} catch (DAOException ex) {
			Logger.getLogger(Create.class.getName()).log(Level.SEVERE, null, ex);
		}

		request.getRequestDispatcher("/WEB-INF/jsp/admin/transport/create.jsp").forward(request, response);
	}

	public void doPost(AdminServlet main, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", "/admin/transport/create");
	}
}
