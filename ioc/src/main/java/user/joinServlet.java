package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class joinServlet
 */
@WebServlet("/joinServlet")
public class joinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public joinServlet() {
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

		String mail = request.getParameter("mail");
		String pw1 = request.getParameter("pw1");
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		
		
		
		int no = 0;
		mail = mail.toLowerCase();
		userDTO user = new userDTO();
		
		user.setId(mail);
		user.setPw(pw1);
		user.setCode(code);
		user.setName(name);
		
		
		userDAO userjoin = new userDAO();
		no = userjoin.userjoin(user);

		System.out.println(mail+pw1+code+name);
		if (no == 11) {
			response.getWriter().println("11");
			response.getWriter().close();
			return;

		}
		
		if (no == 222) {
			response.getWriter().println("222");
			response.getWriter().close();
			return;

		}
		
		if (no == 333) {
			response.getWriter().println("333");
			response.getWriter().close();
			return;

		}
		

	}

}
