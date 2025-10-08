const DELAY = 1000;

function $(id) {
    return document.querySelector(id);
}

async function solve() {
    // await lookForCellsInColumnsGreaterThanSum();
    // await lookForCellsInRowsGreaterThanSum();

    do {
        await removeNonContributingCellsInColumn();
        // await lookForSingleOccurrenceInRowThatIsEqualToTheSum();
    } while (!isWin());
}

async function lookForCellsInColumnsGreaterThanSum() {
    $("#solver").textContent = "Look for cells in columns greater than sum";

    for (let j = 1; j <= gameSize; j++) {
        for (let i = 1; i <= gameSize; i++) {
            let cell = $("#row" + i + "col" + j);
            if (Number(cell.textContent) > Number($("#colSum" + j).textContent)) {
                await new Promise((dummy) => setTimeout(() => { dummy(); cell.click() }, DELAY));
            }
        }
    }
    $("#solver").textContent = "";
}

async function lookForCellsInRowsGreaterThanSum() {
    $("#solver").textContent = "Look for cells in rows greater than sum";

    for (let i = 1; i <= gameSize; i++) {
        for (let j = 1; j <= gameSize; j++) {
            let cell = $("#row" + i + "col" + j);
            if (Number(cell.textContent) > Number($("#rowSum" + i).textContent)) {
                await new Promise((dummy) => setTimeout(() => { dummy(); cell.click() }, DELAY));
            }
        }
    }
    $("#solver").textContent = "";
}

async function removeNonContributingCellsInColumn() {
    $("#solver").innerHTML = "Remove column cells that will not be usable to create the sum";

    for (let j = 1; j <= gameSize; j++) {
        let numbers = [];
        let numberSet = new Set();

        // collect numbers in column
        for (let i = 1; i <= gameSize; i++) {
            let cell = $("#row" + i + "col" + j);
            if (cell.textContent != "") {
                numbers.push(Number(cell.textContent));
                numberSet.add(Number(cell.textContent));
            }
        }

        // collect contributing numbers
        let colSum = Number($("#colSum" + j).textContent);
        let contributingNumbers = new Set();
        for (let k = 1; k < Math.pow(2, numbers.length); k++) {
            let bitset = k.toString(2).split("").reverse().join("");
            let sum = 0;
            for (let i = 0; i < bitset.length; i++) {
                if (bitset.at(i) === "1") {
                    sum += numbers[i];
                    if (sum > colSum) {
                        break;
                    }
                }
            }
            if (sum == colSum) {
                for (let k = 0; k < bitset.length; k++) {
                    if (bitset.at(k) === "1") {
                        contributingNumbers.add(numbers[k]);
                    }
                }
            }
        }

        // remove non-contributing numbers
        let nonContributing = numberSet.difference(contributingNumbers);
        for (let i = 1; i <= gameSize; i++) {
            let cell = $("#row" + i + "col" + j);
            if (nonContributing.has(Number(cell.textContent))) {
                console.log("#row" + i + "col" + j + ": " + cell.textContent);
                cell.click();
                // await new Promise((dummy) => setTimeout(() => { dummy(); cell.click() }, DELAY));
            }
        }
    }

    $("#solver").innerHTML = "";
}