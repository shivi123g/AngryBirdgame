package io.github.bird.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class  Catapult {
    private Vector2 position; // Position of the catapult
    private Vector2 launchForce; // The force to launch the bird
    private boolean isLoaded; // Flag to check if the bird is loaded

    private Texture catapultTexture; // Texture for the catapult image

    private static final float MAX_LAUNCH_FORCE = 500; // Maximum force for launch
    private static final float MIN_LAUNCH_FORCE = 100; // Minimum force to apply

    private Bird loadedBird; // The bird currently loaded in the catapult

    public Catapult(float x, float y) {
        position = new Vector2(x, y); // Set the position of the catapult
        launchForce = new Vector2(0, 0); // Initialize launch force
        isLoaded = false;

        // Load the texture for the catapult
        catapultTexture = new Texture("Catapult.png"); // Replace with actual catapult image path
    }

    // Method to load a bird into the catapult
    public void loadBird(Bird bird) {
        loadedBird = bird;
        loadedBird.setPosition(position.x, position.y); // Set bird's position to catapult position
        isLoaded = true;
    }

    // Method to set launch force based on drag
    public void adjustLaunchForce(float dragX, float dragY) {
        float forceX = Math.max(MIN_LAUNCH_FORCE, Math.min(dragX, MAX_LAUNCH_FORCE));
        float forceY = Math.max(MIN_LAUNCH_FORCE, Math.min(dragY, MAX_LAUNCH_FORCE));

        launchForce.set(forceX, forceY); // Adjust launch force within min and max bounds
    }

    // Method to release the bird from the catapult and launch it
    public void releaseBird() {
        if (isLoaded && loadedBird != null) {
            loadedBird.getVelocity().set(launchForce); // Set bird's velocity to launch force
            isLoaded = false; // Catapult is now unloaded
            loadedBird = null; // Remove the reference to the loaded bird
        }
    }

    // Method to draw the catapult on the screen
    public void draw(Stage stage) {
        stage.getBatch().begin();
        stage.getBatch().draw(catapultTexture, position.x, position.y); // Draw the catapult image
        stage.getBatch().end();
    }

    // Clean up resources
    public void dispose() {
        catapultTexture.dispose(); // Dispose of texture to free memory
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public Vector2 getLaunchForce() {
        return launchForce;
    }
}
