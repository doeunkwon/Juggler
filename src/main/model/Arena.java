package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

// Represents an arena for the game to take place in
public class Arena implements Writable {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;

    private Ball ball;
    private Paddle paddle;
    private ArrayList<Ball> balls;
    private boolean stop;

    // Construct an arena for the game to take place in
    // EFFECTS: a starting paddle is set; 1 white ball is set, stop set to false
    public Arena() {
        ball = new Ball(0, 0, 5.0, 3.0, 20, Color.white);
        paddle = new Paddle(WIDTH / 2, 7, 26);
        balls = new ArrayList<>();
        balls.add(ball);
        stop = false;
    }

    // return paddle
    public Paddle getPaddle() {
        return paddle;
    }

    // return balls
    public ArrayList<Ball> getBalls() {
        return balls;
    }

    // return state of stop
    public Boolean getStop() {
        return stop;
    }

    // return length of balls list
    public Integer getBallsLength() {
        return balls.size();
    }

    // Is game over?
    // EFFECTS: returns true if game is over, false otherwise
    public boolean isOver() {
        return stop;
    }

    // Updates the game on clock tick
    // MODIFIES: this
    // EFFECTS:  updates ball, paddle, and game over status
    public void update() {
        for (Ball ball : balls) {
            ball.move();
        }
        paddle.move();

        checkHitSomething();
        checkStatus();
    }

    // Responds to key press codes
    // MODIFIES: this
    // EFFECTS:  turns paddle and resets game (if game over) in response to
    //           given key pressed code
    public void keyPressed(int k0Code) {
        if (k0Code == KeyEvent.VK_R && stop) {
            setUp();
        } else if (k0Code == KeyEvent.VK_X) {
            System.exit(0);
        } else {
            paddleControl(k0Code);
        }
    }

    // Sets up the game
    // MODIFIES: this
    // EFFECTS:  resets the game
    public void setUp() {
        ArrayList<Ball> ballsReplacement = new ArrayList<>();
        paddle = new Paddle(WIDTH / 2, paddle.getSpeed(), paddle.getWidth());
        for (Ball b : balls) {
            Ball newBall = new Ball(WIDTH / 2, 0, b.getX1Velocity(), b.getY1Velocity(), b.getSize(),
                    b.getColor());
            ballsReplacement.add(newBall);
        }
        balls = ballsReplacement;
        stop = false;
    }

    // Controls the paddle
    // MODIFIES: this
    // EFFECTS: changes direction of paddle in response to key code
    private void paddleControl(int k0Code) {
        if (k0Code == KeyEvent.VK_KP_LEFT || k0Code == KeyEvent.VK_LEFT) {
            paddle.left();
        } else if (k0Code == KeyEvent.VK_KP_RIGHT || k0Code == KeyEvent.VK_RIGHT) {
            paddle.right();
        }
    }

    // Checks for collision between ball and paddle
    // MODIFIES: this
    // EFFECTS:  bounces ball if it collides with paddle
    public void checkHitSomething() {
        for (Ball b : balls) {
            if (b.collidedWithPaddle(paddle)) {
                b.bounceOffPaddle();
            }
        }
    }

    // Is game over? (Has ball hit ground?)
    // MODIFIES: this
    // EFFECTS:  if ball has hit ground, game is marked as over
    public void checkStatus() {
        for (Ball b : balls) {
            if (b.getY1() > HEIGHT) {
                stop = true;
            }
        }
    }

    // adds the starting white ball to list of balls of arena (does not log the event)
    // MODIFIES: this
    // EFFECTS: adds a new ball to the list
    public void addStartingBall(Ball b) {
        balls.add(b);
    }

    // sets a new paddle to the arena
    // MODIFIES: this
    // EFFECTS: sets a new paddle for the arena
    public void setPaddle(Paddle p) {
        paddle = p;
    }

    // clears list of balls of arena
    // MODIFIES: this
    // EFFECTS: clears list of balls
    public void clearBalls() {
        balls.clear();
    }

    // adds a ball to list of balls of arena (logs the event)
    // MODIFIES: this
    // EFFECTS: adds a new ball and logs the event
    public void addBall(Ball b) {
        balls.add(b);
        EventLog.getInstance()
                .logEvent(new Event("A new ball of size " + b.getSize()
                        + " has been added to the list of balls! There are " + balls.size() + " ball(s) now!"));
    }

    // removes a ball from the list of balls
    // MODIFIES: this
    // EFFECTS: removes ball from the list of balls
    public void removeBall(int pos) {
        balls.remove(pos);
        EventLog.getInstance().logEvent(new Event("Removed ball # " + (pos + 1)
                + " from the list! There are " + balls.size() + " ball(s) now!"));
    }

    // EFFECTS: adds properties of the arena to the json file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ball", ball);
        json.put("balls", ballsToJson());
        json.put("paddle", paddle.toJson());
        json.put("stop", stop);
        return json;
    }

    // EFFECTS: returns balls in this arena as a JSON array
    private JSONArray ballsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Ball b : balls) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }

}
