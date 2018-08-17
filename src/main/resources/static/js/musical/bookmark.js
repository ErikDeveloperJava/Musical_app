$(document).ready(function () {
    var token = $("#frsc").attr("content");
    var text = $("#bookmarks").text();
    loadBookmarksCount(token,text);
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