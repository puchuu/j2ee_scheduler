package by.bsu.scheduler.controllers.admin;

import by.bsu.scheduler.servlets.AdminServlet;
import by.bsu.scheduler.dao.AdminDAO;
import by.bsu.scheduler.servlets.IAdminController;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import by.bsu.scheduler.utils.CookiesWrapper;

public class Logout implements IAdminController {
	
	public void init(AdminServlet main) {
	}

	public void doGet(AdminServlet main, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdminDAO ad = main.df.getAdminDAO();
		CookiesWrapper cw = new CookiesWrapper(request.getCookies());
		String adminid = cw.get("adminid");
		String adminpass = cw.get("adminpass");
		if (adminid == null || adminpass == null) {
			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", "/admin/login");
			return;
		}
		cw.delete("adminid", "/admin/");
		cw.delete("adminpass", "/admin/");
		cw.save(response);

		request.getRequestDispatcher("/WEB-INF/jsp/admin/logout.jsp").forward(request, response);
	}

	public void doPost(AdminServlet main, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", "/admin/logout");
	}
}
