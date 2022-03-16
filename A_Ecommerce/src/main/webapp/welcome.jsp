<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome To Ecommerce Website</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link rel = "stylesheet" href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">  
<link href="style.css" rel="stylesheet" type="text/css"/>
</head>
<style>  
    .fa-star {  
        font-size : 15px;  
        align-content: left;  
    }  
   
    </style> 
<body>
<center><h1>Ecommerce Website</h1></center>
<div id="header_page">
	<div class="bgimage">
		<div class="topnav">
		        
			    <a id="user" style="cursor:pointer;" onclick="toStarMember()"></a>
				<a id="home" style="cursor:pointer;" onclick="toHome()">Home</a>
				<a id="cart" style="cursor:pointer;" onclick="toCart()">My cart</a>
				<a id="history" style="cursor:pointer;" onclick="toPurchaseHistory()">Purchase History</a>
				<a id="logout" style="cursor:pointer;" href="logout.jsp">Logout</a>
			    <a style="float:right" id="admin_page" style="cursor:pointer;" href="admin\AdminWelcome.jsp">Admin Page</a>
			    <a style="float:right" id="user_info_page" onclick="toUserInfo()" style="cursor:pointer;">User Details</a>
			    <i style="float:right; color:red">WALLET MONEY: <i id="wallet_money"></i> </i>
				<br><br><br>
		</div>
	</div>
</div>
<div id="home_page">
<div class="bgimage">
	<div class="topnav">
		<div id="searchbar" class="search-container">
			<input type="text" name="search_with_name" id="search_with_name">
		<button type="submit" id="search_button" >Search</button>
		<button id="clear_search" >Clear Search</button>
		</div>
	</div>
	</div>
	<br><br><br>
	<input type="hidden" id="discount_percentage" value="0"/>
	<div id="Product_List_index"></div>
	<div id ="dummy_box_index" class="box-view">
	<div>
			<div class="inside">
				<a class="image"> <img id ="img" src="" style="cursor:pointer;" onclick="description(this.id)"></a>
			    
			    <a><a id="pname"></a></a>
				<p><strike><a id="price"></a></strike><a>  </a><a id="dis_price"></a>$</p>
				<form action="CartHandling" method="POST">
					<p>
						<input type="number" id="quantity" name="quantity"  value="1" placeholder="1" min="1" max="10" />
					</p>
					<input name="star_discount" id="star_discount" value="" type="hidden" />
					<input name="product_id" id="product_id" value="" type="hidden" /> 
					<input name="star_price" id="star_price" value="0" type="hidden" />
					<button type="submit" onclick="">Add to cart</button>
				</form>
			<br>
	        <div style="float:left;" class = "pstar_rating" id="pstar_rating">
					       <i class = "fa fa-star"  aria-hidden = "true" id = "pstar1"></i>  
					       <i class = "fa fa-star"  aria-hidden = "true" id = "pstar2"></i>  
					       <i class = "fa fa-star"  aria-hidden = "true" id = "pstar3"></i>  
					       <i class = "fa fa-star"  aria-hidden = "true" id = "pstar4"></i>  
					       <i class = "fa fa-star"  aria-hidden = "true" id = "pstar5"></i>  
				   <a id="count_review"></a>
				   <br>
			   </div> 
	        </div>
	        
	   </div>
		</div>
		<div id="myModal" >
	        <div id="modal-content" class="modal-content">
	      <a>  <img id ="purticular_img" style="float:left;" src=""> </a> <br><br>
	           <a style="float:right; color:red; cursor:pointer;"  class="fa fa-close" onclick="close_box()"></a>
	     <center>      <h3>PRODUCT DESCRIPTION</h3>
	                  <b>Product Name:  </b><a id="ppname"></a><br>
	                  <b>Category:  </b><a id="pptype"></a><br>
	                  <b>Tag:  </b><a id="pptag"></a></center> 
               <p>Reviews</p>
               <div class = "con" id="own_str"> 
	                <a id="own_cmt"></a>  
					        <i class = "fa fa-star"  aria-hidden = "true" id = "str1"></i>  
					       <i class = "fa fa-star"  aria-hidden = "true" id = "str2"></i>  
					       <i class = "fa fa-star"  aria-hidden = "true" id = "str3"></i>  
					       <i class = "fa fa-star"  aria-hidden = "true" id = "str4"></i>  
					       <i class = "fa fa-star"  aria-hidden = "true" id = "str5"></i>  
				   <a id="edit_review"></a>
			   </div> 
			   <div id="all_cmt">
               <div id="cmt">
               <a id="list_of_review"></a> <a>        </a> 
              			   <i class = "fa fa-star"  aria-hidden = "true" id = "star1"></i>  
					       <i class = "fa fa-star"  aria-hidden = "true" id = "star2"></i>  
					       <i class = "fa fa-star"  aria-hidden = "true" id = "star3"></i>  
					       <i class = "fa fa-star"  aria-hidden = "true" id = "star4"></i>  
					       <i class = "fa fa-star"  aria-hidden = "true" id = "star5"></i> 
					     <a>        </a>   <i id="verification"></i>
               </div>
               </div>
	           <div class = "star_container">  
			       
			       <div class = "con" id="st">  
			           <h4>Give your rating for the product and add your comment</h4>  
				       <i class = "fa fa-star" style="cursor:pointer;" aria-hidden = "true" id = "st1"></i>  
				       <i class = "fa fa-star" style="cursor:pointer;" aria-hidden = "true" id = "st2"></i>  
				       <i class = "fa fa-star" style="cursor:pointer;" aria-hidden = "true" id = "st3"></i>  
				       <i class = "fa fa-star" style="cursor:pointer;" aria-hidden = "true" id = "st4"></i>  
				       <i class = "fa fa-star" style="cursor:pointer;" aria-hidden = "true" id = "st5"></i>  
			       </div>  
    		  </div>
    		  <form action="reviewProduct" method="post" id="review_form">
    		  <input name="pID" id="pID" value="0" type="hidden"/>
    		  <input name="rating" id="rating" value="0" type="hidden"/>
              <input type="text" name="comment" id="comment" value=""/>
              <button type="submit">Submit</button>
              </form>
	        </div>
        </div>
