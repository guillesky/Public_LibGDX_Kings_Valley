package vista2D.ui;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import controler.AbstractControler;
import controler.IView;
import facade.Facade;
import i18n.Messages;
import util.Constantes;
import util.GameConfig;

public class UI2D implements IView, ApplicationListener
{
    private String backgroundFile;
    private String skinFile;
    private String sfxClickFile;
    private String sfxFocusFile;
    private String slideSoundFile;

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
    private TextButton buttonBackFromOptions;
    private TextButton buttonBackFromCredits;
    private Label labelTitleMain;
    private Label labelTitleOption;
    private Label labelTitleCredits;
    private Label labelLanguage;
    private Label creditsLabel;
    private Table tableMain;
    private Table tableOption;
    private Table tableCredits;

    private AssetManager manager;
    private boolean loading = true;
    private String KVName = "Kings Valley Remake";
    private SliderWithLabel slMusicVolume;
    private SliderWithLabel slFXVolume;
    private SliderWithLabel slMasterVolume;
    private SliderDificult slDificultLevel;

   
    private Sound focusSound;
    private Sound clickSound;
    private Sound slideSound;

    private InputListener inputListenerSounds;
    private ChangeListener changeListenerSounds;
    private ScrollPane scrollPane;
    private boolean inCredits = false;
    private SelectBox<String> selectBox;

    public UI2D(AssetManager manager, FreeTypeFontGenerator fontGenerator, UIConfig uiConfig)
    {

	this.fontGenerator = fontGenerator;
	this.manager = manager;
	this.backgroundFile=uiConfig.getBackgroundFile();
	this.skinFile=uiConfig.getSkinFile();
	this.sfxClickFile=uiConfig.getSfxClickFile();
	this.sfxFocusFile=uiConfig.getSfxFocusFile();
	this.slideSoundFile=uiConfig.getSlideSoundFile();
	
	
	manager.load(this.backgroundFile, Texture.class);
	manager.load(this.skinFile, Skin.class);
	manager.load(this.sfxClickFile, Sound.class);
	manager.load(this.sfxFocusFile, Sound.class);
	manager.load(this.slideSoundFile, Sound.class);

	
	this.inputListenerSounds = new InputListener()
	{

	    @Override
	    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
	    {
		clickSound.play(Facade.getInstance().getGameConfig().getMasterVolume()
			* Facade.getInstance().getGameConfig().getSoundsVolume());
		return super.touchDown(event, x, y, pointer, button);
	    }

	    @Override
	    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
	    {
		if (pointer == -1)
		{
		    focusSound.play(Facade.getInstance().getGameConfig().getMasterVolume()
			    * Facade.getInstance().getGameConfig().getSoundsVolume());
		}
	    }
	};

	this.changeListenerSounds = new ChangeListener()
	{

	    @Override
	    public void changed(ChangeEvent event, Actor actor)
	    {
		slideSound.play(Facade.getInstance().getGameConfig().getMasterVolume()
			* Facade.getInstance().getGameConfig().getSoundsVolume());

	    }
	};
    }

    @Override
    public void create()
    {
	this.loadResources();
	stage = new Stage(new ScreenViewport());
	Gdx.input.setInputProcessor(stage);
	this.prepareFonts();

	this.calulateBackground(stage.getWidth(), stage.getHeight());

	stage.addActor(background); // ¡Agregarlo antes que todo lo demás!
	TextButton.TextButtonStyle buttonStyle = skin.get("default", TextButton.TextButtonStyle.class);

	buttonStyle.font = skin.getFont(this.fontSmallName);

	SelectBoxStyle selectBoxStyle = skin.get("default", SelectBoxStyle.class);
	selectBoxStyle.font = skin.getFont(this.fontSmallName);

	ListStyle listStyle = skin.get("default", ListStyle.class);
	listStyle.font = skin.getFont(this.fontSmallName);

	Label.LabelStyle labelStyleLarge = new Label.LabelStyle();
	labelStyleLarge.font = skin.getFont(this.fontLargeName);

	this.skin.add("myLabelStyleLarge", labelStyleLarge);
	Label.LabelStyle labelStyleSmall = skin.get("default", Label.LabelStyle.class);
	labelStyleSmall.font = skin.getFont(this.fontSmallName);
	this.createMainTable();
	this.createOptionTable();
	this.createCreditsTable();

	this.addSounds();

	this.stage.addActor(this.tableMain);

	Table tablaVersion = new Table();
	tablaVersion.bottom().right();
	tablaVersion.setFillParent(true);
	Label versionLabel = new Label(Constantes.VERSION, skin);

	tablaVersion.add(versionLabel).pad(10);
	stage.addActor(tablaVersion);


    }

