$(function () {
    var reserv_status_def;
    var dep;

    function initParam() {
        $.get("/zg/home/openacct/progress/init", function (result) {
            if ("success" == result.retCode) {
                $('#openacct_progress_combobox_status').combobox({
                    valueField: 'status',
                    textField: 'statusDesc',
                    data: g_reserv_status_param
                });
                $('#openacct_progress_combobox_dep').combobox({
                    valueField: 'depCode',
                    textField: 'depDesc',
                    data: g_dep_param
                });
                var datagrid_toolbar = "#openacct_progress_table_datagrid_12_toolbar";
                var params = {
                    MESSAGE_TYPE:"1000",
                    MESSAGE_CODE:"0002"
                };
                $("#openacct_progress_table_datagrid_12").datagrid(
                    {
                        // url: "/zg/home/openacct/progress/datagrid",
                        url: "/zg/gateway",
                        nowrap: false,
                        queryParams:params,
                        editorHeight: 100,
                        striped: true,
                        idField: "RESERV_ID",
                        fitColumns: true,
                        singleSelect: false,
                        pageSize: 15,
                        pageList: [15, 30, 50, 80, 100],
                        toolbar: datagrid_toolbar,
                        columns: [[
                            {
                                field: 'checkbox',
                                checkbox: true
                            },
                            {
                                field: 'RESERV_ID',
                                title: '预约编号',
                                width: 30,
                                align: 'center',
                            },
                            {
                                field: 'PT_NAME',
                                title: '单位名称',
                                width: 30,
                                align: 'center',
                            },
                            {
                                field: 'CERT_NO',
                                title: '证照号码',
                                width: 30,
                                align: 'center',
                                // formatter: function (value, row, index) {
                                //     return getRoleDesc(value);
                                // },
                            },
                            {
                                field: 'STATUS',
                                title: '当前状态',
                                width: 30,
                                align: 'center',
                                formatter: function (value, row, index) {
                                    return getStatusDesc(value);
                                },
                            },

                            {
                                field: 'DEP_CODE',
                                title: '所属机构',
                                width: 30,
                                align: 'center',
                                formatter: function (value, row, index) {
                                    return getDepDesc(value);
                                },
                            },

                            {
                                field: 'RESERV_PHONE',
                                title: '预约电话',
                                width: 30,
                                align: 'center',
                            },
                            {
                                field: 'ACCT_TYPE',
                                title: '账户类型',
                                width: 30,
                                align: 'center',
                                formatter: function (value, row, index) {
                                    return getAcctTypeDesc(value);
                                },
                            },
                            {
                                field: 'VERIFIER',
                                title: '上门核实人',
                                width: 30,
                                align: 'center',
                            },
                            {
                                field: 'NOTE',
                                title: '备注',
                                width: 30,
                                align: 'center',
                                hidden:true
                            },
                        ]],
                        onLoadSuccess: function (data) {
                            // $('#openacct_progress_dialog_adduser').dialog({
                            //     width: "40%",
                            //     height: "70%",
                            //     closed: true,
                            //     modal: true,
                            //     buttons: "#openacct_progress_dialog_adduser_buttons",
                            //     draggable: false
                            // });
                            // $('#openacct_progress_dialog_adduser_buttons_submit').linkbutton({
                            //     iconCls: 'icon-save'
                            // });
                            // $('#openacct_progress_dialog_adduser_buttons_close').linkbutton({
                            //     iconCls: 'icon-cancel'
                            // });
                            // $('#openacct_progress_dialog_upduser').dialog({
                            //     width: "40%",
                            //     height: "70%",
                            //     closed: true,
                            //     modal: true,
                            //     buttons: "#openacct_progress_dialog_upduser_buttons",
                            //     draggable: false
                            // });
                            // $('#openacct_progress_dialog_upduser_buttons_submit').linkbutton({
                            //     iconCls: 'icon-save'
                            // });
                            // $('#openacct_progress_dialog_upduser_buttons_close').linkbutton({
                            //     iconCls: 'icon-cancel'
                            // });
                        }
                    }
                );
                $("#openacct_progress_linkbutton_detail").on("click", function () {
                    var rows = $('#openacct_progress_table_datagrid_12').datagrid("getSelections");
                    if (rows.length != 1) {
                        $.messager.alert('提示信息', '请勾选一条记录', 'error');
                        $('#openacct_progress_table_datagrid_12').datagrid("unselectAll");
                        $('#openacct_progress_table_datagrid_12').datagrid("reload");
                        return;
                    }
                    addTabs("详细预约信息", "/zg/home/openacct/progress/detail");
                });
                $("#openacct_progress_linkbutton_compare").on("click", function () {
                    // $('#openacct_progress_dialog_adduser').dialog({
                    //     title: "新建用户",
                    //     closed: false
                    // });
                    // $('#openacct_progress_dialog_adduser').dialog('center');
                    addTabs("数据比对", "");
                });
                $("#openacct_progress_dialog_adduser_buttons_submit").on("click", function () {
                    var username = $("#openacct_progress_dialog_adduser_input_useracct").textbox("getValue");
                    var cnname = $("#openacct_progress_dialog_adduser_input_cnname").textbox("getValue");
                    var telephone = $("#openacct_progress_dialog_adduser_input_tel").textbox("getValue");
                    var mobilePhone = $("#openacct_progress_dialog_adduser_input_mobile").textbox("getValue");
                    var roleId = $('#openacct_progress_dialog_adduser_combobox_role').combobox('getValue');
                    var depId = $('#openacct_progress_dialog_adduser_combobox_dep').combobox('getValue');
                    var params = {
                        username: username,
                        cnname: cnname,
                        telephone: telephone,
                        mobilePhone: mobilePhone,
                        roleId: roleId,
                        depId: depId
                    };
                    $.post("/zg/home/settings/user/adduser", params, function (result) {
                        if ("success" == result.retCode) {
                            $.messager.show({
                                title: '提示信息',
                                msg: '新建用户成功，初始密码为bocd',
                                showType: 'slide'
                            });
                            $('#openacct_progress_table_usergrid_12').datagrid("reload");
                            $('#openacct_progress_dialog_adduser').dialog("close");
                            $(".openacct_progress_dialog_adduser").textbox("clear");
                            $(".openacct_progress_dialog_adduser").combobox("clear");

                        } else {
                            $.messager.alert('提示信息', result.retMsg, 'error');
                        }
                    });
                });
                $("#openacct_progress_dialog_adduser_buttons_close").on("click", function () {
                    $('#openacct_progress_dialog_adduser').dialog("close");
                });

                function check_openacct_progress_dialog_adduser_buttons_submit(params) {
                    if (params.username == "") {
                        $.messager.alert('提示信息', '账号不能为空！', 'error');
                        return false;
                    }
                    if (params.cnname == "") {
                        $.messager.alert('提示信息', '姓名不能为空！', 'error');
                        return false;
                    }
                }

                //upd begin
                $("#openacct_progress_linkbutton_upduser").on("click", function () {
                    // $(".operated").textbox("enable");
                    // $(".operated").textbox("clear");
                    // $("#btnsSave").show();
                    var rows = $('#openacct_progress_table_usergrid_12').datagrid("getSelections");
                    // console.info("rows",rows);
                    if (rows.length != 1) {
                        $.messager.alert('提示信息', '请勾选一条记录', 'error');
                        $('#openacct_progress_table_usergrid_12').datagrid("unselectAll");
                        $('#openacct_progress_table_usergrid_12').datagrid("reload");
                        return;
                    }
                    $('#openacct_progress_dialog_upduser').dialog({
                        title: "修改用户",
                        closed: false
                    });
                    $('#openacct_progress_dialog_upduser').dialog('center');
                    console.log("rows[0].username", rows[0].username);
                    $('#openacct_progress_dialog_upduser_input_useracct').textbox('setValue', rows[0].username);
                    $('#openacct_progress_dialog_upduser_input_useracct').textbox('disable');
                    $('#openacct_progress_dialog_upduser_input_cnname').textbox('setValue', rows[0].cnname);
                    $('#openacct_progress_dialog_upduser_combobox_role').combobox('setValue', rows[0].roleId);
                    // $('#openacct_progress_dialog_upduser_combobox_role').combobox('disable');
                    $('#openacct_progress_dialog_upduser_combobox_dep').combobox('setValue', rows[0].depId);
                    $('#openacct_progress_dialog_upduser_input_tel').textbox('setValue', rows[0].telephone);
                    $('#openacct_progress_dialog_upduser_input_mobile').textbox('setValue', rows[0].mobilePhone);
                });
                $("#openacct_progress_dialog_upduser_buttons_submit").on("click", function () {
                    var username = $("#openacct_progress_dialog_upduser_input_useracct").textbox("getValue");
                    var cnname = $("#openacct_progress_dialog_upduser_input_cnname").textbox("getValue");
                    var telephone = $("#openacct_progress_dialog_upduser_input_tel").textbox("getValue");
                    var mobilePhone = $("#openacct_progress_dialog_upduser_input_mobile").textbox("getValue");
                    var roleId = $('#openacct_progress_dialog_upduser_combobox_role').combobox('getValue');
                    var depId = $('#openacct_progress_dialog_upduser_combobox_dep').combobox('getValue');
                    var params = {
                        username: username,
                        cnname: cnname,
                        telephone: telephone,
                        mobilePhone: mobilePhone,
                        roleId: roleId,
                        depId: depId
                    };
                    console.log('模拟修改提交,params', params);
                    $.post("/zg/home/settings/user/upduser", params, function (result) {
                        if ("success" == result.retCode) {
                            $.messager.show({
                                title: '提示信息',
                                msg: '修改用户成功',
                                showType: 'slide'
                            });
                            $('#openacct_progress_dialog_upduser').dialog("close");
                            $('#openacct_progress_table_usergrid_12').datagrid("load");
                        } else {
                            $.messager.alert('提示信息', result.retMsg, 'error');
                        }
                    });
                });
                $("#openacct_progress_dialog_upduser_buttons_close").on("click", function () {
                    $('#openacct_progress_dialog_upduser').dialog("close");
                });
                $("#openacct_progress_linkbutton_resetpwd").on("click", function () {
                    var rows = $('#openacct_progress_table_usergrid_12').datagrid("getSelections");
                    if (rows.length != 1) {
                        $.messager.alert('提示信息', '请勾选一条记录', 'error');
                        $('#openacct_progress_table_usergrid_12').datagrid("unselectAll");
                        $('#openacct_progress_table_usergrid_12').datagrid("reload");
                        return;
                    }
                    $.messager
                        .confirm(
                            '提示信息',
                            '您确定要重置该用户密码为bocd吗？',
                            function (r) {
                                if (r) {
                                    var params = {
                                        username: rows[0].username
                                    };
                                    $.post("/zg/home/settings/user/resetpwd", params, function (result) {
                                        if ("success" == result.retCode) {
                                            $.messager.show({
                                                title: '提示信息',
                                                msg: rows[0].username + '的密码已重置为bocd',
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

                $("#openacct_progress_linkbutton_query").on("click", function () {
                    var username = $("#openacct_progress_input_acct").textbox("getValue");
                    var cnname = $("#openacct_progress_input_cnname").textbox("getValue");
                    var roleId = $('#openacct_progress_combobox_role').combobox('getValue');
                    var depId = $('#openacct_progress_combobox_dep').combobox('getValue');
                    var qryParam = {
                        username: username,
                        cnname: cnname,
                        roleId: roleId,
                        depId: depId
                    };
                    $("#openacct_progress_table_usergrid_12").datagrid("reload", qryParam);
                });
                $("#openacct_progress_linkbutton_reset").on("click", function () {
                    $("#openacct_progress_input_acct").textbox("clear");
                    $("#openacct_progress_input_cnname").textbox("clear");
                    $('#openacct_progress_combobox_role').combobox('clear');
                    $('#openacct_progress_combobox_dep').combobox('clear');
                });

            } else {
                $.messager.alert('提示信息', '交易初始化失败', 'error');
            }
        });
    }

    initParam();
});

