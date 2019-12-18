
/**
 * 获取当前登录用户
 */
var v_login_user = JSON.parse(sessionStorage.getItem("user"));

/**
 * v_refType 枚举码值类型
 */
var v_refType = {
	Y_N : "Y_N",//Y or N
	GROUP : "GROUP",// 用户组、任务组
	USER_RANK : "USER_RANK",// 用户级别
	USER_ROLE : "USER_ROLE",// 用户角色
	USER_TYPE : "USER_TYPE", // 用户类型
	ACTIVITY_TYPE : "ACTIVITY_TYPE", // 活动类型
	ACTIVITY_STATUS : "ACTIVITY_STATUS", // 活动状态
	TASK_TYPE : "TASK_TYPE", // 任务类型
	TASK_RANK : "TASK_RANK", // 任务级别
	TASK_STATUS : "TASK_STATUS", // 任务状态
	REVIEW_STATUS : "REVIEW_STATUS" // 评审状态
};
/**
 * v_refDefObj 码值存储对象，格式如下 {
 * USER_ROLE:[{refCode:"",refDesc:""},{refCode:"",refDesc:""}] }
 */
var v_refDefObj = {
	"Y_N":[{refCode:"Y",refDesc:"是"},{refCode:"N",refDesc:"否"}]
};
/**
 * 根据码值类型查询码值列表
 * 
 * @param param json
 * @returns
 */
function queryRefDef(param) {
	var refDefList = null;
	$.ajax({
		url : "refDef/queryRefDef",
		type : "post",
		dataType : "json",
		async : false,
		data : param,
		success : function(result) {
			refDefList = result.rows;
		}
	});
	return refDefList;
}

/**
 * 初始化码值列表
 */
function initRefDefList() {
	var refDefList = queryRefDef(null);
	if (refDefList != null && refDefList.length > 0) {
		var arr = new Array();
		for (var i = 0; i < refDefList.length; i++) {
			var refType = refDefList[i].refType;
			if (i > 0 && refType != refDefList[i - 1].refType) {
				v_refDefObj[refDefList[i - 1].refType] = arr;
				arr = new Array();
			}
			arr.push(refDefList[i]);
		}
		v_refDefObj[refDefList[refDefList.length - 1].refType] = arr;
	}
}

initRefDefList();

/**
 * 通过码值类型和码值获取码值描述
 * 
 * @param refType
 * @param refCode
 * @returns {String}
 */
function getRefDesc(refType, refCode) {
	if (refType && refCode && v_refDefObj) {
		var refDefList = v_refDefObj[refType];
		if (refDefList && refDefList.length > 0) {
			for (var i = 0; i < refDefList.length; i++) {
				if (refCode == refDefList[i].refCode) {
					return refDefList[i].refDesc;
				}
			}
		}
	}
	return "";
}
/**
 * v_userListObj 所有用户存储对象，格式如下 
 * [{userId:"",userName:"",...},{userId:"",userName:"",...}]
 */
var v_userListObj = new Array();
/**
 * 查询用户信息
 * @param param
 * @returns
 */
function queryUser(param) {
	var userList = null;
	$.ajax({
		url : "user/queryList",
		type : "post",
		dataType : "json",
		async : false,
		data : param,
		success : function(result) {
			userList = result.rows;
		}
	});
	return userList;
}

/**
 * 初始化用户列表
 */
function initUserList() {
	var userList = queryUser(null);
	if (userList != null && userList.length > 0) {
		v_userListObj = userList ;
	}
}

initUserList();

/**
 * 查询用户名称
 * @param userId
 * @returns
 */
function getUserName(userId) {
	if (userId && v_userListObj && v_userListObj.length > 0) {
		for (var i = 0; i < v_userListObj.length; i++) {
			if (userId == v_userListObj[i].userId) {
				return v_userListObj[i].userName;
			}
		}
	}
	return "";
}

/**
 * 查询用户下拉列表
 * @param group
 */
function getUserListForCombobox(group){
	var userList = new Array();
	if (group && v_userListObj && v_userListObj.length > 0) {
		for (var i = 0; i < v_userListObj.length; i++) {
			if (group == v_userListObj[i].userGroup) {
				userList.push(v_userListObj[i]);
			}
		}
	}
	return userList;
}
/**
 * 创建用户下拉列表
 * @param obj
 * @param datas
 */
function createUserCombobox(obj, datas) {
	obj.combobox({
		valueField : 'userId',
		textField : 'userName',
		data : datas
	});
};

