// Search (JQuery) ; credit W3Schools
$(document).ready(function(){
    $("#search").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#playerTableBody tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});



function filter() {
    var position = document.getElementById("position").value;
    var team = document.getElementById("team").value;
    var table = document.getElementById("playerTable");
    var tr = table.getElementsByTagName("tr");
    for (var i = 1; i < tr.length; i++) {
        var teamName = tr[i].getElementsByTagName("td")[1].getElementsByTagName("a")[0].innerHTML;
        var positionName = tr[i].getElementsByTagName("td")[2].innerHTML;
        if (positionName != null) {
            console.log(positionName);
            if ((positionName == position || position == "All") && (teamName == team || team == "All")) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}