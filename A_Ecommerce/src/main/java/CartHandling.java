

import java.io.IOException;
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
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.ds.query.UpdateQueryImpl;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.Row;
import com.adventnet.persistence.WritableDataObject;

/**
 * Servlet implementation class addToCart
 */
@WebServlet("/CartHandling")
public class CartHandling extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
        
 		 
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		int cart_id=0;
		if(request.getParameter("id")!=null) {
			cart_id=Integer.parseInt(request.getParameter("id"));
			try {
				Persistence per= (Persistence)BeanUtil.lookup("Persistence");
				DataObject d =per.get("User_Cart",(Criteria)null);
				Criteria c = new Criteria(new Column("User_Cart", "CART_ID"),cart_id, QueryConstants.EQUAL);
				d.deleteRows("User_Cart",c); 
				per.update(d);
				response.sendRedirect("welcome.jsp?goToCart=1");
	 		 }catch(Exception e) {
	 			 System.out.println(e);
	 		 }
		}
		else {
			int quantity=Integer.parseInt(request.getParameter("quantity"));
			int product_id=Integer.parseInt(request.getParameter("product_id"));
			int star_discount=0;
		if(request.getParameter("star_discount")!=null) {
		star_discount=Integer.parseInt(request.getParameter("star_discount"));
		}
		int star_price=Integer.parseInt(request.getParameter("star_price"));
		HttpSession session=request.getSession();   
		String user_id=session.getAttribute("user_id").toString();
		int product_price=0;
		int cart_quantity=0;
		int isAlreadyInCart=0;
		RelationalAPI api = RelationalAPI.getInstance();
		Connection con = null;
		DataSet data = null;
		DataSet data1 = null;
		
		try
		{
			
			con = mConnection.getAvailableConnection();	
			Persistence per= (Persistence)BeanUtil.lookup("Persistence");	
			SelectQuery query = new SelectQueryImpl(new Table("Products"));
			query.addSelectColumn(Column.getColumn("Products",  "PRICE"));
			Criteria cr = new Criteria(new Column("Products", "PRODUCT_ID"),product_id, QueryConstants.EQUAL); 
			query.setCriteria(cr);
			data = api.executeQuery(query, con);
			data.next();
				product_price=data.getInt("PRICE");
			SelectQuery query1 = new SelectQueryImpl(new Table("User_Cart"));
			query1.addSelectColumn(Column.getColumn("User_Cart",  "*"));
			Criteria c = new Criteria(new Column("User_Cart", "PRODUCT_ID"),product_id, QueryConstants.EQUAL); 
			Criteria c1 = new Criteria(new Column("User_Cart", "USER_ID"),user_id, QueryConstants.EQUAL); 
		//	Criteria c2 = new Criteria(new Column("User_Cart", "CART_STATUS"),false, QueryConstants.EQUAL); 
			Criteria c3=c.and(c1);
			query1.setCriteria(c3);
			data1 = api.executeQuery(query1, con);
			while(data1.next())
			{
				cart_quantity=data1.getInt("QUANTITY");
				quantity=quantity+cart_quantity;				
				isAlreadyInCart=1;
			}
			if(isAlreadyInCart==1)
			{
				UpdateQuery s = new UpdateQueryImpl("User_Cart");
				Criteria c4 = new Criteria(new Column("User_Cart", "PRODUCT_ID"),product_id, QueryConstants.EQUAL); 
				Criteria c5 = new Criteria(new Column("User_Cart", "USER_ID"),user_id, QueryConstants.EQUAL); 
			//	Criteria c6 = new Criteria(new Column("User_Cart", "CART_STATUS"),false, QueryConstants.EQUAL); 
				Criteria c7=c4.and(c5);
				s.setCriteria(c7);
				s.setUpdateColumn("QUANTITY",quantity);
				per.update(s);
				//response.getWriter().write("updated");
			}
			else
			{
				
				Row row=new Row("User_Cart");
	 			row.set("USER_ID",user_id);
	 			row.set("PRODUCT_ID",product_id);
                int price=(star_price==0)?product_price:star_price;
                row.set("PRICE",price);
	 			row.set("QUANTITY",quantity);
	 				 			
	 			DataObject datao=new WritableDataObject();
	 			datao.addRow(row);
	        	DataAccess.add(datao);
				//response.getWriter().write("success");
			}
	        con.close();
			response.sendRedirect("welcome.jsp?discount="+star_discount+"&added=1");
			
		}catch(Exception e)
		{
			System.out.println(e);
			response.getWriter().write("failed");
		}
		}
	}

}
