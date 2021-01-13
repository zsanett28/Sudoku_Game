public enum SudokuType {
    //available types of the sudoku game
    SIXBYSIX(6, 6, 3, 2, new String[]{"1", "2", "3", "4", "5", "6"}, "6 By 6 Game"),
    NINEBYNINE(9, 9, 3, 3, new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"}, "9 By 9 Game");

    private final int rows;
    private final int columns;
    private final int boxWidth;
    private final int boxHeight;
    private final String[] validValues;
    private final String desc;

    SudokuType(int rows, int columns, int boxWidth, int boxHeight, String[] validValues, String desc) {
        this.rows = rows;
        this.columns = columns;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.validValues = validValues;
        this.desc = desc;
    }

    /**
     * Get number of rows.
     *
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Get number of rows.
     *
     * @return number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Get width of the box.
     *
     * @return width of the box
     */
    public int getBoxWidth() {
        return boxWidth;
    }

    /**
     * Get height of the box
     *
     * @return height of the box
     */
    public int getBoxHeight() {
        return boxHeight;
    }

    /**
     * Get the array of valid values.
     *
     * @return array of valid values
     */
    public String[] getValidValues() {
        return validValues;
    }

    public String toString() {
        return desc;
    }
}