jQuery(document).ready(function($) {
    $(".clickable").click(function() {
        var modal = document.getElementById(this.dataset.modal);
        modal.style.display = "block";

        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }

        var spans = document.getElementsByClassName("close");
        for (i = 0; i < spans.length; i++) {
            thisSpan = spans[i];

            // When the user clicks on <span> (x), close the modal
            thisSpan.onclick = function() {
                modal.style.display = "none";
            }
        }
    });
});