package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Arena a = new Arena();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBalls() {
        try {
            Arena a = new Arena();
            a.clearBalls();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBalls.json");
            writer.open();
            writer.write(a);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBalls.json");
            a = reader.read();
//            assertEquals(Color.black, a.getBackgroundColor());
            assertFalse(a.getStop());
//            assertEquals(Color.white, a.getPaddle().getColor());
            assertEquals(26, a.getPaddle().getWidth());
            assertEquals(7, a.getPaddle().getSpeed());
            assertEquals(0, a.getBallsLength());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralArena() {
        try {
            Arena a = new Arena();
            a.clearBalls();
            a.addStartingBall(new Ball(1, 2, 3, 4, 10, Color.red));
            a.addStartingBall(new Ball(2, 4, 6, 8, 20, Color.blue));
//            a.changeBackgroundColor(Color.green);
            Paddle p = new Paddle(1, 2, 10);
            a.setPaddle(p);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralArena.json");
            writer.open();
            writer.write(a);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralArena.json");
            a = reader.read();
//            assertEquals(Color.green, a.getBackgroundColor());
            assertFalse(a.getStop());
//            assertEquals(Color.red, a.getPaddle().getColor());
            assertEquals(10, a.getPaddle().getWidth());
            assertEquals(2, a.getPaddle().getSpeed());
            List<Ball> balls = a.getBalls();
            assertEquals(2, balls.size());
            checkBall(1, 2, 3, 4, 10, Color.red, balls.get(0));
            checkBall(2, 4, 6, 8, 20, Color.blue, balls.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}