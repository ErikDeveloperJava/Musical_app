$(document).ready(function () {
    var searchFontMsg = $("#search-font-msg").attr("content");
    var role = $("#role").attr("content");
    var token  =$("#frsc").attr("token");

    $(document).on("submit","#search-form",function (event) {
        event.preventDefault();
        var name = $("#name").val();
        if(name != null  && name.length != 0){
            $.ajax({
                type: "GET",
                url: "/album/search",
                data: {name: name},
                success: function (data) {
                    $("#album-blog").empty();
                    $("#album-blog").html(data);
                    $("#result").text(searchFontMsg + " '" + name + "'");
                },
                error: function () {
                    window.location = "/error";
                }
            })
        }
    })
})