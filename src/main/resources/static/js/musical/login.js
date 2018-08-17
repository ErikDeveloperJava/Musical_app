$(document).ready(function () {
    $(".input").on("input",function () {
        var value = $(this).val();
        var name = $(this).attr("name");
        isValidData(name,value,null);
    });

    $("#login-form").on("submit",function (event) {
        var inputTags = $(".input");
        $.each(inputTags,function (i,input) {
            var value = $(input).val();
            var name = $(input).attr("name");
            isValidData(name,value,event);
        })
    })
});

var usernameError = $("#username-error").attr("content");
var passwordError = $("#password-error").attr("content");

function isValidData(name, value, event) {
    switch (name) {
        case "username":
            if (value == null || value.length < 2 || value.length > 255) {
                if(event != null){
                    event.preventDefault();
                }
                $("#usernameError").text(usernameError);
            } else {
                $("#usernameError").text("")
            }
            break;
        case "password":
            if (value == null || value.length < 4 || value.length > 255) {
                if(event != null){
                    event.preventDefault();
                }
                $("#passwordError").text(passwordError);
            } else {
                $("#passwordError").text("")
            }
    }
}