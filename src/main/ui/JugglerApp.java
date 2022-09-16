package ui;

import java.awt.*;
import model.Arena;
import model.Ball;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;

// Represents the main window in which the Juggler game is played
public class JugglerApp extends JFrame {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;
    private static final int INTERVAL = 20;
    private static final Random RND = new Random();
    private static final String JSON_STORE = "./data/arena.json";

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private final JFrame frame;
    private final JButton menuActionButton;
    private Arena arena;
    private GamePanel gp;
    private JFrame menuFrame;

    // EFFECTS: runs the Juggler application
    public JugglerApp()  throws FileNotFoundException {
        super("Juggler");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        frame = new JFrame();
        frame.setResizable(false);
        menuActionButton = new JButton(new DisplayMenu());
        JPanel menuPanel = new JPanel();
        menuPanel.setBorder(BorderFactory.createEmptyBorder(260, 150, 260, 150));
        menuPanel.setLayout(new GridLayout(0,1));
        menuPanel.add(menuActionButton);
        frame.add(menuPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Juggler Menu");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        init();
    }

    // MODIFIES: this
    // EFFECTS: initializes game
    private void init() {
        arena = new Arena();
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // Displays the menu for the GUI
    private class DisplayMenu extends AbstractAction {
        DisplayMenu() {
            super("Menu");
        }

        // EFFECTS: Performs the action of displaying the menu
        @Override
        public void actionPerformed(ActionEvent evt) {
            displayMenuSetup();
            displayMenuBody();
            menuFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    EventLog el = EventLog.getInstance();
                    for (Event event : el) {
                        System.out.println("\n");
                        System.out.println(event);
                    }
                    System.exit(0);
                }
            });
        }

        // EFFECTS: sets up the main information for DisplayMenu()
        private void displayMenuSetup() {
            frame.dispose();
            menuFrame = new JFrame();
            menuFrame.setResizable(false);
        }