    private void loadResources()
    {
	manager.finishLoading();
	this.backgroundText = manager.get(this.backgroundFile, Texture.class);
	this.clickSound = manager.get(this.sfxClickFile, Sound.class);
	this.focusSound = manager.get(this.sfxFocusFile, Sound.class);
	this.slideSound = manager.get(this.slideSoundFile, Sound.class);
	this.background = new Image(backgroundText);
	this.skin = manager.get(this.skinFile, Skin.class);

    }

    private void addSounds()
    {
	this.slDificultLevel.addChangeListener(changeListenerSounds);
	this.slMasterVolume.addChangeListener(changeListenerSounds);
	this.slFXVolume.addChangeListener(changeListenerSounds);
	this.slMusicVolume.addChangeListener(changeListenerSounds);

	this.selectBox.addListener(inputListenerSounds);
	this.selectBox.addListener(changeListenerSounds);

	this.buttonBackFromOptions.addListener(inputListenerSounds);
	buttonBackFromOptions.getLabel().setTouchable(Touchable.disabled);

	this.buttonBackFromCredits.addListener(inputListenerSounds);
	buttonBackFromOptions.getLabel().setTouchable(Touchable.disabled);

	this.buttonNewGame.addListener(inputListenerSounds);
	buttonNewGame.getLabel().setTouchable(Touchable.disabled);

	this.buttonOptions.addListener(inputListenerSounds);
	buttonOptions.getLabel().setTouchable(Touchable.disabled);

	this.buttonCredits.addListener(inputListenerSounds);
	buttonCredits.getLabel().setTouchable(Touchable.disabled);

	this.buttonExit.addListener(inputListenerSounds);
	buttonExit.getLabel().setTouchable(Touchable.disabled);

    }

    protected void doOptions()
    {
	this.stage.getRoot().removeActor(this.tableMain);
	this.stage.addActor(this.tableOption);

    }

    protected void doMainMenu()
    {
	this.stage.getRoot().removeActor(this.tableOption);
	this.stage.getRoot().removeActor(this.tableCredits);

	this.stage.addActor(this.tableMain);
	this.inCredits = false;

    }

    protected void doCredits()
    {
	this.stage.getRoot().removeActor(this.tableMain);
	this.stage.addActor(this.tableCredits);
	this.inCredits = true;

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
	this.doCredits();

    }

    @Override
    public void updateGameConfig(GameConfig gameConfig)
    {
	// TODO Auto-generated method stub

    }

    @Override
    public int getDificultLevel()
    {

	return this.slDificultLevel.getValue();
    }

    @Override
    public String getLanguage()
    {

	return null;
    }

    @Override
    public float getMasterVolume()
    {

	return this.slMasterVolume.getValue();
    }

    @Override
    public float getMusicVolume()
    {

	return this.slMusicVolume.getValue();
    }

