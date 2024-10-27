package io.github.bird.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Bird extends Actor {
    protected Texture birdTexture;
    protected Vector2 position;
    protected Vector2 velocity;
    protected Rectangle bounds;

    protected static final float GRAVITY = -15;
    protected static final float JUMP_FORCE = 250;

    public Bird(String texturePath) {
        birdTexture = new Texture(texturePath); // Load texture
        position = new Vector2(100, Gdx.graphics.getHeight() / 2); // Initial position
        velocity = new Vector2(0, 0); // Initial velocity
        bounds = new Rectangle(position.x, position.y, birdTexture.getWidth(), birdTexture.getHeight());
        setBounds(position.x, position.y, birdTexture.getWidth(), birdTexture.getHeight());
    }

    public void update(float delta) {
        if (position.y > 0 || velocity.y > 0) {
            velocity.add(0, GRAVITY); // Apply gravity
        }

        velocity.scl(delta);
        position.add(0, velocity.y); // Update position by velocity

        if (position.y < 0) {
            position.y = 0; // Prevent falling below the screen
        }

        velocity.scl(1 / delta); // Un-scale velocity
        bounds.setPosition(position.x, position.y); // Update bounding box
    }

    public void jump() {
        velocity.y = JUMP_FORCE; // Apply jump force
    }

    // Getter for velocity
    public Vector2 getVelocity() {
        return velocity; // Return current velocity
    }

    // Setter for velocity
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity; // Set velocity
    }

    // Getter for position
    public Vector2 getPosition() {
        return position; // Return current position
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(birdTexture, position.x, position.y); // Draw bird at its position
    }

    @Override
    public void act(float delta) {
        update(delta); // Update the bird
        debug(); // Call debug method to print info
    }

    public Rectangle getBounds() {
        return bounds; // Return bounds for collision detection
    }


    public void dispose() {
        birdTexture.dispose(); // Dispose of texture to free memory
    }

}
