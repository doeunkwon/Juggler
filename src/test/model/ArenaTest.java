package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Rectangle;

public class ArenaTest {
    private static final Random RND = new Random();

    private Arena arena;
    private ArrayList<Ball> balls;
    private Ball ball;
    private Paddle paddle;

    @BeforeEach
    void runBefore() {
        arena = new Arena();
    }

    @Test
    void testConstructor() {
        assertFalse(arena.getStop());
    }

    // from lab3
    @Test
    void testInit() {
        Paddle paddle = arena.getPaddle();
        assertEquals(Arena.WIDTH / 2, paddle.getX1());
    }

    @Test
    void testGetBalls() {
        assertEquals(1, arena.getBalls().size());
        ball = new Ball(RND.nextInt(Arena.WIDTH), 10, 2.0, 2.0, 20, Color.white);
        arena.getBalls().add(ball);
        assertEquals(2, arena.getBalls().size());
    }

    @Test
    void testIsOver() {
        // assume game is over
        assertFalse(arena.isOver());
    }

    // from lab3
    @Test
    void testUpdate() {
        Paddle paddle = arena.getPaddle();
        assertEquals(Arena.WIDTH / 2, paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2 - paddle.getSpeed(), paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2 - 2 * paddle.getSpeed(), paddle.getX1());
    }

    // from lab3
    @Test
    void testLeft2RightNonKeyPadKeyEvent() {
        Paddle paddle = arena.getPaddle();
        arena.keyPressed(KeyEvent.VK_LEFT);
        arena.update();
        assertEquals(Arena.WIDTH / 2 - paddle.getSpeed(), paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2 - 2 * paddle.getSpeed(), paddle.getX1());

        arena.keyPressed(KeyEvent.VK_RIGHT);
        arena.update();
        assertEquals(Arena.WIDTH / 2 - paddle.getSpeed(), paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2, paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2 + paddle.getSpeed(), paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2 + 2 * paddle.getSpeed(), paddle.getX1());
    }

    // from lab3
    @Test
    void testRight2LeftNonKeyPadKeyEvent() {
        Paddle paddle = arena.getPaddle();
        arena.keyPressed(KeyEvent.VK_RIGHT);
        arena.update();
        assertEquals(Arena.WIDTH / 2 + paddle.getSpeed(), paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2 + 2 * paddle.getSpeed(), paddle.getX1());

        arena.keyPressed(KeyEvent.VK_LEFT);
        arena.update();
        assertEquals(Arena.WIDTH / 2 + paddle.getSpeed(), paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2, paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2 - paddle.getSpeed(), paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2 - 2 * paddle.getSpeed(), paddle.getX1());
    }

    // from lab3
    @Test
    void testLeft2RightKeyPadKeyEvent() {
        Paddle paddle = arena.getPaddle();
        arena.keyPressed(KeyEvent.VK_KP_RIGHT);
        arena.update();
        assertEquals(Arena.WIDTH / 2 + paddle.getSpeed(), paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2 + 2 * paddle.getSpeed(), paddle.getX1());

        arena.keyPressed(KeyEvent.VK_KP_LEFT);
        arena.update();
        assertEquals(Arena.WIDTH / 2 + paddle.getSpeed(), paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2, paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2 - paddle.getSpeed(), paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2 - 2 * paddle.getSpeed(), paddle.getX1());
    }

    // from lab3
    @Test
    void testRight2LeftKeyPadKeyEvent() {
        Paddle paddle = arena.getPaddle();
        arena.keyPressed(KeyEvent.VK_KP_LEFT);
        arena.update();
        assertEquals(Arena.WIDTH / 2 - paddle.getSpeed(), paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2 - 2 * paddle.getSpeed(), paddle.getX1());

        arena.keyPressed(KeyEvent.VK_KP_RIGHT);
        arena.update();
        assertEquals(Arena.WIDTH / 2 - paddle.getSpeed(), paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2, paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2 + paddle.getSpeed(), paddle.getX1());
        arena.update();
        assertEquals(Arena.WIDTH / 2 + 2 * paddle.getSpeed(), paddle.getX1());
    }

    @Test
    void testSetup() {
        arena.setUp();
        assertEquals(1, arena.getBalls().size());
        assertEquals(Color.white, arena.getBalls().get(0).getColor());
//        assertEquals(Color.white, arena.getPaddle().getColor());
        assertEquals(26, arena.getPaddle().getWidth());
//        assertEquals(Color.black, arena.getBackgroundColor());
    }

//    @Test
//    void testChangeBackgroundColor() {
//        arena.changeBackgroundColor(Color.red);
//        assertEquals(Color.red, arena.getBackgroundColor());
//
//        arena.changeBackgroundColor(Color.blue);
//        assertEquals(Color.blue, arena.getBackgroundColor());
//    }

    @Test
    void testCheckHitSomething() {
        double yVel = arena.getBalls().get(0).getY1Velocity();
        arena.checkHitSomething();
        assertEquals(yVel, arena.getBalls().get(0).getY1Velocity());
    }

    @Test
    void testCheckStatus() {
        // while loop makes ball reach the bottom
        while (arena.getBalls().get(0).getY1() <= Arena.HEIGHT) {
            arena.getBalls().get(0).move();
        }
        arena.checkStatus();

        assertTrue(arena.getStop());
    }

//    @Test
//    void colorToString() {
//        arena.changeBackgroundColor(Color.red);
//        assertEquals("red", arena.colorToString());
//
//        arena.changeBackgroundColor(Color.blue);
//        assertEquals("blue", arena.colorToString());
//
//        arena.changeBackgroundColor(Color.green);
//        assertEquals("green", arena.colorToString());
//    }

}
