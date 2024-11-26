//package io.github.bird.game.Objects;
//
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.World;
//
//public class BlackBird extends Bird {
//    public BlackBird(World world, float x, float y) {
//        super(world, x, y,
//            "Blackbird.png",
//            "sounds/black_bird_flap.wav",
////            "sounds/black_bird_collision.wav");
//    }
//
//    @Override
//    public void handleCollision() {
//        super.handleCollision();
//        // BlackBird explodes upon collision, dealing area damage.
//        System.out.println("BlackBird collided and caused an explosion!");
//    }
//}


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
