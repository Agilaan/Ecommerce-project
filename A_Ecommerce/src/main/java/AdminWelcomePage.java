

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.adventnet.db.api.RelationalAPI;
import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.GroupByClause;
import com.adventnet.ds.query.Join;
import com.adventnet.ds.query.Operation;
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
 * Servlet implementation class AdminWelcomePage
 */
@WebServlet("/AdminWelcomePage")
public class AdminWelcomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String func=request.getParameter("func");
		RelationalAPI api = RelationalAPI.getInstance();
		Connection con = null;
		if(func.equals("getProduct")) {
			String name=request.getParameter("name");
			int product_id=0;
			if(request.getParameter("product_id")!=null)
			{
			 product_id=Integer.parseInt(request.getParameter("product_id"));
			}
			String category=request.getParameter("category");
			int price=Integer.parseInt(request.getParameter("price"));
			String image=request.getParameter("image");
			String quantity=request.getParameter("quantity");
			int discount=Integer.parseInt(request.getParameter("discount"));
			int cp=Integer.parseInt(request.getParameter("cp"));
	        price=cp-(cp*discount/100);
			
			try
			{
				con = mConnection.getAvailableConnection();
				
				Persistence per= (Persistence)BeanUtil.lookup("Persistence");
         		if(product_id!=0)
				{
					UpdateQuery s = new UpdateQueryImpl("Products");
					Criteria c = new Criteria(new Column("Products", "PRODUCT_ID"),product_id, QueryConstants.EQUAL); 
					s.setCriteria(c);
					s.setUpdateColumn("PRODUCT_NAME",name); 
					s.setUpdateColumn("CATEGORY_ID",category); 
					s.setUpdateColumn("PRICE",price); 
					s.setUpdateColumn("PRODUCT_IMAGE",image); 
					s.setUpdateColumn("QUANTITY",quantity); 
					s.setUpdateColumn("DISCOUNT",discount); 
					s.setUpdateColumn("COSTPRICE",cp);
					per.update(s);
					
					response.getWriter().write("updated");
				}
				else if(product_id==0){
					
					Row row=new Row("Products");
					
		 			row.set("PRODUCT_NAME",name);
		 			row.set("CATEGORY_ID",category);
		 			row.set("PRICE",price);
		 			row.set("PRODUCT_IMAGE",image);
		 			row.set("QUANTITY",quantity);
		 			row.set("DISCOUNT",discount);
		 			row.set("COSTPRICE",cp);	 			
		 			DataObject data1=new WritableDataObject();
		 			data1.addRow(row);
		        	DataAccess.add(data1);
					
					response.getWriter().write("success");
				}
         		else {
         			response.getWriter().write("failed");
         		}
			}catch(Exception e)
			{
				
				e.printStackTrace();
			}
		}
		else if(func.equals("getCategory")) {
			String type=request.getParameter("type");
			String tag=request.getParameter("tag");
			int category_id=0;
			if(request.getParameter("category_id")!=null)
			{
			 category_id=Integer.parseInt(request.getParameter("category_id"));
			}
				
			try{
				con = mConnection.getAvailableConnection();	
				Persistence per= (Persistence)BeanUtil.lookup("Persistence");
				if(category_id!=0){
					UpdateQuery s = new UpdateQueryImpl("Product_Category");
					Criteria c = new Criteria(new Column("Product_Category", "CATEGORY_ID"),category_id, QueryConstants.EQUAL); 
					s.setCriteria(c);
					s.setUpdateColumn("TYPE",type); 
					s.setUpdateColumn("TAG",tag); 
					per.update(s);					
					response.getWriter().write("updated");
				}
				else{
					
					Row row=new Row("Product_Category");					
		 			row.set("TYPE",type);
		 			row.set("TAG",tag);		 			
		 			DataObject data1=new WritableDataObject();
		 			data1.addRow(row);
		        	DataAccess.add(data1);
					
					response.getWriter().write("success");
				}
		        con.close();

			}catch(Exception e){
				response.getWriter().write("failed");
				e.printStackTrace();
			}
		}
		else if(func.equals("getAdminHomePageDetails")) {
			
		
			DataSet data1,data2,data3,data4,data5,data6=null;
			
			
			try{
				JSONArray ja=new JSONArray();
				JSONArray ja1=new JSONArray();
				JSONArray ja2=new JSONArray();
				JSONArray ja3=new JSONArray();
				JSONArray ja4=new JSONArray();
				JSONArray ja5=new JSONArray();
				JSONObject json=new JSONObject();
				con = mConnection.getAvailableConnection();
				SelectQuery query1 = new SelectQueryImpl(new Table("LoginUser"));
				query1.addSelectColumn(Column.getColumn("LoginUser",  "*"));
				Criteria c1 = new Criteria(new Column("LoginUser", "STAR_MEMBER"),true, QueryConstants.EQUAL); 
				query1.setCriteria(c1);
				data1 = api.executeQuery(query1, con);
				while(data1.next()){
					JSONObject jo=new JSONObject();
					jo.put("user_name", data1.getString("USER_NAME"));
					jo.put("user_id", data1.getInt("USER_ID"));
					ja.add(jo);
			    }
				
				SelectQuery query2 = new SelectQueryImpl(new Table("OrderDetails"));
				query2.addSelectColumn(Column.getColumn("OrderDetails",  "*"));
				query2.addSelectColumn(Column.getColumn("Products",  "*"));
				query2.addSelectColumn(Column.getColumn("LoginUser",  "*"));
				Join join = new Join("OrderDetails", "Products", new String[]{"PRODUCT_ID"}, new String[]{"PRODUCT_ID"}, Join.INNER_JOIN);
	            query2.addJoin(join);
	            Join join1 = new Join("OrderDetails", "LoginUser", new String[]{"USER_ID"}, new String[]{"USER_ID"}, Join.INNER_JOIN);
	            query2.addJoin(join1);
				Criteria c2 = new Criteria(new Column("OrderDetails", "RETURN_PRODUCT"),true, QueryConstants.EQUAL); 
				query2.setCriteria(c2);
				data2 = api.executeQuery(query2, con);
				while(data2.next()){
					JSONObject jo1=new JSONObject();
					jo1.put("pid", data2.getInt("PRODUCT_ID"));
					jo1.put("product_name", data2.getString("PRODUCT_NAME"));
					jo1.put("user_name", data2.getString("USER_NAME"));
					jo1.put("return_reason", data2.getString("RETURN_REASON"));
					ja1.add(jo1);
			    }
				
				SelectQuery query3 = new SelectQueryImpl(new Table("Products"));
				query3.addSelectColumn(Column.getColumn("Products",  "*"));
				Criteria c3 = new Criteria(new Column("Products", "QUANTITY"),0, QueryConstants.EQUAL); 
				query3.setCriteria(c3);
				data3 = api.executeQuery(query3, con);
				while(data3.next()){
					JSONObject jo2=new JSONObject();
					jo2.put("pid", data3.getInt("PRODUCT_ID"));
					jo2.put("product", data3.getString("PRODUCT_NAME"));
					ja2.add(jo2);
			    }
				
				SelectQuery query_Category_wise = new SelectQueryImpl(new Table("Products"));
				Date now=new Date();
				Calendar cal = Calendar.getInstance();
				  cal.setTime(now);
				  cal.add(Calendar.DATE, -7);
				  now = cal.getTime();
				 Timestamp ts=new Timestamp(now.getTime()); 
				 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				 Criteria c1_Category_wise = new Criteria(new Column("OrderDetails", "ORDER_DATE"),formatter.format(ts).toString(), QueryConstants.GREATER_EQUAL); 
				 Criteria c2_Category_wise=new Criteria(new Column("OrderDetails","RETURN_PRODUCT"),0,QueryConstants.EQUAL);
				 Criteria c_Category_wise=c1_Category_wise.and(c2_Category_wise);
				 Column coll = Column.createOperation(Operation.operationType.MULTIPLY, Column.getColumn("OrderDetails", "PRICE"), Column.getColumn("OrderDetails", "QUANTITY"));
				coll.setColumnAlias("TOTAL");
				Column tot_col= coll.summation();
				tot_col.setColumnAlias("TOT_COUNT");
				query_Category_wise.addSelectColumn(tot_col);
				query_Category_wise.addSelectColumn(coll);
				query_Category_wise.setCriteria(c_Category_wise);			
				Join join_Category_wise = new Join("Products", "OrderDetails", new String[]{"PRODUCT_ID"}, new String[]{"PRODUCT_ID"}, Join.LEFT_JOIN);
				query_Category_wise.addJoin(join_Category_wise);
			    Join join1_Category_wise = new Join("Products", "Product_Category", new String[]{"CATEGORY_ID"}, new String[]{"CATEGORY_ID"}, Join.LEFT_JOIN);
			    query_Category_wise.addJoin(join1_Category_wise);
			    query_Category_wise.addSelectColumn(Column.getColumn("Products",  "CATEGORY_ID"));
			    query_Category_wise.addSelectColumn(Column.getColumn("Product_Category",  "TYPE"));
	            List groupList = new ArrayList(); 
	            groupList.add(new Column("Products","CATEGORY_ID")); 
	            groupList.add(new Column("Product_Category","TYPE"));
	            GroupByClause gr = new GroupByClause(groupList); 
	            query_Category_wise.setGroupByClause(gr);
	            data4= api.executeQuery(query_Category_wise, con);
	            int final_total=0;
				while(data4.next()) {
					JSONObject jo3=new JSONObject();
					final_total=final_total+Integer.parseInt(data4.getString("TOT_COUNT"));
	                jo3.put("category_id",data4.getValue("CATEGORY_ID"));
	                jo3.put("type", data4.getValue("TYPE"));
	                jo3.put("total", data4.getValue("TOT_COUNT"));
	                jo3.put("final_total", final_total);
	                ja3.add(jo3);
				}
				 Date now1=new Date();
				 Calendar cal1 = Calendar.getInstance();
				  cal1.setTime(now1);
				  cal1.add(Calendar.DATE, -1);
				  now1 = cal1.getTime();
				 Timestamp ts1=new Timestamp(now1.getTime()); 
				 Criteria c1_day = new Criteria(new Column("OrderDetails", "ORDER_DATE"),formatter.format(ts1).toString(), QueryConstants.GREATER_EQUAL); 
				 Criteria c_day=c1_day.and(c2_Category_wise);
				 query_Category_wise.setCriteria(c_day);
				 data5= api.executeQuery(query_Category_wise, con);
				 int day_final_total=0;
					while(data5.next()) {
						JSONObject jo4=new JSONObject();
						day_final_total=day_final_total+Integer.parseInt(data5.getString("TOT_COUNT"));						
		                jo4.put("category_id",data5.getValue("CATEGORY_ID"));
		                jo4.put("type", data5.getValue("TYPE"));
		                jo4.put("total", data5.getValue("TOT_COUNT"));
		                jo4.put("final_total", day_final_total);
		                ja4.add(jo4);
						
					}
					Date now2=new Date();
					 Calendar cal2 = Calendar.getInstance();
					  cal2.setTime(now2);
					  cal2.add(Calendar.DATE, -2);
					  now2 = cal2.getTime();
					 Timestamp ts2=new Timestamp(now2.getTime()); 
				   	 Criteria c1_yesturday = new Criteria(new Column("OrderDetails", "ORDER_DATE"),formatter.format(ts1).toString(), QueryConstants.LESS_THAN); 
				   	 Criteria c2_yesturday = new Criteria(new Column("OrderDetails", "ORDER_DATE"),formatter.format(ts2).toString(), QueryConstants.GREATER_THAN); 
					 Criteria c_yesturday=c1_yesturday.and(c2_Category_wise).and(c2_yesturday);
					 query_Category_wise.setCriteria(c_yesturday);
					 data6= api.executeQuery(query_Category_wise, con);
					 int yesturday_final_total=0;
					 while(data6.next()) {
						    JSONObject jo5=new JSONObject();
						    yesturday_final_total=yesturday_final_total+Integer.parseInt(data6.getString("TOT_COUNT"));						
			                jo5.put("category_id",data6.getValue("CATEGORY_ID"));
			                jo5.put("type", data6.getValue("TYPE"));
			                jo5.put("total", data6.getValue("TOT_COUNT"));
			                jo5.put("final_total", yesturday_final_total);
			                ja5.add(jo5);
					 }
					 
	            	json.put("out_of_stock", ja2);		
	            	json.put("returned_product", ja1);
	            	json.put("star_member", ja);
	            	json.put("weeks", ja3);
	            	json.put("days", ja4);
	            	json.put("yesturday", ja5);
				response.getWriter().print(json);
		       
			}catch(Exception e)
			{
			       System.out.println(e);
			}
			
		}
		else if(func.equals("getDisplayAdmin")) {
			
			DataSet data = null;
			DataSet data1 = null;		
			try
			{
				JSONArray pro=new JSONArray();
				JSONArray cat=new JSONArray();
				con = mConnection.getAvailableConnection();
				Persistence per= (Persistence)BeanUtil.lookup("Persistence");
				SelectQuery query = new SelectQueryImpl(new Table("Products"));
				query.addSelectColumn(Column.getColumn("Products", "*"));
				data = api.executeQuery(query, con);
				
				while(data.next())
				{
					JSONObject jo=new JSONObject();
					jo.put("cp", data.getInt("COSTPRICE"));
					jo.put("product_name", data.getString("PRODUCT_NAME"));
					jo.put("quantity", data.getInt("QUANTITY"));
					jo.put("price",data.getInt("PRICE"));
					jo.put("discount", data.getInt("DISCOUNT"));
					jo.put("product_id", data.getInt("PRODUCT_ID"));
					pro.add(jo);
				}

				SelectQuery query1 = new SelectQueryImpl(new Table("Product_Category"));
				query1.addSelectColumn(Column.getColumn("Product_Category", "*"));
				
				data1 = api.executeQuery(query1, con);
				
				while(data1.next())
				{
					JSONObject jo1=new JSONObject();
					
					jo1.put("type", data1.getString("TYPE"));
					jo1.put("tag", data1.getString("TAG"));
					jo1.put("category_id", data1.getInt("CATEGORY_ID"));
					cat.add(jo1);
				}
				
				
				JSONObject json=new JSONObject();
				json.put("product", pro);
				json.put("category", cat);
		        con.close();

				response.getWriter().print(json);
		}catch(Exception e)
			{
			     System.out.println(e);
			}
		}
		else if(func.equals("getPurchasedProduct")) {
			int total=0;
			DataSet data1 = null;
			try
			{
				con = mConnection.getAvailableConnection();
				JSONArray ja1=new JSONArray();
				JSONObject jo=new JSONObject();
				SelectQuery query1 = new SelectQueryImpl(new Table("OrderDetails"));
				query1.addSelectColumn(Column.getColumn("OrderDetails",  "*"));
				query1.addSelectColumn(Column.getColumn("Products",  "PRODUCT_NAME"));
				query1.addSelectColumn(Column.getColumn("LoginUser",  "USER_NAME"));
				Criteria c2 = new Criteria(new Column("OrderDetails", "RETURN_PRODUCT"),0, QueryConstants.EQUAL); 
				query1.setCriteria(c2);
				Join join = new Join("OrderDetails", "Products", new String[]{"PRODUCT_ID"}, new String[]{"PRODUCT_ID"}, Join.INNER_JOIN);
	            query1.addJoin(join);
	            Join join1 = new Join("OrderDetails", "LoginUser", new String[]{"USER_ID"}, new String[]{"USER_ID"}, Join.INNER_JOIN);
	            query1.addJoin(join1);
				data1 = api.executeQuery(query1, con);
				
				while(data1.next())
				{
					total=total+(data1.getInt("PRICE")*data1.getInt("QUANTITY"));
					JSONObject jo1=new JSONObject();
					jo1.put("order_id", data1.getInt("ORDER_ID"));
					jo1.put("user_name", data1.getValue("USER_NAME"));
					jo1.put("product_name", data1.getValue("PRODUCT_NAME"));
					jo1.put("price",data1.getInt("PRICE"));
					jo1.put("quantity",data1.getInt("QUANTITY"));
					jo1.put("date", data1.getAsDate("ORDER_DATE").toString());
					jo1.put("total", data1.getInt("PRICE")*data1.getInt("QUANTITY"));
					ja1.add(jo1);
					
				}
				jo.put("details", ja1);
				jo.put("total", total);
				response.getWriter().print(jo.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		}
		else if(func.equals("getPrintProduct")) {
			String product_id=request.getParameter("id");
			
			DataSet data,data1 = null;
			try
			{
				
				con = mConnection.getAvailableConnection();
				JSONArray ja=new JSONArray();
				JSONObject jo=new JSONObject();
				JSONObject jo2=new JSONObject();
				SelectQuery query = new SelectQueryImpl(new Table("Products"));
				query.addSelectColumn(Column.getColumn("Products",  "*"));
				Criteria c=new Criteria(new Column("Products", "PRODUCT_ID"),product_id, QueryConstants.EQUAL); 
				query.setCriteria(c);
				data = api.executeQuery(query, con);
				while(data.next()){
					
					jo.put("product_id", data.getInt("PRODUCT_ID"));
					jo.put("product_name", data.getString("PRODUCT_NAME"));
					jo.put("price", data.getInt("PRICE"));
					jo.put("quantity", data.getInt("QUANTITY"));
					jo.put("discount", data.getInt("DISCOUNT"));
					jo.put("cp", data.getInt("COSTPRICE"));
					
				}
				SelectQuery query1 = new SelectQueryImpl(new Table("Product_Category"));
				query1.addSelectColumn(Column.getColumn("Product_Category",  "*"));
				data1 = api.executeQuery(query1, con);
				while(data1.next()){
					JSONObject jo1=new JSONObject();
					jo1.put("category_id", data1.getInt("CATEGORY_ID"));
					jo1.put("type", data1.getString("TYPE"));
					ja.add(jo1);
				}
				jo2.put("product", jo);
				jo2.put("category", ja);
				response.getWriter().print(jo2);
		     }catch(Exception e) {
		    	 System.out.print(e);
		     }
		}
		else if(func.equals("getPrintCategory")) {
			String category_id=request.getParameter("category_id");
			
			DataSet data = null;
			try
			{
				
				con = mConnection.getAvailableConnection();
				SelectQuery query = new SelectQueryImpl(new Table("Product_Category"));
				query.addSelectColumn(Column.getColumn("Product_Category",  "*"));
				Criteria c=new Criteria(new Column("Product_Category", "CATEGORY_ID"),category_id, QueryConstants.EQUAL); 
				query.setCriteria(c);
				data = api.executeQuery(query, con);
				while(data.next())
				{
					JSONObject jo=new JSONObject();
					jo.put("category_id", data.getInt("CATEGORY_ID"));
					jo.put("type", data.getString("TYPE"));
					jo.put("tag", data.getString("TAG"));
					response.getWriter().print(jo);
				}
				
		     }catch(Exception e) {
		    	 System.out.print(e);
		     }
		}
		
		
	}

}