/**
 * 创建下拉列表
 * 
 * @param obj
 *            input元素
 * @param datas
 *            json数组
 */
function createCombobox(obj, datas) {
	obj.combobox({
		valueField : 'refCode',
		textField : 'refDesc',
		data : datas
	});
};

/**
 * 日期格式化
 * @param obj
 */
function dateFormatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	m = m<10 ? "0"+m : m;
	var d = date.getDate();
	d = d<10 ? "0"+d : d;
	return ""+y+m+d;
}

function dateParser(s){
	if(s){
		var y = s.substring(0,4);
		var m = s.substring(4,6);
		var d = s.substring(6);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		}
	}
	return "";
}

/**
 * 转json
 */
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
		} else {
			o[this.name] = this.value || "";
		}
	});
	return o;
}

/**
 * 添加tab
 * @param obj
 * @param title
 * @param href
 * @param selectedFlag
 */
function activity_detail_addTab(obj,title,href,selectedFlag,loadFunc){
	if (obj.tabs("exists", title)){
		obj.tabs("select", title);
		return;
	}
	obj.tabs('add', {
		title : title,
		href : href,
		selected : selectedFlag,
		onLoad : loadFunc
	});
};

/**
 * 元素显示或者隐藏
 * @param obj
 * @param name
 * @param falg
 */
function display(obj,name,falg){
	var o = obj.find("input[textboxname='"+name+"']");
	if(falg){
		o.prev().show();
		o.next().show();
	}else{
		o.prev().hide();
		o.next().hide();
	}
}

/**
 * 获取指定任务类型流程的最后一个活动类型
 * @param taskType
 * @returns
 */
function getLastActivityType(taskType){
	var activityTypeList = v_refDefObj[v_refType.ACTIVITY_TYPE];
	for(var i=activityTypeList.length-1;i>=0;i--){
		var at = activityTypeList[i];
		if(at.refCode1.indexOf(taskType)!=-1){
			return at.refCode;
		}
	}
}

/**
 * 设置按钮重复提交，点击后禁用（默认3秒），恢复可用
 * @param btn 按钮ID或class
 * @param sec 禁用时间（秒）
 */
function delayBtnEnable(btn, sec){
	var obj = $("#"+btn) || $("."+btn);
	obj.linkbutton('disable');

	if (""==sec || undefined==sec || 0==sec){
		sec = 3000;
	}else{
		sec *= 1000;
	}
	setTimeout(function(){
		obj.linkbutton('enable');
	}, sec);
}

var v_activityDialog = null;

/**
 * 计算起止日期间有多少个工作日
 * @param startDate
 * @param endDate
 */
function countWorkDay(startDate,endDate){
	if(startDate==endDate){
		return 0;
	}
	var sDate = dateParser(startDate);
	var eDate = dateParser(endDate);
	var sDay = sDate.getDay();
	var eDay = eDate.getDay();
	var diffDay = (eDate-sDate)/(1000*60*60*24)+1;
	var weeks = Math.floor((diffDay-(sDay==0?0:7-sDay))/7);
	if(weeks<=0){
		return 6-sDay+(eDay>sDay?(eDay-6):eDay)-1;
	}else{
		return weeks*5+(6-sDay)+(eDay==6?0:eDay)-1;
	}
}

/**
 * 获取开始日期后指定工作日后的日期
 * @param startDate
 * @param workDay
 */
function getEndDateByStartDate(startDate,workDay){
	if(!startDate){
		return "";
	}
	if(workDay==0){
		return startDate;
	}
	var sDate = dateParser(startDate);
	var sDay = sDate.getDay();
	//本周剩余工作日
	var day = 6-((sDay==0||sDay==6)?6:sDay);
	if(day>=workDay){
		sDate.setDate(sDate.getDate()+workDay);
	}else{
		sDate.setDate(sDate.getDate()+day+2);
		workDay = workDay - day;
		sDate.setDate(sDate.getDate()+Math.floor(workDay/5)*7+workDay%5);
	}
	sDay = sDate.getDay();
	if(sDay==6||sDay==0){
		sDate.setDate(sDate.getDate()+(sDay==6?2:1));
	}
	return dateFormatter(sDate);
}

