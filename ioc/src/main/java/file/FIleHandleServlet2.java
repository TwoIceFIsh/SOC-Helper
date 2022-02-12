package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ioc.dbrw;

/**
 * Servlet implementation class FileHandleServlet
 */
@WebServlet("/FileHandleServlet2")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5
		* 5, location = "C:\\Users\\IGLOO\\git\\ioc_project\\ioc\\src\\main\\uploadFile2")
public class FIleHandleServlet2 extends HttpServlet {

	String fileName = null;
	private static final long serialVersionUID = 1L;
	String location = "C:\\Users\\IGLOO\\git\\ioc_project\\ioc\\src\\main\\uploadFile2";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FIleHandleServlet2() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		

	    String ip = request.getRemoteAddr();

		final Part filePart = request.getPart("file");
		
		if(filePart == null)
			response.sendRedirect("http://222.110.22.168:8080/ioc/oops.jsp");
		
		String fileName = getFileName(filePart);
		// NAME SLICE AND FIX
		DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
		Date date = new Date();
		String dateToStr = dateFormat.format(date);

		fileName = dateToStr + fileName;

		// FILE WRITE
		OutputStream out = null;
		InputStream filecontent = null;
		final PrintWriter writer = response.getWriter();

		String ipAddress = "";
		try {
			out = new FileOutputStream(new File(location + File.separator + fileName));
			filecontent = filePart.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			// 데이터 DB 작성
			dbrw dbrw = new dbrw();
			if (dbrw.readFile2(location, fileName, ip) == 1) {
				
				response.sendRedirect("http://222.110.22.168:8080/ioc/ok.jsp");
			} else {
				response.sendRedirect("http://222.110.22.168:8080/ioc/oops.jsp");
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
