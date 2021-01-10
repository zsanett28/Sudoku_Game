public class EasyGame extends GameLevel {
    public Sudoku createSudoku(SudokuType sudokuType) {
        return super.createSudoku(sudokuType, 0.88888);
    }

    public boolean backtrackSudokuSolver(int row, int column, Sudoku sudoku) {
        return super.backtrackSudokuSolver(row, column, sudoku);
    }
}