function activity_info_date_changeFunc(userId,startDate,endDate,hours){
	hours = hours ? parseInt(hours) : 0;
	if(!userId || !startDate || !endDate || !hours || hours==0){
		return;
	}
	var diffHours = countWorkDay(startDate,endDate)*8;
	var param = {
			userId:userId,
			startDate:startDate,
			endDate:endDate
	};
	$.post("activity/queryActivityHoursSum", param, function(result){
		if(result.retCode=="success"){
			if(result.data && result.data.length>0){
				var sumHours = result.data[0].activityHours;
				if(diffHours<(sumHours+hours)){
					var h = diffHours-sumHours;
					h = h>=0?h:0;
					$.messager.alert('警告', startDate+"-"+endDate+"期间，剩余工时为"+h+"，请谨慎安排工作！", 'warning');
				}
			}
		}
	});
}

function activity_info_date_func(){
	$("#activity_info_userId").combobox({
		onChange:function(newValue,oldValue){
			var options = $(this).combobox("options");
			if(options.stopFirstChangeEvent){
				options.stopFirstChangeEvent = false;
				return;
			}
			if(!newValue){
				return;
			}
			var activity_info_activityHours = $("#activity_info_activityHours").numberbox("getValue");
			activity_info_activityHours = activity_info_activityHours ? activity_info_activityHours : 0 ;

			$("#activity_info_startDate").datebox("setValue","");
			$("#activity_info_endDate").datebox("setValue","");
			var nowDate=new Date();
			var param = {
					userId:newValue,
					startDate:dateFormatter(nowDate)
			};
			$.post("activity/queryActivityHours", param, function(result){
				if(result.retCode=="success"){
					if(result.data && result.data.length>0){
						var resData = result.data;
						var startDate = null;
						var endDate=null;
						var dateTmp = dateFormatter(nowDate);
						var sumHours=0;
						for(var i=0;i<resData.length;i++){
							var ad = resData[i];
							if(ad.startDate <= dateTmp){
								sumHours += ad.activityHours;
								dateTmp = getEndDateByStartDate(dateFormatter(nowDate),Math.ceil(sumHours/8));
							}else{
								if(startDate==null){
									sumHours += activity_info_activityHours;
									startDate = dateTmp;
								}else{
									endDate = dateTmp;
									break;
								}
							}
						}
						if(startDate==null){
							startDate = dateTmp;
							endDate = getEndDateByStartDate(dateTmp,Math.ceil(activity_info_activityHours/8));
						}
						$("#activity_info_startDate").datebox("setValue",startDate);
						$("#activity_info_endDate").datebox("setValue",endDate);
					}
				}
			});
		}
	});
	$("#activity_info_activityHours").numberbox({
		onChange:function(newValue,oldValue){
			var options = $(this).numberbox("options");
			if(options.stopFirstChangeEvent){
				options.stopFirstChangeEvent = false;
				return;
			}
			newValue = newValue ? newValue : 0 ;
			var activity_info_startDate = $("#activity_info_startDate").datebox("getValue");
			var activity_info_userId = $("#activity_info_userId").datebox("getValue");
			if(activity_info_startDate && activity_info_userId){
				$("#activity_info_endDate").datebox("setValue","");
				var param = {
						userId:activity_info_userId,
						startDate:activity_info_startDate
				};
				$.post("activity/queryActivityHours", param, function(result){
					if(result.retCode=="success"){
						if(result.data && result.data.length>0){
							var resData = result.data;
							var endDate=activity_info_startDate;
							var sumHours=parseInt(newValue);
							for(var i=0;i<resData.length;i++){
								var ad = resData[i];
								if(ad.startDate <= endDate){
									sumHours += ad.activityHours;
									endDate = getEndDateByStartDate(activity_info_startDate,Math.ceil(sumHours/8));
								}else{
									break;
								}
							}
							$("#activity_info_endDate").datebox("setValue",endDate);
						}
					}
				});
			}
		}
	});
	$("#activity_info_startDate").datebox({
		onSelect: function(date){
			var activity_info_endDate = $("#activity_info_endDate").datebox("getValue");
			if(date && activity_info_endDate){
				var activity_info_userId = $("#activity_info_userId").datebox("getValue");
				var activity_info_activityHours = $("#activity_info_activityHours").numberbox("getValue");
				if(date>dateParser(activity_info_endDate)){
					$.messager.alert('警告', "开始日期不得大于截止日期", 'warning');
					$("#activity_info_startDate").datebox("setValue","");
				}
				activity_info_date_changeFunc(activity_info_userId,dateFormatter(date),activity_info_endDate,activity_info_activityHours);
			}
		}
	});
	$("#activity_info_endDate").datebox({
		onSelect: function(date){
			var activity_info_startDate = $("#activity_info_startDate").datebox("getValue");
			if(date && activity_info_startDate){
				var activity_info_userId = $("#activity_info_userId").datebox("getValue");
				var activity_info_activityHours = $("#activity_info_activityHours").numberbox("getValue");

				if(date<dateParser(activity_info_startDate)){
					$.messager.alert('警告', "截止日期不得小于开始日期", 'warning');
					$("#activity_info_endDate").datebox("setValue","");
				}
				activity_info_date_changeFunc(activity_info_userId,activity_info_startDate,dateFormatter(date),activity_info_activityHours);
			}
		}
	});
}

