function filterByTeam() {
    var team = document.getElementById("team").value;
    var table = document.getElementById("playerTable");
    var tr = table.getElementsByTagName("tr");
    for (var i = 1; i < tr.length; i++) {
        var teamName = tr[i].getElementsByTagName("td")[1].getElementsByTagName("a")[0].innerHTML;
        if (teamName != null) {
            console.log(teamName);
            if (teamName == team || team == "All") {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}