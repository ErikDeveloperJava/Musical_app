$(document).ready(function () {
    var token = $("#frsc").attr("content");
    var lang = $("#lang").attr("content");
    appendCategories(token,lang);
    appendAlbums(token);

    $(".music-input").on("input",function (event) {
        event.preventDefault();
        var name = $(this).attr("name");
        var value = $(this).val();
        isValidData(name,value);
    });

    $("#music-form").on("submit",function (event) {
        event.preventDefault();
        var inputTags = [$("#name"),$("#file"),$("#year")];
        var index = 0;
        $.each(inputTags,function (i, input) {
            var name = $(input).attr("name");
            var value = $(input).val();
            if(isValidData(name,value)){
                index++;
            }

        });

        if(index == 3){
            index = 0;
            $(".checkbox-input-cat").each(function () {
                if($(this).prop("checked")){
                    index++;
                }
            });
            if(index == 0){
                $("#categoryError").text(categoryError)
            }else {
                index = 0;
                $(".checkbox-input-album").each(function () {
                    if($(this).prop("checked")){
                        index++;
                    }
                });
                if(index == 0){
                    $("#albumError").text(albumError);
                }else {
                    $("#categoryError").text("");
                    event.preventDefault();
                    $.ajax({
                        type: "POST",
                        url: "/admin/music/add",
                        data: new FormData(this),
                        processData: false,
                        contentType: false,
                        success: function (data) {
                            if(data.nameError){
                                $("#nameError").text(nameError);
                            }else if(data.yearError){
                                $("#yearError").text(yearError);
                            }else if(data.musicError){
                                $("#musicError").text(musicError);
                            }else if (data.success) {
                                window.location = "/music"
                            }
                        },
                        error: function () {
                            window.location = "/error";
                        }
                    })
                }

            }

        }
    })
});

var nameError = $("#name-error").attr("content");
var musicError = $("#music-error").attr("content");
var yearError = $("#year-error").attr("content");
var categoryError = $("#category-error").attr("content");
var albumError = $("#album-error").attr("content");

function isValidData(name, value) {
    switch (name) {
        case "name":
            if (value == null || value.length < 2 || value.length > 255) {
                $("#nameError").text(nameError);
                return false;
            }else {
                $("#nameError").text("");
                return true;
            };
            break;
        case "music":
            if (value == null || value.length < 4 || value.length > 255) {
                $("#musicError").text(musicError);
                return false;
            }else {
                $("#musicError").text("");
                return true;
            };
            break;
        case "year":
            if (value == null || value.length != 4 || isNaN(parseInt(value)) || parseInt(value) < 2005 || parseInt(value) > 2019) {
                $("#yearError").text(yearError);
                return false;
            }else {
                $("#yearError").text("");
                return true;
            };
            break;
    }
}

function appendCategories(token,lang) {
    $.ajax({
        type: "POST",
        url: "/categories",
        data: {_csrf: token},
        success: function (data) {
            $.each(data,function (i, category) {
                var id = (parseInt(i) + 1 + Math.floor((Math.random() * 10000) + 1));
                var inputTag = "<div class='inputGroup'>" +
                    "<input id='option" + id + "' value='" + category.id + "' name='categories' class='checkbox-input-cat' type='checkbox'/>";

                if(lang == "en"){
                    inputTag += "<label for='option" + id + "'>" + category.nameEn + "</label>" +
                        "</div>";
                }else if(lang == "ru"){
                    inputTag += "<label for='option" + id + "'>" + category.nameRu + "</label>" +
                        "</div>";
                }else {
                    inputTag += "<label for='option" + id + "'>" + category.nameArm + "</label>" +
                        "</div>";
                }
                $("#cat-blog").append(inputTag);
            })
        },
        error: function () {
            window.location = "/error";
        }
    })
}


function appendAlbums(token) {
    $.ajax({
        type: "POST",
        url: "/admin/albums",
        data: {_csrf: token},
        success: function (data) {
            $.each(data,function (i, album) {
                var inputTag = "<div class='inputGroup'>" +
                    "<input id='option" + (parseInt(i) +1) + "' value='" + album.id + "' name='albums' class='checkbox-input-album' type='checkbox'/>" +
                    "<label for='option" + (parseInt(i) +1) + "'>" + album.name + "</label>" +
                    "</div>";
                $("#album-blog").append(inputTag);
            })
        },
        error: function () {
            window.location = "/error";
        }
    })
}