        // EFFECTS: sets up the body for DisplayMenu()
        private void displayMenuBody() {
            JButton addBallButton = new JButton(new AddBall());
            JButton removeBallButton = new JButton(new RemoveBall());
            JButton editPaddleButton = new JButton(new EditPaddle());
            JButton fileActionButton = new JButton(new FileAction());
            JButton playGameButton = new JButton(new PlayGameAction());
            JButton printLogButton = new JButton(new PrintLogAction());
            JPanel menuPanel = new JPanel();
            menuPanel.setBorder(BorderFactory.createEmptyBorder(200, 150, 200, 150));
            menuPanel.setLayout(new GridLayout(0,1));
            menuPanel.add(addBallButton);
            menuPanel.add(removeBallButton);
            menuPanel.add(editPaddleButton);
            menuPanel.add(fileActionButton);
            menuPanel.add(playGameButton);
            menuPanel.add(printLogButton);
            menuFrame.add(menuPanel, BorderLayout.CENTER);
            menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menuFrame.setTitle("Juggler Menu");
            menuFrame.pack();
            menuFrame.setLocationRelativeTo(null);
            menuFrame.setSize(WIDTH, HEIGHT);
            menuFrame.setVisible(true);
        }

    }

    // Begins the game
    private class PlayGameAction extends AbstractAction {
        PlayGameAction() {
            super("Play");
        }

        // EFFECTS: performs the action of playing the game
        @Override
        public void actionPerformed(ActionEvent evt) {
            menuFrame.dispose();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setUndecorated(true);
            gp = new GamePanel(arena);
            add(gp);
            addKeyListener(new KeyHandler());
            pack();
            centreOnScreen();
            setVisible(true);
            addTimer();
        }
    }


    // Saves or loads file
    private class FileAction extends AbstractAction {
        FileAction() {
            super("File");
        }

        // EFFECTS: performs the action of FileAction
        @Override
        public void actionPerformed(ActionEvent evt) {
            menuFrame.dispose();
            JFrame fileFrame = new JFrame();
            fileFrame.setResizable(false);
            JPanel filePanel = new JPanel();
            filePanel.setBorder(BorderFactory.createEmptyBorder(230, 150, 230, 150));
            filePanel.setLayout(new GridLayout(0,1));
            JButton saveButton = new JButton(new SaveAction());
            JButton loadButton = new JButton(new LoadAction());
            filePanel.add(menuActionButton);
            filePanel.add(saveButton);
            filePanel.add(loadButton);
            // EFFECTS: disposes the previous frame
            menuActionButton.addActionListener(e -> fileFrame.dispose());
            fileFrame.setTitle("File");
            finalSetup(fileFrame, filePanel);
        }
    }

    // edits the properties of paddle
    // MODIFIES: this
    // EFFECTS: edits the properties of paddle
    private void doEditPaddle(JTextField s, JTextField w) {
        int newSpeed = Integer.parseInt(s.getText());
        arena.getPaddle().changeSpeed(newSpeed);

        int newWidth = Integer.parseInt(w.getText());
        arena.getPaddle().changeWidth(newWidth);
    }

    // Action to edit the paddle
    private class EditPaddle extends AbstractAction {
        EditPaddle() {
            super("Paddle");
        }

        // EFFECTS: Performs the action of editing the paddle
        @Override
        public void actionPerformed(ActionEvent evt) {
            menuFrame.dispose();
            JFrame paddleFrame = new JFrame();
            paddleFrame.setResizable(false);
            JPanel paddlePanel = new JPanel();
            paddlePanel.setBorder(BorderFactory.createEmptyBorder(200, 150, 200, 150));
            paddlePanel.setLayout(new GridLayout(0,1));
            JLabel speedLabel = new JLabel("Speed");
            JLabel widthLabel = new JLabel("Width");
            JTextField speedInput = new JTextField("0", 30);
            JTextField widthInput = new JTextField("0", 30);
            JButton submitButton = new JButton("Submit");
            setupNewPaddleInfo(paddleFrame, paddlePanel, speedInput, widthInput, speedLabel, widthLabel, submitButton);
            // EFFECTS: handles the input for speed and width
            submitButton.addActionListener(e -> doEditPaddle(speedInput, widthInput));
            paddleFrame.setTitle("Paddle");
            finalSetup(paddleFrame, paddlePanel);
        }
    }

    // Sets up the information for EditPaddle
    private void setupNewPaddleInfo(JFrame f, JPanel p, JTextField si, JTextField wi, JLabel sl, JLabel wl, JButton s) {
        p.add(menuActionButton);
        // EFFECTS: performs the action of disposing the previous window
        menuActionButton.addActionListener(e -> f.dispose());
        p.add(sl);
        p.add(si);
        p.add(wl);
        p.add(wi);
        p.add(s);
    }

    // Adds a new ball
    // MODIFIES: this
    // EFFECTS: Adds a new ball to balls
    private void doAddBall(JTextField h, JTextField v, JTextField b) {
        double horizontalSpeed = Integer.parseInt(h.getText());
        double verticalSpeed = Integer.parseInt(v.getText());
        int ballSize = Integer.parseInt(b.getText());
        Random rand = new Random();
        float red = rand. nextFloat();
        float green = rand. nextFloat();
        float blue = rand. nextFloat();
        Color color = new Color(red, green, blue);
        Ball newBall = new Ball(RND.nextInt(Arena.WIDTH), 10, horizontalSpeed,
                verticalSpeed, ballSize, color);
        arena.addBall(newBall);
    }

    // adds a new ball
    private class AddBall extends AbstractAction {
        AddBall() {
            super("Add");
        }

        // EFFECTS: performs the action of adding a new ball
        @Override
        public void actionPerformed(ActionEvent evt) {
            menuFrame.dispose();
            JFrame addFrame = new JFrame();
            addFrame.setResizable(false);
            JPanel addPanel = new JPanel();
            addPanel.setBorder(BorderFactory.createEmptyBorder(180, 100, 180, 100));
            addPanel.setLayout(new GridLayout(0,1));
            JLabel horizontalLabel = new JLabel("Horizontal Speed");
            JLabel verticalLabel = new JLabel("Vertical Speed");
            JLabel ballSizeLabel = new JLabel("Ball Size");
            JTextField horizontalSpeedInput = new JTextField("0", 30);
            JTextField verticalSpeedInput = new JTextField("0", 30);
            JTextField ballSizeInput = new JTextField("0", 30);
            JButton submitButton = new JButton("Submit");
            setupNewBallInfo(addFrame, addPanel, horizontalSpeedInput, verticalSpeedInput, ballSizeInput,
                    horizontalLabel, verticalLabel, ballSizeLabel, submitButton);
            // EFFECTS: handles the input for speed and size of ball
            submitButton.addActionListener(e -> doAddBall(horizontalSpeedInput, verticalSpeedInput, ballSizeInput));
            addFrame.setTitle("Add");
            finalSetup(addFrame, addPanel);
        }
    }

    // EFFECTS: structures the information for the class AddBall
    private void setupNewBallInfo(JFrame f, JPanel p, JTextField h, JTextField v, JTextField b, JLabel hl, JLabel vl,
                                  JLabel bl, JButton s) {
        p.add(menuActionButton);
        // EFFECTS: disposes the previous window
        menuActionButton.addActionListener(e -> f.dispose());
        p.add(hl);
        p.add(h);
        p.add(vl);
        p.add(v);
        p.add(bl);
        p.add(b);
        p.add(s);
    }

    // EFFECTS: structures the setup for button classes
    private void finalSetup(JFrame f, JPanel p) {
        f.add(p, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setSize(WIDTH, HEIGHT);
        f.setVisible(true);
    }

    // Removes a ball
    // MODIFIES: this
    // EFFECTS: removes a specified ball from balls
    private void doRemoveBall(JTextField i) {
        int positionInt = Integer.parseInt(i.getText());
        if ((arena.getBalls().size() > 0) && positionInt >= 0 && positionInt
                <= (arena.getBalls().size())) {
            arena.removeBall(positionInt - 1);
        } else {
            System.out.println("Invalid!");
        }
    }

    // enables to removal of a ball
    private class RemoveBall extends AbstractAction {
        RemoveBall() {
            super("Remove");
        }

        // EFFECTS: performs the action of removing the ball
        @Override
        public void actionPerformed(ActionEvent evt) {
            menuFrame.dispose();
            JFrame removeFrame = new JFrame();
            removeFrame.setResizable(false);
            JPanel removePanel = new JPanel();
            removePanel.setBorder(BorderFactory.createEmptyBorder(220, 100, 220, 100));
            removePanel.setLayout(new GridLayout(0,1));
            JLabel positionLabel = new JLabel("Ball # to Remove");
            JTextField positionInput = new JTextField("0", 30);
            JButton submitButton = new JButton("Submit");
            setupRemoveBallInfo(removeFrame, removePanel, positionInput, positionLabel, submitButton);
            // EFFECTS: performs the action of removing the ball at the given input
            submitButton.addActionListener(e -> doRemoveBall(positionInput));
            removeFrame.setTitle("Remove");
            finalSetup(removeFrame, removePanel);
        }
    }

    // EFFECTS: structures the information for the RemoveBall class
    private void setupRemoveBallInfo(JFrame f, JPanel p, JTextField po, JLabel pol, JButton s) {
        p.add(menuActionButton);
        // EFFECTS: performs the action of disposing previous window
        menuActionButton.addActionListener(e -> f.dispose());
        p.add(pol);
        p.add(po);
        p.add(s);
    }

    // saves the game state
    private class SaveAction extends AbstractAction {
        SaveAction() {
            super("Save");
        }

        // EFFECTS: performs the action of SaveAction
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriter.open();
                jsonWriter.write(arena);
                jsonWriter.close();
                System.out.println("Saved the current arena to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // loads the game state
    private class LoadAction extends AbstractAction {
        LoadAction() {
            super("Load");
        }

        // EFFECTS: performs the action of LoadAction
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                arena = jsonReader.read();
                System.out.println("Loaded the latest arena from " + JSON_STORE);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    // prints the log onto the console
    private static class PrintLogAction extends AbstractAction {

        PrintLogAction() {
            super("Log");
        }

        // EFFECTS: performs the action of PrintLogAction
        @Override
        public void actionPerformed(ActionEvent evt) {
            EventLog el = EventLog.getInstance();
            for (Event event : el) {
                System.out.println("\n");
                System.out.println(event);
            }
        }
    }

    // Set up timer
    // MODIFIES: none
    // EFFECTS: initializes a timer that updates game each
    // INTERVAL milliseconds
    private void addTimer() {
        // EFFECTS: performs the action of add timer
        Timer t = new Timer(INTERVAL, ae -> {
            arena.update();
            gp.repaint();
        });
        t.start();
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    // A key handler to respond to key events
    private class KeyHandler extends KeyAdapter {
        // EFFECTS: performs the action of KeyHandler
        @Override
        public void keyPressed(KeyEvent e) {
            arena.keyPressed(e.getKeyCode());
        }
    }

    // EFFECTS: plays the game
    public static void main(String[] args) {
        try {
            new JugglerApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }

}
