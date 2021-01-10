public class HardGame extends GameLevel {
    public Sudoku createSudoku(SudokuType sudokuType) {
        return super.createSudoku(sudokuType, 0.44444);
    }

    public boolean backtrackSudokuSolver(int row, int column, Sudoku sudoku) {
        return super.backtrackSudokuSolver(row, column, sudoku);
    }
}