</div>


<div id="cart_page">
	<table>
	<thead>
	<tr>
	<th scope="col" style="background-color: green;">Total:<a id="final_total"></a></th>
	<th scope="col"><a style="cursor:pointer;" onclick="toPlaceOrder()">Place order</a></th>
	</tr>
	</thead>
	
	<thead>
	<tr>
	    <th scope="col">S.No</th>
		<th scope="col">Product Name</th>
		<th scope="col">Price</th>
		<th scope="col">Quantity</th>
		<th scope="col">Sub total</th>
		<th scope="col">Remove</th>	
	</tr>
	</thead>
	
	<tbody id="Product_List_cart">
	<tr id="dummy_box_cart">
	    <td><a id="sno"></a></td>
		<td id="pname"></td>
		<td id="price"></td>
		<td > <a id="inc" href="" style="text-decoration:none;"> + </a> <a id="quantity"></a>  <a id="dec" href="" style="text-decoration:none;"> - </a></td>
		<td id="total"></td>
	<%-- 	<td id="remove"> <a id="removetag" href="">Remove</a></td> --%>
	    <td> <form id="removetag" action="CartHandling" method="post"><input type="hidden" name="id" id="id"/><button type="submit">Remove</button> </form> </td>
	</tr>	
	</tbody>
	
	</table>
</div>

