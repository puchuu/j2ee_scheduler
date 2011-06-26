package by.bsu.scheduler.controllers.admin;

import by.bsu.scheduler.servlets.IAdminController;
import by.bsu.scheduler.utils.DAOException;
import by.bsu.scheduler.dao.AdminDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import by.bsu.scheduler.models.Admin;
import by.bsu.scheduler.servlets.AdminServlet;
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
				response.setHeader("Location", "/admin/view");
				return;
			}

			Admin admin = ad.get(Integer.valueOf(idParam));
			if (admin == null) {
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "/admin/view");
				return;
			}
			
			if(request.getParameter("login") != null) {
				request.setAttribute("login", request.getParameter("login"));
			} else {
				request.setAttribute("login", admin.getLogin());
			}
			if(request.getParameter("name") != null) {
				request.setAttribute("name", request.getParameter("name"));
			} else {
				request.setAttribute("name", admin.getName());
			}
			if(request.getParameter("email") != null) {
				request.setAttribute("email", request.getParameter("email"));
			} else {
				request.setAttribute("email", admin.getEmail());
			}
			if(request.getParameter("password") != null) {
				request.setAttribute("password", request.getParameter("password"));
			} else {
				request.setAttribute("password", admin.getPassword());
			}
			
			request.setAttribute("admin", admin);
		} catch (DAOException ex) {
			Logger.getLogger(Change.class.getName()).log(Level.SEVERE, null, ex);
		}

		request.getRequestDispatcher("/WEB-INF/jsp/admin/change.jsp").forward(request, response);
	}

	public void doPost(AdminServlet mainServlet, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", "/admin/change");
	}
}
