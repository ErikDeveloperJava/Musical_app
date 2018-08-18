$(document).ready(function () {
    var token = $("#frsc").attr("content");

    $(".default").on("click",function (event) {
        event.preventDefault();
        var userId = $(this).attr("title");
        $.ajax({
            type: "POST",
            url: "/admin/user/delete",
            data: {_csrf: token,userId: userId},
            success: function (data) {
                if(data){
                    $("#" + userId).remove();
                }
            },
            error: function () {
                window.location = "/error" ;
            }
        })
    })
})