<div id="placeOrder_page">
	<table>
	<thead>
	<tr>
	<th scope="col" style="background-color: green;">Total:<a id="final_total_placeOrder"></a></th>
	
	</tr>
	</thead>
	
	<thead>
	<tr>
	    <th scope="col">S.No</th>
		<th scope="col">Product Name</th>
		<th scope="col">Price</th>
		<th scope="col">Quantity</th>
		<th scope="col">Sub total</th>
		
	</tr>
	</thead>
	
	<tbody id="Product_List_placeOrder">
	
	
	
	<tr id="dummy_box_placeOrder">
	    <td><a id="sno"></a></td>
		<td id="pname"></td>
		<td id="price"></td>
		<td > <a id="quantity"></a> </td>
		<td id="total"></td>
		
	
	</tr>
	
	</tbody>
	
	</table>
	<form action="placeOrderAction" method="Post">
		
			<div >
				<h3>Enter Delivery Address</h3>
				<input type="text" name="address" required>
			</div>
			<div>
			   <h3>Enter the Payment Method</h3>
			<label>  <input type="radio" name="paymentMethod" id="pod" value="pod">pay on delivary</label> 
	        <label>  <input type="radio" name="paymentMethod" id="wallet" value="wallet"> use wallet money</label>
			</div>
			
			
			<center><button class="button" type="submit" style="font-size:20px">Place the Order</button></center>
	</form>
</div>
<div id="purchaseHistory_page">
	<table>
	<thead>
	<tr>
	<th scope="col" style="background-color: green;">Total:<a id="total_purchaseHistory"></a></th>
	</tr>
	</thead>
	<thead>
	<tr>
	    <th scope="col">S.No</th>
		<th scope="col">Product Name</th>
		<th scope="col">Price</th>
		<th scope="col">Quantity</th>
		<th scope="col">Sub total</th>
		<th scope="col">return</th>		
	</tr>
	</thead>
	<tbody id="Product_List_purchase">
	<tr id="dummy_box_purchase"> 
	    <td id="sno"></td>
		<td id="pname"></td>
		<td id=price></td>
		<td id="quantity"></td>
		<td id="sub_total"></td>
        <td ><button id="preturn"></button></td></tr>
	</tbody>
	</table>
</div>


<div id="returnProduct_page">
	<form action="ReturnAction" method="post">
	
	<h4>Enter the reason for Return</h4>
	
	<select name="reason" id="reason1" onchange="changeDropdown()">
	<option value="1">others</option>
	<option value="Not as expected">Not as expected</option>
	<option value="Not upto the price">Not upto the price</option>
	<option value="Damaged">Damaged</option>
	
	</select>
	<div id="explain">
	<input  name="othersReason" type="text" />
	</div>
	<input type="hidden" name="order_id" id="order_id_return" value=""/>
	<br>
	<button type="submit">Submit</button>
	
	</form>
</div>

<div id="starMember_page">
	<h1>The benefits of becoming a Star member</h1>
	<ul>
	  <li>You would get a discount in all electronics product of 5%-10%</li>
	  <li>Return time will be extended to 5 Minutes</li>
	  <li>If your purchase is more than 2000 then you would a cashback of 5% of your total Purchase</li>
	</ul>
	
	<form action="starMemberAction" method="post">
	<center><button type="submit" id="become_member">Become a star member</button></center>
	</form>
</div>
<div id="editReview_page">
   <div class = "star_container">      
			       <div class = "con" id="st">  
			           <h4>Edit your rating for the product and add your comment</h4>  
				       <i class = "fa fa-star" style="cursor:pointer;" aria-hidden = "true" id = "s1"></i>  
				       <i class = "fa fa-star" style="cursor:pointer;" aria-hidden = "true" id = "s2"></i>  
				       <i class = "fa fa-star" style="cursor:pointer;" aria-hidden = "true" id = "s3"></i>  
				       <i class = "fa fa-star" style="cursor:pointer;" aria-hidden = "true" id = "s4"></i>  
				       <i class = "fa fa-star" style="cursor:pointer;" aria-hidden = "true" id = "s5"></i>  
			       </div>  
    		  </div>
	  <form action="reviewProduct" method="post" id="review_form">
    		  <input name="pID" id="edit_review_pID" value="0" type="hidden"/>
    		  <input name="review_id" id="edit_review_review_id" value="0" type="hidden"/>
    		  <input name="rating" id="edit_review_rating" value="0" type="hidden"/>
              <input type="text" name="comment" id="comment" value=""/>
              <button type="submit">Submit</button>
      </form>
