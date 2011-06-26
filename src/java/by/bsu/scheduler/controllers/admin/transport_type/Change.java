package by.bsu.scheduler.controllers.admin.transport_type;

import by.bsu.scheduler.servlets.AdminServlet;
import by.bsu.scheduler.utils.DAOException;
import by.bsu.scheduler.dao.AdminDAO;
import by.bsu.scheduler.dao.TransportTypeDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import by.bsu.scheduler.models.TransportType;
import by.bsu.scheduler.servlets.IAdminController;
import by.bsu.scheduler.utils.CookiesWrapper;

public class Change implements IAdminController {
	
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
			String idParam = request.getParameter("id");
			if (idParam == null) {
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "/admin/transport_type/view");
				return;
			}

			TransportTypeDAO ttd = main.df.getTransportTypeDAO();
			TransportType transport_type = ttd.get(Integer.valueOf(idParam));
			if (transport_type == null) {
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "/admin/transport_type/view");
				return;
			}
			
			if(request.getParameter("name") != null) {
				request.setAttribute("name", request.getParameter("name"));
			} else {
				request.setAttribute("name", transport_type.getName());
			}
			if(request.getParameter("image") != null) {
				request.setAttribute("image", request.getParameter("image"));
			} else {
				request.setAttribute("image", transport_type.getImage());
			}
			if(request.getParameter("speed") != null) {
				request.setAttribute("speed", request.getParameter("speed"));
			} else {
				request.setAttribute("speed", transport_type.getSpeed());
			}
			
			request.setAttribute("transport_type", transport_type);
		} catch (DAOException ex) {
			Logger.getLogger(Change.class.getName()).log(Level.SEVERE, null, ex);
		}

		request.getRequestDispatcher("/WEB-INF/jsp/admin/transport_type/change.jsp").forward(request, response);
	}

	public void doPost(AdminServlet main, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", "/admin/transport_type/change");
	}
}
