package io.github.bird.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.bird.game.Objects.Bird;

public class Pig {
    private Vector2 position; // Position of the pig
    private Texture pigTexture; // Texture for the pig image
    private boolean isHit; // Whether the pig has been hit by a bird

    public Pig(float x, float y) {
        position = new Vector2(x, y); // Set the position of the pig
        isHit = false; // Initialize the hit status

        // Load the texture for the pig
        pigTexture = new Texture("Pig.png");
    }

    // Method to check if the pig is hit by a bird
    public void checkCollision(Bird bird) {
        // Simple collision check using positions (you can enhance this based on bird size)
        if (bird.getPosition().dst(position) < 50) { // If the distance between bird and pig is less than 50
            isHit = true; // The pig is hit
        }
    }

    // Method to draw the pig on the screen
    public void draw(Stage stage) {
        if (!isHit) { // Only draw the pig if it hasn't been hit
            stage.getBatch().begin();
            stage.getBatch().draw(pigTexture, position.x, position.y); // Draw the pig image
            stage.getBatch().end();
        }
    }

    // Clean up resources
    public void dispose() {
        pigTexture.dispose(); // Dispose of texture to free memory
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isHit() {
        return isHit;
    }
}
