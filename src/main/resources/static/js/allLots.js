function show() {
    $.ajax({
        type: "GET",
        url: "/user/allLotsTable",
        cache: false,
        success: function(data) {
            $("#upd").html(data);
        }
    });
}

$(document).ready(function() {
    setInterval(show, 1000);
});