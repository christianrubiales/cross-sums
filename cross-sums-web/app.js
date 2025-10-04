
function handleNewGame(size) {
    createGrid(size);
}

function createGrid(size) {
    resetGrid();
    createSumsRows(size);
    createOtherRows(size);
}

function resetGrid() {
    let grid = document.querySelector("#grid");
    grid.innerHTML = "";
    grid.classList.add("grid");
    grid.id = "grid";
}

function createSumsRows(size) {
    let grid = document.querySelector("#grid");
    let row0 = document.createElement("div");
    row0.classList.add("row");
    row0.id = "row0";

    let cell1 = document.createElement("div");
    cell1.classList.add("cell");
    row0.appendChild(cell1);

    for (let i = 1; i <= size; i++) {
        let rollingColSumDiv = document.createElement("div");
        rollingColSumDiv.id = "rollingColSumDiv" + i;
        let colSumDiv = document.createElement("div");
        colSumDiv.id = "colSumDiv" + i;
        let cell = document.createElement("div");
        cell.classList.add("cell");
        cell.classList.add("two-numbers");
        cell.appendChild(rollingColSumDiv);
        cell.appendChild(colSumDiv);
        row0.appendChild(cell);
    }

    grid.appendChild(row0);
}

function createOtherRows(size) {
    let grid = document.querySelector("#grid");

    for (let i = 1; i <= size; i++) {
        // create row
        let row = document.createElement("div");
        row.classList.add("row");
        row.id = "row" + i;

        // create cell for row sum
        let rollingRowSumDiv = document.createElement("div");
        rollingRowSumDiv.id = "rollingRowSumDiv" + (i-1);
        let rowSumDiv = document.createElement("div");
        rowSumDiv.id = "rowSumDiv" + (i-1);
        let cell = document.createElement("div");
        cell.classList.add("cell");
        cell.classList.add("two-numbers");
        cell.appendChild(rollingRowSumDiv);
        cell.appendChild(rowSumDiv);
        row.appendChild(cell);

        // create other cells
        for (let j = 0; j < size; j++) {
            let cell = document.createElement("div");
            cell.classList.add("cell");
            cell.id = "row" + (i-1) + "col" + j;
            row.appendChild(cell);
        }

        grid.appendChild(row);
    }
}