package model;

import java.awt.*;
import java.awt.Rectangle;
import org.json.JSONObject;
import persistence.Writable;

// Represents a ball that will be bounced
public class Ball implements Writable {
    public final int size;         // determines how big the ball is
    private final Color color;     // determines how color of the ball
    private double x1;             // determines the x-coordinate of the ball
    private double y1;             // determines the y-coordinate of the ball
    private double x1Velocity;     // determines the velocity of the ball in the x direction
    private double y1Velocity;     // determines the velocity of the ball in the y direction

    // Construct a ball
    // REQUIRES: A positive x1Speed, y1Speed, and ballSize; a color that is not the same as the background
    // EFFECTS: The speed, size, and color of the ball is set
    public Ball(int x1Point, int y1Point, double deltaX1, double deltaY1, int ballSize, Color ballColor) {
        x1 = x1Point;
        y1 = y1Point;
        x1Velocity = deltaX1;
        y1Velocity = deltaY1;
        size = ballSize;
        color = ballColor;
    }

    // gets X1 value
    public int getX1() {
        return (int) x1;
    }

    // gets Y1 value
    public int getY1() {
        return (int) y1;
    }

    // gets X1 velocity
    public double getX1Velocity() {
        return x1Velocity;
    }

    // gets Y1 velocity
    public double getY1Velocity() {
        return y1Velocity;
    }

    // gets size
    public int getSize() {
        return size;
    }

    // gets color
    public Color getColor() {
        return color;
    }

    // Bounce ball off paddle
    // MODIFIES: this
    // EFFECTS: the y-direction velocity is negated
    public void bounceOffPaddle() {
        y1Velocity *= -1;
    }

    // Updates ball on clock tick
    // MODIFIES: this
    // EFFECTS: the ball is moved in the x and y direction
    public void move() {
        x1 = x1 + x1Velocity;
        y1 = y1 + y1Velocity;
        constrain();
    }

    // Determines if this ball has collided with the paddle
    // EFFECTS:  returns true if this ball has collided with paddle p,
    //           false otherwise
    public boolean collidedWithPaddle(Paddle p) {
        Rectangle ballBoundingRectangle = new Rectangle(getX1() - size / 2, getY1() - size / 2, size, size);
        Rectangle paddleBoundingRectangle = new Rectangle(p.getX1() - p.getWidth() / 2,
                Paddle.Y1 - Paddle.HEIGHT / 2, p.getWidth(), Paddle.HEIGHT);
        return ballBoundingRectangle.intersects(paddleBoundingRectangle);
    }

    // Constrains ball so that it bounces off top and vertical walls
    // MODIFIES: this
    // EFFECTS: ball is constrained to bounce off top and vertical walls
    public void constrain() {
        if (getX1() - size / 2 < 0) {
            x1 = size / 2.0;
            x1Velocity *= -1;
        } else if (getX1() + size / 2 > Arena.WIDTH) {
            x1 = Arena.WIDTH - size / 2.0;
            x1Velocity *= -1;
        } else if (getY1() - size / 2 < 0) {
            y1 = size / 2.0;
            y1Velocity *= -1;
        }
    }

    // EFFECTS: adds properties of ball to the json file
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("size", size);
        json.put("color", Integer.toString(color.getRGB()));
        json.put("x1", x1);
        json.put("y1", y1);
        json.put("x1Velocity", x1Velocity);
        json.put("y1Velocity", y1Velocity);
        return json;
    }

}
