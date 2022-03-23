package service_ioc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import site.siteDAO;

/**
 * Servlet implementation class FileHandleServlet
 */
@WebServlet("/FileHandleServlet2")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5
		* 5, location = "C:\\Temp")
public class FIleHandleServlet_ioc extends HttpServlet {

	String fileName = null;
	private static final long serialVersionUID = 1L;
	String location = "C:\\Temp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FIleHandleServlet_ioc() {
		super();
		 
	}

 
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	 

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String ipAddress = request.getRemoteAddr();

		final Part filePart = request.getPart("file");

		if (filePart == null) {
			response.sendRedirect("/2/oops.jsp");
			return;
		}

		String fileName = getFileName(filePart);

		String extension = fileName.replace(".", "#");
		String[] res = extension.split("#");

		if (!res[res.length - 1].equals("txt")) {
			response.sendRedirect("/2/oops.jsp");
			return;
		}

		if (fileName.matches(".*[¤¡-¤¾¤¿-¤Ó°¡-ÆR]+.*")) {
			response.sendRedirect("/2/oops.jsp");
			return;
		}

		// NAME SLICE AND FIX
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String dateToStr = dateFormat.format(date);

		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date2 = new Date();
		String dateToStr2 = dateFormat2.format(date2);

		fileName = dateToStr + fileName;

		// FILE WRITE
		OutputStream out = null;
		InputStream filecontent = null;
		final PrintWriter writer = response.getWriter();

		try {
			out = new FileOutputStream(new File(location + File.separator + fileName));
			filecontent = filePart.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			//  
			siteDAO dbrw = new siteDAO();
			String address = dbrw.getMail();
		 
			dbrw.setJobq(dateToStr2, ipAddress, "IOC", address, fileName);
			
			dbrw_ioc ioc = new dbrw_ioc();
			if (ioc.readFile2(location, fileName, ipAddress, dateToStr2, address) == 1) {

				response.sendRedirect("/2/ok.jsp");
			} else {
				response.sendRedirect("/2/oops.jsp");
			}

		} catch (FileNotFoundException fne) {
			writer.println("You either did not specify a file to upload or are "
					+ "trying to upload a file to a protected or nonexistent " + "location.");

		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
	}

	private String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
