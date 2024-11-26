package io.github.bird.game.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Bird extends Actor {
    protected World world;
    protected Body body;
    protected Texture texture;
    private static final float RADIUS = 0.5f; // Assuming 0.5 meters for the bird's size

    public Bird(World world, float x, float y, String texturePath) {
        this.world = world;
        this.texture = new Texture(texturePath);

        // Define the bird's physics body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        // Define the bird's shape and fixture
        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Adjust as necessary
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.6f; // Bounciness

        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose(); // Dispose of the shape to avoid memory leaks

        // Set the bird's size based on the texture dimensions
        setSize(texture.getWidth(), texture.getHeight());
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Synchronize the bird's position with the Box2D body
        Vector2 position = body.getPosition();
        setPosition(position.x - getWidth() / 2, position.y - getHeight() / 2);
    }

    public void dispose() {
        texture.dispose();
        world.destroyBody(body);
    }

    public abstract void specialAbility(); // Abstract method for specific bird abilities
}
