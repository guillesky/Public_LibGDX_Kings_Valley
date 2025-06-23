package vista2D.ui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import controler.AbstractControler;
import controler.IView;
import facade.Facade;
import i18n.Messages;
import util.GameConfig;

public class UI2D implements IView, ApplicationListener
{
    private String backgroundFile = "ui/background.jpg";
    private String skinFile = "skin/golden-ui-skin.json";
    private String musicUIName = "music/WombatNoisesAudio - The Legend of Narmer.mp3";
    private Texture backgroundText;
    private Image background;
    private Skin skin;
    private Stage stage;
    private Label labelProgressLoading;
    protected BitmapFont fontLarge = new BitmapFont();
    private BitmapFont fontSmall = new BitmapFont();
    private FreeTypeFontGenerator fontGenerator;
    private final String fontSmallName = "fontSmall";
    private final String fontLargeName = "fontLarge";
    private Controler2D controler;
    private TextButton buttonNewGame;
    private TextButton buttonExit;
    private TextButton buttonCredits;
    private TextButton buttonOptions;
    private ProgressBar progressBar;
    private TextButton buttonBack;
    private Label labelTitleMain;
    private Label labelTitleOption;

    private Table tableMain;
    private Table tableOption;
    private AssetManager manager;
    private boolean loading = true;
    private String KVName = "Kings Valley Remake";
    private SliderWithLabel slMusicVolume;
    private SliderWithLabel slFXVolume;
    private SliderWithLabel slMasterVolume;
    private SliderWithLabel slDificultLevel;
    private Music musica;

    public UI2D(AssetManager manager, FreeTypeFontGenerator fontGenerator)
    {
	this.fontGenerator = fontGenerator;
	manager.load(this.backgroundFile, Texture.class);
	manager.load(this.musicUIName, Music.class);
	manager.load(this.skinFile, Skin.class);
	manager.finishLoading();
	this.backgroundText = manager.get(this.backgroundFile, Texture.class);
	this.background = new Image(backgroundText);
	this.skin = manager.get(this.skinFile, Skin.class);
	this.manager = manager;
    }

