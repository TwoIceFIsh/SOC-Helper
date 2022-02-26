package site;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ioc.dbrw;

/**
 * Servlet implementation class moduleStatusServlet
 */
@WebServlet("/moduleStatusServlet")
public class moduleStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public moduleStatusServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		siteDAO site = new siteDAO();

		int[] result = site.moduleStatusCheck();

		int a = result[0];
		int b = result[1];
		int c = result[2];
		int d = result[3];
		int e = result[4];

		 
		response.getWriter().println(a + "#" + b + "#" + c + "#" + d + "#" + e + "#");
		response.getWriter().close();
		return;
	}

}