function activity_info_container_init(){
	var objId = "activity_info_container";
	$("#activity_info_container").height(200);
	var yTitle = '';
	var xCategories = new Array();
	var seriesData1 = new Array();
	var seriesData2 = new Array();
	var startDate = new Date();
	var startDateStr = dateFormatter(startDate);
	var endDate = startDate;
	var dayCount = 10;
	var endDateStr = getEndDateByStartDate(startDateStr,dayCount);
	var param = {
			startDate:startDateStr,
			endDate:endDateStr
	};
	var title = '人员工作日程表（'+startDateStr+'-'+endDateStr+'）';
	$.post("activity/queryActivityHoursSum", param, function(result){
		if(result.retCode=="success"){
			var resData = result.data;
			if(resData && resData.length>0){
				for(var i=0;i<resData.length;i++){
					var obj = resData[i];
					var activityDay = Math.ceil(obj.activityHours/8);
					activityDay = activityDay > dayCount ? dayCount : activityDay ;
					seriesData1.push(dayCount-activityDay);
					seriesData2.push(activityDay);
					xCategories.push(getUserName(obj.userId));
				}
				var seriesData = [{
			        name: '闲日',
			        data: seriesData1,
			        color:"#8FD6D0"
			    }, {
			        name: '忙日',
			        data: seriesData2,
			        color:"green"
			    }];
				Highcharts.chart(objId, {
				    chart: {
				        type: 'column'
				    },
				    title: {
				        text: title
				    },
				    xAxis: {
				        categories: xCategories
				    },
				    yAxis: {
				        min: 0,
				        max:dayCount,
				        title: {
				            text: yTitle
				        }
				    },
				    tooltip: {
				        pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.percentage:.0f}%)<br/>',
				        shared: true
				    },
				    plotOptions: {
				        column: {
				            stacking: 'normal'
				        }
				    },
				    series: seriesData
				});
			}
		}
	});
	
};

/**
 * 获取星期一
 */
function getMonday(date){
	var w = date.getDay();
	switch(w){
	case 0:
		date.setDate(date.getDate()-6);
		break;
	case 2:
		date.setDate(date.getDate()-1);
		break;
	case 3:
		date.setDate(date.getDate()-2);
		break;
	case 4:
		date.setDate(date.getDate()-3);
		break;
	case 5:
		date.setDate(date.getDate()-4);
		break;
	case 6:
		date.setDate(date.getDate()-5);
		break;
	}
	return dateFormatter(date);
}

/**
 * 获取星期五
 */
function getFriday(date){
	var w = date.getDay();
	switch(w){
	case 0:
		date.setDate(date.getDate()-2);
		break;
	case 1:
		date.setDate(date.getDate()+4);
		break;
	case 2:
		date.setDate(date.getDate()+3);
		break;
	case 3:
		date.setDate(date.getDate()+2);
		break;
	case 4:
		date.setDate(date.getDate()+1);
		break;
	case 6:
		date.setDate(date.getDate()-1);
		break;
	}
	return dateFormatter(date);
}

/**
 * 获取传入日期的星期区间
 * @param date date格式
 * @return {"start":"20180708", "end":"20180713"}
 */
function getWeek(date){
	return week = {
			"start":getMonday(date),
			"end":getFriday(date)
	};
}

/**
 * 获取当月第一天
 */
function getMonthFirstDay(date){
	date.setDate(1);
	return dateFormatter(date);
}

/**
 * 获取当月最后一天
 */
function getMonthLastDay(date){
	var m = date.getMonth();
	var d = 31;
	switch (m){
	case 1:
		var y = date.getFullYear();
		if (0==y%4 && (0!=y%100 || 0==y%400)){
			d = 29;
		}
		d = 28;
		break;
	case 3:
	case 5:
	case 8:
	case 10:
		d =  30;
		break;
	default:
		break;
	}
	date.setDate(d);
	return dateFormatter(date);
}

