package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;

public class BallTest {
    private static final int x1Value = Arena.WIDTH / 2;
    private static final int y1Value = 50;
    private Ball ball;

    @BeforeEach
    void runBefore() {
        ball = new Ball(x1Value, y1Value, 2.0, 2.0, 20, Color.white);
    }

    @Test
    void testConstructor() {
        assertEquals(Arena.WIDTH / 2, ball.getX1());
        assertEquals(50, ball.getY1());
        assertEquals(2.0, ball.getX1Velocity());
        assertEquals(2.0, ball.getY1Velocity());
        assertEquals(20, ball.getSize());
        assertEquals(Color.white, ball.getColor());
    }

    // from lab3
    @Test
    void testUpdate() {
        final int NUM_UPDATES = 4;

        ball.move();
        assertEquals((int) (x1Value + ball.getX1Velocity()), ball.getX1());
        assertEquals((int) (y1Value + ball.getY1Velocity()), ball.getY1());

        for(int count = 1; count < NUM_UPDATES; count++) {
            ball.move();
        }

        assertEquals((int) (x1Value + NUM_UPDATES * ball.getX1Velocity()), ball.getX1());
        assertEquals((int) (y1Value + NUM_UPDATES * ball.getY1Velocity()), ball.getY1());
    }

    // from lab3
    @Test
    void testBounceOffPaddle() {
        double xVel = ball.getX1Velocity();
        double yVel = ball.getY1Velocity();
        ball.bounceOffPaddle();
        assertEquals(xVel, ball.getX1Velocity());
        assertEquals(-yVel, ball.getY1Velocity());
    }

    // from lab3
    @Test
    void testCollideWith() {
        Paddle p = new Paddle(x1Value, 3, 26);

        Ball b = new Ball(0, 0, 2.0, 2.0, 20, Color.white);
        assertFalse(b.collidedWithPaddle(p));

        b = new Ball(p.getX1(), Paddle.Y1, 2.0, 2.0, 20, Color.white);
        assertTrue(b.collidedWithPaddle(p));

        b = new Ball(p.getX1() + p.getWidth() / 2 + 20 / 2 - 1, Paddle.Y1,
                2.0, 2.0, 20, Color.white);
        assertTrue(b.collidedWithPaddle(p));

        b = new Ball(p.getX1() + p.getWidth() / 2 + 20 / 2, Paddle.Y1,
                2.0, 2.0, 20, Color.white);
        assertFalse(b.collidedWithPaddle(p));

        b = new Ball(p.getX1() - p.getWidth() / 2 - 20 / 2 + 1, Paddle.Y1,
                2.0, 2.0, 20, Color.white);
        assertTrue(b.collidedWithPaddle(p));

        b = new Ball(p.getX1() - p.getWidth() / 2 - 20 / 2, Paddle.Y1,
                2.0, 2.0, 20, Color.white);
        assertFalse(b.collidedWithPaddle(p));

        b = new Ball(p.getX1(), Paddle.Y1 - Paddle.HEIGHT / 2 - 20 / 2 + 1,
                2.0, 2.0, 20, Color.white);
        assertTrue(b.collidedWithPaddle(p));

        b = new Ball(p.getX1(), Paddle.Y1 - Paddle.HEIGHT / 2 - 20 / 2,
                2.0, 2.0, 20, Color.white);
        assertFalse(b.collidedWithPaddle(p));
    }

    @Test
    void testConstrain() {
        double xVel;
        double yVel;

        ball = new Ball(5, y1Value, 2.0, 2.0, 20, Color.white);
        xVel = ball.getX1Velocity();
        ball.constrain();
        assertEquals(ball.getSize() / 2.0, ball.getX1());
        assertEquals(-xVel, ball.getX1Velocity());

        ball = new Ball(799, y1Value, 2.0, 2.0, 20, Color.white);
        xVel = ball.getX1Velocity();
        ball.constrain();
        assertEquals(Arena.WIDTH - ball.size / 2.0, ball.getX1());
        assertEquals(-xVel, ball.getX1Velocity());

        ball = new Ball(x1Value, 5, 2.0, 2.0, 20, Color.white);
        yVel = ball.getY1Velocity();
        ball.constrain();
        assertEquals(ball.getSize() / 2.0, ball.getY1());
        assertEquals(-yVel, ball.getY1Velocity());
    }

//    @Test
//    void testColorToString() {
//
//        Ball ball0 = new Ball(1,2,3,4,5,Color.white);
//        assertEquals("white", ball0.colorToString());
//
//        Ball ball1 = new Ball(1,2,3,4,5,Color.red);
//        assertEquals("red", ball1.colorToString());
//
//        Ball ball2 = new Ball(1,2,3,4,5,Color.blue);
//        assertEquals("blue", ball2.colorToString());
//
//        Ball ball3 = new Ball(1,2,3,4,5,Color.green);
//        assertEquals("green", ball3.colorToString());
//    }

//    @Test
//    void testGetColorName() {
//
//        Ball ball0 = new Ball(1,2,3,4,5,Color.white);
//        assertEquals("white", ball0.getColorName());
//
//        Ball ball1 = new Ball(1,2,3,4,5,Color.red);
//        assertEquals("red", ball1.getColorName());
//
//        Ball ball2 = new Ball(1,2,3,4,5,Color.blue);
//        assertEquals("blue", ball2.getColorName());
//
//        Ball ball3 = new Ball(1,2,3,4,5,Color.green);
//        assertEquals("green", ball3.getColorName());
//    }

}
