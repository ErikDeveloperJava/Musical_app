$(document).ready(function () {
    $("#mainImageForm").on("submit",function (event) {
        event.preventDefault();
        var image = $("#mainImg").val();
        if(image != null && image.length > 4){
            $.ajax({
                type: "POST",
                url:"/admin/change/main/image",
                data: new FormData(this),
                processData: false,
                contentType: false,
                success: function (data) {
                    window.location = "/admin/main/change"
                },
                error:function () {
                    //****
                }
            })
        }
    });
    $("#musicianImageForm").on("submit",function (event) {
        event.preventDefault();
        var image = $("#musicianImg").val();
        if(image != null && image.length > 4){
            $.ajax({
                type: "POST",
                url:"/admin/change/musician/image",
                data: new FormData(this),
                processData: false,
                contentType: false,
                success: function (data) {
                    window.location = "/admin/main/change"
                },
                error:function () {
                    //****
                }
            })
        }
    })
})