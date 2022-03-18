<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
        <%@ page import= "java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin's Page</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<link rel="stylesheet" href="AdminStyle.css">
<script src="addNewProductJs.js"></script>
<script src="addNewCategoryJs.js"></script>
</head>
<body>
<div id="adminHeader_page">
 	<%if(session.getAttribute("user_id")==null){
 		response.sendRedirect("../login.jsp");
 	}else{
 		String name=session.getAttribute("user_id").toString(); } %>
	<div class="dropdown">
		<nav>
			<ul>
			<li><a  onclick="toAdminHome()">Home</a></li>
			    <li><a href="#">Add Products or Category</a>
			        <ul>
			        <li><a onclick="toAddNewProduct()">Add New Product</a></li>
			        <li><a onclick="toAddNewCategory()">Add New Category</a></li>
			        </ul>
			    </li>
			    <li><a href="#">Edit Products or Category</a>
			        <ul>
			        <li><a onclick="toViewAndEditCategory()">Edit Category</a></li>
			        <li><a onclick="toViewAndEditProduct()">Edit Product</a></li>
			        </ul>
			    </li>
			     <li><a href="#">Report </a>
			        <ul>
			        <li><a onclick="toViewPurchased()">Purchased Product</a></li> 
			        </ul>
			    </li>
			    <li><a class="logout" href="../logout.jsp">Logout</a></li>
			  <%--  <li style="float:right"><a href="../welcome.jsp">Back To User</a> --%> 
			</ul>
		</nav>
	</div>
</div>

<div id="adminHome_page">

	<div class="b1">
	<h4>CATEGORY WISE SALES</h4>
	<table>
	<thead>
	<tr>
	    <th scope="col">    </th>
	    <th scope="col">Today</th>
	    <th scope="col">Yesterday</th>
	    <th scope="col">Week's</th>

	</tr>
	</thead>
	<tbody>
	<tr>
	    <td scope="row" id="type"></td>
	    <td scope="row" id="day"></td>      
	    <td scope="row" id="yesturday"></td>
	    <td scope="row" id="week"></td>   
	 </tr>
	</tbody>
	    <tr>
	     <td scope="row"> Total </td>
	     <td scope="row" id="days_total"></td>
	     <td scope="row" id="yesturdays_total"></td>
	     <td scope="row" id="weeks_total"></td>
	     
	    </tr>
	
	</table>
	</div>
	<div class="b2">
		<h4>OUT OF STOCK DETAILS</h4>
		<table>
		<thead>
		<tr>
			<th scope="col">Product Name</th>
		</tr>
		</thead>
		<tbody id="Product_List2">
			<tr id="dummy_box2">
				<td><a id="product_name"></a></td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="b3">
		<h4>RETUNED PRODUCT DATA</h4>
		<table>
		<thead>
		<tr>
			<th scope="col">Product Name</th>
			<th scope="col">User Name</th>
			<th scope="col">Return Reason</th>
		</tr>
		</thead>
		<tbody id="Product_List3">
			<tr id="dummy_box3">
				<td id="returned_product"></td>
				<td id="returned_user"></td>
				<td id="reason_of_return"></td>
			</tr>
		</tbody>
		</table>
	</div>
	<div class="b4">
		<h4>STAR MEMBER DETAILS</h4>
		<table>
		<thead>
		<tr>
			<th scope="col">User Name</th>
			<th scope="col">User ID</th>
		</tr>
		</thead>
		<tbody id="Product_List4">
			<tr id="dummy_box4">
				<td><a id="uname"></a></td>
				<td><a id="uid"></a></td>
			</tr>
		</tbody>
		</table>
	</div>
</div>
<div id="addNewProduct_page">
	<center><h1>Add new Product</h1></center>
	<br>
			<div class="">
				<h3>Enter Product Name</h3>
				<input type="text" id="P_name" name="name" required>
				
			</div>
			<div class="">
				<h3>Enter Category</h3>
				<select id="P_category" name="category" required></select>
			</div>
			<div class="">
				<h3>Enter Price</h3>
				<input type="text" id="P_price" name="price"  required>
				
			</div>
			<div class="">
				<h3>Add Image of the Product</h3>
				<input type="file" id="P_image" name="image" required>
				
			</div>
			<div class="">
				<h3>Enter Quantity</h3>
				<input type="text" id="P_quantity" name="quantity"  required>
				
			</div>
			
			<div class="">
				<h3>Enter Discount percent</h3>
				<input type="text" id="P_discount" name="discount"  >
				
			</div>
			<div class="">
				<h3>Enter Cost Price</h3>
				<input type="text" id="P_cp" name="cp"  required>
				
			</div>
					
			<center><button type="submit" onclick="addProduct()">Save</button></center>
