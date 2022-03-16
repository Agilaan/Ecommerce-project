

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
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.Row;
import com.adventnet.persistence.WritableDataObject;

/**
 * Servlet implementation class placeOrderAction
 */
@WebServlet("/placeOrderAction")
public class placeOrderAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int flag=0;
		int admin_quantity=1;
		int total=0;
		int wallet=0;
		boolean star_member=false;
		int cashback=0;
		
		HttpSession session=request.getSession();
		String user_id=session.getAttribute("user_id").toString();
		String address=request.getParameter("address");
		String paymentMethod=request.getParameter("paymentMethod");
		RelationalAPI api = RelationalAPI.getInstance();
		Connection con = null;
		DataSet data = null;
		DataSet data1,data2=null;
		DataObject dataobject=null;
		try {
			Persistence per = (Persistence)BeanUtil.lookup("Persistence");
			con = mConnection.getAvailableConnection();	
			SelectQuery query = new SelectQueryImpl(new Table("User_Cart"));
			query.addSelectColumn(Column.getColumn("User_Cart",  "*"));
			Criteria c1 = new Criteria(new Column("User_Cart", "USER_ID"),user_id, QueryConstants.EQUAL); 
			query.setCriteria(c1);
			data = api.executeQuery(query, con);
			while(data.next()) {
				total=total+(data.getInt("QUANTITY")*data.getInt("PRICE"));
			}
			SelectQuery query1 = new SelectQueryImpl(new Table("LoginUser"));
			query1.addSelectColumn(Column.getColumn("LoginUser",  "*"));
			Criteria c3 = new Criteria(new Column("LoginUser", "USER_ID"),user_id, QueryConstants.EQUAL); 
			query1.setCriteria(c3);
			dataobject = per.get((SelectQuery)query1);
            Row row=dataobject.getFirstRow("LoginUser");
	        wallet=row.getInt("WALLET");
	        star_member=row.getBoolean("STAR_MEMBER");
	        if(star_member==true) {
	    			cashback=total*5/100;
	        }

			data1 = api.executeQuery(query, con);
			while(data1.next()) {
				
				SelectQuery query3 = new SelectQueryImpl(new Table("Products"));
				query3.addSelectColumn(Column.getColumn("Products",  "*"));
				Criteria c7 = new Criteria(new Column("Products", "PRODUCT_ID"),data1.getInt("PRODUCT_ID"),QueryConstants.EQUAL); 
				query3.setCriteria(c7);
				data2 = api.executeQuery(query3, con);
				while(data2.next()) {
					admin_quantity=data2.getInt("QUANTITY")-data1.getInt("QUANTITY");
				}
				if(admin_quantity>0) {
				Row row1=new Row("OrderDetails");
	 			row1.set("USER_ID",data1.getInt("USER_ID"));
	 			row1.set("PRODUCT_ID",data1.getInt("PRODUCT_ID"));
	 			row1.set("PRICE",data1.getInt("PRICE"));
	 			row1.set("QUANTITY",data1.getInt("QUANTITY"));
	 			row1.set("ADDRESS",address);
	 		    
	 			Date now=new Date();
	 			Calendar cal = Calendar.getInstance();
	 			  cal.setTime(now);
	 			 if(star_member==true) {
	 			  cal.add(Calendar.MINUTE, 5); 
	 			 }else {
	 				cal.add(Calendar.MINUTE, 2);
	 			 }
	 		    now = cal.getTime();
	 			Timestamp ts=new Timestamp(now.getTime()); 
	 			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	                  
	 			row1.set("ORDER_DATE",formatter.format(ts));
	 			row1.set("RETURN_PRODUCT",false);
	 			
	 			DataObject db=new WritableDataObject();
	 			db.addRow(row1);
	        	DataAccess.add(db);
	        	DataObject d =per.get("User_Cart",(Criteria)null);
	        	Criteria c8 = new Criteria(new Column("User_Cart", "CART_ID"),data1.getInt("CART_ID"), QueryConstants.EQUAL);
				d.deleteRows("User_Cart",c8); 
				per.update(d);
//				Row r = d.getRow("User_Cart"); 
//				r.set("CART_STATUS",true); 
//				d.updateRow(r); 
//				per.update(d);
				Criteria c9 = new Criteria(new Column("Products", "PRODUCT_ID"),data1.getInt("PRODUCT_ID"), QueryConstants.EQUAL);
				DataObject d1 =per.get("Products",c9);
				Row r1 = d1.getRow("Products"); 
				r1.set("QUANTITY",admin_quantity); 
				d1.updateRow(r1); 
				per.update(d1);
				Criteria c10 = new Criteria(new Column("LoginUser", "USER_ID"),user_id, QueryConstants.EQUAL);
				if("wallet".equals(paymentMethod))
				{
				if(total>wallet)
				{
					total=total-wallet;
					wallet=0+cashback;
					DataObject d2 =per.get("LoginUser",c10);
					Row r2 = d2.getRow("LoginUser"); 
					r2.set("WALLET",wallet); 
					d2.updateRow(r2); 
					per.update(d2);
					//out.println("pay"+total+"in cash ");
				}
		        else
				{
					wallet=wallet-total+cashback;
					total=0;
					DataObject d2 =per.get("LoginUser",c10);
					Row r2 = d2.getRow("LoginUser"); 
					r2.set("WALLET",wallet); 
					d2.updateRow(r2); 
					per.update(d2);
					//out.println("Payment completed");
				}

		        }
				else if("pod".equals(paymentMethod))
				{
					wallet=wallet+cashback;
					DataObject d2 =per.get("LoginUser",c10);
					Row r2 = d2.getRow("LoginUser"); 
					r2.set("WALLET",wallet); 
					d2.updateRow(r2); 
					per.update(d2);
				//	out.println("pay"+total+"in cash ");
				}
		        }
		        else
		        {
		        	flag=1;
		        }
			}
			if(flag==0)
			{
				response.sendRedirect("welcome.jsp?total="+total);
			}
			else
			{
				response.sendRedirect("welcome.jsp?gotoPlaceOrder=1");

			}
		}catch(Exception e) {
			System.out.print(e);
		}
	}

}
