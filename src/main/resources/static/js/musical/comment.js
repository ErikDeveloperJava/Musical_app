$(document).ready(function () {
    loadComments();


    $("#comment-form").on("submit",function (event) {
        event.preventDefault();
        var comment = $("#comment").val();
        if(comment == null || comment.length == 0){
            $("#comment").css("border","2px solid red")
        }else {
            $("#comment").css("border","none");
            $.ajax({
                type: "POST",
                url: "/comment/add",
                data: {_csrf: token,newsId : newsId,comment: comment},
                success: function (data) {
                    if(data !== null){
                        var liTag = "<li class='comment even thread-even depth-1'>";
                        liTag = liTag + getCommentDiv(data);
                        liTag = liTag + "</li>";
                        $("#comments-blog").append(liTag);
                        $("#comment").val("");
                    }
                },
                error: function () {
                    //******
                }
            })
        }
    })

    $("#comment").on("input",function (event) {
        event.preventDefault();
        var value = $(this).val();
        if(value == null || value.length == 0){
            $("#comment").css("border","2px solid red")
        }else {
            $("#comment").css("border","none")
        }
    });
    $(document).on("click",".send-comment",function (event) {
        $(".children-form").remove();
        event.preventDefault();
        var title = $(this).attr("title");
        var parentId = $(this).attr("id");
        var commentDiv = $("#d" + parentId);
        if(title === "close"){
            $(this).attr("title","open");
            var commentForm = "<form class='children-form'>" +
                "<input type='hidden' class='parentId' value='" + parentId + "'>" +
                "<textarea style='height: 70px' class='commentText' placeholder='" + commentPlaceHolder + "' cols='45' rows='8'></textarea>" +
                "<p class='form-submit'>" +
                "<input type='submit' value='" + send + "'>" +
                "</p>" +
                "</form>";
            $(commentDiv).parent().append(commentForm);
        }else {
            $(this).attr("title","close");
            $(commentDiv).children(".children-form").remove();
        }
    });

    $(document).on("submit",".children-form",function (event) {
        event.preventDefault();
        var commentInput = $(this).children(".commentText");
        var comment = commentInput.val();
        var parentId = $(this).children(".parentId").val();
        if(comment == null || comment.length === 0){
            commentInput.css("border","2px solid red");
        }else {
            commentInput.css("border","none");
            $.ajax({
                type: "POST",
                url: "/comment/add",
                data: {_csrf: token,newsId: newsId,comment: comment,parentId: parentId},
                success: function (data) {
                    if(data != null){
                        var liTag = "<li class='comment even thread-even depth-1'>";
                        liTag += getCommentDiv(data);
                        liTag += "</li>";
                        $("#u" + parentId).append(liTag);
                        $(commentInput).val("");
                    }
                },
                error: function () {
                    window.location = "/";
                }
            })
        }
    })
});
var token = $("#frsc").attr("content");
var newsId = $("#newsId").val();
var sendComment = $("#send-comment").attr("content");
var commentPlaceHolder = $("#comment-placeholder").attr("content");
var send =$("#send").attr("content");

function loadComments() {
    $.ajax({
        type: "POST",
        url: "/comments",
        data: {_csrf: token, newsId: newsId},
        success: function (data) {
            $.each(data,function (i, comment) {
                var liTag = "<li class='comment even thread-even depth-1'>";
                liTag += appendComments(comment,liTag);
                liTag +="</li>";
                $("#comments-blog").append(liTag);
            })
        },
        error: function () {
            window.location = "/error"
        }
    })
}

function appendComments(comment,liTag) {
    liTag += getCommentDiv(comment.comment);
    if(comment.childrenList !== null){
        liTag += "<ul id='u" + comment.comment.id +"' class='children'>";
        $.each(comment.childrenList,function (i, child) {
            var childLiTag = "<li class='comment even thread-even depth-1'>";
            childLiTag += appendComments(child,childLiTag);
            childLiTag += "</li>";
            liTag += childLiTag;
        });
        liTag +="</ul>";
    }else {
        liTag += "<ul id='u" + comment.comment.id +"' class='children'>";
        liTag += "</ul>";
    }
    return liTag;
};

function dateFormat(date) {
    var day = date.getDate();
    if (parseInt(day) < 10) {
        day = "0" + day;
    }
    var month = date.getMonth() + 1;
    if (parseInt(month) < 10) {
        month = "0" + month;
    }
    var year = date.getFullYear();
    var hours = date.getHours();
    var minute = date.getMinutes();
    return " " + day + "." + month + "." + year + " " + hours + ":" + minute;
};

function getCommentDiv(comment) {
    var strDate = dateFormat(new Date(comment.sendDate));
    return "<div class='comment-body comment-div' id='d" + comment.id + "'>" +
        "<div class='comment-author vcard'>" +
        "<img src='/resources/images/users/" + comment.user.imgUrl + "' class='avatar avatar-32 photo'>" +
        "<cite><a class='url'>" + comment.user.name + " " + comment.user.surname + "</a></cite>" +
        "</div>" +
        "<div class='comment-meta commentmetadata'>" +
        "<a>" + strDate + "</a>" +
        "</div>" +
        "<p>" + comment.comment + "<a style='cursor: pointer;color: #953b39' title='close' class='send-comment' id='" + comment.id + "'>" + "  " +  sendComment + "</a></p>" +
        "</div>";
};
