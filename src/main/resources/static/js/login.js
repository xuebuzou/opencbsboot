$(function(){
	$("#login_form").modal("show");
	$("#login_form").keydown(function(){
		if (13 == event.keyCode){	//回车登录
			loginUser();
		}
	});
	
	//显示登录框事件
	$("#openLoginForm").click(function(){
		$("#login_form").modal("show");
	});
	
	//登录
	$("#login").on("click",function(){
		loginUser();
	});
	
	function loginUser(){
		var userAcct = $("#userAcct").val();
		var userPwd = $("#userPwd").val();
	
		if( userPwd=="" || userName==""){
			$("#erroMsg").show();
			$("#erroMsg div").text("用户名或密码不能为空!");
			return;
		}
		var param = {
				userAcct:userAcct,
				userPwd:userPwd
		};
		$.ajax({
			url : "user/login",
			type : "post",
			dataType : "json",
			async : false,
			data : param,
			success : function(result) {
				if(result.retCode=="success"){
					sessionStorage.setItem("user", JSON.stringify(result.data));
					location.href="home.html";
				}else{
					$("#erroMsg").show();
					$("#erroMsg div").text(result.retMsg);
				}
			}
		});
	}
});