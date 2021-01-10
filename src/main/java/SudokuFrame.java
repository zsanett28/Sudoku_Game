import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;

public class SudokuFrame extends JFrame {

    private final SudokuPanel sudokuPanel;
    private final JPanel numbersSelectionPanel;
    private final JPanel mainPanel;
    //TO DO
    private final JPanel timerPanel;
    private final StopWatch stopWatch;
    private final JPanel buttonPanel;
    private Sudoku sudoku;

    public SudokuFrame() {
        this.setTitle("Sudoku Game");
        this.setSize(700, 850);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar mainMenuBar = new JMenuBar();
        mainMenuBar.setBackground(new Color(200, 80, 5));

        JMenu newGameMenu = new JMenu("New Game");
        newGameMenu.setForeground(Color.WHITE);

        final JMenuItem easy = new JMenuItem("Easy");
        final JMenuItem medium = new JMenuItem("Medium");
        final JMenuItem hard = new JMenuItem("Hard");

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setForeground(Color.WHITE);
        helpMenu.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                stopWatch.pause();
                try {
                    // open an audio input stream
                    URL url = this.getClass().getClassLoader().getResource("help.wav");
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

                JOptionPane.showMessageDialog(mainPanel,
                        "Each of the 6 or 9 blocks has to contain all the numbers 1-9 or 1-6 within its squares.\nEach number can only appear once in a row, column or box.",
                        "How to play Sudoku?",
                        JOptionPane.INFORMATION_MESSAGE);
                if (!sudoku.boardFull()) {
                    stopWatch.resume();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        JMenu exitMenu = new JMenu("Exit");
        exitMenu.setForeground(Color.WHITE);
        exitMenu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //adding game types menu
        newGameMenu.add(sixBySix);
        newGameMenu.add(nineByNine);

        //add new game menu
        mainMenuBar.add(newGameMenu);
        //add help menu
        mainMenuBar.add(helpMenu);
        //add exit menu
        mainMenuBar.add(exitMenu);

        //set the whole menubar
        setJMenuBar(mainMenuBar);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setSize(700, 850);

        timerPanel = new JPanel();
        stopWatch = new StopWatch();
        JLabel timeLabel = new JLabel();
        startTimer(timeLabel);
        timeLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        timerPanel.setBackground(new Color(226, 246, 255));
        timerPanel.add(timeLabel);

        numbersSelectionPanel = new JPanel();
        numbersSelectionPanel.setBackground(new Color(226, 246, 255));
        numbersSelectionPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        sudokuPanel = new SudokuPanel();
        sudokuPanel.setBackground(new Color(226, 246, 255));

        buttonPanel = new JPanel();
        final JButton submitButton = new JButton("Done");
        submitButton.setBackground(new Color(200, 80, 5));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sudoku.boardFull()) {
                    stopWatch.pause();
                    try {
                        // open an audio input stream
                        URL url = this.getClass().getClassLoader().getResource("applause.wav");
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

                    JOptionPane.showMessageDialog(mainPanel,
                            "Good Job! Your Sudoku is solved!",
                            "Solved Sudoku",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    stopWatch.pause();
                    try {
                        // open an audio input stream
                        URL url = this.getClass().getClassLoader().getResource("fail.wav");
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

                    JOptionPane.showMessageDialog(mainPanel,
                            "Oops, there are some empty squares!",
                            "Unsolved Sudoku",
                            JOptionPane.ERROR_MESSAGE);
                    stopWatch.resume();
                }
            }
        });
        buttonPanel.add(submitButton);

        mainPanel.add(timerPanel);
        mainPanel.add(sudokuPanel);
        mainPanel.add(numbersSelectionPanel);
        mainPanel.add(buttonPanel);
        mainPanel.setBackground(new Color(226, 246, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        this.add(mainPanel);

        recreateGame(SudokuType.NINEBYNINE);

        setResizable(false);
        setVisible(true);
    }

    //regenerate the sudoku board and number buttons according to sudoku type parameter
    public void recreateGame(SudokuType sukoduType) {
        stopWatch.start();
        this.sudoku = new SudokuCreator().createSudoku(sukoduType);

        sudokuPanel.setSudoku(sudoku);
        numbersSelectionPanel.removeAll();

        addNumberButtons(sudoku);

        sudokuPanel.repaint();
        numbersSelectionPanel.revalidate();
        numbersSelectionPanel.repaint();
    }

    //design and add number buttons to numbersSelectionPanel
    private void addNumberButtons(Sudoku sudoku) {
        for (String number : sudoku.getValidValues()) {
            JButton numberButton = new JButton(number);
            numberButton.setPreferredSize(new Dimension(50, 50));
            numberButton.addActionListener(sudokuPanel.new NumberActionListener());
            numberButton.setBackground(new Color(200, 80, 5));
            numberButton.setForeground(new Color(226, 246, 255));
            numberButton.setFont(new Font("SansSerif", Font.PLAIN, 26));
            numbersSelectionPanel.add(numberButton);
        }
    }

    private void startTimer(final JLabel timeLabel) {
        Thread thread = new Thread(new Runnable() {

            public void run() {
                stopWatch.start();
                while (true) {
                    final String timeString = new SimpleDateFormat("mm:ss:SSS").format(stopWatch.getElapsedTime());
                    timeLabel.setText("" + timeString);
                }
            }
        });
        thread.start();
    }
}