</div>

<div id="addNewCategory_page">
	<div class="">
			<h3>Enter type</h3>
			<input type="text" id="C_type" name="type" required>
		</div>
		<div class="">
			<h3>Enter tag</h3>
			<input type="text" id="C_tag" name="tag" required>
		</div>	
		<center><button type="submit" onclick="addNewCategory()">Save</button></center>

</div>
<div id="viewSelectedPurchaseHistory_page">
<table id="table">
<thead>
<tr>   
    <th scope="col">User Name</th>
	<th scope="col">Product Name</th>
	<th scope="col">Price</th>
	<th scope="col">Quantity</th>
	<th scope="col">Date of purchase</th>
	<th scope="col">Sub total</th>	
</tr>
</thead>
<tbody id="selected_purchased_product_box1">
<tr id="selected_purchased_product_box2"> 
    <td id="selected_purchased_user"></td>
	<td id="selected_purchased_product"></td>
	<td id="selected_purchased_price"></td>
	<td id="selected_purchased_quantity"></td>
	<td id="selected_purchased_dop"></td>
	<td id="selected_purchased_sub_total"> </td>	
</tr>
</tbody>
</table>
</div>

<div id="viewPurchased_page">
<table>
<thead>
<tr>   
    <th scope="col">Purchased id</th>
	<th scope="col">User Name</th>
	<th scope="col">Date of purchase</th>
	<th scope="col">Total Amount of purchase</th>
	<th scope="col">View Details</th>	
</tr>
</thead>
<tbody id="purchased_product_box1">
<tr id="purchased_product_box2"> 
    <td id="purchased_id"></td>
    <td id="purchased_user"></td>
	<td id="purchased_dop"></td>
	<td id="purchased_total"></td>
	<td id="purchased_sub_total"> <button id="viewDetails" onclick="viewDetails(this)">View details</button> </td>	
</tr>
</tbody>
</table>
</div>
<div id="viewAndEditCategory_page">
	<center><h1>View and Edit Category</h1></center>
	<table>
			<thead>
			<tr>			
			<th scope="col">Type</th>
			<th scope="col">Tag</th>
			<th scope="col">Edit</th>
			</tr>
			</thead>
			<tbody id="viewAndEditCategory_box1">
				<tr id="viewAndEditCategory_box2">
					<td id="type"></td>
					<td id="tag"></td>
					
					<td><button id="edit">Edit</button></td>
				</tr>
			</tbody>
	</table>
</div>
<div id="viewAndEditProduct_page">
	<center><h1>View and Edit Products</h1></center>
	<table>
			<thead>
			<tr>	
			<th scope="col">Name</th>
			<th scope="col">Price to be sold</th>
			<th scope="col">Quantity</th>
			<th scope="col">Discount</th>
			<th scope="col">Cost Price</th>
			<th scope="col">Edit</th>
			</tr>
			</thead>
			<tbody id="ViewAndEditPurchase_box1">
				<tr id="ViewAndEditPurchase_box2">				
					<td id="pname"></td>
					<td id="price"></td>
					<td id="quantity"></td>
					<td id="discount"></td>
					<td id="cp"></td>	
					<td ><button id="edit" >Edit</button></td>
				</tr>
			</tbody>		
	</table>
</div>
<div id="editProduct_page">

           <input type="hidden" id="ViewAndEdit_product_id" name="id" value="">
		<div >
			<h3>Enter Product Name</h3>
			<input type="text" id="ViewAndEdit_pname" name="name" value="" required>
			
		</div>
		<div class="">
			<h3>Enter Category</h3>
			<select id="ViewAndEdit_category" name="category" required></select>
		</div>
		<div>
			<h3>Enter Price</h3>
			<input type="text" id="ViewAndEdit_price" name="price" value="" required>	
		</div>
		<div>
			<h3>Enter Quantity</h3>
			<input type="text" id="ViewAndEdit_quantity" name="quantity" value="" required>	
		</div>
		<div class="">
			<h3>Add Image of the Product</h3>
			<input type="file" id="ViewAndEdit_image" name="image" required>	
		</div>
		<div class="">
			<h3>Enter Discount percent</h3>
			<input type="text" id="ViewAndEdit_discount" name="discount" value="" required >	
		</div>
		<div class="">
			<h3>Enter Cost Price</h3>
			<input type="text" id="ViewAndEdit_cp" name="cp" value="" required>	
		</div>
		
	<center><button type="submit" onclick="EditPurticularProduct()">Save</button></center>	