/**
 * 获取传入日期的月区间
 * @param date date格式
 * @return {"start":"20180701", "end":"20180731"}
 */
function getMonth(date){
	return month = {
			"start":getMonthFirstDay(date),
			"end":getMonthLastDay(date)
	};
}

/**
 * 获取季度第一天
 */
function getQuarterFirstDay(date){
	var m = date.getMonth();
	switch (m){
	case 0:
	case 1:
	case 2:
		date.setDate(1);
		date.setMonth(0);
		break;
	case 3:
	case 4:
	case 5:
		date.setDate(1);
		date.setMonth(3);
		break;
	case 6:
	case 7:
	case 8:
		date.setDate(1);
		date.setMonth(6);
		break;
	case 9:
	case 10:
	case 11:
		date.setDate(1);
		date.setMonth(9);
		break;
	}
	return dateFormatter(date);
}

/**
 * 获取季度最后一天
 */
function getQuarterLastDay(date){
	var m = date.getMonth();
	switch (m){
	case 0:
	case 1:
	case 2:
		date.setMonth(2);
		date.setDate(31);
		break;
	case 3:
	case 4:
	case 5:
		date.setMonth(5);
		date.setDate(30);
		break;
	case 6:
	case 7:
	case 8:
		date.setMonth(8);
		date.setDate(30);
		break;
	case 9:
	case 10:
	case 11:
		date.setMonth(11);
		date.setDate(31);
		break;
	}
	return dateFormatter(date);
}

/**
 * 获取传入日期的季区间
 * @param date date格式
 * @return {"start":"20180701", "end":"20180731"}
 */
function getQuarter(date){
	return month = {
			"start":getQuarterFirstDay(date),
			"end":getQuarterLastDay(date)
	};
}

/** 
 * 获取传入日期的年区间
 * @param date date格式
 * @return {"start":"20180701", "end":"20180731"}
 */
function getYear(date){
	var y = date.getFullYear();
	return month = {
			"start":y+"0101",
			"end":y+"1231"
	};
}


/**日期差计算*/
function calcDays(strStartDate, strEndDate){
	var startDate = dateParser(strStartDate);
	var endDate = dateParser(strEndDate);
	if ((endDate-startDate) <= 0){
		return 1;
	}
	return (endDate-startDate)/1000/60/60/24 + 1;
}

/**
 * 加载日程报表
 */
function loadScheduleHighcharts(containerId,containerTitle,categoriesArr,seriesDataArr){
	var minHeight = $("#"+containerId).parent().css('height');
	minHeight = minHeight.replace("px","");
	var lineHeight = 25;
	var height = categoriesArr.length*lineHeight>minHeight?categoriesArr.length*lineHeight:minHeight;
	Highcharts.chart(containerId, {
	    chart: {
	        type: 'xrange',
	        height:height+"px"
	    },
	    title: {
	        text: containerTitle
	    },
	    xAxis: {
	        type: 'datetime',
	        dateTimeLabelFormats:{
	        	day:'%d/%m'
	        }
	    },
	    yAxis: {
	        title: {
	            text: ''
	        },
	        categories: categoriesArr,
	        reversed: true
	    },
	    series: [{
	        name: '日期',
	        // pointPadding: 0,
	        // groupPadding: 0,
	        borderColor: 'gray',
	        pointWidth: 15,
	        data: seriesDataArr,
	        dataLabels: {
	            enabled: false
	        } 
	    }],
	    tooltip: {// xDateFormat:'%%',
	    	formatter:function(){
	    		var point = this.point;
	    		return '<b>'+point.taskName+'</b>'
	        	+'<br/>'+(point.taskStage ? point.taskStage : point.taskType)+'        '+point.startDate+'-'+point.endDate+'        工时:'+point.taskHours+''
	        	+'<br/>'+point.userName+'        '+point.taskStatus+'<br/>';
	    	}
	    }
	});
}

/** session超时，跳转登录页面 */
$.ajaxSetup({
	complete:function(XMLHttpRequest, textStatus){
		var sessionStatus = XMLHttpRequest.getResponseHeader("sessionStatus");
		if ("timeOut" == sessionStatus){
			var w = window;
			while (w != w.parent){
				w = w.parent;
			}
			w.location.href = "index.html";
		}
	}
});