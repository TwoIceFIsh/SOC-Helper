package site;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ioc.dbrw;

/**
 * Servlet implementation class hearthCheckServlet
 */
@WebServlet("/hearthCheckServlet")
public class hearthCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public hearthCheckServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		dbrw dbrw = new dbrw();
		int result = 0;
		String address = dbrw.getMail();
		if (address.equals("DLZ1160@s-oil.com"))
			result = 1;
		if (address.equals("sungwoo.kwon@s-oil.com"))
			result = 2;
		if (address.equals("jsh0119@s-oil.com"))
			result = 3;
		if (address.equals("kmh0816@s-oil.com"))
			result = 4;
		if (address.equals("bh.lee@s-oil.com"))
			result = 5;
		if (address.equals("ksm0117@s-oil.com"))
			result = 6;
		if (address.equals("lyj0409@s-oil.com"))
			result = 7;
		if (address.equals("khw1205@s-oil.com"))
			result = 8;
		if (address.equals("osh10105@s-oil.com"))
			result = 9;
		response.getWriter().println(result + "");
		response.getWriter().close();
	}

}
