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
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ioc.dbrw;
import ioc.functions;

/**
 * Servlet implementation class FileHandleServlet
 */
@WebServlet("/FileHandleServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5
		* 5, location = "D:\\MyProject\\ioc\\src\\main\\uploadFile")
public class FileHandleServlet extends HttpServlet {

	private static final String CHARSET = "utf-8";
	String fileName = null;
	private static final long serialVersionUID = 1L;
	String location = "D:\\MyProject\\ioc\\src\\main\\uploadFile";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileHandleServlet() {
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

		// Create path components to save the file
		final String path = request.getParameter("destination");
		final Part filePart = request.getPart("file");
		String fileName = getFileName(filePart);
		String tmp;

		
		//NAME SLICE AND FIX
		DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
		Date date = new Date();
		String dateToStr = dateFormat.format(date);
		System.out.println(dateToStr);

		fileName = dateToStr+fileName;
		
		//대충 ㅇ양식
		
		
		//FILE WRITE
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
			writer.println("1." + fileName + " 서버 업로드 완료.");

			// path 저장 루틴
			dbrw dbrw = new dbrw();
			dbrw.pathWrite(fileName, location);
			writer.println("\r\n2." + fileName + " DB 저장 완료");
			System.out.println(location + "\\" + fileName);
			// 파일 분석 루틴
			functions fn = new functions();
			// fn.readExcel_igloo(location + "\\"+fileName);

		} catch (FileNotFoundException fne) {
			writer.println("You either did not specify a file to upload or are "
					+ "trying to upload a file to a protected or nonexistent " + "location.");
			writer.println("<br/> ERROR: " + fne.getMessage());

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
		final String partHeader = part.getHeader("content-disposition");
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
