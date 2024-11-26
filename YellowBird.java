package io.github.bird.game.Objects;

import com.badlogic.gdx.physics.box2d.World;

public class YellowBird extends Bird {
    public YellowBird(World world, float x, float y) {
        super(world, x, y, "Yellowbird.png");
    }

    @Override
    public void specialAbility() {
        // Implement the yellow bird's specific ability
        System.out.println("Yellow Bird special ability activated!");
        // Example: Speed boost
        body.applyLinearImpulse(5f, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
        // Additional logic for speed boost can be added here
    }
}
