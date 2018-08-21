$(document).ready(function () {
    var token = $("#frsc").attr("content");
    var text = $("#bookmarks").text();
    loadBookmarksCount(token,text);
    var bookmarkToken = $("#bookmark-token").attr("content");
    var bookmark;
    var addBookmark;
    if(bookmarkToken === "ok"){
        bookmark = $("#bookmark").attr("content");
        addBookmark = $("#add-bookmark").attr("content");
        loadBookMarks(token,bookmark);
    }

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
function loadBookmarksCount(token,text) {
    $.ajax({
        type: "POST",
        url: "/user/bookmarks/count",
        data: {_csrf: token},
        success: function (data) {
            $("#bookmarks").text(text + "(" + data +")")
        },
        error: function () {
            window.location = "/error"
        }
    })
}
function loadBookMarks(token,bookmark) {
    var musicList = [];
    var i = 0;
    $(".bookmark").each(function () {
        musicList[i++] = $(this);
    });
    console.log(musicList);
    $.ajax({
        type: "POST",
        url: "/user/bookmarks",
        data: {_csrf :token},
        success: function (data) {
            $.each(data,function (i, music) {
                $.each(musicList,function (i, bookmarkTag) {
                    var id = $(bookmarkTag).attr("id");
                    if(id == music.id){
                        $("#" + id).text(bookmark);
                        $("#" + id).css("background","yellow");
                        $("#" + id).css("color","black");
                        $("#" + id).css("box-shadow","0px 0px 0px 1px yellow");
                        return false;
                    }
                })
            })
        },
        error: function () {
            window.location = "/";
        }
    })
}