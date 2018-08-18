$(document).ready(function () {
    var token = $("#frsc").attr("content");

    $(".musician-input").on("input",function (event) {
        var name = $(this).attr("name");
        var value = $(this).val();
        isValidData(name,value,event);
    });

    $("#musician-form").on("submit",function (event) {
        var inputTags = [$("#name"),$("#surname"),$("#birthDate"),$("#biography"),$("#file")];
        $.each(inputTags,function (i, input) {
            var name = $(input).attr("name");
            var value = $(input).val();
            isValidData(name,value,event);
        })
    });

    $(document).on("click",".delete-musician",function (event) {
        event.preventDefault();
        var musicianId = $(this).attr("id");
        $.ajax({
            type: "POST",
            url: "/admin/musician/delete/" + musicianId,
            data: {_csrf: token},
            success: function (data) {
                if(data){
                    loadMusicians($(".current").text())
                }
            },
            error: function () {
                window.location = "/error";
            }
        })
    })
});
var nameError = $("#name-error").attr("content");
var surnameError = $("#surname-error").attr("content");
var birthDateError = $("#birth-date-error").attr("content");
var biographyError = $("#biography-error").attr("content");
var imageError = $("#image-error").attr("content");

function isValidData(name, value, event) {
    switch (name){
        case "name":
            if(value == null || value.length < 2 || value.length > 255){
                event.preventDefault();
                $("#nameError").text(nameError);
            }else {
                $("#nameError").text("");
            };
            break;
        case "surname":
            if(value == null || value.length < 4 || value.length > 255){
                event.preventDefault();
                $("#surnameError").text(surnameError);
            }else {
                $("#surnameError").text("");
            };
            break;
        case "birthDate":
            if(value == null || value.length !== 10 || parseInt(value.split("-")[0]) > 2006 || parseInt(value.split("-")[0]) < 1970){
                event.preventDefault();
                $("#birthDateError").text(birthDateError);
            }else {
                $("#birthDateError").text("");
            };
            break;
        case "biography":
            if(value == null || value.length < 10){
                event.preventDefault();
                $("#biographyError").text(biographyError);
            }else {
                $("#biographyError").text("");
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
    }
};
function loadMusicians(page) {
    $.ajax({
        type: "GET",
        url: "/musicians",
        data: {page: page,token: "load"},
        success: function (data) {
            $("#musicians-blog").empty();
            $("#musicians-blog").html(data);
        },
        error: function () {
            window.location = "/error";
        }
    })
}