$(document).ready(function () {
    var searchMsg = $("#search-msg").attr("content");

    $(document).on("submit","#search-form",function (event) {
        event.preventDefault();
        var name = $("#name").val();
        if(name != null && name.length != 0){
            $.ajax({
                type: "GET",
                url: "/musician/search",
                data: {name: name},
                success: function (data) {
                    $("#musicians-blog").empty();
                    $("#musicians-blog").html(data);
                    $("#result").text(searchMsg + " '" + name + "'");
                    $("#result").css("left","190px")
                },
                error: function () {
                    window.location = "/error";
                }
            })
        }
    })
});