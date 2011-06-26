package by.bsu.scheduler.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAdminController {

    void doGet(AdminServlet main, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    void doPost(AdminServlet main, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
	
	void init(AdminServlet main) throws ServletException;
}
