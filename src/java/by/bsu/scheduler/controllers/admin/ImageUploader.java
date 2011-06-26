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
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;

public class ImageUploader implements IAdminController {

	private static final int MAX_SIZE = 1 * 1024 * 1024;
	private static final String TMP = "/WEB-INF/temp";
	private File tmp;
	private static final String DEST = "/static/upload";
	private File dest;
	private static final ArrayList<String> CONTENT_TYPES;

	static {
		CONTENT_TYPES = new ArrayList<String>();
		CONTENT_TYPES.add("image/png");
		CONTENT_TYPES.add("image/jpeg");
		CONTENT_TYPES.add("image/gif");
	}

	public void init(AdminServlet main) throws ServletException {
		this.tmp = new File(main.getServletContext().getRealPath(TMP));
		if (!this.tmp.isDirectory()) {
			throw new ServletException(TMP + " is not a directory");
		}
		this.dest = new File(main.getServletContext().getRealPath(DEST));
		if (!this.dest.isDirectory()) {
			throw new ServletException(DEST + " is not a directory");
		}
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

			JSONObject file_info = new JSONObject();
			DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
			fileItemFactory.setSizeThreshold(MAX_SIZE);
			fileItemFactory.setRepository(this.tmp);
			ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
			try {
				List items = uploadHandler.parseRequest(request);
				Iterator it = items.iterator();
				while (it.hasNext()) {
					FileItem item = (FileItem) it.next();
					if (item.getFieldName().equals("image")
							&& !item.isFormField()
							&& item.getSize() <= MAX_SIZE
							&& CONTENT_TYPES.indexOf(item.getContentType()) != -1) {
						String filename = item.getName();
						String extension = filename.substring(filename.indexOf(".") + 1);
						File file = null;
						do {
							filename = DAOUtils.salt(100) + "." + extension;
							file = new File(this.dest, filename);
						} while (file.exists());
						item.write(file);
						file_info.put("file_name", filename);
						file_info.put("file_size", item.getSize());
						file_info.put("content_type", item.getContentType());
					}
				}
			} catch (FileUploadException ex) {
				Logger.getLogger(ImageUploader.class.getName()).log(Level.SEVERE, null, ex);
			} catch (Exception ex) {
				Logger.getLogger(ImageUploader.class.getName()).log(Level.SEVERE, null, ex);
			} finally {
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				out.print(file_info);
				out.close();
			}
		} catch (DAOException ex) {
			Logger.getLogger(ImageUploader.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
