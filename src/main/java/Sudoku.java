public class Sudoku {

    private final int ROWS;
    private final int COLUMNS;
    private final int BOXWIDTH;
    private final int BOXHEIGHT;
    private final String[] VALIDVALUES;
    protected String[][] board;
    protected boolean[][] mutable;

    /**
     * Constructor of Sudoku.
     * @param rows number of rows of the sudoku
     * @param columns number of columns of the sudoku
     * @param boxWidth width of the box
     * @param boxHeight heigth of the box
     * @param validValues a String array with the valid values
     */
    public Sudoku(int rows, int columns, int boxWidth, int boxHeight, String[] validValues) {
        this.ROWS = rows;
        this.COLUMNS = columns;
        this.BOXWIDTH = boxWidth;
        this.BOXHEIGHT = boxHeight;
        this.VALIDVALUES = validValues;
        this.board = new String[ROWS][COLUMNS];
        this.mutable = new boolean[ROWS][COLUMNS];
        initializeBoard();
        initializeMutableSlots();
    }

    /**
     * Constructor of Sudoku.
     * @param sudoku sudoku game
     */
    public Sudoku(Sudoku sudoku) {
        this.ROWS = sudoku.ROWS;
        this.COLUMNS = sudoku.COLUMNS;
        this.BOXWIDTH = sudoku.BOXWIDTH;
        this.BOXHEIGHT = sudoku.BOXHEIGHT;
        this.VALIDVALUES = sudoku.VALIDVALUES;
        this.board = new String[ROWS][COLUMNS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = sudoku.board[row][col];
            }
        }
        this.mutable = new boolean[ROWS][COLUMNS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                this.mutable[row][col] = sudoku.mutable[row][col];
            }
        }
    }

    /**
     * Get the sudoku's number of rows.
     * @return number of rows
     */
    public int getNumRows() {
        return this.ROWS;
    }

    /**
     * Get the sudoku's number of columns.
     * @return number of columns
     */
    public int getNumColumns() {
        return this.COLUMNS;
    }

    /**
     * Get the width of the box.
     * @return width of the box
     */
    public int getBoxWidth() {
        return this.BOXWIDTH;
    }

    /**
     * Get the height of the box.
     * @return height of the box
     */
    public int getBoxHeight() {
        return this.BOXHEIGHT;
    }

    /**
     * Get the valid values from the String[] array
     * @return array of valid values
     */
    public String[] getValidValues() {
        return this.VALIDVALUES;
    }

    /**
     * Make a move in the sudoku game.
     * @param row row index
     * @param col column index
     * @param value the given value
     * @param isMutable mutable
     */
    public void makeMove(int row, int col, String value, boolean isMutable) {
        if (this.isValidValue(value) && this.isValidMove(row, col, value) && this.isSlotMutable(row, col)) {
            this.board[row][col] = value;
            this.mutable[row][col] = isMutable;
        }
    }

    /**
     * Check if the current move is valid or not.
     * @param row row index
     * @param col column index
     * @param value the given value
     * @return
     * true, if it is valid
     * false, if it is not valid
     */
    public boolean isValidMove(int row, int col, String value) {
        if (this.inRange(row, col)) {
            return !this.numInCol(col, value) && !this.numInRow(row, value) && !this.numInBox(row, col, value);
        }
        return false;
    }

    public boolean numInCol(int col, String value) {
        if (col <= this.COLUMNS) {
            for (int row = 0; row < this.ROWS; row++) {
                if (this.board[row][col].equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean numInRow(int row, String value) {
        if (row <= this.ROWS) {
            for (int col = 0; col < this.COLUMNS; col++) {
                if (this.board[row][col].equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean numInBox(int row, int col, String value) {
        if (this.inRange(row, col)) {
            int boxRow = row / this.BOXHEIGHT;
            int boxCol = col / this.BOXWIDTH;

            int startingRow = (boxRow * this.BOXHEIGHT);
            int startingCol = (boxCol * this.BOXWIDTH);

            for (int r = startingRow; r <= (startingRow + this.BOXHEIGHT) - 1; r++) {
                for (int c = startingCol; c <= (startingCol + this.BOXWIDTH) - 1; c++) {
                    if (this.board[r][c].equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if the slot is available
     * @param row row index
     * @param col column index
     * @return
     * true, if it is available
     * false, if it is not available
     */
    public boolean isSlotAvailable(int row, int col) {
        return (this.inRange(row, col) && this.board[row][col].equals("") && this.isSlotMutable(row, col));
    }

    /**
     * Check if the slot is mutable
     * @param row row index
     * @param col column index
     * @return
     * true, if it is mutable
     * false, if it is not mutable
     */
    public boolean isSlotMutable(int row, int col) {
        return this.mutable[row][col];
    }

    /**
     * Get the value from a specific position of the sudoku
     * @param row row index
     * @param col column index
     * @return the searched value
     */
    public String getValue(int row, int col) {
        if (this.inRange(row, col)) {
            return this.board[row][col];
        }
        return "";
    }

    public String[][] getBoard() {
        return this.board;
    }

    /**
     * Return if the given value if valid or not.
     * @param value the given value
     * @return
     * true, if it is valid
     * flase, if it is not valid
     */
    private boolean isValidValue(String value) {
        for (String str : this.VALIDVALUES) {
            if (str.equals(value))
                return true;
        }
        return false;
    }

    /**
     * Return if the given indexes are in range or not.
     * @param row row index
     * @param col column index
     * @return
     * true, if the indexes are in range
     * false, if the indexes are not in range
     */
    public boolean inRange(int row, int col) {
        return row <= this.ROWS && col <= this.COLUMNS && row >= 0 && col >= 0;
    }

    /**
     * Check if he sudoku is filled
     * @return
     * true, if the sudoku board is full
     * false, if the sudoku board is not full
     */
    public boolean boardFull() {
        for (int r = 0; r < this.ROWS; r++) {
            for (int c = 0; c < this.COLUMNS; c++) {
                if (this.board[r][c].equals(""))
                    return false;
            }
        }
        return true;
    }

    public void makeSlotEmpty(int row, int col) {
        this.board[row][col] = "";
    }

    /**
     * Print the sudoku board.
     * @return
     */
    @Override
    public String toString() {
        String str = "Game Board:\n";
        for (int row = 0; row < this.ROWS; row++) {
            for (int col = 0; col < this.COLUMNS; col++) {
                str += this.board[row][col] + " ";
            }
            str += "\n";
        }
        return str + "\n";
    }

    /**
     * Initialize the sudoku board.
     */
    private void initializeBoard() {
        for (int row = 0; row < this.ROWS; row++) {
            for (int col = 0; col < this.COLUMNS; col++) {
                this.board[row][col] = "";
            }
        }
    }

    /**
     * Initialize mutable slots.
     */
    private void initializeMutableSlots() {
        for (int row = 0; row < this.ROWS; row++) {
            for (int col = 0; col < this.COLUMNS; col++) {
                this.mutable[row][col] = true;
            }
        }
    }
}