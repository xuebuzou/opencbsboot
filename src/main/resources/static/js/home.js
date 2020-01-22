var g_dep_param;
var g_reserv_status_param;
var g_acct_type_param;
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
    $.get("/zg/home/init", function (result) {
        if ("success" == result.retCode) {
            g_reserv_status_param = result.result['reserv_status_def'];
            g_dep_param = result.result['dep'];
            g_acct_type_param = result.result['acct_type_def'];
        }
    });
});

function doPostFromZG(msgType,msgCode,params,func){
    params.MESSAGE_TYPE=msgType;
    params.MESSAGE_CODE=msgCode;
    $.post("/zg/gateway", params, func);
}

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

function getDepDesc(value) {
    for (var i = 0; i < g_dep_param.length; i++) {
        if (value == g_dep_param[i]['depCode']) {
            return g_dep_param[i]['depDesc'];
            break;
        }
    }
}

function getRoleDesc(roleId) {
    for (var i = 0; i < role_param.length; i++) {
        if (roleId == role_param[i]['id']) {
            return role_param[i]['description'];
            break;
        }
    }
}

function getStatusDesc(value) {
    for (var i = 0; i < g_reserv_status_param.length; i++) {
        if (value == g_reserv_status_param[i]['status']) {
            return g_reserv_status_param[i]['statusDesc'];
            break;
        }
    }
}

function getAcctTypeDesc(value) {
    for (var i = 0; i < g_acct_type_param.length; i++) {
        if (value == g_acct_type_param[i]['acctType']) {
            return g_acct_type_param[i]['acctTypeDesc'];
            break;
        }
    }
}