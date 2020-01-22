$(function () {
    var rows = $('#openacct_progress_table_datagrid_12').datagrid("getSelections");
    $("#openacct_progress_detail_input_reservId").textbox({disabled:true});//{disabled:true}
    $("#openacct_progress_detail_input_reservId").textbox("setValue",rows[0].RESERV_ID);

    $("#openacct_progress_detail_input_ptName").textbox({disabled:true});
    $("#openacct_progress_detail_input_ptName").textbox("setValue",rows[0].PT_NAME);

    $("#openacct_progress_detail_input_certNo").textbox({disabled:true});
    $("#openacct_progress_detail_input_certNo").textbox("setValue",rows[0].CERT_NO);

    $("#openacct_progress_detail_combobox_status").combobox({
        valueField: 'status',
        textField: 'statusDesc',
        data: g_reserv_status_param
    });
    $("#openacct_progress_detail_combobox_status").combobox("setValue",rows[0].STATUS);

    $("#openacct_progress_detail_combobox_dep").combobox({
        valueField: 'depCode',
        textField: 'depDesc',
        data: g_dep_param,
        disabled:true
    });
    $("#openacct_progress_detail_combobox_dep").combobox("setValue",rows[0].DEP_CODE);

    $("#openacct_progress_detail_input_reservPhone").textbox({disabled:true});
    $("#openacct_progress_detail_input_reservPhone").textbox("setValue",rows[0].RESERV_PHONE);

    $("#openacct_progress_detail_input_acctType").combobox({
        valueField: 'acctType',
        textField: 'acctTypeDesc',
        data: g_acct_type_param,
        disabled:true
    });
    $("#openacct_progress_detail_input_acctType").combobox("setValue",rows[0].ACCT_TYPE);

    $("#openacct_progress_detail_input_note").textbox({
        multiline:true,
        height:300
    });
    $("#openacct_progress_detail_input_note").textbox("setValue",rows[0].NOTE);

    $("#openacct_progress_detail_linkbutton_submit").on("click", function () {
        var reservId = $("#openacct_progress_detail_input_reservId").textbox("getValue");
        var ptName = $("#openacct_progress_detail_input_ptName").textbox("getValue");
        var certNo = $("#openacct_progress_detail_input_certNo").textbox("getValue");
        var status = $("#openacct_progress_detail_combobox_status").combobox("getValue");
        var depCode = $("#openacct_progress_detail_combobox_dep").combobox("getValue");
        var reservPhone = $("#openacct_progress_detail_input_reservPhone").textbox("getValue");
        var acctType = $("#openacct_progress_detail_input_acctType").combobox("getValue");
        var note = $("#openacct_progress_detail_input_note").textbox("getValue");
        var params = {
            RESERV_ID:reservId,
            PT_NAME:ptName,
            CERT_NO:certNo,
            STATUS:status,
            DEP_CODE:depCode,
            RESERV_PHONE:reservPhone,
            ACCT_TYPE:acctType,
            NOTE:note
        };
        doPostFromZG("1000","0003",params,function (result) {
            if ("success" == result.retCode) {
                $('#openacct_progress_table_datagrid_12').datagrid("reload");
                $.messager.alert('提交成功', "交易执行成功","info");
            } else {
                $.messager.alert('提交失败', result.retCode+":"+result.retMsg, 'error');
            }
        });
    });

});

