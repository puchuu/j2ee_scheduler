package by.bsu.scheduler.controllers.admin;

import by.bsu.scheduler.servlets.AdminServlet;
import by.bsu.scheduler.utils.DAOException;
import by.bsu.scheduler.dao.AdminDAO;
import by.bsu.scheduler.servlets.IAdminController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import by.bsu.scheduler.utils.CookiesWrapper;
import by.bsu.scheduler.utils.DAOUtils;

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
				response.setHeader("Location", "/admin/view");
				return;
			}
			
			String email = request.getParameter("email");
			String name = request.getParameter("name");
			String login = request.getParameter("login").trim();
			String password = request.getParameter("password").trim();
			int id;
			try {
				id = Integer.valueOf(request.getParameter("id"));
			} catch (NumberFormatException ex) {
				id = 0;
			}
			
			if (id <= 0
					|| login.length() < 4
					|| password.length() < 4
					|| name.length() < 4
					|| !DAOUtils.isEmailValid(email)) {
				request.setAttribute("error", 1);
				IAdminController change = main.getController("/admin/change");
				change.doGet(main, request, response);
				return;
			}
			
			ad.save(id, email, name, login, password);
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", "/admin/view");
		} catch (DAOException ex) {
			Logger.getLogger(ChangeAction.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
