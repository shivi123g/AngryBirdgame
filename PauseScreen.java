package io.github.bird.game.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.bird.game.Main;
import io.github.bird.game.GameState;

import java.util.ArrayList;

public class PauseScreen implements Screen {

    private Main game;
    private Stage stage;
    private GameScreen gameScreen;
    private TextButton exitButton;
    private TextButton resumeButton;
    private Texture backgroundTexture;
    private Texture resumeUpTexture;
    private Texture resumeDownTexture;
    private Texture exitUpTexture; // Texture for exit button
    private TextButton settingsButton;
    private Table settingsPanel;



    public PauseScreen(Main game, GameScreen gameScreen, int level) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        if (level == 1) {
            backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        } else if (level == 2) {
            backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        } else if (level == 3) {
            backgroundTexture = new Texture(Gdx.files.internal("background.png"));
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



        resumeUpTexture = new Texture(Gdx.files.internal("resumeup.png"));
        resumeDownTexture = new Texture(Gdx.files.internal("resumedown.png"));

        exitUpTexture = new Texture(Gdx.files.internal("exitup.png")); // Replace with your quit button texture path



        // Create a resume button
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(resumeUpTexture));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(resumeDownTexture));

        buttonStyle.font = font;
        buttonStyle.fontColor = Color.RED;

        resumeButton = new TextButton("", buttonStyle);
        resumeButton.setPosition(Gdx.graphics.getWidth() / 2f - resumeButton.getWidth()/ 2f, Gdx.graphics.getHeight() / 2f);
        stage.addActor(resumeButton);

        // Add button click listener
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(gameScreen);
                Gdx.input.setInputProcessor(gameScreen.getStage());
            }
        });


        TextButton.TextButtonStyle exitButtonStyle = new TextButton.TextButtonStyle();
        exitButtonStyle.up = new TextureRegionDrawable(new TextureRegion(exitUpTexture));
        exitButtonStyle.font = font;
        exitButtonStyle.fontColor = Color.RED;

        exitButton = new TextButton("", exitButtonStyle); // Add text for clarity
        exitButton.setPosition(Gdx.graphics.getWidth() / 2f - exitButton.getWidth()/ 2f, Gdx.graphics.getHeight() / 2f - 100); // Adjust position
        stage.addActor(exitButton);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameState currentState = GameScreen.collectGameState();

                // Save the state to a file
                GameScreen.saveGameState(currentState, "gamestate.dat");
                game.setScreen(new PlayScreen(game));

            }
        });





    }

    @Override
    public void show() {
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().end();


        stage.act(delta);
        stage.draw();
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
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        resumeUpTexture.dispose();
        resumeDownTexture.dispose();
        exitUpTexture.dispose(); // Dispose quit button textures

    }
}
