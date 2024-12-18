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
