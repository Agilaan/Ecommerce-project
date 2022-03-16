function register()
       {
       	var action="register";
       	var name=$("#name").val();
       	var email=$("#email").val();
       	var password=$("#password").val();
       	if(name=="" || email=="" || password=="")
       		{
       		alert("Enter all the credentials");
       		}
       	else if(name=="admin")
       		{
	        alert("Use some other username");
            }
       	else
       		{
       		$.post("regServ",{
       			func:action,
       			name:name,
       			email:email,
       			password:password
       			
       		},
       		function(data,status){
       			console.log(data);
       			console.log(status);
       			if(data=="failed"){
       				alert("Try with different name or email");
       			}
       			else 
       				{
       				window.location.href = "userLogin.jsp";
       				}
       			
       			
       		}); 
       		}
       }
      