import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;

public class SudokuFrame extends JFrame {

    private final JPanel cardPanel;
    private final JPanel startPanel;
    private final SudokuPanel sudokuPanel;
    private final JPanel numbersSelectionPanel;
    private final JPanel mainPanel;
    private final JPanel timerPanel;
    private final StopWatch stopWatch;
    private final JPanel buttonPanel;
    private Sudoku sudoku;

    public SudokuFrame() {
        this.setTitle("Sudoku Game");
        this.setSize(700, 850);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set card layout to the frame
        final CardLayout layout = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setLayout(layout);
        add(cardPanel);

        startPanel = new JPanel();
        startPanel.setSize(700, 850);
        startPanel.setBackground(new Color(200, 80, 5));

        JButton button6x6 = new JButton("6x6 Sudoku");
        button6x6.setBackground(new Color(255, 229, 180));
        button6x6.setForeground(Color.BLACK);
        button6x6.setFont(new Font("SansSerif", Font.BOLD, 20));
        button6x6.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button9x9 = new JButton("9x9 Sudoku");
        button9x9.setBackground(new Color(255, 229, 180));
        button9x9.setForeground(Color.BLACK);
        button9x9.setFont(new Font("SansSerif", Font.BOLD, 20));
        button9x9.setAlignmentX(Component.CENTER_ALIGNMENT);

        BufferedImage image = null;
        try {
            image = ImageIO.read(new FileInputStream("src/main/resources/sudoku.png"));
        } catch (IOException ex) {
            System.out.println("Image not found!");
        }

        JLabel picLabel = new JLabel(new ImageIcon(image));
        picLabel.setSize(new Dimension(10, 10));
        picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        startPanel.add(new Box.Filler(new Dimension(5, 80), new Dimension(5, 80), new Dimension(Short.MAX_VALUE, 80)));
        startPanel.add(picLabel);
        startPanel.add(new Box.Filler(new Dimension(5, 80), new Dimension(5, 80), new Dimension(Short.MAX_VALUE, 80)));
        startPanel.add(button6x6);
        startPanel.add(new Box.Filler(new Dimension(5, 20), new Dimension(5, 20), new Dimension(Short.MAX_VALUE, 20)));
        startPanel.add(button9x9);
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        cardPanel.add(startPanel, "StartPanel");

        final JMenuBar mainMenuBar = new JMenuBar();
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
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException exception) {
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
        newGameMenu.add(easy);
        newGameMenu.add(medium);
        newGameMenu.add(hard);

        //add new game menu
        mainMenuBar.add(newGameMenu);
        //add help menu
        mainMenuBar.add(helpMenu);
        //add exit menu
        mainMenuBar.add(exitMenu);

        mainMenuBar.setVisible(false);

        //set the whole menubar
        setJMenuBar(mainMenuBar);

        timerPanel = new JPanel();
        stopWatch = new StopWatch();
        final JLabel timeLabel = new JLabel();
        timeLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        timerPanel.setBackground(new Color(226, 246, 255));
        timerPanel.add(timeLabel);

        button6x6.addActionListener(e -> {
            layout.show(cardPanel, "MainPanel");
            startTimer(timeLabel);
            mainMenuBar.setVisible(true);
            recreateGame(SudokuType.SIXBYSIX, 0.66666);
            setLevelFor6x6(easy, medium, hard);
        });

        button9x9.addActionListener(e -> {
            layout.show(cardPanel, "MainPanel");
            startTimer(timeLabel);
            mainMenuBar.setVisible(true);
            recreateGame(SudokuType.NINEBYNINE, 0.66666);
            setLevelFor9x9(easy, medium, hard);
        });

        mainPanel = new JPanel();
        mainPanel.setSize(700, 850);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        numbersSelectionPanel = new JPanel();
        numbersSelectionPanel.setBackground(new Color(226, 246, 255));
        numbersSelectionPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        sudokuPanel = new SudokuPanel();
        sudokuPanel.setBackground(new Color(226, 246, 255));

        buttonPanel = new JPanel();
        final JButton submitButton = new JButton("Done");
        submitButton.setBackground(new Color(200, 80, 5));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        submitButton.addActionListener(e -> {
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
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException exception) {
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
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException exception) {
                    exception.printStackTrace();
                }

                JOptionPane.showMessageDialog(mainPanel,
                        "Oops, there are some empty squares!",
                        "Unsolved Sudoku",
                        JOptionPane.ERROR_MESSAGE);
                stopWatch.resume();
            }
        });
        buttonPanel.add(submitButton);

        mainPanel.add(timerPanel);
        mainPanel.add(sudokuPanel);
        mainPanel.add(numbersSelectionPanel);
        mainPanel.add(buttonPanel);
        mainPanel.setBackground(new Color(226, 246, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(255, 130, 67));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> {
            layout.show(cardPanel, "StartPanel");
            mainMenuBar.setVisible(false);
            for (ActionListener al : easy.getActionListeners()) {
                easy.removeActionListener(al);
            }
            for (ActionListener al : medium.getActionListeners()) {
                medium.removeActionListener(al);
            }
            for (ActionListener al : hard.getActionListeners()) {
                hard.removeActionListener(al);
            }
        });
        mainPanel.add(backButton);
        cardPanel.add(mainPanel, "MainPanel");

        setResizable(false);
        setVisible(true);
    }

    //regenerate the sudoku board and number buttons according to sudoku type parameter
    public void recreateGame(SudokuType sukoduType, double level) {
        stopWatch.start();
        this.sudoku = new SudokuCreator().createSudoku(sukoduType, level);

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

    private void setLevelFor6x6(JMenuItem easy, JMenuItem medium, JMenuItem hard) {
        easy.addActionListener(e -> recreateGame(SudokuType.SIXBYSIX, 0.88888));
        medium.addActionListener(e -> recreateGame(SudokuType.SIXBYSIX, 0.66666));
        hard.addActionListener(e -> recreateGame(SudokuType.SIXBYSIX, 0.44444));
    }

    private void setLevelFor9x9(JMenuItem easy, JMenuItem medium, JMenuItem hard) {
        easy.addActionListener(e -> recreateGame(SudokuType.NINEBYNINE, 0.88888));
        medium.addActionListener(e -> recreateGame(SudokuType.NINEBYNINE, 0.66666));
        hard.addActionListener(e -> recreateGame(SudokuType.NINEBYNINE, 0.44444));
    }

    private void startTimer(final JLabel timeLabel) {
        Thread thread = new Thread(() -> {
            stopWatch.start();
            while (true) {
                final String timeString = new SimpleDateFormat("mm:ss:SSS").format(stopWatch.getElapsedTime());
                timeLabel.setText("" + timeString);
            }
        });
        thread.start();
    }
}