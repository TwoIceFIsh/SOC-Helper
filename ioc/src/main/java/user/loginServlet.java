package user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public loginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String id = request.getParameter("id");
		String pw = request.getParameter("pw");

		System.out.println(id + pw);

		userDTO user = new userDTO();
		user.setId(id);
		user.setPw(pw);

		System.out.println(user.getId() + user.getPw());

		if (userDAO.login(user)) {
			System.out.println("boolean");
			//HttpSession session = request.getSession();
			request.getSession().setAttribute("ok", user.getId()+""); 
			response.getWriter().println("11");
			response.getWriter().close();
			return;
		} else {
			response.getWriter().println("333");
			response.getWriter().close();
			return;

		}

	}
}
