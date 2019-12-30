$(function(){
	var loginUser = JSON.parse(sessionStorage.getItem("user"));
	$("#foot_userName").text(loginUser.username);
    $("#foot_userCnname").text(loginUser.cnname);
    $("#foot_userRole").text(loginUser.roles[0].description);
    $("#foot_departmentId").text(loginUser.departmentId);

	// var adminFlag = loginUser.userRole.indexOf("1");
	// /*var roles = loginUser.userRole.split("|");
	// var userRolesTxt = "";
	// for (var i in roles){
	// 	userRolesTxt += getRefDesc(v_refType.USER_ROLE, roles[i])+"|";
	// }
	// $("#userRole").text(userRolesTxt.substring(0, userRolesTxt.length-1));*/
	//
	// $("#tm_quit_btn").click(function(){
	// 	$.post("user/quit", function(result){
	// 		sessionStorage.clear();
	// 		location.href="index2.html";
	// 	});
	// });
	// var userTypeParam;
	// if (loginUser.userType == "1"){
	// 	userTypeParam = "HF";
	// }else{
	// 	userTypeParam = "WB";
	// }
	$.ajax(
		{
			url:"/data/menu_all.json",
			type:"get",
			dataType:"json",
			success:function(result){
				$("#menu").tree({
					data: result,
					lines: true,
					onClick:function(node){
						addTabs(node.text, node.url);
					}
				});
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