package by.bsu.scheduler.controllers.admin;

import by.bsu.scheduler.servlets.AdminServlet;
import by.bsu.scheduler.utils.DAOException;
import by.bsu.scheduler.dao.AdminDAO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import by.bsu.scheduler.models.Admin;
import by.bsu.scheduler.servlets.IAdminController;

public class LoginAction implements IAdminController {
	
	public void init(AdminServlet main) {
	}

	public void doGet(AdminServlet main, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", "/admin/login");
	}

	public void doPost(AdminServlet main, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			AdminDAO ad = main.df.getAdminDAO();
			String login = request.getParameter("login");
			String password = request.getParameter("password");
			if (login == null || password == null) {
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "/admin/login");
				return;
			}
			Admin admin = ad.check(login, password);
			if (admin != null) {
				response.addCookie(new Cookie("adminid", String.valueOf(admin.getId())));
				response.addCookie(new Cookie("adminpass", admin.getPassword()));
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "/admin/");
			} else {
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", "/admin/login");
			}
		} catch (DAOException ex) {
			Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
