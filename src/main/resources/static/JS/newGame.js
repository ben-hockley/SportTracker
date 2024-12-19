function fixHomeScore() {
    const score = document.getElementById("homeTeamScore").value;

    const scoreQ1 = document.getElementById("homeTeamScoreQ1").value;
    const scoreQ2 = document.getElementById("homeTeamScoreQ2").value;
    const scoreQ3 = document.getElementById("homeTeamScoreQ3").value;
    const scoreQ4 = document.getElementById("homeTeamScoreQ4").value;

    if (scoreQ1 != "" && scoreQ2 != "" && scoreQ3 != "" && scoreQ4 != "") {
        // If all 4 quarters are filled in, calculate and update the total score.
        const totalScore = parseInt(scoreQ1) + parseInt(scoreQ2) + parseInt(scoreQ3) + parseInt(scoreQ4);
        document.getElementById("homeTeamScore").value = totalScore;
    }
}

function fixAwayScore() {
    const score = document.getElementById("awayTeamScore").value;

    const scoreQ1 = document.getElementById("awayTeamScoreQ1").value;
    const scoreQ2 = document.getElementById("awayTeamScoreQ2").value;
    const scoreQ3 = document.getElementById("awayTeamScoreQ3").value;
    const scoreQ4 = document.getElementById("awayTeamScoreQ4").value;

    if (scoreQ1 != "" && scoreQ2 != "" && scoreQ3 != "" && scoreQ4 != "") {
        // If all 4 quarters are filled in, calculate and update the total score.
        const totalScore = parseInt(scoreQ1) + parseInt(scoreQ2) + parseInt(scoreQ3) + parseInt(scoreQ4);
        document.getElementById("awayTeamScore").value = totalScore;
    }
}

function setSelectedHomeTeam() {
    let homeTeamName =  document.getElementById("homeTeam").options[document.getElementById("homeTeam").selectedIndex].innerHTML;
    document.getElementById("homeTeamName").innerHTML = homeTeamName;
    document.getElementById("homeTeamScoreLabel").innerHTML = homeTeamName + " Score";
}
function setSelectedAwayTeam() {
    let awayTeamName = document.getElementById("awayTeam").options[document.getElementById("awayTeam").selectedIndex].innerHTML;
    document.getElementById("awayTeamName").innerHTML = awayTeamName;
    document.getElementById("awayTeamScoreLabel").innerHTML = awayTeamName + " Score";
}

function getSelectedHomeTeamId() {
    return document.getElementById("homeTeam").value;
}
function getSelectedAwayTeamId() {
    return document.getElementById("awayTeam").value;
}

function showAddPlayerStatsForm() {
    document.getElementById("addPlayerStatsForm").style.display = "block";
}

function updateStatInputFields() {
    let statType = document.getElementById("statType").value;

    if (statType == "Passing") {
        document.getElementById("playerStat1").type = "text";
        document.getElementById("playerStat1").setAttribute('pattern', '[0-9-]{5}')
        document.getElementById("playerStat1Label").innerHTML = "C-A";
        document.getElementById("playerStat2Label").innerHTML = "YDS";
        document.getElementById("playerStat3Label").innerHTML = "TDS";
        document.getElementById("playerStat4Label").innerHTML = "INT";
    } else if (statType == "Rushing") {
        document.getElementById("playerStat1").type = "number";
        document.getElementById("playerStat1Label").innerHTML = "ATT";
        document.getElementById("playerStat2Label").innerHTML = "YDS";
        document.getElementById("playerStat3Label").innerHTML = "TDS";
        document.getElementById("playerStat4Label").innerHTML = "LNG";
    } else if (statType == "Receiving") {
        document.getElementById("playerStat1").type = "number";
        document.getElementById("playerStat1Label").innerHTML = "REC";
        document.getElementById("playerStat2Label").innerHTML = "YDS";
        document.getElementById("playerStat3Label").innerHTML = "TDS";
        document.getElementById("playerStat4Label").innerHTML = "LNG";
    }
}

function addStatToForm() {

    document.getElementById("addPlayerStatsForm").style.display = "none";

    let statType = document.getElementById("statType").value; // Passing, Rushing, Receiving
    let homeOrAway = document.getElementById("homeOrAway").value; // Home, Away

    let tableName;
    let hiddenInputName;
    if (homeOrAway == "Home") {
        if (statType == "Passing") {
            tableName = "homePassingTable";
            hiddenInputName = "homePassing";
        } else if (statType == "Rushing") {
            tableName = "homeRushingTable";
            hiddenInputName = "homeRushing";
        } else if (statType == "Receiving") {
            tableName = "homeReceivingTable";
            hiddenInputName = "homeReceiving";
        }
    } else if (homeOrAway == "Away") {
        if (statType == "Passing") {
            tableName = "awayPassingTable";
            hiddenInputName = "awayPassing";
        } else if (statType == "Rushing") {
            tableName = "awayRushingTable";
            hiddenInputName = "awayRushing";
        } else if (statType == "Receiving") {
            tableName = "awayReceivingTable";
            hiddenInputName = "awayReceiving";
        }
    }

    let playerNumber = document.getElementById("player").options[document.getElementById("player").selectedIndex].className;
    let playerName = document.getElementById("player").options[document.getElementById("player").selectedIndex].innerHTML;

    let playerStat1 = document.getElementById("playerStat1").value;
    let playerStat2 = document.getElementById("playerStat2").value;
    let playerStat3 = document.getElementById("playerStat3").value;
    let playerStat4 = document.getElementById("playerStat4").value;

    let playerStatsTable = document.getElementById(tableName);
    let row = playerStatsTable.insertRow(-1);
    let cell1 = row.insertCell(0);
    let cell2 = row.insertCell(1);
    let cell3 = row.insertCell(2);
    let cell4 = row.insertCell(3);
    let cell5 = row.insertCell(4);
    let cell6 = row.insertCell(5);

    cell1.innerHTML = playerNumber;
    cell2.innerHTML = playerName;
    cell3.innerHTML = playerStat1;
    cell4.innerHTML = playerStat2;
    cell5.innerHTML = playerStat3;
    cell6.innerHTML = playerStat4;

    let hiddenString = playerNumber + "," + playerName + "," + playerStat1 + "," + playerStat2 + "," + playerStat3 + "," + playerStat4 + ";";
    document.getElementById(hiddenInputName).value += hiddenString;

    document.getElementById("playerStat1").value = "";
    document.getElementById("playerStat2").value = "";
    document.getElementById("playerStat3").value = "";
    document.getElementById("playerStat4").value = "";
}

function cancelForm() {
    document.getElementById("addPlayerStatsForm").style.display = "none";
}