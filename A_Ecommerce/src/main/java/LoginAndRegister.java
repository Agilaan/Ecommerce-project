

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.adventnet.db.api.RelationalAPI;
import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import com.adventnet.persistence.WritableDataObject;

/**
 * Servlet implementation class LoginAndRegister
 */
@WebServlet("/LoginAndRegister")
public class LoginAndRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String func=request.getParameter("func");
		if(func.equals("doLogin")) {
			String name=request.getParameter("name");
			String password=request.getParameter("password");
			
			PrintWriter out=response.getWriter();
			RelationalAPI api = RelationalAPI.getInstance();
			Connection con = null;
			DataSet data = null;
			try {
				con = mConnection.getAvailableConnection();	
				SelectQuery query = new SelectQueryImpl(new Table("LoginUser"));
				query.addSelectColumn(Column.getColumn("LoginUser",  "*"));
				Criteria c1 = new Criteria(new Column("LoginUser", "USER_NAME"),name, QueryConstants.EQUAL); 
				Criteria c2 = new Criteria(new Column("LoginUser", "PASSWORD"),password, QueryConstants.EQUAL); 
				Criteria c=c1.and(c2);
				
				query.setCriteria(c);
				data = api.executeQuery(query, con);
				while(data.next()) {
					int id=data.getInt("USER_ID");
					if(name.equals("admin") && password.equals("admin")){
						HttpSession session=request.getSession();   
						session.setAttribute("user_id", id);
						out.write("admin");
					}else {
					HttpSession session=request.getSession();   
					session.setAttribute("user_id", id);
					out.write("success");}
				}	
				con.close();
				}
			 catch (Exception e) 
			{
				out.write("failed");
				e.printStackTrace();
			}	
		}
		else if(func.equals("doRegister")) {
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			String password=request.getParameter("password");
			String action=request.getParameter("func");
			PrintWriter out=response.getWriter();	
			RelationalAPI api = RelationalAPI.getInstance();
			Connection con = null;
			try {
				con = mConnection.getAvailableConnection();
				Row row=new Row("LoginUser");
	 			row.set("USER_NAME",name);
	 			row.set("EMAIL",email);
	 			row.set("PASSWORD",password);
	 			DataObject data=new WritableDataObject();
	 			data.addRow(row);
	        	DataAccess.add(data);
	        	out.write("completed");
				}
			 catch (Exception e) 
			{
				out.write("failed");
				e.printStackTrace();
			}

		}
	}

}
