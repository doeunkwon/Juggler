package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;
import model.Arena;
import model.Ball;
import model.Paddle;

/*
 * The panel in which the game is rendered.
 */
public class GamePanel extends JPanel {
    private static final String OVER = "Game Over!";
    private static final String REPLAY = "R to replay";

    private final Arena arena;

    // Constructs a game panel
    // EFFECTS:  sets size and background colour of panel,
    //  updates this with the game to be displayed
    GamePanel(Arena a) {
        setPreferredSize(new Dimension(Arena.WIDTH, Arena.HEIGHT));
        setBackground(Color.black);
        this.arena = a;
    }

    // EFFECTS: adds graphics to the game panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
        if (arena.isOver()) {
            gameOver(g);
        }
    }

    // Draws the game
    // MODIFIES: g
    // EFFECTS:  draws the game onto g
    private void drawGame(Graphics g) {
        drawPaddle(g);
        drawBall(g);
    }

    // Draws the paddle
    // MODIFIES: g
    // EFFECTS:  draws the paddle onto g
    private void drawPaddle(Graphics g) {
        Paddle p = arena.getPaddle();
        Color savedCol = g.getColor();
        g.setColor(Color.white);
        g.fillRect(p.getX1() - p.getWidth() / 2, Paddle.Y1 - Paddle.HEIGHT / 2,
                p.getWidth(), Paddle.HEIGHT);
        g.setColor(savedCol);
    }

    // Draws the balls
    // MODIFIES: g
    // EFFECTS:  draws the balls onto g
    private void drawBall(Graphics g) {
        for (Ball b : arena.getBalls()) {
            Color savedCol = g.getColor();
            g.setColor(b.getColor());
            g.fillOval(b.getX1() - b.getSize() / 2, b.getY1() - b.getSize() / 2, b.getSize(), b.getSize());
            g.setColor(savedCol);
        }
    }

    // Draws the "game over" message and replay instructions
    // MODIFIES: g
    // EFFECTS:  draws "game over" and replay instructions onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(Color.orange);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, Arena.HEIGHT / 2);
        centreString(REPLAY, g, fm, Arena.HEIGHT / 2 + 50);
        g.setColor(saved);
    }

    // Centres a string on the screen
    // MODIFIES: g
    // EFFECTS:  centres the string str horizontally onto g at vertical position y
    private void centreString(String str, Graphics g, FontMetrics fm, int y) {
        int width = fm.stringWidth(str);
        g.drawString(str, (Arena.WIDTH - width) / 2, y);
    }
}
