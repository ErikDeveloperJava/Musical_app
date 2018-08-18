$(document).ready(function () {

    var token = $("#frsc").attr("content");

    $("#musicians").on("mouseover",function () {
        var clazz = $(this).attr("class");
        if(clazz === "empty"){
            $.ajax({
                type: "POST",
                url: "/admin/musicians",
                data: {_csrf: token},
                success:function (data) {
                    $("#musicians").children(".option").remove();
                    $.each(data,function (i,musician) {
                        var optionalTag =
                            "<option class='option' value='" + musician.id + "'>" + musician.name + " " +  musician.surname + "</option>";
                        $("#musicians").append(optionalTag);
                    })
                    $("#musicians").attr("class","not-empty")
                },
                error: function () {
                    window.location = "/error";
                }
            })
        }
    });

    $(".album-input").on("input",function (event) {
        var name = $(this).attr("name");
        var value = $(this).val();
        isValidData(name,value,event);
    });

    $("#album-form").on("submit",function (event) {
        var inputTags = [$("#name"),$("#description"),$("#releaseDate"),
            $("#musicians"),$("#file")];
        $.each(inputTags,function (i, input) {
            var name = $(input).attr("name");
            var value = $(input).val();
            isValidData(name,value,event);
        })
    });

    $(document).on("click",".delete-album",function (event) {
        event.preventDefault();
        var albumId = $(this).attr("id");
        $.ajax({
            type: "POST",
            url: "/admin/album/delete/" + albumId,
            data:{_csrf: token},
            success: function (data) {
                if(data){
                    loadAlbums($(".current").text())
                }
            },
            error:function () {
                window.location = "/error";
            }
        })
    })
});
var nameError = $("#name-error").attr("content");
var descriptionError = $("#description-error").attr("content");
var releaseDateError = $("#release-date-error").attr("content");
var musicianIdError = $("#musician-id-error").attr("content");
var imageError = $("#image-error").attr("content");

function isValidData(name, value, token) {
    switch (name){
        case "name":
            if(value == null || value.length < 2 || value.length > 255){
                event.preventDefault();
                $("#nameError").text(nameError);
            }else {
                $("#nameError").text("");
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
        case "releaseDate":
            if(value == null || value.length < 10 || parseInt(value.split("-")[0]) > 2018 || parseInt(value.split("-")[0]) <1990){
                event.preventDefault();
                $("#releaseDateError").text(releaseDateError);
            }else {
                $("#releaseDateError").text("");
            };
            break;
        case "image":
            if(value == null || value.length < 4 || value.length > 255){
                event.preventDefault();
                $("#imageError").text(imageError);
            }else {
                $("#imageError").text("");
            };
            break;
        case "musicianId":
            if(value == null || parseInt(value) <= 0){
                event.preventDefault();
                $("#musicianIdError").text(musicianIdError);
            }else {
                $("#musicianIdError").text("");
            };
            break;
    }
};

function loadAlbums(page) {
    $.ajax({
        type: "GET",
        url: "/albums",
        data:{page: page,token: "load"},
        success: function (data) {
            $("#album-blog").empty();
            $("#album-blog").html(data);
        },
        error: function () {
            window.location = "/error"
        }
    })
}