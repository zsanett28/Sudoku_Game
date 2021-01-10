import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SudokuCreator {

    public Sudoku createSudoku(SudokuType sudokuType) {
        Sudoku sudoku = new Sudoku(sudokuType.getRows(), sudokuType.getColumns(), sudokuType.getBoxWidth(), sudokuType.getBoxHeight(), sudokuType.getValidValues());
        Sudoku copy = new Sudoku(sudoku);

        Random randomGenerator = new Random();

        List<String> notUsedValidValues =  new ArrayList<String>(Arrays.asList(copy.getValidValues()));
        for(int row = 0;row < copy.getNumRows();row++) {
            int randomValue = randomGenerator.nextInt(notUsedValidValues.size());
            copy.makeMove(row, 0, notUsedValidValues.get(randomValue), true);
            notUsedValidValues.remove(randomValue);
        }

        backtrackSudokuSolver(0, 0, copy);

        int numberOfValuesToKeep = (int)(0.99999*(copy.getNumRows()*copy.getNumRows()));

        for(int i = 0;i < numberOfValuesToKeep;) {
            int randomRow = randomGenerator.nextInt(sudoku.getNumRows());
            int randomColumn = randomGenerator.nextInt(sudoku.getNumColumns());

            if(sudoku.isSlotAvailable(randomRow, randomColumn)) {
                sudoku.makeMove(randomRow, randomColumn, copy.getValue(randomRow, randomColumn), false);
                i++;
            }
        }

        return sudoku;
    }

    private boolean backtrackSudokuSolver(int row, int column, Sudoku sudoku) {
        //if the move is not valid return false
        if(!sudoku.inRange(row,column)) {
            return false;
        }

        //if the current space is empty
        if(sudoku.isSlotAvailable(row, column)) {

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
            if(row == sudoku.getNumRows() - 1) {
                return backtrackSudokuSolver(0,column + 1,sudoku);
            } else {
                return backtrackSudokuSolver(row + 1,column,sudoku);
            }
        }

        //undo move
        sudoku.makeSlotEmpty(row, column);

        return false;
    }
}
