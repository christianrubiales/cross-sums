let solvedGrid;

function handleNewGame(size) {
    createGrid(size);

    // get next grid for the given size
    let solvedGames =  localStorage.getItem("solved" + size);
    if (!solvedGames) {
        solvedGames = "0";
        localStorage.setItem("solved" + size, solvedGames);
    }
    solvedGames = Number(solvedGames);
    let grid = boards[size][solvedGames];

    // load column sums
    for (let i = 0; i < size; i++) {
        document.querySelector("#colSum" + (i+1)).textContent = grid.colSums[i];
    }

    // load row sums
    for (let i = 0; i < size; i++) {
        document.querySelector("#rowSum" + (i+1)).textContent = grid.rowSums[i];
    }

    // load grid
    for (let i = 0; i < size; i++) {
        for (let j = 0; j < size; j++) {
            document.querySelector("#row" + (i+1) + "col" + (j+1)).textContent = grid.initialGrid[i][j];
        }
    }

    // initialize solvedGrid
    solvedGrid = [];
    for (let i = 0; i < size; i++) {
        let row = [];
        for (let j = 0; j < size; j++) {
            row.push(grid.initialGrid[i][j]);
        }
        solvedGrid.push(row);
    }
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
        rollingColSumDiv.id = "rollingColSum" + i;
        let colSumDiv = document.createElement("div");
        colSumDiv.id = "colSum" + i;
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
        rollingRowSumDiv.id = "rollingRowSum" + i;
        let rowSumDiv = document.createElement("div");
        rowSumDiv.id = "rowSum" + i;
        let cell = document.createElement("div");
        cell.classList.add("cell");
        cell.classList.add("two-numbers");
        cell.appendChild(rollingRowSumDiv);
        cell.appendChild(rowSumDiv);
        row.appendChild(cell);

        // create other cells
        for (let j = 1; j <= size; j++) {
            let cell = document.createElement("div");
            cell.classList.add("cell");
            cell.id = "row" + i + "col" + j;
            row.appendChild(cell);
        }

        grid.appendChild(row);
    }
}