</div>
<div id="editCategory_page">
<input type="hidden" id="ViewAndEditC_category_id" name="category_id" value=""/>
		<div >
			<h3>Enter Type</h3>
			<input type="text" id="ViewAndEditC_type" name="type" value="" required/>
			
		</div>

		<div>
			<h3>Enter Tag</h3>
			<input type="text" id="ViewAndEditC_tag" name="tag" value="" required/>
			
		</div>
		
		<center><button type="submit" onclick="EditPurticularCategory()">Save</button></center>
</div>
<script>
function EditPurticularProduct()
{
		var name=$("#ViewAndEdit_pname").val();
       	var product_id="0";
		if($("#ViewAndEdit_product_id").val()!=null)
		{
		 product_id=$("#ViewAndEdit_product_id").val();
		}
      
       	var category=$("#ViewAndEdit_category").val();
       	var price=$("#ViewAndEdit_price").val();
       	var image=$("#ViewAndEdit_image").val();
       	image = image.replace("C:\\fakepath\\", "product_image\\");
       	var quantity=$("#ViewAndEdit_quantity").val();
       	var discount=$("#ViewAndEdit_discount").val();
       	var cp=$("#ViewAndEdit_cp").val();
       	if(name=="" || category=="" || price=="" ||image=="" ||quantity=="" ||discount=="" ||cp=="" )
       		{
       		alert("Enter all the credentials");
       		}
       	else if(price<0 || discount<0 || quantity<0 || cp<0){
	         alert("Enter valid credentials");
            }
    	else if(price>=0 || discount>=0 || quantity>=0  || cp>=0)
   		{
   		$.post("../AdminWelcomePage",{
   			func:"getProduct",
   			name:name,
   			product_id:product_id,
   			category:category,
   			price:price,
   			image:image,
   			quantity:quantity,
   			discount:discount,
   			cp:cp
   			
   		},
   		function(data,status){
   			console.log(data);
   			console.log(status);
   			if(data=="failed"){
   				alert("Pls Try again");
   			}
   			else if(data=="success")
   				{
   				alert("Added Product successfully");
   				}
   			else if(data=="updated")
   			{
	             alert("Updated");
	             window.location.href = "AdminWelcome.jsp?";
             }
   		}); 
   		}
       
}
function AdminHomePageDetails(){
	$.post("../AdminWelcomePage", {
			func:"getAdminHomePageDetails"
		}, function(data, status) {	
			console.log(data);
			data = JSON.parse(data);
			console.log(data);
			
		for(var i=0;i<data.star_member.length;i++) {
			var $row = jQuery("#dummy_box4").clone().attr('id',data.star_member[i].user_id);
			$row.find("#uname").text(data.star_member[i].user_name);
			$row.find("#uid").text(data.star_member[i].user_id);
			jQuery("#Product_List4").append($row);
		}
			$("#dummy_box4").css('visibility','hidden');
		for(var i=0;i<data.returned_product.length;i++) {
			var $row = jQuery("#dummy_box3").clone().attr('id',data.returned_product[i].pid);
			$row.find("#returned_product").text(data.returned_product[i].product_name);
			$row.find("#returned_user").text(data.returned_product[i].user_name);
			$row.find("#reason_of_return").text(data.returned_product[i].return_reason);
			jQuery("#Product_List3").append($row);
		}
		$("#dummy_box3").css('visibility','hidden');
		for(var i=0;i<data.out_of_stock.length;i++) {
			var $row = jQuery("#dummy_box2").clone().attr('id',data.out_of_stock[i].pid);
			$row.find("#product_name").text(data.out_of_stock[i].product);
			jQuery("#Product_List2").append($row);
		}
		$("#dummy_box2").css('visibility','hidden');
		
		
		for(var i=0;i<data.weeks.length;i++) {
			var $row = jQuery("<p></p>").clone().attr('id',data.weeks[i].category_id);
		//	$row.find("#type").text(data.weeks[i].type);
		//	$row.find("#week").text(data.weeks[i].total);
		    $row.text(data.weeks[i].total);
			$("#weeks_total").text(data.weeks[i].final_total);
			jQuery("#week").append($row);
		}
		for(var i=0;i<data.weeks.length;i++) {
			var $row = jQuery("<p></p>").clone().attr('id',data.weeks[i].category_id);
		    $row.text(data.weeks[i].type);
			jQuery("#type").append($row);
		}
		for(var i=0;i<data.days.length;i++) {
			var $row = jQuery("<p></p>").clone().attr('id',data.days[i].category_id);	
		    $row.text(data.days[i].total);
			$("#days_total").text(data.days[i].final_total);
			jQuery("#day").append($row);
		}
		for(var i=0;i<data.yesturday.length;i++) {
			var $row = jQuery("<p></p>").clone().attr('id',data.yesturday[i].category_id);	
		    $row.text(data.yesturday[i].total);
			$("#yesturdays_total").text(data.yesturday[i].final_total);
			jQuery("#yesturday").append($row);
		}
		
		
	});
}
function displayCategory(){
	$.post("../AdminWelcomePage", {
			func:"getDisplayAdmin"
		}, function(data, status) {	
			data = JSON.parse(data);
			for(var i=0;i<data.category.length;i++) {
				var $row = jQuery("<option></option>").attr('id',data.category[i].category_id);
				$row.text(data.category[i].type);
				$row.val(data.category[i].category_id);
				jQuery("#P_category").append($row);
			}
		});
}
/*function displayPurchasedProduct(){
	$.post("../AdminWelcomePage", {
			func:"getPurchasedProduct"
		}, function(data, status) {	
			data = JSON.parse(data);
			console.log(data);
			$("#purchased_total").text(data.total);
			for(var i=0;i<data.details.length;i++) {
				var $row = jQuery("#purchased_product_box2").clone().attr('id',data.details[i].order_id);
				$row.find("#purchased_user").text(data.details[i].user_name);
				$row.find("#purchased_product").text(data.details[i].product_name);
				$row.find("#purchased_price").text(data.details[i].price);
				$row.find("#purchased_quantity").text(data.details[i].quantity);
				$row.find("#purchased_dop").text(data.details[i].date);
				$row.find("#purchased_sub_total").text(data.details[i].total);
				jQuery("#purchased_product_box1").append($row);
			}
			$("#purchased_product_box2").css("visibility","hidden");
		});
}*/
function displayPurchasedProduct(){
	$.post("../AdminWelcomePage", {
			func:"getPurchasedProduct"
		}, function(data, status) {	
			//data = JSON.parse(data);
			console.log(data);
			
			for(var i=0;i<data.details.length;i++) {
				var $row = jQuery("#purchased_product_box2").clone().attr('id',data.details[i].purchase_id);
				$row.find("#purchased_id").text(data.details[i].purchase_id);
				$row.find("#purchased_user").text(data.details[i].user_name);
				$row.find("#purchased_dop").text(data.details[i].date);
				$row.find("#purchased_total").text(data.details[i].total);
				jQuery("#purchased_product_box1").append($row);
			}
			$("#purchased_product_box2").css("visibility","hidden");
		});
}
function viewDetails(sender){
	var id=sender.parentNode.parentNode.id;
	
	$.post("../AdminWelcomePage", {
		func:"getPurticularPurchasedProduct",
		id:id
		}, function(data, status) {	
			data=JSON.parse(data);
			jQuery("#selected_purchased_product_box1").empty();
			for(var i=0;i<data.details.length;i++) {
				var $row = jQuery("<tr></tr>").attr('id',data.details[i].order_id);
				$row.append(jQuery("<td id=selected_purchased_user></td>").text(data.details[i].user_name));
				$row.append(jQuery("<td id=selected_purchased_product></td>").text(data.details[i].product_name)); 
				$row.append(jQuery("<td id=selected_purchased_price></td>").text(data.details[i].price)); 
				$row.append(jQuery("<td id=selected_purchased_quantity></td>").text(data.details[i].quantity)); 
				$row.append(jQuery("<td id=selected_purchased_dop></td>").text(data.details[i].date)); 
				$row.append(jQuery("<td id=selected_purchased_sub_total></td>").text(data.details[i].total)); 

				jQuery("#selected_purchased_product_box1").append($row);
			}
			$("#selected_purchased_product_box2").css("visibility","hidden");
			togetPurticularPurchasedProduct();
		});
	
}
function displayViewAndEditCategory(){
	$.post("../AdminWelcomePage", {
		func:"getDisplayAdmin"
		}, function(data, status) {				
			data = JSON.parse(data);			
			for(var i=0;i<data.category.length;i++) {
				var $row = jQuery("#viewAndEditCategory_box2").clone().attr('id',data.category[i].category_id);
				$row.find("#type").text(data.category[i].type);
				$row.find("#tag").text(data.category[i].tag);
				//$row.find("#edit").attr("href","editCategory.jsp?category_id="+data.category[i].category_id);
				var button = $row.find("#edit");
				button.click(function(event){
					console.log(event);
					$("#ViewAndEditC_category_id").val($(this).parent().parent().attr("id"));
					toEditCategory();
					
				});
				jQuery("#viewAndEditCategory_box1").append($row);
			}
			$("#viewAndEditCategory_box2").css('visibility','hidden');
		});
}
function displayViewAndEditProduct(){
	$.post("../AdminWelcomePage", {
		func:"getDisplayAdmin"
		}, function(data, status) {			
			data = JSON.parse(data);		
			for(var i=0;i<data.product.length;i++) {
				var $row = jQuery("#ViewAndEditPurchase_box2").clone().attr('id',data.product[i].product_id);
				
				$row.find("#pname").text(data.product[i].product_name);
				$row.find("#price").text(data.product[i].price);
				$row.find("#quantity").text(data.product[i].quantity);
				$row.find("#discount").text(data.product[i].discount);
				$row.find("#cp").text(data.product[i].cp);
				//$row.find("#edit").attr("href","editProduct.jsp?id="+data.product[i].product_id);
				var button = $row.find("#edit");
				button.click(function(event){
					console.log(event);
					$("#ViewAndEdit_product_id").val($(this).parent().parent().attr("id"));
					toEditProduct();
				});
				jQuery("#ViewAndEditPurchase_box1").append($row);
			}
			$("#ViewAndEditPurchase_box2").css('visibility','hidden');
		});
}
		