    @Override
    public float getSoundsVolume()
    {

	return this.slFXVolume.getValue();

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
	if (this.inCredits)
	{
	    float velocidadScroll = 30f; // píxeles por segundo
	    float nuevoScrollY = scrollPane.getScrollY() + velocidadScroll * Gdx.graphics.getDeltaTime();

	    // Asegurarte de no pasar el límite

	    if (nuevoScrollY < scrollPane.getMaxY())
		scrollPane.setScrollY(nuevoScrollY);

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

    void setText()

    {
	this.buttonBackFromOptions.setText(Messages.GO_BACK.getValue());
	this.buttonBackFromCredits.setText(Messages.GO_BACK.getValue());
	this.buttonCredits.setText(Messages.CREDITS.getValue());
	this.buttonExit.setText(Messages.EXIT.getValue());
	this.buttonOptions.setText(Messages.OPTIONS.getValue());
	this.buttonNewGame.setText(Messages.NEW_GAME.getValue());
	this.labelTitleOption.setText(Messages.OPTIONS.getValue());
	this.labelTitleCredits.setText(Messages.CREDITS.getValue());
	this.labelProgressLoading.setText(Messages.LOAD_PROGRESS.getValue());
	this.slDificultLevel.setText(Messages.DIFICULT_LEVEL.getValue(), Messages.EASY.getValue(),
		Messages.NORMAL.getValue(), Messages.HARD.getValue());
	this.slMasterVolume.setText(Messages.MASTER_VOLUME.getValue());
	this.slMusicVolume.setText(Messages.MUSIC_VOLUME.getValue());
	this.slFXVolume.setText(Messages.FX_VOLUME.getValue());
	this.creditsLabel.setText(Facade.getInstance().getCredits());

    }

    @Override
    public void updateMasterVolume()
    {
	
    }

    @Override
    public void updateMusicVolume()
    {
	
    }

    @Override
    public void updateSoundsVolume()
    {

    }

    private void createOptionTable()
    {
	this.tableOption = new Table();
	this.tableOption.setFillParent(true);
	this.tableOption.top();

	this.slMasterVolume = new SliderWithLabel(Messages.MASTER_VOLUME.getValue(), 0, 100, 1,
		Facade.getInstance().getGameConfig().getMasterVolume() * 100, skin, AbstractControler.MASTER_VOLUME,
		controler.getChangeListener());
	this.slFXVolume = new SliderWithLabel(Messages.FX_VOLUME.getValue(), 0, 100, 1,
		Facade.getInstance().getGameConfig().getSoundsVolume() * 100, skin, AbstractControler.FX_VOLUME,
		controler.getChangeListener());
	this.slMusicVolume = new SliderWithLabel(Messages.MUSIC_VOLUME.getValue(), 0, 100, 1,
		Facade.getInstance().getGameConfig().getMusicVolume() * 100, skin, AbstractControler.MUSIC_VOLUME,
		controler.getChangeListener());

	this.labelTitleOption = new Label(Messages.OPTIONS.getValue(), skin, "myLabelStyleLarge");
	this.labelTitleOption.setAlignment(Align.center);

	this.buttonBackFromOptions = new TextButton(Messages.GO_BACK.getValue(), skin);

	this.buttonBackFromOptions.addListener(new ClickListener()
	{
	    @Override
	    public void clicked(InputEvent event, float x, float y)
	    {
		if (!selectBox.getSelected().equals(Messages.LANGUAGE_NAME.getValue()))
		    controler.changeLanguage(selectBox.getSelected());
		doMainMenu();
	    }
	});

	this.labelLanguage = new Label(Messages.LANGUAGE.getValue(), skin);

	this.selectBox = new SelectBox<String>(skin);
	Array<String> array = new Array<String>();
	Iterator<String> it = Facade.getInstance().getAllLanguages().getLanguagesName();
	while (it.hasNext())
	    array.add(it.next());

	selectBox.setItems(array);
	selectBox.setSelected(Messages.LANGUAGE_NAME.getValue());

	labelTitleOption.setAlignment(Align.center);

	tableOption.add(labelTitleOption).colspan(3).expandX().fillX().pad(20).row();

	tableOption.row();

	tableOption.add(this.slMusicVolume).expandY().pad(10).left();
	tableOption.row();
	tableOption.add(this.slFXVolume).expandY().pad(10).left();
	tableOption.row();
	tableOption.add(this.slMasterVolume).expandY().pad(10).left();
	tableOption.row();
	Table t = new Table();
	t.add(labelLanguage).width(350).left().padRight(10);
	t.add(selectBox).width(300).padRight(10);

	tableOption.add(t).expandY().pad(10).left();
	tableOption.row();
	tableOption.row();
	tableOption.add(this.buttonBackFromOptions).expandY().pad(10).left();
	tableOption.row();

    }

    private void createMainTable()
    {
	this.tableMain = new Table();
	this.tableMain.setFillParent(true);
	this.tableMain.top();
	this.labelTitleMain = new Label(this.KVName, skin, "myLabelStyleLarge");
	this.labelTitleMain.setAlignment(Align.center);

	this.slDificultLevel = new SliderDificult(Messages.DIFICULT_LEVEL.getValue(), -4, 4, 1, 0, skin,
		AbstractControler.DIFICULT_LEVEL, controler.getChangeListener(), Messages.EASY.getValue(),
		Messages.NORMAL.getValue(), Messages.HARD.getValue());

	this.buttonNewGame = new TextButton(Messages.NEW_GAME.getValue(), skin, "default");
	buttonNewGame.addListener(this.controler.getInputListener());
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

	tableMain.add(this.slDificultLevel).expandY().pad(10).left();
	tableMain.row();

	tableMain.add(this.buttonExit).expandY().pad(10).left();
	tableMain.row();

	tableMain.add(labelProgressLoading).expandX().pad(0).row();
	tableMain.add(this.progressBar).expandX().pad(0).row();

	tableMain.row();

	if (!Facade.getInstance().getGameConfig().isFinishedOneTime())
	{
	    this.slDificultLevel.setVisible(false);
	    this.slDificultLevel.setTouchable(Touchable.disabled);
	}
    }

    private void createCreditsTable()
    {
	this.tableCredits = new Table();
	this.tableCredits.setFillParent(true);
	this.tableCredits.top();

	this.labelTitleCredits = new Label(Messages.CREDITS.getValue(), skin, "myLabelStyleLarge");
	this.labelTitleCredits.setAlignment(Align.center);

	float anchoTexto = stage.getWidth() * 0.8f;
	float altoTexto = stage.getHeight() * 0.6f;
	String textoCreditos = Facade.getInstance().getCredits();

	this.creditsLabel = new Label(textoCreditos, skin);
	creditsLabel.setWrap(true); // Muy importante para que el texto se ajuste al ancho

	creditsLabel.setAlignment(Align.topLeft); // Opcional, más natural

	Table contenedorTexto = new Table();

	contenedorTexto.top(); // para que el texto quede arriba
	contenedorTexto.add(creditsLabel).width(anchoTexto - 40).pad(10).left().expandX();

	this.scrollPane = new ScrollPane(contenedorTexto, skin);
	scrollPane.setFadeScrollBars(false); // opcional: mantiene visibles las barras
	scrollPane.setScrollingDisabled(true, false); // solo scroll vertical

	this.buttonBackFromCredits = new TextButton(Messages.GO_BACK.getValue(), skin);

	this.buttonBackFromCredits.addListener(new ClickListener()
	{
	    @Override
	    public void clicked(InputEvent event, float x, float y)
	    {
		doMainMenu();
	    }
	});

	tableCredits.add(labelTitleCredits).colspan(3).expandX().fillX().pad(20).row();

	tableCredits.row();

	tableCredits.add(scrollPane).width(anchoTexto).height(altoTexto).pad(20).expand().center();
	tableCredits.row();
	tableCredits.add(this.buttonBackFromCredits).expandY().pad(10).left();
	tableCredits.row();

    }

    @Override
    public void updateLanguage()
    {
	this.setText();
    }
    public void doEnterGame() 
    {
	this.stage.getRoot().removeActor(this.tableMain);
    }

}