    @Override
    public void create()
    {
	stage = new Stage(new ScreenViewport());
	Gdx.input.setInputProcessor(stage);
	this.prepareFonts();
	// this.background.setFillParent(true); // Se adapta al tamaño del stage
	this.calulateBackground(stage.getWidth(), stage.getHeight());

	stage.addActor(background); // ¡Agregarlo antes que todo lo demás!
	TextButton.TextButtonStyle buttonStyle = skin.get("default", TextButton.TextButtonStyle.class);

	buttonStyle.font = skin.getFont(this.fontSmallName);

	Label.LabelStyle labelStyleLarge = new Label.LabelStyle();
	labelStyleLarge.font = skin.getFont(this.fontLargeName);

	this.skin.add("myLabelStyleLarge", labelStyleLarge);
	Label.LabelStyle labelStyleSmall = skin.get("default", Label.LabelStyle.class);
	labelStyleSmall.font = skin.getFont(this.fontSmallName);

	this.tableMain = new Table();
	this.tableMain.setFillParent(true);
	this.tableMain.top();

	this.tableOption = new Table();
	this.tableOption.setFillParent(true);
	this.tableOption.top();

	this.slDificultLevel = new SliderDificult(Messages.DIFICULT_LEVEL.getValue(), -4, 4, 1, 0, skin,
		AbstractControler.DIFICULT_LEVEL, controler.getChangeListener(), "Facil", "Normal", "Dificil");
	this.slMasterVolume = new SliderWithLabel(Messages.MASTER_VOLUME.getValue(), 0, 100, 1, Facade.getInstance().getGameConfig().getMasterVolume()*100, skin,
		AbstractControler.MASTER_VOLUME, controler.getChangeListener());
	this.slFXVolume = new SliderWithLabel(Messages.FX_VOLUME.getValue(), 0, 100, 1, Facade.getInstance().getGameConfig().getSoundsVolume()*100, skin,
		AbstractControler.FX_VOLUME, controler.getChangeListener());
	this.slMusicVolume = new SliderWithLabel(Messages.MUSIC_VOLUME.getValue(), 0, 100, 1, Facade.getInstance().getGameConfig().getMusicVolume()*100, skin,
		AbstractControler.MUSIC_VOLUME, controler.getChangeListener());

	this.stage.addActor(this.tableMain);

	this.labelTitleMain = new Label(this.KVName, skin, "myLabelStyleLarge");
	this.labelTitleMain.setAlignment(Align.center);

	this.labelTitleOption = new Label(Messages.OPTIONS.getValue(), skin, "myLabelStyleLarge");
	this.labelTitleOption.setAlignment(Align.center);

	labelTitleOption.setAlignment(Align.center);

	tableOption.add(labelTitleOption).colspan(3).expandX().fillX().pad(20).row();

	tableOption.row();

	/*
	 * tableOption.add(this.slMasterVolume).expandY().pad(10).left();
	 * tableOption.row();
	 * 
	 * tableOption.add(this.slMusicVolume).expandY().pad(10).left();
	 * tableOption.row();
	 * 
	 * tableOption.add(this.slSoundVolume).expandY().pad(10).left();
	 * tableOption.row();
	 * 
	 * tableOption.add(this.slDificultLevel).expandY().pad(10).left();
	 * tableOption.row();
	 */

	tableOption.add(this.slMusicVolume).expandY().pad(10).left();
	tableOption.row();
	tableOption.add(this.slFXVolume).expandY().pad(10).left();
	tableOption.row();
	tableOption.add(this.slMasterVolume).expandY().pad(10).left();
	tableOption.row();
	tableOption.add(this.slDificultLevel).expandY().pad(10).left();
	tableOption.row();
	tableOption.add(new TextButton(Messages.NEW_GAME.getValue(), skin, "default")).expandY().pad(10).left();
	tableOption.row();

	this.buttonNewGame = new TextButton(Messages.NEW_GAME.getValue(), skin, "default");
	buttonNewGame.addListener(this.controler.getInputListener());
	this.buttonNewGame.setTouchable(Touchable.disabled);
	this.buttonNewGame.setUserObject(AbstractControler.NEW_GAME);

	this.buttonOptions = new TextButton(Messages.OPTIONS.getValue(), skin);
	this.buttonOptions.addListener(new ClickListener()
	{
	    @Override
	    public void clicked(InputEvent event, float x, float y)
	    {
		doOptions();
	    }
	});
	this.buttonOptions.setUserObject(AbstractControler.OPTIONS);

	this.buttonCredits = new TextButton(Messages.CREDITS.getValue(), skin);
	this.buttonCredits.addListener(this.controler.getInputListener());
	this.buttonCredits.setUserObject(AbstractControler.CREDITS);

	this.buttonExit = new TextButton(Messages.EXIT.getValue(), skin);
	this.buttonExit.addListener(this.controler.getInputListener());
	this.buttonExit.setUserObject(AbstractControler.EXIT);

	labelProgressLoading = new Label(Messages.LOAD_PROGRESS.getValue(), skin, "default");

	// Fila del título, centrado arriba
	labelTitleMain.setAlignment(Align.center);

	tableMain.add(labelTitleMain).colspan(3).expandX().fillX().pad(20).row();

	tableMain.row();

	this.progressBar = new ProgressBar(0, 100, 1, false, skin);

	tableMain.add(this.buttonNewGame).expandY().pad(10).left();
	tableMain.row();

	tableMain.add(this.buttonOptions).expandY().pad(10).left();
	tableMain.row();

	tableMain.add(this.buttonCredits).expandY().pad(10).left();
	tableMain.row();

	tableMain.add(this.buttonExit).expandY().pad(10).left();
	tableMain.row();

	tableMain.add(labelProgressLoading).expandX().pad(0).row();
	tableMain.add(this.progressBar).expandX().pad(0).row();

	tableMain.row();

	this.musica = manager.get(this.musicUIName, Music.class);
	musica.setLooping(true);
	musica.setVolume(Facade.getInstance().getGameConfig().getMasterVolume()*Facade.getInstance().getGameConfig().getMusicVolume());
	musica.play();
	

	
	  
	
	
	
    }

    protected void doOptions()
    {
	this.stage.getRoot().removeActor(this.tableMain);
	this.stage.addActor(this.tableOption);

    }

