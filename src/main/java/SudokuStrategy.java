public interface SudokuStrategy {
    Sudoku createSudoku(SudokuType sudokuType, double value);

    boolean backtrackSudokuSolver(int row, int column, Sudoku sudoku);
}
