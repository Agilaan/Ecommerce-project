

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject; 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.adventnet.db.api.RelationalAPI;
import com.adventnet.db.persistence.IntegerSequenceGenerator;
import com.adventnet.db.persistence.SequenceGenerator;
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
 * Servlet implementation class ReturnAction
 */
@WebServlet("/ReturnAction")
public class ReturnAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reason=request.getParameter("reason");
		if(request.getParameter("othersReason")!="")
		{
			reason=request.getParameter("othersReason");
		}
		HttpSession session=request.getSession();
		String user_id=session.getAttribute("user_id").toString();
		String order_id=request.getParameter("order_id");
		int quantity=0;
		int product_id=0;
		int product_quantity=0;
		 RelationalAPI api = RelationalAPI.getInstance();
 		 Connection con = null;
 		 DataSet data = null;
		
		 DataObject dataobject=null;
 		 try {
 			Persistence per = (Persistence)BeanUtil.lookup("Persistence");
 			con = mConnection.getAvailableConnection();	
			SelectQuery query = new SelectQueryImpl(new Table("OrderDetails"));
			query.addSelectColumn(Column.getColumn("OrderDetails",  "*"));
			Criteria c1 = new Criteria(new Column("OrderDetails", "ORDER_ID"),order_id, QueryConstants.EQUAL); 
			query.setCriteria(c1);
			data = api.executeQuery(query, con);
			while(data.next()) {
				quantity=data.getInt("QUANTITY");
				product_id=data.getInt("PRODUCT_ID");
			}
			SelectQuery query1 = new SelectQueryImpl(new Table("Products"));
			query1.addSelectColumn(Column.getColumn("Products",  "*"));
			Criteria c3 = new Criteria(new Column("Products", "PRODUCT_ID"),product_id, QueryConstants.EQUAL); 
			query1.setCriteria(c3);
			dataobject = per.get((SelectQuery)query1);
            Row row=dataobject.getFirstRow("Products");
            product_quantity=row.getInt("QUANTITY");
    		quantity=product_quantity+quantity;
    		//update orderDetails and product
    		UpdateQuery s = new UpdateQueryImpl("Products");
			//Criteria c4 = new Criteria(new Column("Products", "PRODUCT_ID"),product_id, QueryConstants.EQUAL); 
			s.setCriteria(c3);
			s.setUpdateColumn("QUANTITY",quantity);
			per.update(s);
			
			UpdateQuery s1 = new UpdateQueryImpl("OrderDetails");
			Criteria c4 = new Criteria(new Column("OrderDetails", "ORDER_ID"),order_id, QueryConstants.EQUAL); 
			s1.setCriteria(c4);
			s1.setUpdateColumn("RETURN_REASON",reason);
			s1.setUpdateColumn("RETURN_PRODUCT",true);
			per.update(s1);
            response.sendRedirect("welcome.jsp");
 		 }catch(Exception e) {
 			 System.out.println(e);
 		 }
	}

}
