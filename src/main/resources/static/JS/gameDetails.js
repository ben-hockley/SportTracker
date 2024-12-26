
/*
function removeItemAll(arr, value) {
    var i = 0;
    while (i < arr.length) {
        if (arr[i] === value) {
            arr.splice(i, 1);
        } else {
            ++i;
        }
    }
    return arr;
}
function processData(rawData) {
    if (rawData === ""){
        return []
    }
    let playersList = rawData.split(";")
    playersList = removeItemAll(playersList, "")

    let playersStatsList = []
    for (let i=0; i<playersList.length; i++) {
        let playerStats = playersList[i].split(",")
        playersStatsList.push(playerStats)
    }
    return playersStatsList
}

function displayStats(rawData, table) {
    let stats = processData(rawData)

    //sort by highest yards first
    stats.sort(function(a, b){return b[3] - a[3]});

    for (let i=0; i<stats.length; i++){
        let row = table.insertRow(-1);
        let cell1 = row.insertCell(0);
        let cell2 = row.insertCell(1);
        let cell3 = row.insertCell(2);
        let cell4 = row.insertCell(3);
        let cell5 = row.insertCell(4);
        let cell6 = row.insertCell(5);

        cell1.innerHTML = stats[i][1]; //player number
        cell2.innerHTML = `<a href="/playerDetails/${stats[i][0]}">${stats[i][2]}</a>`;                      //player name
        cell3.innerHTML = stats[i][3]; //C-A or ATT or REC
        cell4.innerHTML = stats[i][4]; //YDS
        cell5.innerHTML = stats[i][5]; //TD
        cell6.innerHTML = stats[i][6]; //INT or LNG
    }
}

function loadBoxScore(){
    // Raw data from database to be formatted and added to the box score tables
    let homePassingRaw = document.getElementById("homePassing").innerHTML
    let awayPassingRaw = document.getElementById("awayPassing").innerHTML
    let homeRushingRaw = document.getElementById("homeRushing").innerHTML
    let awayRushingRaw = document.getElementById("awayRushing").innerHTML
    let homeReceivingRaw = document.getElementById("homeReceiving").innerHTML
    let awayReceivingRaw = document.getElementById("awayReceiving").innerHTML

    // Box Score Tables to add Stats to.
    let homePassingTable = document.getElementById("homePassingTable")
    let awayPassingTable = document.getElementById("awayPassingTable")
    let homeRushingTable = document.getElementById("homeRushingTable")
    let awayRushingTable = document.getElementById("awayRushingTable")
    let homeReceivingTable = document.getElementById("homeReceivingTable")
    let awayReceivingTable = document.getElementById("awayReceivingTable")

    displayStats(homePassingRaw, homePassingTable)
    displayStats(awayPassingRaw, awayPassingTable)
    displayStats(homeRushingRaw, homeRushingTable)
    displayStats(awayRushingRaw, awayRushingTable)
    displayStats(homeReceivingRaw, homeReceivingTable)
    displayStats(awayReceivingRaw, awayReceivingTable)
}

 */