package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class getCodeServlet
 */
@WebServlet("/getCodeServlet")
public class getCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getCodeServlet() {
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
		email = email.toLowerCase();
		userDAO user = new userDAO();

		System.out.println(email);

		// 1 가능 0 가입되어있다 2 회사도메인아니다
		if (email.contains("@")) {

			if (!user.getEmail(email)) {
				int out = user.setCode(email);
				if (out == 1) {
					response.getWriter().println("1");
					response.getWriter().close();
				} else if (out == 9) {
					response.getWriter().println("9");
					response.getWriter().close();
				} else if (out == 4) {
					response.getWriter().println("4");
					response.getWriter().close();
				}

			} else {
				response.getWriter().println("0");
				response.getWriter().close();
				return;
			}

		} else {
			response.getWriter().println("2");
			response.getWriter().close();
		}

	}

}
