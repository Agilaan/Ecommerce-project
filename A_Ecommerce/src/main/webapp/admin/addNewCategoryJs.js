function addNewCategory()
{
       	
       	var type=$("#C_type").val();
       	var category_id="0";
		if($("#category_id").val()!=null)
		{
		 category_id=$("#category_id").val();
		}
       
       	var tag=$("#C_tag").val();
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