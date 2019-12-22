$(function() {
    $.backstretch("assets/img/backgrounds/1.jpg");
    /*
        Form validation
    */
//    $('.login-form input[type="text"], .login-form input[type="password"], .login-form textarea').on('focus', function() {
//    	$(this).removeClass('input-error');
//    });

    $('#sub_sign_in').on('click', function() {
    	
//    	$(this).find('input[type="text"], input[type="password"], textarea').each(function(){
//    		if( $(this).val() == "" ) {
//    			e.preventDefault();
//    			$(this).addClass('input-error');
//    		}
//    		else {
//    			$(this).removeClass('input-error');
//    		}
//    	});
//    	loginUser();
		console.info("submit");
    	var userAcct = $("#form-username").val();
    	var userPwd = $("#form-password").val();
    	var param = {
    			userAcct:userAcct,
    			userPwd:userPwd
    	};
    	// $.ajax({
    	// 	url : "user/login",
    	// 	type : "post",
    	// 	dataType : "json",
    	// 	async : false,
    	// 	data : param,
    	// 	success : function(result) {
    	// 		if(result.retCode=="success"){
    	// 			sessionStorage.setItem("user", JSON.stringify(result.data));
    	// 			location.href="home.html";
    	// 		}else{
    	// 			$("#logind").hide();
    	// 			$("#logind").html("<b color=\"red\">用户名或密码错误!</b>");
    	// 			$("#logind").fadeIn();
    	// 		}
    	// 	}
    	// });

		$.ajax({
			url : "/login",
			type : "post",
			dataType : "json",
			async : false,
			data : param,
			success : function(result) {
				// if(result.retCode=="success"){
				// 	sessionStorage.setItem("user", JSON.stringify(result.data));
				// 	location.href="home.html";
				// }else{
				// 	$("#logind").hide();
				// 	$("#logind").html("<b color=\"red\">用户名或密码错误!</b>");
				// 	$("#logind").fadeIn();
				// }
			}
		});

        // location.href="home";
    	
    });
});


