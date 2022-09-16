package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a paddle that will be used to bounce the balls
public class Paddle implements Writable {
    public static final int HEIGHT = 10;             // the height of the paddle
    public static final int Y1 = Arena.HEIGHT - 40;  // the y-coordinate of the ball

    private int x1;                                  // determines the x-coordinate of the ball
    private int speed;                               // determines how fast the paddle moves horizontally
    private int width;                               // determines the width of the paddle
    private int whichWay;                            // determines the direction of the paddle

    // Construct a paddle.
    // REQUIRES: A positive paddleSpeed and paddleWidth; a color that is not the same as the background
    // EFFECTS: The speed, width, and color of the paddle is set
    public Paddle(int x1Point, int paddleSpeed, int paddleWidth) {
        x1 = x1Point;
        speed = paddleSpeed;
        width = paddleWidth;
        whichWay = -1;
    }

    // gets X1
    public int getX1() {
        return x1;
    }

    // gets speed
    public int getSpeed() {
        return speed;
    }

    // gets width
    public int getWidth() {
        return width;
    }

    // Paddle moves to right
    // MODIFIES: this
    // EFFECTS: paddle is moving right
    public void right() {
        whichWay = 1;
    }

    // Paddle moves to left
    // MODIFIES: this
    // EFFECTS: paddle is moving left
    public void left() {
        whichWay = -1;
    }

    // Updates the paddle on clock tick
    // MODIFIES: this
    // EFFECTS:  paddle is moved DX units in whatever direction it is facing and is
    //           constrained to remain within boundaries of game
    public void move() {
        x1 = x1 + whichWay * speed;
        constrain();
    }

    // Constrains paddle so that it doesn't travel off sides of screen
    // MODIFIES: this
    // EFFECTS: paddle is constrained to remain within vertical boundaries of game
    private void constrain() {
        if (x1 - width / 2 < 0) {
            x1 = width / 2;
        } else if (x1 + width / 2 > Arena.WIDTH) {
            x1 = Arena.WIDTH - width / 2;
        }
    }

    // changes the horizontal speed of the paddle
    // REQUIRES: A newSpeed that is positive
    // MODIFIES: this
    // EFFECTS: Changes the speed of the paddle to newSpeed
    public void changeSpeed(int newSpeed) {
        speed = newSpeed;
    }

    // changes the width of the paddle
    // REQUIRES: A newWidth that is positive
    // MODIFIES: this
    // EFFECTS: Changes the width of the paddle to newWidth
    public void changeWidth(int newWidth) {
        width = newWidth;
    }

    // EFFECTS: adds properties of paddle to the json file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x1", x1);
        json.put("speed", speed);
        json.put("width", width);
        json.put("whichWay", whichWay);
        return json;
    }

}
