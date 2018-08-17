$(document).ready(function () {
    var token = $("#frsc").attr("content");
    var lang = $("#lang").attr("content");
    var catId = $("#catId").attr("content");
    if(catId == null){
        catId = 0;
    }
    loadCategories(token,lang,catId);
    loadHome(token);
});

function loadCategories(token, lang, catId) {
    $.ajax({
        type: "POST",
        url: "/categories",
        data: {_csrf: token},
        success: function (data) {
            $.each(data, function (i, category) {
                var name;
                var liTag;
                if (lang === "en") {
                    name = category.nameEn;
                }else if(lang === "ru"){
                    name = category.nameRu;
                }else {
                    name = category.nameArm;
                }
                if (category.id === catId) {
                    liTag = "<li class='current-item'><a style='cursor: pointer;'>" + name + "</a></li>";
                } else {
                    liTag = "<li><a href='/category/" + category.id + "'>" + name + "</a></li>";
                }
                $("#category-ul").append(liTag);
            })
        },
        error: function () {
            window.location = "/error"
        }
    })
};
function loadHome(token) {
    $.ajax({
        type: "POST",
        url: "/home",
        data: {_csrf : token},
        success: function (data) {
            $("#body").css("background-image","url(/resources/images/" + data.homeImg + ")");
            $("#body").css("background-repeat","no-repeat");
            $("#body").css("background-position","top center");
            $("#body").css("background-attachment","fixed");
            $("#musician-img").attr("src","/resources/images/musicians/" + data.musician.imgUrl);
        },
        error: function () {
            window.location = "/error";
        }
    });
}