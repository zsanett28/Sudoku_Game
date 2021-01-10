public class HardGame extends GameLevel {
    /**
     * Return a hard level sudoku game.
     * @param sudokuType chosen type of the sudoku game (6x6 or 9x9)
     * @return hard level sudoku
     */
    public Sudoku createSudoku(SudokuType sudokuType) {
        return super.createSudoku(sudokuType, 0.44444);
    }

    /**
     *
     * @param row number of rows of the sudoku
     * @param column number of columns of the sudoku
     * @param sudoku sudoku game, which is created
     * @return
     * true, if the moves are valid during solving the sudoku
     * false, if the moves are not valid during solving the sudoku
     */
    public boolean backtrackSudokuSolver(int row, int column, Sudoku sudoku) {
        return super.backtrackSudokuSolver(row, column, sudoku);
    }
}