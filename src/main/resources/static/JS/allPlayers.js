// Search (JQuery) ; credit W3Schools
$(document).ready(function(){
    $("#search").on("keyup", function() {
        const value = $(this).val().toLowerCase();
        $("#playerTableBody tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});



function filter() {
    const position = document.getElementById("position").value;
    const team = document.getElementById("team").value;
    const table = document.getElementById("playerTable");
    const tr = table.getElementsByTagName("tr");
    for (let i = 1; i < tr.length; i++) {
        const teamName = tr[i].getElementsByTagName("td")[1].getElementsByTagName("a")[0].innerHTML;
        const positionName = tr[i].getElementsByTagName("td")[2].innerHTML;
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