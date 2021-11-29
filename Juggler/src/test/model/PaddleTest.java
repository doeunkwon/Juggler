package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;

public class PaddleTest {
    private static final int x1Value = Arena.WIDTH / 2;
    private Paddle paddle;

    @BeforeEach
    void runBefore() {
        paddle = new Paddle(x1Value, 3, 26);
    }

    @Test
    void testGetX1() {
        assertEquals(x1Value, paddle.getX1());
    }

    @Test
    void testConstructor() {
        assertEquals(Arena.WIDTH / 2, paddle.getX1());
        assertEquals(3, paddle.getSpeed());
        assertEquals(26, paddle.getWidth());
//        assertEquals(Color.red, paddle.getColor());
    }

    // from lab3
    @Test
    void testUpdate() {
        final int NUM_UPDATES = 8;

        paddle.move();
        assertEquals(x1Value - paddle.getSpeed(), paddle.getX1());

        for(int count = 1; count < NUM_UPDATES; count++) {
            paddle.move();
        }

        assertEquals(x1Value - NUM_UPDATES * paddle.getSpeed(), paddle.getX1());
    }

    // from lab3
    @Test
    void testFlipDirection() {
        paddle.move();
        assertEquals(x1Value - paddle.getSpeed(), paddle.getX1());
        paddle.right();
        paddle.move();
        assertEquals(x1Value, paddle.getX1());
        paddle.left();
        paddle.move();
        assertEquals(x1Value - paddle.getSpeed(), paddle.getX1());
    }

    // from lab3
    @Test
    void testLeftBoundary() {
        paddle.left();
        for(int count = 0; count < (Arena.WIDTH / 2 - paddle.getWidth() / 2) /
                paddle.getSpeed() + 1; count++)
            paddle.move();
        assertEquals(paddle.getWidth() / 2, paddle.getX1());
        paddle.move();
        assertEquals(paddle.getWidth() / 2, paddle.getX1());
    }

    // from lab3
    @Test
    void testRightBoundary() {
        paddle.right();
        for(int count = 0; count < (Arena.WIDTH / 2 - paddle.getWidth() / 2) /
                paddle.getSpeed()  + 1; count++)
            paddle.move();
        assertEquals(Arena.WIDTH - paddle.getWidth() / 2, paddle.getX1());
        paddle.move();
        assertEquals(Arena.WIDTH - paddle.getWidth() / 2, paddle.getX1());
    }

    @Test
    void testChangeSpeed() {
        paddle.changeSpeed(2);
        assertEquals(2, paddle.getSpeed());
        paddle.changeSpeed(3);
        assertEquals(3, paddle.getSpeed());
    }

    @Test
    void testChangeWidth() {
        paddle.changeWidth(2);
        assertEquals(2, paddle.getWidth());
        paddle.changeWidth(3);
        assertEquals(3, paddle.getWidth());
    }

//    @Test
//    void testChangeColor() {
//        paddle.changeColor(Color.green);
//        assertEquals(Color.green, paddle.getColor());
//        paddle.changeColor(Color.blue);
//        assertEquals(Color.blue, paddle.getColor());
//    }

//    @Test
//    void colorToString() {
//        paddle.changeColor(Color.red);
//        assertEquals("red", paddle.colorToString());
//
//        paddle.changeColor(Color.blue);
//        assertEquals("blue", paddle.colorToString());
//
//        paddle.changeColor(Color.green);
//        assertEquals("green", paddle.colorToString());
//    }

}