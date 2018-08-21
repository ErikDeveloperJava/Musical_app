$(document).ready(function () {

    var fontSearchMsg = $("#search-font-msg").attr("content");
    var role = $("#role").attr("content");
    var bookmark = $("#bookmark").attr("content");

    $(".font").on("click",function (event) {
        event.preventDefault();
        var font = $(this).attr("id");
        $(".font").each(function () {
            $(this).css("color","black")
        });
        $.ajax({
            type: "GET",
            url: "/music",
            data: {font: font},
            success: function (data) {
                $("#music-blog").empty();
                $("#music-blog").append(data);
                $("#result").text(fontSearchMsg + " '" + font + "'");
                $("#" + font).css("color","red");
                if(role == "USER"){
                    var token = $("#frsc_").val();
                    loadBookMarks(token,bookmark);
                }
                $("#custom-id").remove();
                $("#body").append("<script id='custom-id' type='text/javascript' src='/resources/js/custom.js'></script>")
            },
            error: function () {
                window.location = "/error";
            }
        })
    })
});