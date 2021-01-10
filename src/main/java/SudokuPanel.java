import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.io.IOException;
import java.net.URL;

public class SudokuPanel extends JPanel {
    private Sudoku sukodu;
    private int selectedColumn;
    private int selectedRow;
    private int boardWidth;
    private int boardHeight;
    private int columnWidth;
    private int rowHeight;
    private final int fontSize;

    public SudokuPanel() {
        selectedColumn = -1;
        selectedRow = -1;
        boardWidth = 0;
        boardHeight = 0;
        columnWidth = 0;
        rowHeight = 0;
        fontSize = 26;
        this.sukodu = new SudokuCreator().createSudoku(SudokuType.NINEBYNINE, 0.88888);
        this.setPreferredSize(new Dimension(500, 500));
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {

                    try {
                        // open an audio input stream
                        URL url = this.getClass().getClassLoader().getResource("click.wav");
                        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                        // get a sound clip resource
                        Clip clip = AudioSystem.getClip();
                        // open audio clip and load samples from the audio input stream
                        clip.open(audioIn);
                        clip.start();
                    } catch (UnsupportedAudioFileException exception) {
                        exception.printStackTrace();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    } catch (LineUnavailableException exception) {
                        exception.printStackTrace();
                    }

                    int slotWidth = boardWidth / sukodu.getNumColumns();
                    int slotHeight = boardHeight / sukodu.getNumRows();
                    selectedRow = e.getY() / slotHeight;
                    selectedColumn = e.getX() / slotWidth;
                    e.getComponent().repaint();
                }
            }
        });
    }

    public void setSudoku(Sudoku sudoku) {
        this.sukodu = sudoku;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(new Color(255, 233, 226));

        //set the proper distance between columns
        setColumnWidth(this.getWidth() / sukodu.getNumColumns());
        setRowHeight(this.getHeight() / sukodu.getNumRows());

        boardWidth = (this.getWidth() / sukodu.getNumColumns()) * sukodu.getNumColumns();
        boardHeight = (this.getHeight() / sukodu.getNumRows()) * sukodu.getNumRows();

        //fill with color the sudoku board
        graphics2D.fillRect(0, 0, boardWidth, boardHeight);

        graphics2D.setColor(new Color(200, 80, 5));

        //set the line's width between the columns of the sudoku board
        for (int column = 0; column <= boardWidth; column += columnWidth) {
            if ((column / columnWidth) % sukodu.getBoxWidth() == 0) {
                graphics2D.setStroke(new BasicStroke(3));
            } else {
                graphics2D.setStroke(new BasicStroke(1));
            }
            graphics2D.drawLine(column, 0, column, boardHeight);
        }

        //set the line's width between rows of the sudoku board
        for (int row = 0; row <= boardHeight; row += rowHeight) {
            if ((row / rowHeight) % sukodu.getBoxHeight() == 0) {
                graphics2D.setStroke(new BasicStroke(3));
            } else {
                graphics2D.setStroke(new BasicStroke(1));
            }
            graphics2D.drawLine(0, row, boardWidth, row);
        }

        Font f = new Font("SansSerif", Font.PLAIN, fontSize);
        graphics2D.setFont(f);
        FontRenderContext fContext = graphics2D.getFontRenderContext();

        fillSudokuNumbers(graphics2D, fContext, f);

        if (selectedColumn != -1 && selectedRow != -1) {
            graphics2D.setColor(new Color(0.0f, 0.0f, 1.0f, 0.3f));
            graphics2D.fillRect(selectedColumn * columnWidth, selectedRow * rowHeight, columnWidth, rowHeight);
        }
    }

    //draw the base numbers of the sudoku board
    private void fillSudokuNumbers(Graphics2D graphics2D, FontRenderContext fContext, Font f) {
        for (int row = 0; row < sukodu.getNumRows(); row++) {
            for (int col = 0; col < sukodu.getNumColumns(); col++) {
                if (!sukodu.isSlotAvailable(row, col)) {
                    int textWidth = (int) f.getStringBounds(sukodu.getValue(row, col), fContext).getWidth();
                    int textHeight = (int) f.getStringBounds(sukodu.getValue(row, col), fContext).getHeight();
                    graphics2D.drawString(sukodu.getValue(row, col), (col * columnWidth) + ((columnWidth / 2) - (textWidth / 2)), (row * rowHeight) + ((rowHeight / 2) + (textHeight / 2)) - 5);
                }
            }
        }
    }

    public class NumberActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedColumn != -1 && selectedRow != -1) {
                sukodu.makeMove(selectedRow, selectedColumn, ((JButton) e.getSource()).getText(), true);
                repaint();
            }
        }
    }

    /*public Sudoku getSukodu() {
        return sukodu;
    }*/

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }
}