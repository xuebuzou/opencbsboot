$(function(){
	var loginUser = JSON.parse(sessionStorage.getItem("user"));
	$("#userName").text(loginUser.userName.split("-")[1]);
	var adminFlag = loginUser.userRole.indexOf("1");
	/*var roles = loginUser.userRole.split("|");
	var userRolesTxt = "";
	for (var i in roles){
		userRolesTxt += getRefDesc(v_refType.USER_ROLE, roles[i])+"|";
	}
	$("#userRole").text(userRolesTxt.substring(0, userRolesTxt.length-1));*/
	
	$("#tm_quit_btn").click(function(){
		$.post("user/quit", function(result){
			sessionStorage.clear();
			location.href="index2.html";
		});
	});
	var userTypeParam;
	if (loginUser.userType == "1"){
		userTypeParam = "HF";
	}else{
		userTypeParam = "WB";
	}
	$.ajax(
		{
			url:"menu/queryMenus",
			type:"post",
			data:{
				"userType":userTypeParam
			},
			dataType:"json",
			success:function(result){
				$("#menu").tree({
					data:result.rows,
					lines:true,
					formatter:function(node){
						return node.menuName;
					},
					onClick:function(node){
						addTabs(node.menuName, node.url);
					}
				});
				addTabs(result.rows[0].menuName,result.rows[0].url);
			}
		}
	);
});

function addTabs(text, url){
	if ($("#dataTabs").tabs("exists", text)){
		$("#dataTabs").tabs("select", text);
		return;
	}
	$("#dataTabs").tabs("add",{
	    title:text,
	    closable:true,
	    href:url
	});
}