</div>
<div id="non_admin_user_page">
<table>
	<thead>
	<tr>
		<th scope="col">User Name</th>
		<th scope="col">Make as Admin</th>	
			
	</tr>
	</thead>
	<tbody id="Product_List_user">
	<tr id="dummy_box_user"> 
		<td id="uname"></td>
   	    <td> <form id="makeAdmin" action="MakeAdmin" method="post"><input type="hidden" name="user_id" id="user_id"/><button type="submit">Make Admin</button> </form> </td>
	</tbody>
	</table>
</div>
<script>
var product_data;
var search_data;
var dd;
function getUser(){
	
	$.post("UserWelcomeServlet", {
		func:"getUserDetails"
	}, function(data, status) {
		
		 data = JSON.parse(data);
		if (data.star_member) {
			jQuery("#user").addClass("star");
			jQuery("#become_member").hide();
			var today = new Date();
			dd = String(today.getDate());
			if (dd > 1 && dd < 5) {
				dd = 5;
			}
			if (dd > 15) {
				dd = dd / 2;
			}
		}else{
			dd=0;
		}
		jQuery("#discount_percentage").val(dd);
		jQuery("#user").text(data.user_name);
		
		$("#admin_page").hide();
		$("#user_info_page").hide();
		$("#wallet_money").text(data.wallet);
		if(data.is_admin==1){
			$("#admin_page").show();
			$("#user_info_page").show();
		}
		display();
	});
	
}
function getAllUser(){
	$.post("UserWelcomeServlet", {
		func:"getAllUserDetails"
	}, function(data, status) {
		console.log(data);
		data = JSON.parse(data);
		for(var i=0;i<data.length;i++) {
			var $row = jQuery("#dummy_box_user").clone().attr('id',data[i].user_id);
			
			$row.find("#uname").text(data[i].user_name);
			$row.find("#user_id").val(data[i].user_id);
			jQuery("#Product_List_user").append($row);
	}
		$("#dummy_box_user").css('visibility','hidden');
});
}
function close_box(){
	 $("#myModal").hide();
	 $("#myModal").removeClass("modal");
}
function description(pID){
  $("#myModal").show();
 
  $("#pID").val(pID);
  $.post("UserWelcomeServlet", {
	  func:"getReviewSection",
	  product_id : pID
	}, function(data, status) {			
		data = JSON.parse(data);
		console.log(data);
		if(data.editable==1){
            
			$("#st").css('visibility','hidden');
			$("#review_form").css('visibility','hidden');
			$("#own_cmt").text(data.own_comment)
			$("#edit_review").text("Edit");
			//$("#edit").attr('href',"editReview.jsp?review_id="+data.review_id+"&product_id="+pID);
			var button = $("#edit_review");
			button.click(function(event){
				console.log(event);
				$("#edit_review_pID").val(pID);
				$("#edit_review_review_id").val(data.review_id);
				toEditReview();
			});
			//$("#own_cmt").css('visibility','visible');
			$("#own_str").css('visibility','visible');
			if(data.rating==1){
				   $("#own_str .fa-star").css("color", "black");  
		           $("#own_str #str1").css("color", "gold"); 
			}
			else if(data.rating==2){
				  $("#own_str .fa-star").css("color", "black");  
	              $("#own_str #str1,#own_str #str2").css("color", "gold"); 
			}
			else if(data.rating==3){
				  $("#own_str .fa-star").css("color", "black");  
	              $("#own_str #str1,#own_str #str2,#own_str #str3").css("color", "gold"); 
			}else if(data.rating==4){
				  $("#own_str .fa-star").css("color", "black");  
	              $("#own_str #str1,#own_str #str2,#own_str #str3,#own_str #str4").css("color", "gold"); 
			}else if(data.rating==5){
				  $("#own_str .fa-star").css("color", "black");  
	              $("#own_str #str1,#own_str #str2,#own_str #str3,#own_str #str4,#own_str #str5").css("color", "gold"); 
			}
			
			
		}else if(data.editable==0){
				$("#own_str").css('visibility','hidden');
			$("#st").css('visibility','visible');
			$("#review_form").css('visibility','visible');
			}
		$("#purticular_img").attr("src",data.product_details[0].product_image);
		$("#ppname").text(data.product_details[0].product_name);
		$("#pptype").text(data.product_details[0].type);
		$("#pptag").text(data.product_details[0].tag);
		$("#all_cmt").css("visibility","hidden");
		for(var i=0;i<data.review.length;i++) {
			$("#all_cmt").css("visibility","visible");
			var $row = jQuery("#cmt").clone().attr('id',data.review[i].rID);
			   $row.find("#list_of_review").text(data.review[i].uname+" : "+data.review[i].comment);
			   if(data.review[i].verified){
				   $row.find("#verification").text("(verified)");
			   }
			  if(data.review[i].rate==1){
				   $row.find("#cmt .fa-star").css("color", "black");  
				   $row.find("#star1").css("color", "gold"); 
			}
			else if(data.review[i].rate==2){
				$row.find("#cmt .fa-star").css("color", "black");  
				$row.find("#star1,#star2").css("color", "gold"); 
			}
			else if(data.review[i].rate==3){
				$row.find("#cmt .fa-star").css("color", "black");  
				$row.find("#star1,#star2,#star3").css("color", "gold"); 
			}else if(data.review[i].rate==4){
				$row.find("#cmt .fa-star").css("color", "black");  
				$row.find("#star1,#star2,#star3,#star4").css("color", "gold"); 
			}else if(data.review[i].rate==5){
				$row.find("#cmt .fa-star").css("color", "black");  
				$row.find("#star1,#star2,#star3,#star4,#star5").css("color", "gold"); 
			}
				jQuery("#all_cmt").append($row);
		}
		$("#cmt").css("visibility","hidden");
		
		 $("#myModal").addClass("modal");
	});
  
  
  $("#st1").dblclick(function(){
	   $(".con .fa-star").css("color", "black");
	   $("#rating").val(0);
  });
  $("#st1").click(function() {  
      $(".con .fa-star").css("color", "black");  
      $("#st1").css("color", "yellow");  
      $("#rating").val(1);
  });  
  $("#st2").click(function() {  
      $(".con .fa-star").css("color", "black");  
      $("#st1, #st2").css("color", "yellow");  
      $("#rating").val(2);
  });  
  $("#st3").click(function() {  
      $(".con .fa-star").css("color", "black")  
      $("#st1, #st2, #st3").css("color", "yellow");  
      $("#rating").val(3);
  });  
  $("#st4").click(function() {  
      $(".con .fa-star").css("color", "black");  
      $("#st1, #st2, #st3, #st4").css("color", "yellow");  
      $("#rating").val(4);
  });  
  $("#st5").click(function() {  
      $(".con .fa-star").css("color", "black");  
      $("#st1, #st2, #st3, #st4, #st5").css("color", "yellow");  
      $("#rating").val(5);
  });  
  
}
$("#search_button").click(function(event){
	console.log(event);
	search_data=$("#search_with_name").val();
	for(var i=0;i<product_data.length;i++){
		if((product_data[i].product_name.search("\\b"+search_data)<0)&&(product_data[i].type.search("\\b"+search_data)<0)&&(product_data[i].tag.search("\\b"+search_data)<0)){
			$("#"+product_data[i].product_id).hide();
			$("#clear_search").show();
		}
	}
 });
