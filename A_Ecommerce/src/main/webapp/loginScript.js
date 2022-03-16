function login() {
	var name = $("#name").val();
	var password = $("#password").val();
	if (name == "" || password == "") {
		alert("Enter all the credentials");
	}
	else {
		$.post("LoginAndRegister", {
            func:"doLogin",
			name: name,
			password: password

		},
			function(data, status) {
				if (data == "failed") {
					alert("Try with valid username or password");
				}
				else if (data == "success") {
					 
						var dd = 0;
						$.post("UserWelcomeServlet", {
							func:"getUserDetails"
						}, function(data, status) {
							console.log(data);
							data = JSON.parse(data);		
							window.location.href = "welcome.jsp?discount=" + dd;
						});
					
				}
				else if (data == "admin") {
					window.location.href = "admin/AdminWelcome.jsp";
				}
			});
	}

}
function register()
       {
       	
       	var name=$("#r_name").val();
       	var email=$("#r_email").val();
       	var password=$("#r_password").val();
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
       		$.post("LoginAndRegister",{
       			func:"doRegister",
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
       				window.location.href = "login.jsp";
       				}
       			
       			
       		}); 
       		}
       }
       function toRegister(){
	$("#login").hide();
	$("#register").show();
}
function toLogin(){
	$("#login").show();
	$("#register").hide();
}
      jQuery(document).ready(function() {
		$("#register").hide();
	})