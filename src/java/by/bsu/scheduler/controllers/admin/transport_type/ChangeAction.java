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
import by.bsu.scheduler.servlets.IAdminController;
import by.bsu.scheduler.utils.CookiesWrapper;

public class ChangeAction implements IAdminController {

	public void init(AdminServlet main) {
	}

	public void doGet(AdminServlet main, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", "/admin/");
	}

	public void doPost(AdminServlet main, HttpServletRequest request, HttpServletResponse response)
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

			String name = request.getParameter("name").trim();
			String image = request.getParameter("image");
			int speed, id;
			try {
				id = Integer.valueOf(request.getParameter("id"));
			} catch (NumberFormatException ex) {
				id = 0;
			}
			try {
				speed = Integer.valueOf(request.getParameter("speed"));
			} catch (NumberFormatException ex) {
				speed = 0;
			}

			if (id <= 0
					|| name.length() == 0
					|| speed <= 0) {
				request.setAttribute("error", 1);
				IAdminController change = main.getController("/admin/transport_type/change");
				change.doGet(main, request, response);
				return;
			}

			TransportTypeDAO ttd = main.df.getTransportTypeDAO();
			ttd.save(
					id,
					name,
					image,
					speed);
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", "/admin/transport_type/view");
		} catch (DAOException ex) {
			Logger.getLogger(ChangeAction.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