$("#clear_search").click(function(event){
	console.log(event);
	$("#search_with_name").val("");
	for(var i=0;i<product_data.length;i++){
			$("#"+product_data[i].product_id).show();
	}
	$("#clear_search").hide();
 });

 function display(){
	
		var total=0;
	 	var star_discount=$("#discount_percentage").val();
		$("#clear_search").hide();
		
		
		if(<%=request.getParameter("total")%>!=null)
		{
		total=<%=request.getParameter("total")%>;
		alert("Pay "+total+" in CASH");
		}
		
		$.post("UserWelcomeServlet", {
			func:"toDisplayDetails",
			star_discount : star_discount
		}, function(data, status) {			
			data = JSON.parse(data);
			product_data=data;
			console.log(data);
			for(var i=0;i<data.length;i++) {
				var $row = jQuery("#dummy_box_index").clone().attr('id',data[i].product_id);
				$row.find("#pname").text(data[i].product_name);
				if(data[i].price!=data[i].discount_price) {
					$row.find("#price").text(data[i].price);
					$row.find("#dis_price").text(data[i].discount_price);
					$row.find("#star_price").val(data[i].discount_price);
				}else{
					$row.find("#dis_price").text(data[i].discount_price);
				}
					$row.find("#product_id").val(data[i].product_id);
					$row.find(" #count_review").text("("+data[i].count+")");
					$row.find("#star_discount").val(star_discount);
               $row.find("#img").attr("src",data[i].product_img);
               $row.find("#img").attr('id',data[i].product_id);
               if(data[i].average==1){
				   $row.find("#pstar_rating .fa-star").css("color", "black");  
				   $row.find("#pstar1").css("color", "gold"); 
			}
			else if(data[i].average==2){
				$row.find("#pstar_rating .fa-star").css("color", "black");  
				$row.find("#pstar1,#pstar2").css("color", "gold"); 
			}
			else if(data[i].average==3){
				$row.find("#pstar_rating .fa-star").css("color", "black");  
				$row.find("#pstar1,#pstar2,#pstar3").css("color", "gold"); 
			}else if(data[i].average==4){
				$row.find("#pstar_rating .fa-star").css("color", "black");  
				$row.find("#pstar1,#pstar2,#pstar3,#pstar4").css("color", "gold"); 
			}else if(data[i].average==5){
				$row.find("#pstar_rating .fa-star").css("color", "black");  
				$row.find("#pstar1,#pstar2,#pstar3,#pstar4,#pstar5").css("color", "gold"); 
			}
					jQuery("#Product_List_index").append($row);
			}
			$("#dummy_box_index").css('visibility','hidden');
		});
	}
	function displayCart(){
		$.post("UserWelcomeServlet", {
				func:"getDisplayCart"
			}, function(data, status) {	
				console.log(data);
				data = JSON.parse(data);
				console.log(data);
				
				for(var i=0;i<data.length;i++) {
					var $row = jQuery("#dummy_box_cart").clone().attr('id',data[i].cart_id);
					$row.find("#sno").text(i+1);
					$row.find("#pname").text(data[i].product_name);
					$row.find("#price").text(data[i].price);
					$row.find("#quantity").text(data[i].quantity);
					//$row.find("#product_id").val(data[i].product_id);
					$row.find("#total").text(data[i].quantity*data[i].price);
					$("#final_total").text(data[i].total);
					
					$row.find("#id").val(data[i].cart_id);
					$row.find("#inc").attr("href","incDecQuantity?id="+data[i].cart_id+"&quantity=inc");
					$row.find("#dec").attr("href","incDecQuantity?id="+data[i].cart_id+"&quantity=dec");
					jQuery("#Product_List_cart").append($row);
				}
				$("#dummy_box_cart").css('visibility','hidden');
			});
	}
	function displayPlaceOrder(){
		$.post("UserWelcomeServlet", {
			func:"getDisplayCart"
			}, function(data, status) {	
				console.log(data);
				data = JSON.parse(data);
				console.log(data);
				
				for(var i=0;i<data.length;i++) {
					var $row = jQuery("#dummy_box_placeOrder").clone().attr('id',data[i].cart_id);
					$row.find("#sno").text(i+1);
					$row.find("#pname").text(data[i].product_name);
					$row.find("#price").text(data[i].price);
					$row.find("#quantity").text(data[i].quantity);
					
					$row.find("#total").text(data[i].quantity*data[i].price);
					$("#final_total_placeOrder").text(data[i].total);
					
					jQuery("#Product_List_placeOrder").append($row);
				}
				$("#dummy_box_placeOrder").css('visibility','hidden');
			});
	}
	function displayPurchase(){
		$.post("UserWelcomeServlet", {
				func:"getDisplayPurchase"
			}, function(data, status) {	
				console.log(data);
				data = JSON.parse(data);
				console.log(data);
				
				for(var i=0;i<data.length;i++) {
					var $row = jQuery("#dummy_box_purchase").clone().attr('id',data[i].order_id);
					$row.find("#sno").text(i+1);
					$row.find("#pname").text(data[i].product_name);
					$row.find("#price").text(data[i].price);
					$row.find("#quantity").text(data[i].quantity);
					//$row.find("#product_id").val(data[i].product_id);
					$row.find("#sub_total").text(data[i].quantity*data[i].price);
					$("#total_purchaseHistory").text(data[i].total);
					if(data[i].returned){
						$row.find("#preturn").text("Returned");
						}
					else{
						if(data[i].is_returnable==1){
							$row.find("#preturn").text("Return");
							var button = $row.find("#preturn");
							button.text("Return");
							button.click(function(event){
								console.log(event);
								$("#order_id_return").val($(this).parent().parent().attr("id"));
								toReturnProduct();
						});
					}
					}
					jQuery("#Product_List_purchase").append($row);
				}
				$("#dummy_box_purchase").css('visibility','hidden');
			});
	}
			function changeDropdown()
			{
			var r=document.getElementById("reason1").value;
			console.log(r);
			if(r==1)
				{
				document.getElementById("explain").style.visibility="visible";
				}
			else
				{
				document.getElementById("explain").style.visibility="hidden";
				}
			}
		$("#s1").dblclick(function(){
			   $(".fa-star").css("color", "black");
			   $("#edit_review_rating").val(0);
		});
		$("#s1").click(function() {  
		    $(".fa-star").css("color", "black");  
		    $("#s1").css("color", "yellow");  
		    $("#edit_review_rating").val(1);
		});  
		$("#s2").click(function() {  
		    $(".fa-star").css("color", "black");  
		    $("#s1, #s2").css("color", "yellow");  
		    $("#edit_review_rating").val(2);
		});  
		$("#s3").click(function() {  
		    $(".fa-star").css("color", "black")  
		    $("#s1, #s2, #s3").css("color", "yellow");  
		    $("#edit_review_rating").val(3);
		});  
		$("#s4").click(function() {  
		    $(".fa-star").css("color", "black");  
		    $("#s1, #s2, #s3, #s4").css("color", "yellow");  
		    $("#edit_review_rating").val(4);
		});  
		$("#s5").click(function() {  
		    $(".fa-star").css("color", "black");  
		    $("#s1, #s2, #s3, #s4, #s5").css("color", "yellow");  
		    $("#edit_review_rating").val(5);
		});  
