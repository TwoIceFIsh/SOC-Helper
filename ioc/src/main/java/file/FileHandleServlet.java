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
@WebServlet("/FileHandleServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5
		* 5, location = "C:\\Users\\IGLOO\\git\\ioc_project\\ioc\\src\\main\\uploadFile")
public class FileHandleServlet extends HttpServlet {

	String fileName = null;
	private static final long serialVersionUID = 1L;
	String location = "C:\\Users\\IGLOO\\git\\ioc_project\\ioc\\src\\main\\uploadFile";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileHandleServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		final Part filePart = request.getPart("file");

		if (filePart == null)
			response.sendRedirect("http://222.110.22.168:8080/ioc/oops.jsp");

		String fileName = getFileName(filePart);

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String dateToStr = dateFormat.format(date);

		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date2 = new Date();
		String dateToStr2 = dateFormat2.format(date2);
		System.out.println("#############################"+dateToStr2);
		
		
		
		fileName = dateToStr + fileName;

		OutputStream out = null;
		InputStream filecontent = null;
		final PrintWriter writer = response.getWriter();

		String ipAddress = request.getRemoteAddr();
		try {
			out = new FileOutputStream(new File(location + File.separator + fileName));
			filecontent = filePart.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			// path 저장 루틴
			dbrw dbrw = new dbrw();
			String address = dbrw.getMail();
			String date21 = dbrw.setDate(dateToStr2);

			if (dbrw.readFile(location, fileName, ipAddress, dateToStr2, address) == 1) {
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
