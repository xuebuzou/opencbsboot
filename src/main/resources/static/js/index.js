function login() {
    var username = $("#username").val();
    var password = $("#password").val();
    if (username == "" || password == "") {
        window.alert("用户名和密码不能为空");
        return;
    }
    $.ajax({
        url: "/checkLogin",
        type: "post",
        dataType: "json",
        async: false,
        data: {"username": username, "password": password},
        success: function (result) {
            if (result.retCode == "success") {
                sessionStorage.setItem("user", JSON.stringify(result.data));
                location.href = "zg/home";
            } else {
                $("#showMsg").html(result.retMsg);
            }
        },
        error: function (request) {
            $("#showMsg").html(request);
        },
    });
}

