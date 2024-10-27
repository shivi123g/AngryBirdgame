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

public class GameScreen implements Screen {

    private Main game;
    private Stage stage;
    private int level;
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
        this.level = level;
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        if (level == 1) {
            backgroundTexture = new Texture(Gdx.files.internal("gameback.png"));
        } else if (level == 2) {
            backgroundTexture = new Texture(Gdx.files.internal("level2back.png"));
        } else if (level == 3) {
            backgroundTexture = new Texture(Gdx.files.internal("level3back.png"));
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

        TextButton.TextButtonStyle winButtonStyle = new TextButton.TextButtonStyle();
        winButtonStyle.up = new TextureRegionDrawable(new TextureRegion(winUpTexture));
        winButtonStyle.down = new TextureRegionDrawable(new TextureRegion(winDownTexture));
        winButtonStyle.font = font;  // Add the font to the style
        winButtonStyle.fontColor = Color.RED;

        winButton = new TextButton("", winButtonStyle);
        winButton.setPosition(Gdx.graphics.getWidth() / 2f - 400, Gdx.graphics.getHeight() / 2f + 400);
        winButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new WinningScreen(game));
            }
        });
        stage.addActor(winButton);

        TextButton.TextButtonStyle loseButtonStyle = new TextButton.TextButtonStyle();
        loseButtonStyle.up = new TextureRegionDrawable(new TextureRegion(loseUpTexture));
        loseButtonStyle.down = new TextureRegionDrawable(new TextureRegion(loseDownTexture));
        loseButtonStyle.font = font;
        loseButtonStyle.fontColor = Color.RED;

        loseButton = new TextButton("", loseButtonStyle);
        loseButton.setPosition(Gdx.graphics.getWidth() / 2f -400, Gdx.graphics.getHeight() / 2f + 300);
        loseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LosingScreen(game));
            }
        });
        stage.addActor(loseButton);

    }
    public Stage getStage() {
        return this.stage;  // Assuming `stage` is the name of the stage in GameScreen
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.8f, 1);
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
        pauseUpTexture.dispose();
        pauseDownTexture.dispose();
        winUpTexture.dispose();
        winDownTexture.dispose();
        loseUpTexture.dispose();
        loseDownTexture.dispose();
    }
}

