function showSeasonGames() {
    let currentSeasonId = document.getElementById("season").value;
    let recentGamesTable = document.getElementById("recentGames");

    let seasons = recentGamesTable.getElementsByTagName("tbody");

    for (let i=0; i<seasons.length; i++) {
        let season = seasons[i];
        if (season.id == currentSeasonId) {
            season.style.display = "";
        } else {
            season.style.display = "none";
        }
    }
}