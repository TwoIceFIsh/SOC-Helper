package user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public loginServlet() { 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String id = request.getParameter("id");
		String pw = request.getParameter("pw");

		System.out.println(id + pw);

		userDTO user = new userDTO();
		userDAO userDAO = new userDAO();
		user.setId(id);
		user.setPw(pw);

		System.out.println(user.getId() + user.getPw());

		if (userDAO.login(user)) {
			
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
