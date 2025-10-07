const DELAY = 1000;

function $(id) {
    return document.querySelector(id);
}

async function solve() {
    await lookForCellsInColumnsGreaterThanSum();
    await lookForCellsInRowsGreaterThanSum();

    // await lookForSingleOccurrenceInColumnThatIsEqualToTheSum();
    // await lookForSingleOccurrenceInRowThatIsEqualToTheSum();
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

// async function lookForSingleOccurrenceInColumnThatIsEqualToTheSum() {
//     $("#solver").innerHTML = "Look for single occurrence in column that is equal <br/>" +
//                                     "to the sum. Remove all the other cells";
//
//     for (let j = 1; j <= gameSize; j++) {
//         let equals = [];
//         for (let i = 1; i <= gameSize; i++) {
//             let cell = $("#row" + i + "col" + j);
//             if (Number(cell.textContent) == Number($("#colSum" + j).textContent)) {
//                 equals.push("row" + i + "col" + j);
//             }
//         }
//         if (equals.length === 1) {
//             for (let i = 1; i <= gameSize; i++) {
//                 let cell = $("#row" + i + "col" + j);
//                 if (cell.id != equals[0] && cell.textContent != "") {
//                     await new Promise((dummy) => setTimeout(() => { dummy(); cell.click(); }, DELAY));
//                 }
//             }
//         }
//     }
//     $("#solver").innerHTML = "";
// }
//
//
// async function lookForSingleOccurrenceInRowThatIsEqualToTheSum() {
//     $("#solver").innerHTML = "Look for single occurrence in row that is equal <br/>" +
//         "to the sum. Remove all the other cells";
//
//     for (let i = 1; i <= gameSize; i++) {
//         let equals = [];
//         for (let j = 1; j <= gameSize; j++) {
//             let cell = $("#row" + i + "col" + j);
//             if (Number(cell.textContent) == Number($("#rowSum" + i).textContent)) {
//                 equals.push("row" + i + "col" + j);
//             }
//         }
//         if (equals.length === 1) {
//             for (let j = 1; j <= gameSize; j++) {
//                 let cell = $("#row" + i + "col" + j);
//                 if (cell.id != equals[0] && cell.textContent != "") {
//                     await new Promise((dummy) => setTimeout(() => { dummy(); cell.click(); }, DELAY));
//                 }
//             }
//         }
//     }
//     $("#solver").innerHTML = "";
// }