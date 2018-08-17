$(document).ready(function () {
    $(".input").on("input",function () {
        var name = $(this).attr("name");
        var value = $(this).val();
        isValidData(name,value,null);
    });
    $("#file").on("change",function () {
        var name = $(this).attr("name");
        var value = $(this).val();
        isValidData(name,value,null);
    });

    $("#register-form").on("submit",function (event) {
        var inputTags = $(".input");
        $.each(inputTags,function (i,input) {
            var name = $(input).attr("name");
            var value = $(input).val();
            isValidData(name,value,event);
        });
        var name = $("#file").attr("name");
        var value = $("#file").val();
        isValidData(name,value,event);
    })
});
var nameError = $("#name-error").attr("content");
var surnameError = $("#surname-error").attr("content");
var usernameError = $("#username-error").attr("content");
var passwordError = $("#password-error").attr("content");
var imageError = $("#image-error").attr("content");

function isValidData(name, value, event) {
    switch (name) {
        case "name":
            if (value == null || value.length < 2 || value.length > 255) {
                if(event != null){
                    event.preventDefault();
                }
                $("#nameError").text(nameError);
            } else {
                $("#nameError").text("");
            };
            break;
        case "surname":
            if (value == null || value.length < 4 || value.length > 255) {
                if(event != null){
                    event.preventDefault();
                }
                $("#surnameError").text(surnameError);
            } else {
                $("#surnameError").text("");
            };
            break;
        case "username":
            if (value == null || value.length < 2 || value.length > 255) {
                if(event != null){
                    event.preventDefault();
                }
                $("#usernameError").text(usernameError);
            } else {
                $("#usernameError").text("");
            };
            break;
        case "password":
            if (value == null || value.length < 4 || value.length > 255) {
                if(event != null){
                    event.preventDefault();
                }
                $("#passwordError").text(passwordError);
            } else {
                $("#passwordError").text("");
            };
            break;
        case "image":
            if (value == null || value.length < 5 || value.length > 255) {
                if(event != null){
                    event.preventDefault();
                }
                $("#imageError").text(imageError);
            } else {
                $("#imageError").text("");
            };
    }
}