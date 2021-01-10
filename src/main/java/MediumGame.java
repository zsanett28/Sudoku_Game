public class MediumGame extends GameLevel {
    public Sudoku createSudoku(SudokuType sudokuType) {
        return super.createSudoku(sudokuType, 0.66666);
    }

    public boolean backtrackSudokuSolver(int row, int column, Sudoku sudoku) {
        return super.backtrackSudokuSolver(row, column, sudoku);
    }
}