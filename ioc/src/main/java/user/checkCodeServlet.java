package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class checkCodeServlet
 */
@WebServlet("/checkCodeServlet")
public class checkCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public checkCodeServlet() {
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

		String email = request.getParameter("mail");
		String code = request.getParameter("code");
		email = email.toLowerCase();

		System.out.println(email + code);

		userDAO user = new userDAO();
		int no = user.checkCode(email, code);

		if (no == 10) {
			response.getWriter().println("10");
			response.getWriter().close();
			return;

		}
		if (no == 11) {
			response.getWriter().println("11");
			response.getWriter().close();
			return;
		}
		
		response.getWriter().println("33");
		response.getWriter().close();
		return;

	}

}
