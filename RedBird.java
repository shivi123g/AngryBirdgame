package io.github.bird.game.Objects;

import com.badlogic.gdx.physics.box2d.World;

public class RedBird extends Bird {
    public RedBird(World world, float x, float y) {
        super(world, x, y, "Redbird.png"); // Use the appropriate texture file for the red bird
    }

    @Override
    public void specialAbility() {
        // Implement the red bird's specific ability
        System.out.println("Red Bird special ability activated!");
        // Example: Increase speed or change trajectory
        body.applyLinearImpulse(5f, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
    }
}
