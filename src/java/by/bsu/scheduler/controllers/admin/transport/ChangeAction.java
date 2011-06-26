package by.bsu.scheduler.controllers.admin.transport;

import by.bsu.scheduler.servlets.AdminServlet;
import by.bsu.scheduler.utils.DAOException;
import by.bsu.scheduler.dao.AdminDAO;
import by.bsu.scheduler.dao.TransportDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import by.bsu.scheduler.servlets.IAdminController;
import by.bsu.scheduler.utils.CookiesWrapper;
import java.sql.Time;

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
				response.setHeader("Location", "/admin/transport/view");
				return;
			}

			int id, from_place_id, to_place_id, transport_type_id, number;
			Time start, end, spend, period;
			String image = request.getParameter("image");
			try {
				start = Time.valueOf(request.getParameter("start"));
			} catch (IllegalArgumentException ex) {
				start = Time.valueOf("00:00:00");
			}
			try {
				end = Time.valueOf(request.getParameter("end"));
			} catch (IllegalArgumentException ex) {
				end = Time.valueOf("00:00:00");
			}
			try {
				spend = Time.valueOf(request.getParameter("spend"));
			} catch (IllegalArgumentException ex) {
				spend = Time.valueOf("00:00:00");
			}
			try {
				period = Time.valueOf(request.getParameter("period"));
			} catch (IllegalArgumentException ex) {
				period = Time.valueOf("00:00:00");
			}
			try {
				id = Integer.valueOf(request.getParameter("id"));
			} catch (NumberFormatException ex) {
				id = 0;
			}
			try {
				from_place_id = Integer.valueOf(request.getParameter("from_place_id"));
			} catch (NumberFormatException ex) {
				from_place_id = 0;
			}
			try {
				to_place_id = Integer.valueOf(request.getParameter("to_place_id"));
			} catch (NumberFormatException ex) {
				to_place_id = 0;
			}
			try {
				transport_type_id = Integer.valueOf(request.getParameter("transport_type_id"));
			} catch (NumberFormatException ex) {
				transport_type_id = 0;
			}
			try {
				number = Integer.valueOf(request.getParameter("number"));
			} catch (NumberFormatException ex) {
				number = 0;
			}

			if (id <= 0
					|| from_place_id <= 0
					|| to_place_id <= 0
					|| from_place_id == to_place_id
					|| transport_type_id <= 0
					|| number <= 0) {
				request.setAttribute("from_place_id", from_place_id);
				request.setAttribute("to_place_id", to_place_id);
				request.setAttribute("transport_type_id", transport_type_id);
				request.setAttribute("error", 1);
				IAdminController change = main.getController("/admin/transport/change");
				change.doGet(main, request, response);
				return;
			}

			TransportDAO td = main.df.getTransportDAO();
			td.save(
					id,
					from_place_id,
					to_place_id,
					start,
					end,
					spend,
					period,
					image,
					transport_type_id,
					number);
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", "/admin/transport/view");
		} catch (DAOException ex) {
			Logger.getLogger(ChangeAction.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