function displayEditProduct(){
	var id=$("#ViewAndEdit_product_id").val();
	$.post("../AdminWelcomePage", {
		    func:"getPrintProduct",
			id:id
		}, function(data, status) {	
			data = JSON.parse(data);
			$("#ViewAndEdit_product_id").val(data.product.product_id);
			$("#ViewAndEdit_pname").val(data.product.product_name);
			$("#ViewAndEdit_price").val(data.product.price);
			$("#ViewAndEdit_quantity").val(data.product.quantity);
			$("#ViewAndEdit_discount").val(data.product.discount);
			$("#ViewAndEdit_cp").val(data.product.cp);
			for(var i=0;i<data.category.length;i++) {
				var $row = jQuery("<option></option>").attr('id',data.category[i].category_id);
				$row.text(data.category[i].type);
				$row.val(data.category[i].category_id);
				jQuery("#ViewAndEdit_category").append($row);
			}
	});
}
function displayEditCategory(){
	var category_id=$("#ViewAndEditC_category_id").val();
	$.post("../AdminWelcomePage", {
		    func:"getPrintCategory",
			category_id:category_id
		}, function(data, status) {	
			data = JSON.parse(data);
			$("#ViewAndEditC_category_id").val(data.category_id);
			$("#ViewAndEditC_type").val(data.type);
			$("#ViewAndEditC_tag").val(data.tag);
		});
}
function EditPurticularCategory()
{
       	
       	var type=$("#ViewAndEditC_type").val();
       	var category_id="0";
		if($("#ViewAndEditC_category_id").val()!=null)
		{
		 category_id=$("#ViewAndEditC_category_id").val();
		}
       
       	var tag=$("#ViewAndEditC_tag").val();
       	if(type=="" || tag=="")
       		{
       		alert("Enter all the credentials");
       		}
    	else
   		{
   		$.post("../AdminWelcomePage",{
   			func:"getCategory",
   			type:type,
   			category_id:category_id,
   			tag:tag
   			
   		},
   		function(data,status){
   			console.log(data);
   			console.log(status);
   			if(data=="failed"){
   				alert("Try again");
   			}
   			else if(data=="success")
   				{
   				alert("Added Category successfully");
   				window.location.href = "AdminWelcome.jsp?";
   				}
   			else if(data=="updated")
   			{
	             alert("Updated");
             }
   		}); 
   		}
       
 }
