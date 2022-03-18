

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckSession
 */
@WebServlet("/CheckSession")
public class CheckSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(); 
		if(session.getAttribute("user_id")==null)
	    {
	           response.getWriter().print("failed");
	    }else if(session.getAttribute("user_id")!=null) {
	    	response.getWriter().print("already_using");
	    }
	}

}
