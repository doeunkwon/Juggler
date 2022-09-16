package persistence;

import model.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.awt.*;
import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads arena from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Arena read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseArena(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses arena from JSON object and returns it
    private Arena parseArena(JSONObject jsonObject) {
        Arena a = new Arena();
        a.clearBalls();
        addBalls(a, jsonObject);
        addPaddle(a, jsonObject);
        return a;
    }

    // MODIFIES: a
    // EFFECTS: parses balls from JSON object and adds them to Arena
    private void addBalls(Arena a, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("balls");
        for (Object json : jsonArray) {
            JSONObject nextBall = (JSONObject) json;
            addBall(a, nextBall);
        }
    }

    // MODIFIES: a
    // EFFECTS: parses ball from JSON object and adds it to arena
    private void addBall(Arena a, JSONObject jsonObject) {
        double x1Velocity = jsonObject.getDouble("x1Velocity");
        int size = jsonObject.getInt("size");
        Color ballColor = new Color(Integer.parseInt(jsonObject.getString("color")));
        double y1Velocity = jsonObject.getDouble("y1Velocity");
        int y1 = jsonObject.getInt("y1");
        int x1 = jsonObject.getInt("x1");
        Ball ball = new Ball(x1, y1, x1Velocity, y1Velocity, size, ballColor);
        a.addBall(ball);
    }

    // MODIFIES: a
    // EFFECTS: parses paddle from JSON object and adds it to arena
    private void addPaddle(Arena a, JSONObject jsonObject) {
        JSONObject savedPaddle = jsonObject.getJSONObject("paddle");
        int x1 = savedPaddle.getInt("x1");
        int speed = savedPaddle.getInt("speed");
        int width = savedPaddle.getInt("width");
        Paddle paddle = new Paddle(x1, speed, width);
        a.setPaddle(paddle);
    }

}