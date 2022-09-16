package persistence;

import model.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkBall(int x1Point, int y1Point, double deltaX1, double deltaY1, int ballSize,
                             Color ballColor, Ball ball) {
        assertEquals(x1Point, ball.getX1());
        assertEquals(y1Point, ball.getY1());
        assertEquals(deltaX1, ball.getX1Velocity());
        assertEquals(deltaY1, ball.getY1Velocity());
        assertEquals(ballSize, ball.getSize());
        assertEquals(ballColor, ball.getColor());
    }
}