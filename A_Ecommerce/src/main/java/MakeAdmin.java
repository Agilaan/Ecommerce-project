

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adventnet.db.api.RelationalAPI;
import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.ds.query.UpdateQueryImpl;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;

/**
 * Servlet implementation class MakeAdmin
 */
@WebServlet("/MakeAdmin")
public class MakeAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		      Connection con = null;
		     int user_id=Integer.parseInt(request.getParameter("user_id"));
			try {
				con = mConnection.getAvailableConnection();	
				Persistence per= (Persistence)BeanUtil.lookup("Persistence");	
				UpdateQuery s = new UpdateQueryImpl("LoginUser");
				 Criteria c = new Criteria(new Column("LoginUser", "USER_ID"),user_id, QueryConstants.EQUAL); 
                s.setCriteria(c);
				s.setUpdateColumn("IS_ADMIN",true);
				per.update(s);
				response.sendRedirect("welcome.jsp");
	 		 }catch(Exception e) {
	 			 System.out.println(e);
	 		 }
			
		}
	}


