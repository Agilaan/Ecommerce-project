

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.adventnet.db.api.RelationalAPI;
import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.GroupByClause;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.Table;
import com.adventnet.ds.query.UpdateQuery;
import com.adventnet.ds.query.UpdateQueryImpl;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Persistence;
import com.adventnet.persistence.Row;

/**
 * Servlet implementation class UserWelcomeServlet
 */
@WebServlet("/UserWelcomeServlet")
public class UserWelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RelationalAPI api = RelationalAPI.getInstance();
		Connection con = null;
		HttpSession session=request.getSession();  
		String user_id=session.getAttribute("user_id").toString();
		String func=request.getParameter("func");
		if(func.equals("getUserDetails")) {
			PrintWriter out=response.getWriter();
			String user_name="";
			DataSet data1=null;
		    DataObject dataobject=null;
		    boolean star_member;
		    try
			{
				Persistence per = (Persistence)BeanUtil.lookup("Persistence");
				con = mConnection.getAvailableConnection();	
				SelectQuery query1 = new SelectQueryImpl(new Table("LoginUser"));
				query1.addSelectColumn(Column.getColumn("LoginUser",  "*"));
				Criteria c2 = new Criteria(new Column("LoginUser", "USER_ID"),user_id, QueryConstants.EQUAL); 
				query1.setCriteria(c2);
				data1 = api.executeQuery(query1, con);	      
				while(data1.next()){
					Date now=new Date();
					Timestamp ts=new Timestamp(now.getTime());
					if(data1.getAsTimestamp("MEMBER_TIME")!=null) {
					Timestamp now1=data1.getAsTimestamp("MEMBER_TIME");
					if(now1.compareTo(ts)<0){					
						UpdateQuery s = new UpdateQueryImpl("LoginUser");
						s.setCriteria(c2);
						s.setUpdateColumn("STAR_MEMBER",false);
						per.update(s);
						
					}
					}
				}
				
				SelectQueryImpl squery4=new SelectQueryImpl(new Table("LoginUser"));
				squery4.addSelectColumn(Column.getColumn("LoginUser",  "*"));
				Criteria c1=new Criteria(new Column("LoginUser", "USER_ID"),user_id, QueryConstants.EQUAL);
				squery4.setCriteria(c1);
		        dataobject = per.get((SelectQuery)squery4);
		        System.out.println(dataobject);
	            Row row=dataobject.getFirstRow("LoginUser");
		        user_name=row.getString("USER_NAME");
		        star_member=row.getBoolean("STAR_MEMBER");

		        JSONObject json = new JSONObject();  
		        json.put("user_name", user_name);
		        json.put("star_member", star_member);
		        json.put("wallet", row.getInt("WALLET"));
		        if(row.getBoolean("IS_ADMIN")) {
		        	json.put("is_admin", true);
		        }else{
		        	json.put("is_admin", false);
		        }
		        out.print(json.toString());
		        con.close();
			}catch(Exception e)
			{
				System.out.println("UserDetails"+e);
			}
		}
		else if(func.equals("getReviewSection")) {
			
			String product_id=request.getParameter("product_id");
			DataSet data1,data2,data3=null;
			try {
				JSONArray ja1=new JSONArray();
				JSONArray ja2=new JSONArray();

				JSONObject json=new JSONObject();
				boolean edit=false;
				con = mConnection.getAvailableConnection();
				SelectQuery query1 = new SelectQueryImpl(new Table("Review"));
				query1.addSelectColumn(Column.getColumn("Review",  "*"));
				Criteria c1 = new Criteria(new Column("Review", "USER_ID"),user_id, QueryConstants.EQUAL); 
				Criteria c2 = new Criteria(new Column("Review", "PRODUCT_ID"),product_id, QueryConstants.EQUAL); 
				Criteria c=c1.and(c2);
				query1.setCriteria(c);
				data1 = api.executeQuery(query1, con);
				while(data1.next()){
					edit=true;
					json.put("editable", 1);
					json.put("review_id", data1.getInt("REVIEW_ID"));					
					json.put("own_comment", data1.getString("COMMENT"));
				    json.put("rating", data1.getInt("RATING"));
				}
				if(edit==false) {
					json.put("editable", 0);
				}
				SelectQuery query2 = new SelectQueryImpl(new Table("Review"));
				query2.addSelectColumn(Column.getColumn("Review",  "REVIEW_ID"));
				query2.addSelectColumn(Column.getColumn("Review",  "RATING"));
				query2.addSelectColumn(Column.getColumn("Review",  "COMMENT"));
				query2.addSelectColumn(Column.getColumn("Review",  "VERIFIED"));
				query2.addSelectColumn(Column.getColumn("LoginUser",  "USER_NAME"));

				Join join2 = new Join("Review", "LoginUser", new String[]{"USER_ID"}, new String[]{"USER_ID"}, Join.LEFT_JOIN);
	            query2.addJoin(join2);
	            
	            
	            query2.setCriteria(c2);
				data2 = api.executeQuery(query2, con);
				while(data2.next()) {
					JSONObject jo=new JSONObject();
//					if(data2.getAsBoolean("VERIFIED")) {
//						jo.put("verified", true);
//					}else {
//						jo.put("verified", false);
//					}
					jo.put("verified", data2.getAsBoolean("VERIFIED"));
					jo.put("rID", data2.getInt("REVIEW_ID"));		
					jo.put("comment", data2.getString("COMMENT"));
					jo.put("rate", data2.getInt("RATING"));
					jo.put("uname", data2.getString("USER_NAME"));
					ja1.add(jo);
				}
				SelectQuery query3 = new SelectQueryImpl(new Table("Products"));
				query3.addSelectColumn(Column.getColumn("Products",  "*"));
				query3.addSelectColumn(Column.getColumn("Product_Category",  "*"));
				Join join1 = new Join("Products", "Product_Category", new String[]{"CATEGORY_ID"}, new String[]{"CATEGORY_ID"}, Join.INNER_JOIN);
	            query3.addJoin(join1);
				Criteria c3 = new Criteria(new Column("Products", "PRODUCT_ID"),product_id, QueryConstants.EQUAL); 
	            query3.setCriteria(c3);
	            System.out.println(query3);
				data3 = api.executeQuery(query3, con);
				while(data3.next()) {
					JSONObject jo1=new JSONObject();
					jo1.put("type", data3.getString("TYPE"));
					jo1.put("tag", data3.getString("TAG"));
					jo1.put("product_name", data3.getString("PRODUCT_NAME"));
					jo1.put("product_image", data3.getString("PRODUCT_IMAGE"));
					ja2.add(jo1);
				}
				
				json.put("review", ja1);
				json.put("product_details", ja2);
				response.getWriter().print(json);
			}
			 catch (Exception e) 
			{
				
				e.printStackTrace();
			}
		}
		else if(func.equals("toDisplayDetails")) {
			
			int star_discount=Integer.parseInt(request.getParameter("star_discount"));
			
			DataSet data = null;
			DataSet data2 = null;
			
			try
			{
				JSONArray ja=new JSONArray();
				con = mConnection.getAvailableConnection();	
				Persistence per= (Persistence)BeanUtil.lookup("Persistence");
				SelectQuery query = new SelectQueryImpl(new Table("Products"));
				query.addSelectColumn(Column.getColumn("Products", "*"));
				query.addSelectColumn(Column.getColumn("Product_Category", "*"));
				Join join = new Join("Products", "Product_Category", new String[]{"CATEGORY_ID"}, new String[]{"CATEGORY_ID"}, Join.INNER_JOIN);
	            query.addJoin(join);
				Criteria c1 = new Criteria(new Column("Products", "QUANTITY"),0, QueryConstants.NOT_EQUAL); 
				query.setCriteria(c1);
				data = api.executeQuery(query, con);	
				while(data.next())
				{	
					JSONObject jo=new JSONObject();
					SelectQuery query2 = new SelectQueryImpl(new Table("Review"));
					Criteria c2 = new Criteria(Column.getColumn("Review", "PRODUCT_ID"),data.getInt("PRODUCT_ID"), QueryConstants.EQUAL); 
					Column col1=new Column("Review","PRODUCT_ID");
					Column Pcount=col1.count();
					Column col2=new Column("Review","RATING");
					Column Pavg=col2.average();
					Pcount.setColumnAlias("P_COUNT");
					Pavg.setColumnAlias("P_AVG");
					query2.addSelectColumn(col1);
					query2.addSelectColumn(Pcount);
					query2.addSelectColumn(col2);
					query2.addSelectColumn(Pavg);
					query2.setCriteria(c2);
					data2=api.executeQuery(query2, con);
					data2.next();
					int count=0;
					int avg=0;
					if(data2.getValue("P_COUNT")!=null) {
					  count=(int)data2.getValue("P_COUNT");
					}
					if(data2.getValue("P_AVG")!=null) {
					  avg=(int)data2.getValue("P_AVG");
					}
					jo.put("average", avg);
					jo.put("count", count);
					jo.put("product_id", data.getInt("PRODUCT_ID"));
					jo.put("product_name", data.getString("PRODUCT_NAME"));
					jo.put("type", data.getString("TYPE"));
					jo.put("tag", data.getString("TAG"));
					int price=data.getInt("PRICE");
					jo.put("price",price);
					int discount_price=price-(price*star_discount/100);
					jo.put("discount_price",discount_price);
					jo.put("product_img", data.getString("PRODUCT_IMAGE"));
					
					ja.add(jo);
				}
		        con.close();

				response.getWriter().print(ja.toString());
		}catch(Exception e)
			{
			     System.out.println("hi hello"+e);
			}

			
		}
		else if(func.equals("getDisplayCart")) {
			
			
			DataSet data = null;
			DataSet data1=null;
			int total=0;
			
			try
			{
				JSONArray ja=new JSONArray();
				con = mConnection.getAvailableConnection();	
				Persistence per= (Persistence)BeanUtil.lookup("Persistence");
				SelectQuery query = new SelectQueryImpl(new Table("User_Cart"));
				query.addSelectColumn(Column.getColumn("User_Cart",  "*"));
				Criteria c1 = new Criteria(new Column("User_Cart", "USER_ID"),user_id, QueryConstants.EQUAL); 
				query.setCriteria(c1);
				data = api.executeQuery(query, con);
				while(data.next())
				{
					total=total+(data.getInt("PRICE")*data.getInt("QUANTITY"));
				}
				SelectQuery query1 = new SelectQueryImpl(new Table("User_Cart"));
				query1.addSelectColumn(Column.getColumn("User_Cart",  "*"));
				query1.addSelectColumn(Column.getColumn("Products",  "PRODUCT_NAME"));
				Join join = new Join("User_Cart", "Products", new String[]{"PRODUCT_ID"}, new String[]{"PRODUCT_ID"}, Join.INNER_JOIN);
	            query1.addJoin(join);
				
				query1.setCriteria(c1);
				
				data1 = api.executeQuery(query1, con);
				
				while(data1.next())
				{
					JSONObject jo=new JSONObject();
					
					jo.put("product_id", data1.getInt("PRODUCT_ID"));
					jo.put("product_name", data1.getString("PRODUCT_NAME"));
					jo.put("quantity", data1.getInt("QUANTITY"));
					jo.put("price",data1.getInt("PRICE"));
					jo.put("total", total);
					jo.put("cart_id", data1.getInt("CART_ID"));
					ja.add(jo);

								}
		        con.close();

				response.getWriter().print(ja.toString());
		}catch(Exception e)
			{
			
			}		
		}
		else if(func.equals("getDisplayPurchase")) {
			
			DataSet data = null;
			DataSet data1=null;
			DataSet data2=null;
			DataObject dataobject=null;
			int total=0;
			int order_id=0;
			boolean star_member=false;
			try
			{
				JSONArray ja1=new JSONArray();
				con = mConnection.getAvailableConnection();	
				Persistence per= (Persistence)BeanUtil.lookup("Persistence");
				SelectQuery query = new SelectQueryImpl(new Table("OrderDetails"));
				query.addSelectColumn(Column.getColumn("OrderDetails",  "*"));
				Criteria c1 = new Criteria(new Column("OrderDetails", "USER_ID"),user_id, QueryConstants.EQUAL); 
				query.setCriteria(c1);
				
				data = api.executeQuery(query, con);
				while(data.next()){
					total=total+(data.getInt("PRICE")*data.getInt("QUANTITY"));
				}
				SelectQuery squery = new SelectQueryImpl(new Table("LoginUser"));
				squery.addSelectColumn(Column.getColumn("LoginUser",  "*"));
				Criteria sc = new Criteria(new Column("LoginUser", "USER_ID"),user_id, QueryConstants.EQUAL); 
				squery.setCriteria(sc);
				dataobject = per.get((SelectQuery)squery);
	            Row row=dataobject.getFirstRow("LoginUser");
		        star_member=row.getBoolean("STAR_MEMBER");
				SelectQuery query1 = new SelectQueryImpl(new Table("OrderDetails"));
				query1.addSelectColumn(Column.getColumn("OrderDetails",  "*"));
				query1.addSelectColumn(Column.getColumn("Products",  "PRODUCT_NAME"));
				query1.addSelectColumn(Column.getColumn("PurchasedDetails",  "PURCHASED_DATE"));
				Join join = new Join("OrderDetails", "Products", new String[]{"PRODUCT_ID"}, new String[]{"PRODUCT_ID"}, Join.INNER_JOIN);
	            query1.addJoin(join);
	            Join join1=new Join("OrderDetails","PurchasedDetails",new String[]{"PURCHASE_ID"}, new String[]{"PURCHASE_ID"}, Join.INNER_JOIN);
	            query1.addJoin(join1);
				Criteria c2 = new Criteria(new Column("OrderDetails", "USER_ID"),user_id, QueryConstants.EQUAL); 
				query1.setCriteria(c2);
				data1 = api.executeQuery(query1, con);
				while(data1.next()){
					JSONObject jo1=new JSONObject();
					
					jo1.put("product_name", data1.getString("PRODUCT_NAME"));
					jo1.put("quantity", data1.getInt("QUANTITY"));
					jo1.put("price",data1.getInt("PRICE"));
					jo1.put("total", total);
					jo1.put("order_id", data1.getInt("ORDER_ID"));
					jo1.put("star_member", star_member);
					Date now=new Date();
					Timestamp ts=new Timestamp(now.getTime());
					Timestamp now1=data1.getAsTimestamp("PURCHASED_DATE");
					//Timestamp ts1=new Timestamp(now1.getTime());
					if(now1.compareTo(ts)>0){
						jo1.put("is_returnable", 1);
					}
					else {
						jo1.put("is_returnable", 0);
					}
						jo1.put("returned", data1.getAsBoolean("RETURN_PRODUCT"));
					ja1.add(jo1);
				}
		        con.close();

				    response.getWriter().print(ja1.toString());
		 
		}catch(Exception e)
			{
		     	System.out.println(e);
			}
		}else if(func.equals("getAllUserDetails")) {
			PrintWriter out=response.getWriter();
			DataSet data1=null;
		    try
			{
		    	JSONArray ja = new JSONArray(); 
				Persistence per = (Persistence)BeanUtil.lookup("Persistence");
				con = mConnection.getAvailableConnection();	
				SelectQuery query1 = new SelectQueryImpl(new Table("LoginUser"));
				query1.addSelectColumn(Column.getColumn("LoginUser",  "*"));
				Criteria c = new Criteria(new Column("LoginUser", "IS_ADMIN"),false, QueryConstants.EQUAL); 
				query1.setCriteria(c);
				data1 = api.executeQuery(query1, con);	      
				while(data1.next()){
					JSONObject json = new JSONObject();  
			        json.put("user_name", data1.getValue("USER_NAME"));
			        json.put("user_id", data1.getValue("USER_ID"));
					ja.add(json);
					}	
		        out.print(ja.toString());
		        con.close();
			}catch(Exception e)
			{
				System.out.println("UserDetails"+e);
			}
		}
	
		
	}

}
