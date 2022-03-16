

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.adventnet.db.api.RelationalAPI;
//import com.adventnet.db.persistence.IntegerSequenceGenerator;
//import com.adventnet.db.persistence.SequenceGenerator;
import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.Operation;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.QueryConstructionException;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.ds.query.UpdateQueryImpl;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.Row;
import com.adventnet.persistence.WritableDataObject;
/**
 * Servlet implementation class reviewProduct
 */
@WebServlet("/reviewProduct")
public class reviewProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String user_id=session.getAttribute("user_id").toString();
		String product_id=request.getParameter("pID");
		String rating=request.getParameter("rating");
		String review=request.getParameter("comment");
		int review_id=0;
		if(request.getParameter("review_id")!=null) {
		review_id=Integer.parseInt(request.getParameter("review_id"));
		}
		RelationalAPI api = RelationalAPI.getInstance();
		Connection con = null;
		DataSet data1=null;
		boolean is_present=false;
		try {
			con = mConnection.getAvailableConnection();
			Persistence per= (Persistence)BeanUtil.lookup("Persistence");
			SelectQuery query = new SelectQueryImpl(new Table("OrderDetails"));
			query.addSelectColumn(Column.getColumn("OrderDetails","*"));
			Criteria c1 = new Criteria(new Column("OrderDetails", "PRODUCT_ID"),product_id, QueryConstants.EQUAL); 
			Criteria c2 = new Criteria(new Column("OrderDetails", "USER_ID"),user_id, QueryConstants.EQUAL);
			Criteria c3=c1.and(c2);
            query.setCriteria(c3);
            data1 = api.executeQuery(query, con);
            while(data1.next()) {
            	is_present=true;
            }
			if(review_id==0) {
			Row row=new Row("Review");
 			row.set("USER_ID",user_id);
 			row.set("PRODUCT_ID",product_id);
 			row.set("RATING",rating);
 			row.set("COMMENT",review);
 			row.set("VERIFIED", is_present);
 			DataObject data=new WritableDataObject();
 			data.addRow(row);
        	DataAccess.add(data);
			}else {
				UpdateQuery s = new UpdateQueryImpl("Review");
				Criteria c = new Criteria(new Column("Review", "REVIEW_ID"),review_id, QueryConstants.EQUAL); 
				s.setCriteria(c);
				s.setUpdateColumn("RATING",rating); 
				s.setUpdateColumn("COMMENT",review); 
				per.update(s);
			}
        	response.sendRedirect("welcome.jsp");
			}
		 catch (Exception e) 
		{
			
			e.printStackTrace();
		}
		

		
	}

}
