package io.github.bird.game.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.bird.game.Main;
import com.badlogic.gdx.graphics.Color;

public class PlayScreen  implements Screen {

    private Main game;
    private Stage stage;
    private TextButton startButton;
    private Texture backgroundTexture;
    private TextButton settingsButton;
    private Table settingsPanel;


    public PlayScreen(Main game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load the background texture (image file path)
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));


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

        // Create a start button
        TextButton.TextButtonStyle ButtonStyle = new TextButton.TextButtonStyle();
        ButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("playup.png"))));

        ButtonStyle.font = font;  // Add the font to the style
        ButtonStyle.fontColor = Color.RED;

        startButton = new TextButton("", ButtonStyle);
        startButton.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        stage.addActor(startButton);


        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectionScreen(game));
            }
        });



    }
    public Stage getStage() {
        return this.stage;  // Assuming `stage` is the name of the stage in GameScreen
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the background image first
        game.getBatch().begin();
        game.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.getBatch().end();

        // Render the stage (UI elements like the button)
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
        backgroundTexture.dispose();  // Dispose of the texture
    }
}
