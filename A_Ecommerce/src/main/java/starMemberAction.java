

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.ds.query.UpdateQueryImpl;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.Persistence;

/**
 * Servlet implementation class starMemberAction
 */
@WebServlet("/starMemberAction")
public class starMemberAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();  
		String user_id=session.getAttribute("user_id").toString();
		RelationalAPI api = RelationalAPI.getInstance();
		Connection con = null;
		DataSet data = null;
		DataSet data1=null;
		boolean star_member=false;
		int wallet=0;
		int total=0;
		try {
			con = mConnection.getAvailableConnection();	
			Persistence per= (Persistence)BeanUtil.lookup("Persistence");
			SelectQuery query = new SelectQueryImpl(new Table("LoginUser"));
			query.addSelectColumn(Column.getColumn("LoginUser",  "*"));
			Criteria c1 = new Criteria(new Column("LoginUser", "USER_ID"),user_id, QueryConstants.EQUAL); 
			query.setCriteria(c1);
			data = api.executeQuery(query, con);
			data.next();
			   star_member=data.getAsBoolean("STAR_MEMBER");
			   wallet=data.getInt("WALLET");
			   if(wallet>499) {
				   wallet=wallet-499;
			   }else {
				   total=499-wallet;
				   wallet=0;
			   }
			if(star_member) {
				response.sendRedirect("welcome.jsp?msg=0");
			}
			else {
				  Date now=new Date();
	 			  Calendar cal = Calendar.getInstance();
	 			  cal.setTime(now);
	 			  cal.add(Calendar.MINUTE, 5);
	 			  now = cal.getTime();
	 			  Timestamp ts=new Timestamp(now.getTime()); 
	 			  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	 			 UpdateQuery s1 = new UpdateQueryImpl("LoginUser");
	 			s1.setCriteria(c1);
	 			s1.setUpdateColumn("STAR_MEMBER",true);
	 			s1.setUpdateColumn("MEMBER_TIME",formatter.format(ts));
	 			s1.setUpdateColumn("WALLET", wallet);
	 			per.update(s1);
				response.sendRedirect("welcome.jsp?msg=1&total="+total);

			}
		}catch(Exception e) {
			System.out.println(e);
		}

	}

}
