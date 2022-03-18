

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
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.Row;
import com.adventnet.persistence.WritableDataObject;

/**
 * Servlet implementation class incDecQuantity
 */
@WebServlet("/incDecQuantity")
public class incDecQuantity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int cart_id=Integer.parseInt(request.getParameter("id"));
		String quan=request.getParameter("quantity");
		int quantity=0;
		int price=0;
        RelationalAPI api = RelationalAPI.getInstance();
	    Connection con = null;
	    DataSet data=null;
		 try {
			 con = mConnection.getAvailableConnection();	
			Persistence per= (Persistence)BeanUtil.lookup("Persistence");
			SelectQuery query = new SelectQueryImpl(new Table("User_Cart"));
			query.addSelectColumn(Column.getColumn("User_Cart",  "QUANTITY"));
			query.addSelectColumn(Column.getColumn("User_Cart",  "PRICE"));
			Criteria c1 = new Criteria(new Column("User_Cart", "CART_ID"),cart_id, QueryConstants.EQUAL); 
			query.setCriteria(c1);
			data = api.executeQuery(query, con);
			data.next();
			quantity=data.getInt("QUANTITY");
			price=data.getInt("PRICE");
			if(quan.equals("inc")) {
				Criteria c = new Criteria(new Column("User_Cart", "CART_ID"),cart_id, QueryConstants.EQUAL);
				DataObject d =per.get("User_Cart",c);
				Row r = d.getRow("User_Cart"); 
				quantity++;
				r.set("QUANTITY",quantity); 
				d.updateRow(r); 
				per.update(d);
			}
			else if(quan.equals("dec")&&quantity>1) {
				Criteria c = new Criteria(new Column("User_Cart", "CART_ID"),cart_id, QueryConstants.EQUAL);
				DataObject d =per.get("User_Cart",c);
				Row r = d.getRow("User_Cart"); 
				quantity--;
				r.set("QUANTITY",quantity); 
				d.updateRow(r); 
				per.update(d);
			}
			con.close();
			price=quantity*price;
			JSONObject jo=new JSONObject();
			jo.put("quantity", quantity);
			jo.put("price", price);
			response.getWriter().print(jo);
		//	response.sendRedirect("welcome.jsp?goToCart=1");
		 }catch(Exception e) {
			 System.out.println(e);
		 }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