function toEditReview(){
	$("#cart_page").hide();
	$("#home_page").hide();
	$("#placeOrder_page").hide();
	$("#purchaseHistory_page").hide();
	$("#search_product").hide();
	$("#returnProduct_page").hide();
	$("#starMember_page").hide();
	$("#editReview_page").show();
	$("#non_admin_user_page").hide();
}
function toStarMember(){
	$("#cart_page").hide();
	$("#home_page").hide();
	$("#placeOrder_page").hide();
	$("#purchaseHistory_page").hide();
	$("#search_product").hide();
	$("#returnProduct_page").hide();
	$("#starMember_page").show();
	$("#editReview_page").hide();
	$("#non_admin_user_page").hide();
}
function toReturnProduct(){
	$("#cart_page").hide();
	$("#home_page").hide();
	$("#placeOrder_page").hide();
	$("#purchaseHistory_page").hide();
	$("#search_product").hide();
	$("#returnProduct_page").show();
	$("#starMember_page").hide();
	$("#editReview_page").hide();
	$("#non_admin_user_page").hide();
}
function toPurchaseHistory(){
	$("#cart_page").hide();
	$("#home_page").hide();
	$("#placeOrder_page").hide();
	$("#purchaseHistory_page").show();
	$("#search_product").hide();
	$("#returnProduct_page").hide();
	$("#starMember_page").hide();
	$("#editReview_page").hide();
	$("#non_admin_user_page").hide();
	displayPurchase();
}
function toPlaceOrder(){
	$("#cart_page").hide();
	$("#home_page").hide();
	$("#placeOrder_page").show();
	$("#purchaseHistory_page").hide();
	$("#search_product").hide();
	$("#returnProduct_page").hide();
	$("#starMember_page").hide();
	$("#non_admin_user_page").hide();
	displayPlaceOrder();
}
function toCart(){
	$("#cart_page").show();
	$("#home_page").hide();
	$("#placeOrder_page").hide();
	$("#purchaseHistory_page").hide();
	$("#search_product").hide();
	$("#returnProduct_page").hide();
	$("#starMember_page").hide();
	$("#editReview_page").hide();
	$("#non_admin_user_page").hide();
	displayCart();
}
function toHome(){
	$("#home_page").show();
	$("#cart_page").hide();
	$("#placeOrder_page").hide();
	$("#purchaseHistory_page").hide();
	$("#search_product").hide();
	$("#returnProduct_page").hide();
	$("#starMember_page").hide();
	$("#editReview_page").hide();
	$("#non_admin_user_page").hide();
}
function toUserInfo(){
	$("#home_page").hide();
	$("#cart_page").hide();
	$("#placeOrder_page").hide();
	$("#purchaseHistory_page").hide();
	$("#search_product").hide();
	$("#returnProduct_page").hide();
	$("#starMember_page").hide();
	$("#editReview_page").hide();
	$("#non_admin_user_page").show();
	getAllUser();
}
jQuery(document).ready(function() {
	getUser();
	$("#header_page").show();
	$("#home_page").show();
	$("#cart_page").hide();
	$("#non_admin_user_page").hide();
	$("#placeOrder_page").hide();
	$("#purchaseHistory_page").hide();
	$("#returnProduct_page").hide();
	$("#starMember_page").hide();
	$("#editReview_page").hide();
	
//	display();
	if(<%=request.getParameter("goToCart")%>!=null){
		toCart();
	}
	if(<%=request.getParameter("gotoPlaceOrde")%>!=null){
		toPlaceOrder();
	}
	if(<%=request.getParameter("goToHome")%>!=null){
		toHome();
	}
	if(<%=request.getParameter("added")%>!=null){
		
		jQuery("#cart").addClass("addedToCart");
	}
	if(<%=request.getParameter("msg")%>!=null){
		var msg=<%=request.getParameter("msg")%>;
		if(msg==0){
		alert("YOUR ALREADY A STAR MEMBER!!");
		}else{
		alert("YOU BECAME A STAR MEMBER!! YAYYYY");
		}
		}
	$("#myModal").hide();
	
	
	
}) 
</script>
</body>
</html>