function togetPurticularPurchasedProduct(){
	$("#addNewCategory_page").hide();
	$("#adminHome_page").hide();
	$("#addNewProduct_page").hide();
	$("#viewPurchased_page").hide();
	$("#viewAndEditCategory_page").hide();
	$("#viewAndEditProduct_page").hide();
	$("#editProduct_page").hide();
	$("#editCategory_page").hide();
	$("#viewSelectedPurchaseHistory_page").show();
}
function toEditCategory(){
	$("#addNewCategory_page").hide();
	$("#adminHome_page").hide();
	$("#addNewProduct_page").hide();
	$("#viewPurchased_page").hide();
	$("#viewAndEditCategory_page").hide();
	$("#viewAndEditProduct_page").hide();
	$("#editProduct_page").hide();
	$("#editCategory_page").show();
	$("#viewSelectedPurchaseHistory_page").hide();
	displayEditCategory();
 }
function toEditProduct(){
	$("#addNewCategory_page").hide();
	$("#adminHome_page").hide();
	$("#addNewProduct_page").hide();
	$("#viewPurchased_page").hide();
	$("#viewAndEditCategory_page").hide();
	$("#viewAndEditProduct_page").hide();
	$("#editProduct_page").show();
	$("#editCategory_page").hide();
	$("#viewSelectedPurchaseHistory_page").hide();
	displayEditProduct();
}
function toViewAndEditProduct(){
	$("#addNewCategory_page").hide();
	$("#adminHome_page").hide();
	$("#addNewProduct_page").hide();
	$("#viewPurchased_page").hide();
	$("#viewAndEditCategory_page").hide();
	$("#viewAndEditProduct_page").show();
	$("#editProduct_page").hide();
	$("#editCategory_page").hide();
	$("#viewSelectedPurchaseHistory_page").hide();
	displayViewAndEditProduct();
}
function toViewAndEditCategory(){
	$("#addNewCategory_page").hide();
	$("#adminHome_page").hide();
	$("#addNewProduct_page").hide();
	$("#viewPurchased_page").hide();
	$("#viewAndEditCategory_page").show();
	$("#viewAndEditProduct_page").hide();
	$("#editProduct_page").hide();
	$("#editCategory_page").hide();
	$("#viewSelectedPurchaseHistory_page").hide();
	displayViewAndEditCategory();
}
function toViewPurchased(){
	$("#addNewCategory_page").hide();
	$("#adminHome_page").hide();
	$("#addNewProduct_page").hide();
	$("#viewPurchased_page").show();
	$("#viewAndEditCategory_page").hide();
	$("#viewAndEditProduct_page").hide();
	$("#editProduct_page").hide();
	$("#editCategory_page").hide();
	$("#viewSelectedPurchaseHistory_page").hide();
	displayPurchasedProduct();
}
function toAddNewCategory(){
	$("#addNewCategory_page").show();
	$("#adminHome_page").hide();
	$("#addNewProduct_page").hide();
	$("#viewPurchased_page").hide();
	$("#viewAndEditCategory_page").hide();
	$("#viewAndEditProduct_page").hide();
	$("#editProduct_page").hide();
	$("#editCategory_page").hide();
	$("#viewSelectedPurchaseHistory_page").hide();
}
function toAddNewProduct(){
	$("#addNewCategory_page").hide();
	$("#adminHome_page").hide();
	$("#addNewProduct_page").show();
	$("#viewPurchased_page").hide();
	$("#viewAndEditCategory_page").hide();
	$("#viewAndEditProduct_page").hide();
	$("#editProduct_page").hide();
	$("#editCategory_page").hide();
	$("#viewSelectedPurchaseHistory_page").hide();
	displayCategory();
}
function toAdminHome(){
	$("#adminHome_page").show();
	$("#addNewProduct_page").hide();
	$("#addNewCategory_page").hide();
	$("#viewPurchased_page").hide();
	$("#viewAndEditCategory_page").hide();
	$("#viewAndEditProduct_page").hide();
	$("#editProduct_page").hide();
	$("#editCategory_page").hide();
	$("#viewSelectedPurchaseHistory_page").hide();
}
jQuery(document).ready(function() {
	
	AdminHomePageDetails();
	$("#adminHeader_page").show();
	$("#adminHome_page").show();
	$("#addNewProduct_page").hide();
	$("#addNewCategory_page").hide();
	$("#viewPurchased_page").hide();
	$("#viewAndEditCategory_page").hide();
	$("#viewAndEditProduct_page").hide();
	$("#editProduct_page").hide();
	$("#editCategory_page").hide();
	$("#viewSelectedPurchaseHistory_page").hide();
	})
</script>
</body>
</html>