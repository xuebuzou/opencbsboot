$(function(){
    var role_param;
    var dep_param;
    function initParam() {
        $.get("/zg/home/settings/user/init", function (result) {
            if ("success" == result.retCode) {
                role_param = result.result['role'];
                dep_param = result.result['dep'];
                console.log('role_param',role_param);
                console.log('dep_param',dep_param);
                $('#settings_user_dialog_adduser_combobox_role').combobox({
                    valueField: 'id',
                    textField: 'description',
                    data: role_param
                });
                $('#settings_user_dialog_adduser_combobox_dep').combobox({
                    valueField: 'depId',
                    textField: 'depDesc',
                    data: dep_param
                });
                $('#settings_user_dialog_upduser_combobox_role').combobox({
                    valueField: 'id',
                    textField: 'description',
                    data: role_param
                });
                $('#settings_user_dialog_upduser_combobox_dep').combobox({
                    valueField: 'depId',
                    textField: 'depDesc',
                    data: dep_param
                });
                $('#settings_user_combobox_role').combobox({
                    valueField: 'id',
                    textField: 'description',
                    data: role_param
                });
                $('#settings_user_combobox_dep').combobox({
                    valueField: 'depId',
                    textField: 'depDesc',
                    data: dep_param
                });

                qryparams = {
                    "username":"",
                    "cnname":""
                };
                var usergrid_toolbar = "#settings_user_table_usergrid_12_toolbar";
                $("#settings_user_table_usergrid_12").datagrid(
                    {
                        url: "/zg/home/settings/user/query",
                        nowrap: false,
                        editorHeight: 100,
                        queryParams: qryparams,
                        striped: true,
                        idField: "username",
                        fitColumns: true,
                        singleSelect: false,
                        pageSize: 15,
                        pageList: [15, 30, 50, 80, 100],
                        toolbar: usergrid_toolbar,
                        columns: [[
                            {
                                field: 'checkbox',
                                checkbox: true
                            },
                            {
                                field: 'username',
                                title: '账号',
                                width: 30,
                                align: 'center',
                            },
                            {
                                field: 'cnname',
                                title: '姓名',
                                width: 30,
                                align: 'center',
                            },
                            {
                                field: 'roleId',
                                title: '角色',
                                width: 30,
                                align: 'center',
                                formatter: function (value, row, index) {
                                    return getRoleDesc(value);
                                },
                            },
                            {
                                field: 'depId',
                                title: '机构',
                                width: 30,
                                align: 'center',
                                formatter: function (value, row, index) {
                                    return getDepDesc(value);
                                },
                            },
                            {
                                field: 'telephone',
                                title: '座机',
                                width: 30,
                                align: 'center',
                            },
                            {
                                field: 'mobilePhone',
                                title: '手机',
                                width: 30,
                                align: 'center',
                            }
                        ]],
                        onLoadSuccess:function(data){
                            $('#settings_user_dialog_adduser').dialog({
                                width: "40%",
                                height: "70%",
                                closed: true,
                                modal: true,
                                buttons: "#settings_user_dialog_adduser_buttons",
                                draggable: false
                            });
                            $('#settings_user_dialog_adduser_buttons_submit').linkbutton({
                                iconCls: 'icon-save'
                            });
                            $('#settings_user_dialog_adduser_buttons_close').linkbutton({
                                iconCls: 'icon-cancel'
                            });
                            $('#settings_user_dialog_upduser').dialog({
                                width: "40%",
                                height: "70%",
                                closed: true,
                                modal: true,
                                buttons: "#settings_user_dialog_upduser_buttons",
                                draggable: false
                            });
                            $('#settings_user_dialog_upduser_buttons_submit').linkbutton({
                                iconCls: 'icon-save'
                            });
                            $('#settings_user_dialog_upduser_buttons_close').linkbutton({
                                iconCls: 'icon-cancel'
                            });
                        }
                    }
                );
                //add
                $("#settings_user_linkbutton_adduser").on("click", function () {
                    $('#settings_user_dialog_adduser').dialog({
                        title: "新建用户",
                        closed: false
                    });
                    $('#settings_user_dialog_adduser').dialog('center');
                });
                $("#settings_user_dialog_adduser_buttons_submit").on("click", function () {
                    var username = $("#settings_user_dialog_adduser_input_useracct").textbox("getValue");
                    var cnname = $("#settings_user_dialog_adduser_input_cnname").textbox("getValue");
                    var telephone = $("#settings_user_dialog_adduser_input_tel").textbox("getValue");
                    var mobilePhone = $("#settings_user_dialog_adduser_input_mobile").textbox("getValue");
                    var roleId = $('#settings_user_dialog_adduser_combobox_role').combobox('getValue');
                    var depId = $('#settings_user_dialog_adduser_combobox_dep').combobox('getValue');
                    var params = {
                        username:username,
                        cnname:cnname,
                        telephone:telephone,
                        mobilePhone:mobilePhone,
                        roleId:roleId,
                        depId:depId
                    };
                    $.post("/zg/home/settings/user/adduser", params, function (result) {
                        if ("success" == result.retCode) {
                            $.messager.show({
                                title: '提示信息',
                                msg: '新建用户成功，初始密码为bocd',
                                showType: 'slide'
                            });
                            $('#settings_user_table_usergrid_12').datagrid("reload");
                            $('#settings_user_dialog_adduser').dialog("close");
                            $(".settings_user_dialog_adduser").textbox("clear");
                            $(".settings_user_dialog_adduser").combobox("clear");

                        } else {
                            $.messager.alert('提示信息', result.retMsg, 'error');
                        }
                    });
                });
                $("#settings_user_dialog_adduser_buttons_close").on("click", function () {
                    $('#settings_user_dialog_adduser').dialog("close");
                });
                function check_settings_user_dialog_adduser_buttons_submit(params) {
                    if(params.username==""){
                        $.messager.alert('提示信息', '账号不能为空！', 'error');
                        return false;
                    }
                    if(params.cnname==""){
                        $.messager.alert('提示信息', '姓名不能为空！', 'error');
                        return false;
                    }
                }
                //upd begin
                $("#settings_user_linkbutton_upduser").on("click", function () {
                    // $(".operated").textbox("enable");
                    // $(".operated").textbox("clear");
                    // $("#btnsSave").show();
                    var rows = $('#settings_user_table_usergrid_12').datagrid("getSelections");
                    // console.info("rows",rows);
                    if (rows.length != 1) {
                        $.messager.alert('提示信息', '请勾选一条记录', 'error');
                        $('#settings_user_table_usergrid_12').datagrid("unselectAll");
                        $('#settings_user_table_usergrid_12').datagrid("reload");
                        return;
                    }
                    $('#settings_user_dialog_upduser').dialog({
                        title: "修改用户",
                        closed: false
                    });
                    $('#settings_user_dialog_upduser').dialog('center');
                    console.log("rows[0].username",rows[0].username);
                    $('#settings_user_dialog_upduser_input_useracct').textbox('setValue',rows[0].username);
                    $('#settings_user_dialog_upduser_input_useracct').textbox('disable');
                    $('#settings_user_dialog_upduser_input_cnname').textbox('setValue',rows[0].cnname);
                    $('#settings_user_dialog_upduser_combobox_role').combobox('setValue',rows[0].roleId);
                    // $('#settings_user_dialog_upduser_combobox_role').combobox('disable');
                    $('#settings_user_dialog_upduser_combobox_dep').combobox('setValue',rows[0].depId);
                    $('#settings_user_dialog_upduser_input_tel').textbox('setValue',rows[0].telephone);
                    $('#settings_user_dialog_upduser_input_mobile').textbox('setValue',rows[0].mobilePhone);
                });
                $("#settings_user_dialog_upduser_buttons_submit").on("click", function () {
                    var username = $("#settings_user_dialog_upduser_input_useracct").textbox("getValue");
                    var cnname = $("#settings_user_dialog_upduser_input_cnname").textbox("getValue");
                    var telephone = $("#settings_user_dialog_upduser_input_tel").textbox("getValue");
                    var mobilePhone = $("#settings_user_dialog_upduser_input_mobile").textbox("getValue");
                    var roleId = $('#settings_user_dialog_upduser_combobox_role').combobox('getValue');
                    var depId = $('#settings_user_dialog_upduser_combobox_dep').combobox('getValue');
                    var params = {
                        username:username,
                        cnname:cnname,
                        telephone:telephone,
                        mobilePhone:mobilePhone,
                        roleId:roleId,
                        depId:depId
                    };
                    console.log('模拟修改提交,params',params);
                    $.post("/zg/home/settings/user/upduser", params, function (result) {
                        if ("success" == result.retCode) {
                            $.messager.show({
                                title: '提示信息',
                                msg: '修改用户成功',
                                showType: 'slide'
                            });
                            $('#settings_user_dialog_upduser').dialog("close");
                            $('#settings_user_table_usergrid_12').datagrid("load");
                        } else {
                            $.messager.alert('提示信息', result.retMsg, 'error');
                        }
                    });
                });
                $("#settings_user_dialog_upduser_buttons_close").on("click", function () {
                    $('#settings_user_dialog_upduser').dialog("close");
                });
                $("#settings_user_linkbutton_resetpwd").on("click", function () {
                    var rows = $('#settings_user_table_usergrid_12').datagrid("getSelections");
                    if (rows.length != 1) {
                        $.messager.alert('提示信息', '请勾选一条记录', 'error');
                        $('#settings_user_table_usergrid_12').datagrid("unselectAll");
                        $('#settings_user_table_usergrid_12').datagrid("reload");
                        return;
                    }
                    $.messager
                        .confirm(
                            '提示信息',
                            '您确定要重置该用户密码为bocd吗？',
                            function (r) {
                                if (r) {
                                    var params = {
                                        username:rows[0].username
                                    };
                                    $.post("/zg/home/settings/user/resetpwd", params, function (result) {
                                        if ("success" == result.retCode) {
                                            $.messager.show({
                                                title: '提示信息',
                                                msg: rows[0].username+'的密码已重置为bocd',
                                                showType: 'slide'
                                            });
                                        } else {
                                            $.messager.alert('提示信息', result.retMsg, 'error');
                                        }
                                    });
                                }
                            }
                        );
                });

                $("#settings_user_linkbutton_query").on("click", function (){
                    var username = $("#settings_user_input_acct").textbox("getValue");
                    var cnname = $("#settings_user_input_cnname").textbox("getValue");
                    var roleId = $('#settings_user_combobox_role').combobox('getValue');
                    var depId = $('#settings_user_combobox_dep').combobox('getValue');
                    var qryParam={
                        username:username,
                        cnname:cnname,
                        roleId:roleId,
                        depId:depId
                    }
                    $("#settings_user_table_usergrid_12").datagrid("reload",qryParam);
                });
                $("#settings_user_linkbutton_reset").on("click", function (){
                    $("#settings_user_input_acct").textbox("clear");
                    $("#settings_user_input_cnname").textbox("clear");
                    $('#settings_user_combobox_role').combobox('clear');
                    $('#settings_user_combobox_dep').combobox('clear');
                });

            } else {
                $.messager.alert('提示信息', '交易初始化失败', 'error');
            }
        });
    }

    initParam();

    function getDepDesc(depId) {
        for(var i=0;i<dep_param.length;i++){
            if(depId == dep_param[i]['depCode']){
                return dep_param[i]['depDesc'];
                break;
            }
        }
    }
    function getRoleDesc(roleId) {
        for(var i=0;i<role_param.length;i++){
            if(roleId == role_param[i]['id']){
                return role_param[i]['description'];
                break;
            }
        }
    }
});