    private void prepareFonts()
    {
	int fontSize = (int) (Gdx.graphics.getHeight() * (1.f / 12.f));

	FreeTypeFontGenerator.FreeTypeFontParameter parameterLarge = new FreeTypeFontGenerator.FreeTypeFontParameter();
	parameterLarge.size = fontSize; // Tamaño de fuente
	parameterLarge.color = Color.GOLD;
	parameterLarge.borderColor = Color.BLACK;
	fontLarge = fontGenerator.generateFont(parameterLarge);

	FreeTypeFontGenerator.FreeTypeFontParameter parameterSmall = new FreeTypeFontGenerator.FreeTypeFontParameter();
	parameterSmall.size = (int) (fontSize * .4); // Tamaño de fuente
	parameterSmall.color = Color.WHITE;
	parameterSmall.borderColor = Color.BLACK;
	parameterSmall.borderWidth = 1;

	fontSmall = fontGenerator.generateFont(parameterSmall);

	if (skin.has(this.fontSmallName, BitmapFont.class))
	{
	    skin.getFont(this.fontSmallName).dispose();
	    skin.remove(this.fontSmallName, BitmapFont.class);
	}
	skin.add(this.fontSmallName, this.fontSmallName);

	// Hay que actualizar los estilos

	skin.add(this.fontSmallName, fontSmall);
	skin.add(this.fontLargeName, fontLarge);

    }

    @Override
    public void updateCredits(String credits)
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void updateGameConfig(GameConfig gameConfig)
    {
	// TODO Auto-generated method stub

    }

    @Override
    public int getDificultLevel()
    {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public String getLanguage()
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public float getMasterVolume()
    {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public float getMusicVolume()
    {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public float getSoundsVolume()
    {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public void setControler(AbstractControler controler)
    {
	this.controler = (Controler2D) controler;
    }

    public void dispose()
    {

    }

    @Override
    public void render()
    {
	Gdx.gl.glClearColor(0, 0, 0, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	if (loading)
	    if (this.manager.update())
	    {
		this.buttonNewGame.setTouchable(Touchable.enabled);
		this.labelProgressLoading.setVisible(false);
		this.progressBar.setVisible(false);
		this.loading = false;
	    } else
	    {
		this.progressBar.setValue(this.manager.getProgress());

	    }
	stage.act();
	stage.draw();

    }

    @Override
    public void resize(int width, int height)
    {
	stage.getViewport().update(width, height, true);
	this.calulateBackground(width, height);
    }

    @Override
    public void pause()
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void resume()
    {
	// TODO Auto-generated method stub

    }

    private void calulateBackground(float width, float height)
    {

	float imageRatio = background.getWidth() / (float) background.getHeight();
	float stageRatio = width / stage.getHeight();

	if (imageRatio < stageRatio)
	{
	    background.setWidth(width);
	    background.setHeight(width / imageRatio);
	} else
	{
	    background.setHeight(height);
	    background.setWidth(height * imageRatio);
	}

	background.setPosition((width - background.getWidth()) / 2f, (height - background.getHeight()) / 2f);

    }

    @Override
    public void updateLoadProgress(float value)
    {
	this.progressBar.setValue(value);

    }

    /*
     * 
     * Slider sl = new Slider(0, 9, 1, false, skin); SelectBox<String> l = new
     * SelectBox<String>(skin); l.setItems("holas y chauses", "jamones", "quesos");
     * 
     * // table.add(l).size(400, 100).expandY().pad(10).left();
     * table.add(l).expandY().pad(10).left(); table.row();
     * table.add(sl).expandY().pad(10).left();
     * 
     * table.row();
     * 
     */
    void setText()

    {
	this.buttonBack.setText(Messages.GO_BACK.getValue());
	this.buttonCredits.setText(Messages.CREDITS.getValue());
	this.buttonExit.setText(Messages.EXIT.getValue());
	this.buttonNewGame.setText(Messages.OPTIONS.getValue());
	this.buttonNewGame.setText(Messages.NEW_GAME.getValue());
	this.labelTitleOption.setText(Messages.OPTIONS.getValue());

	this.labelProgressLoading.setText(Messages.LOAD_PROGRESS.getValue());
	this.slDificultLevel.setText(Messages.DIFICULT_LEVEL.getValue());
	this.slMasterVolume.setText(Messages.MASTER_VOLUME.getValue());
	this.slMusicVolume.setText(Messages.MUSIC_VOLUME.getValue());
	this.slFXVolume.setText(Messages.FX_VOLUME.getValue());

    }

    @Override
    public void updateMasterVolume()
    {
	musica.setVolume(Facade.getInstance().getGameConfig().getMasterVolume()*Facade.getInstance().getGameConfig().getMusicVolume());


    }

    @Override
    public void updateMusicVolume()
    {
	musica.setVolume(Facade.getInstance().getGameConfig().getMasterVolume()*Facade.getInstance().getGameConfig().getMusicVolume());

    }

    @Override
    public void updateSoundsVolume()
    {
	// TODO Auto-generated method stub

    }
}
