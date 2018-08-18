$(document).ready(function () {
    var token = $("#frsc").attr("content");
    loadHome(token);
});

function loadHome(token) {
    $.ajax({
        type: "POST",
        url: "/home",
        data: {_csrf: token},
        success: function (data) {
            $("#body").css("background-image", "url(/resources/images/" + data.homeImg + ")");
            $("#body").css("background-repeat", "no-repeat");
            $("#body").css("background-position", "top center");
            $("#body").css("background-attachment", "fixed");
            $("#musician-img").attr("src", "/resources/images/musicians/" + data.musician.imgUrl);
        },
        error: function () {
            window.location = "/error";
        }
    });
}