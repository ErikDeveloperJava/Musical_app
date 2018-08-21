$(document).ready(function () {
    var token = $("#frsc").attr("content");
    var musicChangedMsg = $("#music-changed-success-msg").attr("content");

    $(document).on("click",".make-main-music", function (event) {
        event.preventDefault();
        var musicId = $(this).attr("id").split("-")[0];
        $.ajax({
            type: "POST",
            url: "/admin/home/music/change",
            data: {_csrf: token,musicId: musicId},
            success: function (data) {
                if(data){
                    alert(musicChangedMsg);
                }
            },
            error: function () {
                window.location = "/error";
            }
        })
    });

    $(document).on("click",".delete-music",function (event) {
        event.preventDefault();
        var musicId = $(this).attr("id").split("-")[0];
        $.ajax({
            type: "POST",
            url: "/admin/music/delete",
            data: {_csrf: token,musicId : musicId},
            success: function (data) {
                if(data){
                    refresh();
                }
            },
            error: function () {
                window.location = "/error";
            }
        })
    })
});

function refresh() {
    $.ajax({
        type: "GET",
        url: "/music",
        data: {nimda: "ok"},
        success: function (data) {
            $("#music-blog").empty();
            $("#music-blog").append(data);
        },
        error: function () {
            window.location = "/error";
        }
    })
}