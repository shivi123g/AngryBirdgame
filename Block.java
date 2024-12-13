package io.github.bird.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Block {
    protected Vector2 position; // Position of the block
    protected Texture blockTexture; // Texture for the block image
    protected int durability; // Durability of the block

    public Block(float x, float y, int durability, String texturePath) {
        this.position = new Vector2(x, y); // Set block's position
        this.durability = durability; // Set block's durability
        this.blockTexture = new Texture(texturePath); // Load the block's texture
    }

    // Method to reduce block durability when hit by a bird
    public void hit(int damage) {
        durability -= damage;
        if (durability <= 0) {
            destroy();
        }
    }

    // Method to destroy the block
    protected void destroy() {

        durability = 0;
    }

    // Method to draw the block on the screen
    public void draw(Stage stage) {
        if (durability > 0) {
            stage.getBatch().begin();
            stage.getBatch().draw(blockTexture, position.x, position.y); // Draw the block
            stage.getBatch().end();
        }
    }

    // Dispose of the texture when the block is no longer needed
    public void dispose() {
        blockTexture.dispose();
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getDurability() {
        return durability;
    }
}
