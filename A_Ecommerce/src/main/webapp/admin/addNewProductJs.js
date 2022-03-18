function addProduct()
{
		var name=$("#P_name").val();
       	var product_id="0";
		if($("#P_id").val()!=null)
		{
		 product_id=$("#P_id").val();
		}
        
       	var category=$("#P_category").val();
       	var price=$("#P_price").val();
       	var image=$("#P_image").val();
       	image = image.replace("C:\\fakepath\\", "product_image\\");
       	var quantity=$("#P_quantity").val();
       	var discount=$("#P_discount").val();
       	var cp=$("#P_cp").val();
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
   				window.location.href = "AdminWelcome.jsp?";
   				}
   			else if(data=="updated")
   			{
	             alert("Updated");
	             
             }
   		}); 
   		}
       
}