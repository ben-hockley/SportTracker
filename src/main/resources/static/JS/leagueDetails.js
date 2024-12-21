function showSeasonGames() {
    let currentSeasonId = document.getElementById("season").value;

    let allTables = document.getElementsByTagName("tbody");
    for (let i = 0; i < allTables.length; i++) {
        if (allTables[i].className == currentSeasonId) {
            allTables[i].style.display = "";
        } else {
            allTables[i].style.display = "none";
        }
    }
}