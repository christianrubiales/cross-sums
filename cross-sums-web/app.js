let solvedGrid;
let selectedGrid;
let gameSize;
let colTotals;
let rowTotals;

initializeHelpModal();
handleNewGame(4);

function handleNewGame(size) {
    gameSize = size;
    document.getElementById("youwin").style.display = "none";
    createGrid(size);

    // get next grid for the given size
    let solvedGames =  localStorage.getItem("solved" + size);
    if (!solvedGames) {
        solvedGames = "0";
        localStorage.setItem("solved" + size, solvedGames);
    }
    solvedGames = Number(solvedGames);
    let grid = boards[size][solvedGames];
    selectedGrid = grid;

    // load column sums
    colTotals = new Array(size);
    for (let i = 0; i < size; i++) {
        document.querySelector("#rollingColSum" + (i+1)).textContent = grid.colTotals[i];
        document.querySelector("#colSum" + (i+1)).textContent = grid.colSums[i];
        colTotals[i] = grid.colTotals[i];
    }

    // load row sums
    rowTotals = new Array(size);
    for (let i = 0; i < size; i++) {
        document.querySelector("#rollingRowSum" + (i+1)).textContent = grid.rowTotals[i];
        document.querySelector("#rowSum" + (i+1)).textContent = grid.rowSums[i];
        rowTotals[i] = grid.rowTotals[i];
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

    // add handlers
    document.querySelectorAll('.cell:not(.two-numbers)').forEach(div => {
        div.addEventListener('click', handleClick);
    });
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

function handleClick(event) {
    const divId = event.currentTarget.id; // Get the id of the div clicked

    let i = divId.slice(3, divId.indexOf("col"));
    let j = divId.slice(divId.indexOf("col") + 3);

    if (selectedGrid.solvedGrid[i-1][j-1] === ' ') {
        event.currentTarget.innerHTML = "";
        colTotals[j-1] -= solvedGrid[i-1][j-1];
        rowTotals[i-1] -= solvedGrid[i-1][j-1];
        solvedGrid[i-1][j-1] = ' ';
        document.querySelector("#rollingColSum" + (j)).textContent = colTotals[j-1];
        document.querySelector("#rollingRowSum" + (i)).textContent = rowTotals[i-1];

        if (colTotals[j-1] == document.querySelector("#colSum" + (j)).textContent) {
            document.querySelector("#rollingColSum" + (j)).textContent = "";
            document.querySelector("#colSum" + (j)).textContent = "";

            for (let a = 1; a <= gameSize; a++) {
                let cell = document.querySelector("#row" + a + "col" + j);
                if (cell.textContent != '') {
                    cell.classList.add("solved");
                }
            }
        }

        if (rowTotals[i-1] == document.querySelector("#rowSum" + (i)).textContent) {
            document.querySelector("#rollingRowSum" + (i)).textContent = "";
            document.querySelector("#rowSum" + (i)).textContent = "";

            for (let a = 1; a <= gameSize; a++) {
                let cell = document.querySelector("#row" + i + "col" + a);
                if (cell.textContent != '') {
                    cell.classList.add("solved");
                }
            }
        }

        checkIfWin();
    } else {
        console.log("Mistake");
    }
}

function checkIfWin() {
    // compare solvedGrid with selectedGrid
    for (let i = 0; i < gameSize; i++) {
        for (let j = 0; j < gameSize; j++) {
            if (solvedGrid[i][j] !== selectedGrid.solvedGrid[i][j]) {
                return false;
            }
        }
    }

    document.querySelectorAll('.cell:not(.two-numbers)').forEach(div => {
        div.removeEventListener('click', handleClick);
    });

    document.getElementById("youwin").style.display = "flex";
    let solvedGames =  Number(localStorage.getItem("solved" + gameSize));
    localStorage.setItem("solved" + gameSize, solvedGames + 1);

    for (let i = 0; i < gameSize; i++) {
        // blank out column sums and totals
        document.querySelector("#rollingColSum" + (i+1)).textContent = "";
        document.querySelector("#colSum" + (i+1)).textContent = "";

        // blank out row sums and totals
        document.querySelector("#rollingRowSum" + (i+1)).textContent = "";
        document.querySelector("#rowSum" + (i+1)).textContent = "";
    }

    return true;
}

function initializeHelpModal() {
    const overlay = document.getElementById('overlay');
    const openBtn = document.getElementById('openModal');
    const closeBtn = document.getElementById('closeModal');

    openBtn.addEventListener('click', () => {
        overlay.style.display = 'flex';
    });

    closeBtn.addEventListener('click', () => {
        overlay.style.display = 'none';
    });

    overlay.addEventListener('click', (e) => {
        if (e.target === overlay) {
            overlay.style.display = 'none';
        }
    });
}