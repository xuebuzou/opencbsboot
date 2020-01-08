$(function(){
    params = {
        "username":"",
        "cnname":""
    };
    var usergrid_toolbar = "#settings_user_table_usergrid_12_toolbar";
    $("#settings_user_table_usergrid_12").datagrid(
        {
            url: "/zg/home/settings/user/query",
            nowrap: false,
            editorHeight: 100,
            queryParams: params,
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
                    field: 'roleDesc',
                    title: '角色',
                    width: 30,
                    align: 'center',
                },
                {
                    field: 'departmentId',
                    title: '机构',
                    width: 30,
                    align: 'center',
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
        // $(".operated").textbox("enable");
        // $(".operated").textbox("clear");
        // $("#btnsSave").show();
        $('#settings_user_dialog_adduser').dialog({
            title: "新建用户",
            closed: false
        });
        $('#settings_user_dialog_adduser').dialog('center');
        // if (loginUser.userId == 888) {
        //     $("#operateTaskStatus").combobox("setValue", WAIT_ALLOT);
        //     $("#operateTaskUser").combobox("setValue", current_manager_user_id);
        //     $("#operateTaskUser").combobox("disable");
        //     $("#operateWorkload").combobox("disable");
        //     //operateOnlineDate
        //     $("#operateOnlineDate").combobox("disable");
        //     $("#operateFinishDate").combobox("disable");
        // } else {
        //     $("#operateTaskStatus").combobox("setValue", PROCESSING);
        // }
        // $("#operateTaskStatus").combobox("disable");
        // $("#operateTaskManager").combobox("setValue", current_manager_user_id);
        // $("#operateTaskManager").combobox("disable");
    });
    $("#settings_user_dialog_adduser_buttons_submit").on("click", function () {
        var username = $("#settings_user_dialog_input_useracct").textbox("getValue");
        var cnname = $("#settings_user_dialog_input_cnname").textbox("getValue");
        var telephone = $("#settings_user_dialog_input_tel").textbox("getValue");
        var mobilePhone = $("#settings_user_dialog_input_mobile").textbox("getValue");
        var params = {
            username:username,
            cnname:cnname,
            telephone:telephone,
            mobilePhone:mobilePhone
        };
        $.post("/zg/home/settings/user/adduser", params, function (result) {
            if ("success" == result.retCode) {
                $.messager.show({
                    title: '提示信息',
                    msg: '新建用户成功，初始密码为bocd',
                    showType: 'slide'
                });
                $('#settings_user_dialog_adduser').dialog("close");
                $('#settings_user_table_usergrid_12').datagrid("load");
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
    //upd
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
        console.info("rows.username",rows[0].username);
        $('settings_user_dialog_upduser_input_useracct').textbox("setValue",rows[0].username);
        // if (loginUser.userId == 888) {
        //     $("#operateTaskStatus").combobox("setValue", WAIT_ALLOT);
        //     $("#operateTaskUser").combobox("setValue", current_manager_user_id);
        //     $("#operateTaskUser").combobox("disable");
        //     $("#operateWorkload").combobox("disable");
        //     //operateOnlineDate
        //     $("#operateOnlineDate").combobox("disable");
        //     $("#operateFinishDate").combobox("disable");
        // } else {
        //     $("#operateTaskStatus").combobox("setValue", PROCESSING);
        // }
        // $("#operateTaskStatus").combobox("disable");
        // $("#operateTaskManager").combobox("setValue", current_manager_user_id);
        // $("#operateTaskManager").combobox("disable");
    });
    $("#settings_user_dialog_upduser_buttons_close").on("click", function () {
        $('#settings_user_dialog_upduser').dialog("close");
    });

});

