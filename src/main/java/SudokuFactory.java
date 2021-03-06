import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class SudokuFactory {
    /**
     * Return a sudoku game with random numbers.
     *
     * @param sudokuType  chosen type of the sudoku game (6x6 or 9x9)
     * @param sudokuLevel depends on the chosen sudokuLevel of the sudoku game (easy, medium, hard)
     * @return sudoku game
     */
    public Sudoku createSudoku(SudokuLevel sudokuLevel, SudokuType sudokuType) {
        Sudoku sudoku = new Sudoku(sudokuType.getRows(), sudokuType.getColumns(), sudokuType.getBoxWidth(), sudokuType.getBoxHeight(), sudokuType.getValidValues());
        Sudoku copy = new Sudoku(sudoku);
        Random randomGenerator = new Random();

        List<String> notUsedValidValues = new ArrayList<String>(Arrays.asList(copy.getValidValues()));

        IntStream.range(0, copy.getNumRows())
                .forEach(row -> {
                            int randomValue = randomGenerator.nextInt(notUsedValidValues.size());
                            copy.makeMove(row, 0, notUsedValidValues.get(randomValue), true);
                            notUsedValidValues.remove(randomValue);
                        }
                );

        backtrackSudokuSolver(0, 0, copy);

        double level = sudokuLevelToValue(sudokuLevel);
        int numberOfValuesToKeep = (int) (level * (copy.getNumRows() * copy.getNumRows()));

        for (int i = 0; i < numberOfValuesToKeep; ) {
            int randomRow = randomGenerator.nextInt(sudoku.getNumRows());
            int randomColumn = randomGenerator.nextInt(sudoku.getNumColumns());

            if (sudoku.isSlotAvailable(randomRow, randomColumn)) {
                sudoku.makeMove(randomRow, randomColumn, copy.getValue(randomRow, randomColumn), false);
                i++;
            }
        }

        return sudoku;
    }

    private double sudokuLevelToValue(SudokuLevel level) {
        switch (level) {
            case EASY:
                return 0.88888;
            case HARD:
                return 0.44444;
            default:
                return 0.66666;
        }
    }

    /**
     * Solve the sudoku with backtracking.
     *
     * @param row    number of rows of the sudoku
     * @param column number of columns of the sudoku
     * @param sudoku sudoku game, which is created
     * @return true, if the moves are valid during solving the sudoku
     * false, if the moves are not valid during solving the sudoku
     */
    private boolean backtrackSudokuSolver(int row, int column, Sudoku sudoku) {
        //if the move is not valid return false
        if (!sudoku.inRange(row, column)) {
            return false;
        }

        //if the current space is empty
        if (sudoku.isSlotAvailable(row, column)) {

            //loop to find the correct value for the space
            for (int i = 0; i < sudoku.getValidValues().length; i++) {

                //if the current number works in the space
                if (!sudoku.numInRow(row, sudoku.getValidValues()[i]) && !sudoku.numInCol(column, sudoku.getValidValues()[i]) && !sudoku.numInBox(row, column, sudoku.getValidValues()[i])) {

                    //make the move
                    sudoku.makeMove(row, column, sudoku.getValidValues()[i], true);

                    //if sudoku solved return true
                    if (sudoku.boardFull()) {
                        return true;
                    }

                    //go to next move
                    if (row == sudoku.getNumRows() - 1) {
                        if (backtrackSudokuSolver(0, column + 1, sudoku))
                            return true;
                    } else {
                        if (backtrackSudokuSolver(row + 1, column, sudoku))
                            return true;
                    }
                }
            }
        } else {
            //got to the next move
            if (row == sudoku.getNumRows() - 1) {
                return backtrackSudokuSolver(0, column + 1, sudoku);
            } else {
                return backtrackSudokuSolver(row + 1, column, sudoku);
            }
        }

        //undo move
        sudoku.makeSlotEmpty(row, column);

        return false;
    }
}
