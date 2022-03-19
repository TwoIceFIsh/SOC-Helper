package site;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class sendMessageServlet
 */
@WebServlet("/sendMessageServlet")
public class sendMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public sendMessageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String message = request.getParameter("message");
		siteDAO site = new siteDAO();

		HttpSession session = request.getSession();

		String mail = (String) session.getAttribute("ok");

		String lastMessage = site.sendMessage(mail, message);

		String messages = site.getMessage();

		System.out.println(messages + "#" + lastMessage + "");

		response.getWriter().write(messages + "#" + lastMessage + "");
		response.getWriter().close();
		return;
	}

}
