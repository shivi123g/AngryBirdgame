package io.github.bird.game.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.bird.game.GameState;
import io.github.bird.game.Main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private SpriteBatch batch;

    private Texture birdTexture1, birdTexture2, birdTexture3, birdTexture;
    private Texture pigTexture1, pigTexture2, pigTexture3;
    private Texture woodTexture, glassTexture, steelTexture;
    private Texture slingshotTexture;
    private List<Vector2> birdTrail = new ArrayList<>();
    float timeStep = 1/60f;
    int trajectorySteps = 300;
    float gravity = 9.8f;
    private static final float WORLD_WIDTH = 960;
    private static final float WORLD_HEIGHT = 544;
    private static final float UNITS_PER_METER = 32F;

    private static Body birdBody;
    private static Body[] pigBodies;
    private int[] pigHits; // Track hits on pigs
    private static Body[] blockBodies;

    private Vector2 slingshotPosition;
    private boolean isDragging = false;
    private int currentBirdType = 1;
    private static int birdsRemaining = 5;

    private Main game;
    private Stage stage;
    private int level;
    private ShapeRenderer shapeRenderer;
    private TextButton pauseButton;
    private TextButton winButton;
    private TextButton loseButton;
    private Texture backgroundTexture;
    private Texture pauseUpTexture;
    private Texture pauseDownTexture;
    private Texture winUpTexture;
    private Texture winDownTexture;
    private Texture loseUpTexture;
    private Texture loseDownTexture;
    private TextButton settingsButton;
    private Table settingsPanel;


    public GameScreen(Main game,int level) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.level = level;
        if (level ==1) {
            backgroundTexture = new Texture(Gdx.files.internal("bglevel1.png"));
        }
        else if (level ==2) {
            backgroundTexture = new Texture(Gdx.files.internal("bglevel2.png"));
        }
        else if (level ==3) {
            backgroundTexture = new Texture(Gdx.files.internal("bglevel3.png"));
        }
        BitmapFont font = new BitmapFont();

        TextButton.TextButtonStyle settingsButtonStyle = new TextButton.TextButtonStyle();
        settingsButtonStyle.font = font;
        settingsButtonStyle.fontColor = Color.WHITE;
        settingsButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("settingup.png")));
        settingsButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("settingsdown.png")));


        settingsButton = new TextButton("", settingsButtonStyle);
        settingsButton.setPosition(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 100);
        stage.addActor(settingsButton);

        settingsPanel = new Table();
        settingsPanel.setVisible(false);  // Initially hidden
        settingsPanel.setPosition(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 250);


        TextButton.TextButtonStyle soundButtonStyle = new TextButton.TextButtonStyle();
        soundButtonStyle.font = font;
        soundButtonStyle.fontColor = Color.WHITE;
        soundButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("soundup.png")));
        soundButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("sounddown.png")));



        TextButton soundOption = new TextButton("", soundButtonStyle);

        TextButton.TextButtonStyle quitButtonStyle = new TextButton.TextButtonStyle();
        quitButtonStyle.font = font;
        quitButtonStyle.fontColor = Color.WHITE;
        quitButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("quitup.png")));
        quitButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("quitdown.png")));

        TextButton quitButton = new TextButton("", quitButtonStyle);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Perform quit action, such as navigating to the main menu or exiting
                Gdx.app.exit();
            }
        });
        settingsPanel.add(soundOption).pad(10);
        settingsPanel.row();
        settingsPanel.add(quitButton).pad(10);

        stage.addActor(settingsPanel);

        // Toggle panel visibility on settings button click
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsPanel.setVisible(!settingsPanel.isVisible());
            }
        });

        pauseUpTexture = new Texture(Gdx.files.internal("pauseup.png"));
        pauseDownTexture = new Texture(Gdx.files.internal("pausedown.png"));
        winUpTexture = new Texture(Gdx.files.internal("winbuttonup.png"));
        winDownTexture = new Texture(Gdx.files.internal("winbuttondown.png"));
        loseUpTexture = new Texture(Gdx.files.internal("loseup.png"));
        loseDownTexture = new Texture(Gdx.files.internal("losedown.png"));

        // Create a pause button
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(pauseUpTexture));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(pauseDownTexture));
        buttonStyle.font = font;  // Add the font to the style
        buttonStyle.fontColor = Color.RED;


        pauseButton = new TextButton("", buttonStyle);
        pauseButton.setPosition(Gdx.graphics.getWidth() - 1930, Gdx.graphics.getHeight() - 60);
        stage.addActor(pauseButton);

        // Add button click listener
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseScreen(game, GameScreen.this, level));
            }
        });

        create();

    }
    public void create() {
        camera = new OrthographicCamera();
        shapeRenderer = new ShapeRenderer();
        camera.setToOrtho(false, 800, 480);

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();

        birdTexture1 = new Texture("RedBird.png");
        birdTexture2 = new Texture("BlackBird.png");
        birdTexture3 = new Texture("YellowBird.png");
        pigTexture1 = new Texture("Pig.png");
        pigTexture2 = new Texture("Pig.png");
        pigTexture3 = new Texture("Pig.png");
        woodTexture = new Texture("wood.png");
        glassTexture = new Texture("glass.png");
        steelTexture = new Texture("steel.png");
        slingshotTexture = new Texture("catapult.png");

        slingshotPosition = new Vector2(150, 150);
        createBird(currentBirdType);
        createPigs();
        createBlocks();

        // Add contact listener to handle collisions
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                handleContact(contact);
            }

            @Override
            public void endContact(Contact contact) {}
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });

       // Stage gets input first
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);

        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
                if (new Vector2(worldCoordinates.x, worldCoordinates.y).dst(slingshotPosition) < 50) {
                    isDragging = true;
                }
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragging) {
                    Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
                    Vector2 newBirdPosition = new Vector2(worldCoordinates.x, worldCoordinates.y);
                    float maxDistance = 100; // Maximum drag distance from slingshot

                    // Clamp the bird's position to within the max drag distance
                    if (newBirdPosition.dst(slingshotPosition) > maxDistance) {
                        newBirdPosition = slingshotPosition.cpy().add(newBirdPosition.sub(slingshotPosition).nor().scl(maxDistance));
                    }

                    birdBody.setTransform(newBirdPosition, 0); // Update bird's position
                }
                System.out.println("Dragging bird to: " + birdBody.getPosition());
                return true;
            }


            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isDragging) {
                    Vector3 releasePosition = camera.unproject(new Vector3(screenX, screenY, 0));
                    Vector2 releaseVector = slingshotPosition.cpy().sub(new Vector2(releasePosition.x, releasePosition.y));

                    float strength = Math.min(releaseVector.len() * 0.1f, 15); // Clamp strength to prevent excessive force
                    releaseVector.nor().scl(strength);

                    birdBody.setType(BodyDef.BodyType.DynamicBody); // Switch to dynamic
                    birdBody.setAwake(true);
                    birdBody.applyLinearImpulse(releaseVector, birdBody.getWorldCenter(), true);

                    isDragging = false;
                    birdsRemaining--;

                    // Prepare the next bird if available
                    if (birdsRemaining > 0) {
                        currentBirdType = (currentBirdType % 3) + 1;
                        createBird(currentBirdType);
                    }
                    System.out.println("Released bird with impulse: " + releaseVector);
                }

                return true;
            }

        });

        Gdx.input.setInputProcessor(inputMultiplexer);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                handleContact(contact);

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });

    }
    public static GameState collectGameState() {
        GameState state = new GameState();

        // Save pigs
        state.pigPositions = new ArrayList<>();
        state.pigActiveStates = new ArrayList<>();
        for (Body pigBody : pigBodies) {
            state.pigPositions.add(pigBody.getPosition());
            state.pigActiveStates.add(pigBody.isActive());
        }

        // Save birds
        state.birdPositions = new ArrayList<>();
        if (birdBody != null) {
            state.birdPositions.add(birdBody.getPosition());
        }
        state.birdsRemaining = birdsRemaining;

        // Save blocks
        state.blockPositions = new ArrayList<>();
        state.blockTypes = new ArrayList<>();
        for (int i = 0; i < blockBodies.length; i++) {
            Body blockBody = blockBodies[i];
            state.blockPositions.add(blockBody.getPosition());
            state.blockTypes.add(i % 3 == 0 ? "wood" : (i % 3 == 1 ? "glass" : "steel"));
        }

        return state;
    }
    public static void saveGameState(GameState state, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(state);
            System.out.println("Game state saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save game state.");
        }
    }
    private void createBird(int type) {
        if (birdBody != null) {
            world.destroyBody(birdBody);
        }

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(slingshotPosition.x, slingshotPosition.y); // Position the bird at the slingshot location

        birdBody = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(15);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Default density
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        birdBody.createFixture(fixtureDef);
        shape.dispose();

        // Set the bird texture
        switch (type) {
            case 1:
                birdTexture = birdTexture1;
                break;
            case 2:
                birdTexture = birdTexture2;
                break;
            case 3:
                birdTexture = birdTexture3;
                break;
        }
    }

    private void createPigs() {
        if (level ==1 ){
            pigBodies = new Body[3]; // Example with 3 pigs
            pigHits = new int[3];

        }else if (level ==2 ){
            pigBodies = new Body[5];
            pigHits = new int[5];
        }
        else{
            pigBodies = new Body[7];
            pigHits = new int[7];
        }
         // Track hits for each pig
        for (int i = 0; i < pigBodies.length; i++) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(600 + i * 50, 400); // Position the pigs

            pigBodies[i] = world.createBody(bodyDef);
            pigBodies[i].setActive(true);

            CircleShape shape = new CircleShape();
            shape.setRadius(20);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 0.5f;
            fixtureDef.friction = 0.4f;
            fixtureDef.restitution = 0.6f;

            pigBodies[i].createFixture(fixtureDef);
            shape.dispose();
        }
    }

    private void createBlocks() {
        blockBodies = new Body[9];
        int index = 0;// Example with 3 blocks of different materials
        for (int i = 0; i < 3; i++) {
            blockBodies[index++]= createBlock(400 + i * 50, 200, woodTexture, 1.0f - 0.3f * 0); // Wood block
            blockBodies[index++]= createBlock(450 + i * 50, 200, glassTexture, 1.0f - 0.3f * 1); // Glass block
            blockBodies[index++]= createBlock(500 + i * 50, 200, steelTexture, 1.0f - 0.3f * 2); // Steel block
        }
    }

    private Body createBlock(float x, float y, Texture texture, float density) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y); // Position the block

        Body blockBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(25, 25); // Example block size

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density; // Different densities for different materials
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.5f;

        blockBody.createFixture(fixtureDef);
        shape.dispose();

        return blockBody;
    }
    public void drawTrajectory(Vector2 initialPosition, Vector2 initialVelocity) {
        Vector2 position = new Vector2(initialPosition); // Avoid unnecessary cpy()
        Vector2 velocity = new Vector2(initialVelocity); // Avoid unnecessary cpy()

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // Line mode for trajectory

        shapeRenderer.setColor(0, 0, 1, 1); // Blue line for trajectory

        // Simulate the trajectory
        for (int i = 0; i < trajectorySteps; i++) {
            // Calculate the next position
            Vector2 nextPosition = position.cpy().add(velocity.cpy().scl(timeStep));

            // Draw a line segment between the current and next position
            shapeRenderer.line(position.x, position.y, nextPosition.x, nextPosition.y);

            // Update position and velocity for the next step
            position.set(nextPosition);
            velocity.y -= gravity * timeStep; // Apply gravity to y-velocity
        }

        shapeRenderer.end();
    }

    private void handleContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Handle collision with pigs
        for (int i = 0; i < pigBodies.length; i++) {

            if ((fixtureA.getBody() == pigBodies[i] || fixtureB.getBody() == pigBodies[i]) && pigBodies[i].isActive()) {
                pigHits[i]++;
                // Deactivate pig if it has received enough hits
                if (pigHits[i] >= 2) { // Example: 2 hits required to deactivate a pig
                    pigBodies[i].setActive(false);
                }
            }
        }

    }
    private void drawDragLine() {
        if (birdBody != null) {
            Vector2 birdPosition = birdBody.getPosition();
            Vector2 dragDirection = slingshotPosition.cpy().sub(birdPosition);

            // Start drawing the drag line
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 0, 0, 1); // Red line
            shapeRenderer.line(slingshotPosition, birdPosition);
            shapeRenderer.end();

            // Optional: Log or display the drag angle
            float angle = dragDirection.angleDeg();
            System.out.println("Drag angle: " + angle); // For debugging
        }
    }

    public Stage getStage() {
        return this.stage;  // Assuming `stage` is the name of the stage in GameScreen
    }


    @Override
    public void show() {
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {


    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);





        camera.update();
        world.step(1 / 60f, 6, 2);

        debugRenderer.render(world, camera.combined);

        batch.begin();
        // Draw slingshot
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(slingshotTexture, slingshotPosition.x , slingshotPosition.y , 200, 200);


        if (birdBody != null) {
            Vector2 birdPosition = birdBody.getPosition();
            batch.draw(birdTexture, birdPosition.x + 40, birdPosition.y + 50, 150, 150);
        }




        // Draw pigs
        for (int i = 0; i < pigBodies.length; i++) {
            Body pigBody = pigBodies[i];
            if (pigBody.isActive()) {
                Vector2 pigPosition = pigBody.getPosition();
                batch.draw(pigTexture1, pigPosition.x , pigPosition.y , 200, 200); }
        } // Draw blocks
        for (int i = 0; i < blockBodies.length; i++) {
            Body blockBody = blockBodies[i];
            Vector2 blockPosition = blockBody.getPosition();
            Texture blockTexture;
            if (i % 3 == 0) {
                blockTexture = woodTexture;
            }
            else if (i % 3 == 1) {
                blockTexture = glassTexture;
            } else {
                blockTexture = steelTexture; }
            batch.draw(blockTexture, blockPosition.x - 25, blockPosition.y - 25, 200, 200);
        }


        batch.end();

        if (isDragging && birdBody != null) {
            Vector2 birdPosition = birdBody.getPosition();
            Vector2 dragDirection = slingshotPosition.cpy().sub(birdPosition);
            Vector2 initialVelocity = dragDirection.scl(5);
            drawTrajectory(birdPosition, initialVelocity);
        }

        stage.act(delta); // Update actors
        stage.draw();



        if (isDragging) {
            drawDragLine();
        }

        // Check win/loss conditions
        if (allPigsDead()) {
            System.out.println("You win!");
        } else if (birdsRemaining == 0 && !isDragging) {
            System.out.println("You lose!");
        }


    }


    private boolean allPigsDead() {
        for (Body pig : pigBodies) {
            if (pig.isActive()) {
                return false;
            }
        }
        return true;

    }

    @Override
    public void dispose() {

        birdTexture1.dispose();
        birdTexture2.dispose();
        birdTexture3.dispose();
        pigTexture1.dispose();
        pigTexture2.dispose();
        pigTexture3.dispose();
        woodTexture.dispose();
        glassTexture.dispose();
        shapeRenderer.dispose();
        steelTexture.dispose();
        slingshotTexture.dispose();
        backgroundTexture.dispose();
        pauseUpTexture.dispose();
        pauseDownTexture.dispose();
        winUpTexture.dispose();
        winDownTexture.dispose();
        loseUpTexture.dispose();
        loseDownTexture.dispose();
        debugRenderer.dispose();
        batch.dispose();
        stage.dispose();
        world.dispose();
    }

}


