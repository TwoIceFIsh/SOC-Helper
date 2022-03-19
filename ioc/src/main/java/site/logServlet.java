package site;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class loglog
 */
@WebServlet("/loglog")
public class logServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public logServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	 

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
	 
		siteDAO site = new siteDAO();
	
		String[] log = site.loglog();
 
		String arrayToString = String.join(",", log);
 
		response.getWriter().println(arrayToString + "");
		response.getWriter().close();

	}

}
