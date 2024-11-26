

package io.github.bird.game.Objects;

import com.badlogic.gdx.physics.box2d.World;

public class BlackBird extends Bird {
    public BlackBird(World world, float x, float y) {
        super(world, x, y, "Blackbird.png"); // Use the appropriate texture file for the black bird
    }

    @Override
    public void specialAbility() {
        // Implement the black bird's specific ability
        System.out.println("Black Bird special ability activated!");
        // Example: Explode or cause area damage
        body.applyLinearImpulse(0, 10f, body.getWorldCenter().x, body.getWorldCenter().y, true);
        // Additional logic for explosion can be added here
    }
}
