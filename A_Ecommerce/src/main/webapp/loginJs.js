function login() {
	var name = $("#name").val();
	var password = $("#password").val();
	if (name == "" || password == "") {
		alert("Enter all the credentials");
	}
	else {
		$.post("loginServlet", {

			name: name,
			password: password

		},
			function(data, status) {
				if (data == "failed") {
					alert("Try with valid username or password");
				}
				else if (data == "success") {
					 
						var dd = 0;
						$.post("userDetails", {
							
						}, function(data, status) {
							console.log(data);
							data = JSON.parse(data);
							if (data.star_member) {
								var today = new Date();
								dd = String(today.getDate());
								if (dd > 1 && dd < 5) {
									dd = 5;
								}
								if (dd > 15) {
									dd = dd / 2;
								}
								
							}
							
							window.location.href = "index.jsp?discount=" + dd;
						});
					
				}
				else if (data == "admin") {
					window.location.href = "admin/adminHome.jsp";
				}
			});
	}

}


