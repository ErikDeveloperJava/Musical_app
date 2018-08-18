$(document).ready(function () {

    var token = $("#frsc").attr("content");

    $(".news-input").on("input",function (event) {
        var name = $(this).attr("name");
        var value = $(this).val();
        isValidData(name,value,event);
    });

    $("#news-form").on("submit",function (event) {
        var inputTags = [$("#title"),$("#description"),$("#file")];
        $.each(inputTags,function (i, input) {
            var name = $(input).attr("name");
            var value = $(input).val();
            isValidData(name,value,event);
        })
    });

    $(".delete-news").on("click",function (event) {
        event.preventDefault();
        var newsId = $(this).attr("href").split("/")[4];
        alert("newsId : " + newsId);
        if(newsId != null && newsId >0){
            $.ajax({
                type: "POST",
                url: "/admin/news/delete",
                data: {_csrf: token,newsId : newsId},
                success: function (data) {
                    if(data){
                        $("#" + newsId).remove();
                    }
                },
                error: function () {
                    window.location = "/error";
                }
            })
        }
    })

});
var titleError = $("#title-error").attr("content");
var descriptionError = $("#description-error").attr("content");
var imageError = $("#image-error").attr("content");

function isValidData(name, value, event) {
    switch (name){
        case "title":
            if(value == null || value.length < 4 || value.length > 255){
                event.preventDefault();
                $("#titleError").text(titleError)
            }else {
                $("#titleError").text("")
            };
            break;
        case "description":
            if(value == null || value.length < 8){
                event.preventDefault();
                $("#descriptionError").text(descriptionError);
            }else {
                $("#descriptionError").text("");
            };
            break;
        case "image":
            if(value == null || value.length < 4 || value.length > 255){
                event.preventDefault();
                $("#imageError").text(imageError);
            }else {
                $("#imageError").text("");
            };
    }
}