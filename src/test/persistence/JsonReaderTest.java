package persistence;

import model.*;
import java.awt.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Arena a = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBalls() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBalls.json");
        try {
            Arena a = reader.read();
//            assertEquals(Color.green, a.getBackgroundColor());
            assertFalse(a.getStop());
//            assertEquals(Color.green, a.getPaddle().getColor());
            assertEquals(12, a.getPaddle().getWidth());
            assertEquals(10, a.getPaddle().getSpeed());
            assertEquals(0, a.getBallsLength());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralArena() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralArena.json");
        try {
            Arena a = reader.read();
//            assertEquals(Color.green, a.getBackgroundColor());
            assertFalse(a.getStop());
//            assertEquals(Color.green, a.getPaddle().getColor());
            assertEquals(12, a.getPaddle().getWidth());
            assertEquals(10, a.getPaddle().getSpeed());
            List<Ball> balls = a.getBalls();
            assertEquals(3, balls.size());
            checkBall(439, 10, 5, 6, 30, Color.blue, balls.get(0));
            checkBall(157, 10, 6, 7, 20, Color.red, balls.get(1));
            checkBall(772, 10, 6, 7, 4, Color.red, balls.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

//    @Test
//    void testAddBackgroundColor() {
//        JsonReader reader = new JsonReader("./data/testAddBackgroundColor.json");
//        try {
//            Arena a = reader.read();
//
////            a.changeBackgroundColor(Color.red);
//
//            JsonWriter writer = new JsonWriter("./data/testAddBackgroundColor.json");
//            writer.open();
//            writer.write(a);
//            writer.close();
//
//            a = reader.read();
//
////            assertEquals(Color.red, a.getBackgroundColor());
//
////            a.changeBackgroundColor(Color.blue);
//
//            writer.open();
//            writer.write(a);
//            writer.close();
//
//            a = reader.read();
//
//            assertFalse(a.getStop());
//
//        } catch (IOException e) {
//            fail("Couldn't read from file");
//        }
//    }
}