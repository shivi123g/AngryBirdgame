package io.github.bird.game.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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

public class LevelSelectionScreen implements Screen {
    private Main game;
    private Stage stage;
    private TextButton settingsButton;
    private Table settingsPanel;
    private Texture backgroundTexture;

    public LevelSelectionScreen(Main game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

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



        // Button style for Level 1
        TextButton.TextButtonStyle level1ButtonStyle = new TextButton.TextButtonStyle();
        level1ButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("level1up.png"))));
        level1ButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("level1down.png"))));
        level1ButtonStyle.font = font;  // Add the font to the style
        level1ButtonStyle.fontColor = Color.RED;

        // Button style for Level 2
        TextButton.TextButtonStyle level2ButtonStyle = new TextButton.TextButtonStyle();
        level2ButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("level2up.png"))));
        level2ButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("level2down.png"))));
        level2ButtonStyle.font = font;  // Add the font to the style
        level2ButtonStyle.fontColor = Color.RED;

        // Button style for Level 3
        TextButton.TextButtonStyle level3ButtonStyle = new TextButton.TextButtonStyle();
        level3ButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("level3up.png"))));
        level3ButtonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("level3down.png"))));
        level3ButtonStyle.font = font;  // Add the font to the style
        level3ButtonStyle.fontColor = Color.RED;

        // Create the buttons
        TextButton level1Button = new TextButton("", level1ButtonStyle);
        TextButton level2Button = new TextButton("", level2ButtonStyle);
        TextButton level3Button = new TextButton("", level3ButtonStyle);

        // Position level buttons
        level1Button.setPosition(Gdx.graphics.getWidth() / 2f - level1Button.getWidth() / 2f, Gdx.graphics.getHeight() / 2f + 60);
        level2Button.setPosition(Gdx.graphics.getWidth() / 2f - level2Button.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        level3Button.setPosition(Gdx.graphics.getWidth() / 2f - level3Button.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - 60);

        // Add buttons to the stage
        stage.addActor(level1Button);
        stage.addActor(level2Button);
        stage.addActor(level3Button);

        // Add listeners for each button
        level1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game,1));
            }
        });

        level2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game,2));
            }
        });

        level3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game,3));
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
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
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}
}
