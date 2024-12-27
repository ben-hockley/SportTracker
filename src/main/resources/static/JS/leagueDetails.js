function showSeasonGames() {
    let currentSeasonId = document.getElementById("season").value;

    let allTables = document.getElementsByTagName("tbody");
    for (let i = 0; i < allTables.length; i++) {
        if (allTables[i].className == ""){
            allTables[i].className = allTables[i].children[0].className;
            console.log(allTables[i].children[0].className);
        }

        console.log(allTables[i].className);
        console.log(currentSeasonId);
        console.log(allTables[i].className == currentSeasonId);
        console.log()

        if (allTables[i].className == currentSeasonId || allTables[i].className == "showThis") {
            allTables[i].style.display = "";
        } else {
            allTables[i].style.display = "none";
        }
    }
}