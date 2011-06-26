package by.bsu.scheduler.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import by.bsu.scheduler.utils.DAOFactory;
import by.bsu.scheduler.utils.URIDispatcher;
import java.util.HashMap;

public class AdminServlet extends HttpServlet {

	private URIDispatcher dispatcher;
	public DAOFactory df;
	private HashMap<String, IAdminController> mappingClasses;
	private static final String classesPrefix = "by.bsu.scheduler.controllers.";

	public AdminServlet() {
		super();
		this.mappingClasses = new HashMap<String, IAdminController>();
		this.df = DAOFactory.getInstance();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.dispatcher = new URIDispatcher(config.getServletContext());
	}

	protected IAdminController processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		String requestURI = request.getRequestURI();
		IAdminController controller = this.getController(requestURI);

		if (controller == null) {
			response.sendError(response.SC_NOT_FOUND, "this page is not found");
		}
		return controller;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IAdminController controller = this.processRequest(request, response);
		if (controller != null) {
			controller.doGet(this, request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IAdminController controller = this.processRequest(request, response);
		if (controller != null) {
			controller.doPost(this, request, response);
		}
	}

	public IAdminController getController(String uri) throws ServletException {
		IAdminController controller = null;

		controller = this.mappingClasses.get(uri);
		if (controller != null) {
			return controller;
		}

		String targetClass = this.dispatcher.getClassByURI(uri);
		if (targetClass != null) {
			try {
				controller = (IAdminController) Class.forName(this.classesPrefix + targetClass).newInstance();
				controller.init(this);
				this.mappingClasses.put(uri, controller);
				return controller;
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InstantiationException ex) {
				Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return null;
	}
}
