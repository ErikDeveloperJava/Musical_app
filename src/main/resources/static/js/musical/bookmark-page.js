$(document).ready(function () {
    var token = $("#frsc").attr("content");
    var text = $("#bookmarks").text().split("(")[0];
    var bookmark = $("#bookmark").attr("content");
    var addBookmark = $("#add-bookmark").attr("content");

    $(document).on("click",".bookmark",function (event) {
        event.preventDefault();
        var id = $(this).attr("id");
        $.ajax({
            type: "POST",
            url: "/user/bookmark/add",
            data: {_csrf: token,musicId : id},
            success:function (data) {
                var count = $("#bookmarks").text().split("(")[1].split(")")[0];
                if(data){
                    $("#" + id).text(bookmark);
                    $("#" + id).css("background","yellow");
                    $("#" + id).css("color","black");
                    $("#" + id).css("box-shadow","0px 0px 0px 1px yellow");
                    $("#bookmarks").text(text + "(" + (parseInt(count) + 1) + ")")
                }else {
                    $("#" + id).text(addBookmark);
                    $("#" + id).css("background","white");
                    $("#" + id).css("color","#e74c3c");
                    $("#" + id).css("box-shadow","0px 0px 0px 1px #e74c3c");
                    $("#bookmarks").text(text + "(" + (parseInt(count) - 1) + ")")
                }
            },
            error: function () {
                //****
            }
        })
    })
});
