package vista2D.ui;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import controler.AbstractControler;
import controler.IView;
import i18n.Messages;
import util.Constants;

/**
 * Clase que representa la ventana inicial del juego
 * 
 * @author Guillermo Lazzurri
 */
public class UI2D implements IView, ApplicationListener
{

    private Texture backgroundText;
    private Image backgroundImage;
    private Skin skin;
    private Stage stage;
    private BitmapFont fontLarge = new BitmapFont();
    private BitmapFont fontSmall = new BitmapFont();
    private FreeTypeFontGenerator fontGenerator;
    private final String fontSmallName = "fontSmall";
    private final String fontLargeName = "fontLarge";
    private Controler2D controler;
    private TextButton buttonNewGame;
    private TextButton buttonExit;
    private TextButton buttonCredits;
    private TextButton buttonOptions;
    private TextButton buttonRetry;
    private TextButton buttonExitInGame;
    private TextButton buttonOptionsInGame;
    private TextButton buttonMainMenu;
    private TextButton buttonMap;
    private TextButton buttonBackFromOptions;
    private TextButton buttonBackFromCredits;
    private TextButton buttonBackFromMap;
    private Label labelTitleMain;
    private Label labelTitleOption;
    private Label labelTitleCredits;
    private Label labelLanguage;
    private Label creditsLabel;
    private Table tableMainInUi;
    private Table tableMainActual;
    private Table tableOption;
    private Table tableCredits;
    private Table tableInGame;
    private Table tableVersion;
    private AssetManager manager;
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
    private SelectBox<String> selectBoxLanguage;
    private SelectBox<String> selectBoxGameType;
    private SelectBox<String> selectBoxEpisode;
    private UIConfig uiConfig;
    private Cursor cursor;
    private Table tableMap;
    private Label labelTitleMap;
    private UIAbstractMap uiMap;
    private boolean fromInGameTable;
    private Dialog confirmRetryDialog;
    private Dialog confirmExitDialog;
    private Dialog confirmGoMainMenuDialog;
    private Drawable backgroundBlackTransparent;
    private Array<String> arrayGameTypeMessage = new Array<String>();
    private Array<String> arrayEpisodesMessages = new Array<String>();
    private TextTooltip tooltip;

    /**
     * Constructor de clase
     * 
     * @param manager  manager de carga
     * @param uiConfig Objeto UIConfig con la configuracion de la ventana
     */
    public UI2D(AssetManager manager, UIConfig uiConfig)
    {
	this.uiConfig = uiConfig;

	this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PAPYRUS.TTF"));
	;
	this.manager = manager;

	manager.load(this.uiConfig.getBackgroundFile(), Texture.class);
	manager.load(this.uiConfig.getClassicMapFile(), Texture.class);
	manager.load(this.uiConfig.getExtendedMapFile(), Texture.class);
	
	manager.load(this.uiConfig.getPyramidActualFile(), Texture.class);
	manager.load(this.uiConfig.getPyramidCompletedFile(), Texture.class);

	manager.load(this.uiConfig.getSkinFile(), Skin.class);
	manager.load(this.uiConfig.getSfxClickFile(), Sound.class);
	manager.load(this.uiConfig.getSfxFocusFile(), Sound.class);
	manager.load(this.uiConfig.getSlideSoundFile(), Sound.class);

	this.inputListenerSounds = new InputListener()
	{

	    @Override
	    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
	    {
		clickSound.play(controler.getMasterVolume() * controler.getSoundsVolume());
		return super.touchDown(event, x, y, pointer, button);
	    }

	    @Override
	    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
	    {
		if (pointer == -1)
		{
		    focusSound.play(controler.getMasterVolume() * controler.getSoundsVolume());
		}
	    }
	};

	this.changeListenerSounds = new ChangeListener()
	{

	    @Override
	    public void changed(ChangeEvent event, Actor actor)
	    {
		slideSound.play(controler.getMasterVolume() * controler.getSoundsVolume());

	    }
	};

    }

    /**
     * Llamado al crear la Ventana.
     */
    @Override
    public void create()
    {

	this.getResources();
	this.prepareFonts();

	this.calulateBackground(backgroundImage, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	TextButton.TextButtonStyle buttonStyle = skin.get("default", TextButton.TextButtonStyle.class);

	buttonStyle.font = skin.getFont(this.fontSmallName);

	SelectBoxStyle selectBoxStyle = skin.get("default", SelectBoxStyle.class);
	selectBoxStyle.font = skin.getFont(this.fontSmallName);

	ListStyle listStyle = skin.get("default", ListStyle.class);
	listStyle.font = skin.getFont(this.fontSmallName);

	Label.LabelStyle labelStyleLarge = new Label.LabelStyle();
	labelStyleLarge.font = skin.getFont(this.fontLargeName);
	this.backgroundBlackTransparent = skin.newDrawable("black-transparent");

	this.skin.add("myLabelStyleLarge", labelStyleLarge);
	Label.LabelStyle labelStyleSmall = skin.get("default", Label.LabelStyle.class);
	labelStyleSmall.font = skin.getFont(this.fontSmallName);
	stage = new Stage(new ScreenViewport());
	Gdx.input.setInputProcessor(stage);

	this.createDialogs();
	this.createMainTable();
	this.createOptionTable();

	this.createInGameTable();
	this.createTableVersion();

	this.createCreditsTable();
	this.createMapTable();
	this.addSounds();

    }

    /**
     * Llamado internamente por create. Contruye la talba con la version actual del
     * juego
     */
    private void createTableVersion()
    {
	this.tableVersion = new Table();
	tableVersion.bottom().right();
	tableVersion.setFillParent(true);
	Label versionLabel = new Label(Constants.VERSION, skin);
	tableVersion.add(versionLabel).pad(10);
    }

    /**
     * Llamado internamente por create. Obtiene los recursos del AssetManager
     */
    private void getResources()
    {
	manager.finishLoading();

	this.backgroundText = manager.get(this.uiConfig.getBackgroundFile(), Texture.class);

	this.clickSound = manager.get(this.uiConfig.getSfxClickFile(), Sound.class);
	this.focusSound = manager.get(this.uiConfig.getSfxFocusFile(), Sound.class);
	this.slideSound = manager.get(this.uiConfig.getSlideSoundFile(), Sound.class);
	this.backgroundImage = new Image(backgroundText);

	this.skin = manager.get(this.uiConfig.getSkinFile(), Skin.class);

	Pixmap pixmap = new Pixmap(Gdx.files.internal(this.uiConfig.getCursorFile()));
	this.cursor = Gdx.graphics.newCursor(pixmap, 0, 0);
	Gdx.graphics.setCursor(cursor);

    }

    /**
     * Llamado internamente por create. Agrega los sonidos a los listeners
     */
    private void addSounds()
    {
	this.slDificultLevel.addChangeListener(changeListenerSounds);
	this.slMasterVolume.addChangeListener(changeListenerSounds);
	this.slFXVolume.addChangeListener(changeListenerSounds);
	this.slMusicVolume.addChangeListener(changeListenerSounds);

	this.selectBoxLanguage.addListener(inputListenerSounds);
	this.selectBoxLanguage.addListener(changeListenerSounds);

	this.selectBoxEpisode.addListener(inputListenerSounds);
	this.selectBoxEpisode.addListener(changeListenerSounds);

	this.selectBoxGameType.addListener(inputListenerSounds);
	this.selectBoxGameType.addListener(changeListenerSounds);

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

	this.buttonExitInGame.addListener(inputListenerSounds);
	buttonExitInGame.getLabel().setTouchable(Touchable.disabled);

	this.buttonRetry.addListener(inputListenerSounds);
	buttonRetry.getLabel().setTouchable(Touchable.disabled);

	this.buttonOptionsInGame.addListener(inputListenerSounds);
	buttonOptionsInGame.getLabel().setTouchable(Touchable.disabled);

	this.buttonMainMenu.addListener(inputListenerSounds);
	buttonMainMenu.getLabel().setTouchable(Touchable.disabled);

    }

    /**
     * Llamado al entrar a la Ventana
     * 
     */
    public void doEnterUi()
    {

	stage.addActor(backgroundImage);
	this.tableMainActual = this.tableMainInUi;
	this.stage.addActor(this.tableMainInUi);
	stage.addActor(tableVersion);
	Gdx.input.setCursorCatched(false);
	if (this.controler.isFinishedOneTime())
	{
	    this.slDificultLevel.setVisible(true);
	    this.slDificultLevel.setTouchable(Touchable.enabled);
	}

    }

    /**
     * Llamado al cambiar a la ventana de opciones
     */
    protected void doOptions()
    {
	this.stage.getRoot().removeActor(this.tableMainActual);
	this.tableMainActual = this.tableOption;
	this.stage.addActor(this.tableOption);

    }

    /**
     * Llamado al volver a la Ventana desde opciones o creditos
     */
    protected void doMainMenu()
    {
	this.stage.getRoot().removeActor(this.tableOption);
	this.stage.getRoot().removeActor(this.tableCredits);
	this.tableMainActual = this.tableMainInUi;
	this.stage.addActor(this.tableMainActual);
	this.inCredits = false;

    }

    /**
     * Llamado al cambiar a la ventana de creditos
     */
    private void doCredits()
    {
	this.stage.getRoot().removeActor(this.tableMainActual);
	this.stage.addActor(this.tableCredits);
	this.inCredits = true;
    }

    /**
     * Llamado internamente por create. Prepara las fuentes de la ventana
     */
    private void prepareFonts()
    {
	int fontSize = (int) (Gdx.graphics.getHeight() * (1.f / 12.f));

	FreeTypeFontGenerator.FreeTypeFontParameter parameterLarge = new FreeTypeFontGenerator.FreeTypeFontParameter();
	parameterLarge.size = fontSize;
	parameterLarge.color = Color.GOLD;
	parameterLarge.borderColor = Color.BLACK;
	fontLarge = fontGenerator.generateFont(parameterLarge);

	FreeTypeFontGenerator.FreeTypeFontParameter parameterSmall = new FreeTypeFontGenerator.FreeTypeFontParameter();
	parameterSmall.size = (int) (fontSize * .4);
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
    public int getDificultLevel()
    {

	return this.slDificultLevel.getValue();
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

    /**
     * Libera los recursos creados
     */
    public void dispose()
    {
	this.cursor.dispose();
	if (this.uiMap != null)
	    this.uiMap.dispose();
	this.fontGenerator.dispose();

    }

    @Override
    public void render()
    {
	Gdx.gl.glClearColor(0, 0, 0, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	if (this.inCredits)
	{
	    float velocidadScroll = 30f;
	    float nuevoScrollY = scrollPane.getScrollY() + velocidadScroll * Gdx.graphics.getDeltaTime();

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
	this.calulateBackground(backgroundImage, width, height);
	if (this.uiMap != null)
	    this.uiMap.resize(width, height);

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    /**
     * Recalcula la imagen de fondo de acuerdo al tamano de la ventana
     * 
     * @param image  Imagen de fondo
     * @param width  Ancho de la ventana
     * @param height alto de la ventana
     */
    private void calulateBackground(Image image, float width, float height)
    {

	float imageRatio = image.getWidth() / (float) image.getHeight();
	float stageRatio = width / height;

	if (imageRatio < stageRatio)
	{
	    image.setWidth(width);
	    image.setHeight(width / imageRatio);
	} else
	{
	    image.setHeight(height);
	    image.setWidth(height * imageRatio);
	}

	image.setPosition((width - image.getWidth()) / 2f, (height - image.getHeight()) / 2f);

    }

    /**
     * Setea los textos de la ventana. Llamado internamente por updateLanguage
     */
    private void setText()

    {
	// table main
	this.buttonCredits.setText(Messages.CREDITS.getValue());
	this.buttonExit.setText(Messages.EXIT.getValue());
	this.buttonOptions.setText(Messages.OPTIONS.getValue());
	this.buttonNewGame.setText(Messages.NEW_GAME.getValue());
	this.slDificultLevel.setText(Messages.DIFICULT_LEVEL.getValue(), Messages.EASY.getValue(),
		Messages.NORMAL.getValue(), Messages.HARD.getValue());

	// Table Options

	this.buttonBackFromOptions.setText(Messages.GO_BACK.getValue());
	this.labelTitleOption.setText(Messages.OPTIONS.getValue());
	this.slMasterVolume.setText(Messages.MASTER_VOLUME.getValue());
	this.slMusicVolume.setText(Messages.MUSIC_VOLUME.getValue());
	this.slFXVolume.setText(Messages.FX_VOLUME.getValue());

	// Table Credits
	this.buttonBackFromCredits.setText(Messages.GO_BACK.getValue());
	this.labelTitleCredits.setText(Messages.CREDITS.getValue());
	this.creditsLabel.setText(this.controler.getCredits());

	// Table InGame
	this.buttonExitInGame.setText(Messages.EXIT.getValue());
	this.buttonOptionsInGame.setText(Messages.OPTIONS.getValue());
	this.buttonRetry.setText(Messages.RETRY.getValue());
	this.buttonMainMenu.setText(Messages.MAIN_MENU.getValue());
	this.buttonMap.setText(Messages.MAP.getValue());

	// Table inMap
	this.labelTitleMap.setText(Messages.MAP.getValue());
	this.buttonBackFromMap.setText(Messages.GO_BACK.getValue());

	this.setTextOfSelectBox();

	this.createDialogs();

    }

    private void setTextOfSelectBox()
    {
	TextTooltip.TextTooltipStyle tooltipStyle = new TextTooltip.TextTooltipStyle();
	tooltipStyle.label = skin.get("default", Label.LabelStyle.class);

	tooltipStyle.label.font = skin.getFont(this.fontSmallName);
	tooltipStyle.background = this.backgroundBlackTransparent;

	this.arrayEpisodesMessages.clear();
	int limit=4;
	for (int i = 1; i <= limit; i++)
	    this.arrayEpisodesMessages.add(Messages.EPISODE.getValue() + i);
	this.arrayGameTypeMessage.clear();
	this.arrayGameTypeMessage.add(Messages.CLASSIC_VERSION.getValue());
	this.arrayGameTypeMessage.add(Messages.EXTENDED_VERSION.getValue());

	if (this.selectBoxEpisode != null && this.selectBoxGameType != null)
	{

	    this.selectBoxGameType.setItems(arrayGameTypeMessage);
	    this.selectBoxGameType.setSelected(arrayGameTypeMessage.first());

	    this.selectBoxEpisode.setItems(arrayEpisodesMessages);
	    this.selectBoxEpisode.setSelected(arrayEpisodesMessages.first());
	    this.selectBoxEpisode.setVisible(false);
	    if (this.tooltip != null)
		this.selectBoxEpisode.removeListener(this.tooltip);
	    this.tooltip = new TextTooltip(Messages.ENABLED_EPISODE_MESSAGE.getValue(), tooltipStyle);
	    this.selectBoxEpisode.addListener(tooltip);
	}

    }

    /**
     * Llamado intermente por create. Crea la Ventana de opciones
     */
    private void createOptionTable()
    {
	this.tableOption = new Table();
	this.tableOption.setFillParent(true);
	this.tableOption.top();

	this.slMasterVolume = new SliderWithLabel(Messages.MASTER_VOLUME.getValue(), 0, 100, 1,
		this.controler.getMasterVolume() * 100, skin, AbstractControler.MASTER_VOLUME,
		controler.getChangeListener());
	this.slFXVolume = new SliderWithLabel(Messages.FX_VOLUME.getValue(), 0, 100, 1,
		this.controler.getSoundsVolume() * 100, skin, AbstractControler.FX_VOLUME,
		controler.getChangeListener());
	this.slMusicVolume = new SliderWithLabel(Messages.MUSIC_VOLUME.getValue(), 0, 100, 1,
		this.controler.getMusicVolume() * 100, skin, AbstractControler.MUSIC_VOLUME,
		controler.getChangeListener());

	this.labelTitleOption = new Label(Messages.OPTIONS.getValue(), skin, "myLabelStyleLarge");
	this.labelTitleOption.setAlignment(Align.center);

	this.buttonBackFromOptions = new TextButton(Messages.GO_BACK.getValue(), skin);

	this.buttonBackFromOptions.addListener(new ClickListener()
	{
	    @Override
	    public void clicked(InputEvent event, float x, float y)
	    {
		if (!selectBoxLanguage.getSelected().equals(Messages.LANGUAGE_NAME.getValue()))
		    controler.changeLanguage(selectBoxLanguage.getSelected());

		if (UI2D.this.fromInGameTable)
		    doUiInGame();
		else
		    doMainMenu();
	    }
	});

	this.labelLanguage = new Label(Messages.LANGUAGE.getValue(), skin);

	Array<String> array = new Array<String>();
	Iterator<String> it = this.controler.getLanguagesName();
	while (it.hasNext())
	    array.add(it.next());

	this.selectBoxLanguage = new SelectBox<String>(skin);
	selectBoxLanguage.setItems(array);
	selectBoxLanguage.setSelected(Messages.LANGUAGE_NAME.getValue());

	labelTitleOption.setAlignment(Align.center);

	tableOption.add(labelTitleOption).colspan(3).expandX().fillX().row();

	tableOption.row();
	Table t = new Table();
	t.add(labelLanguage).width(350).left().padRight(10);
	t.add(selectBoxLanguage).width(300).padRight(10);

	this.addExpandableRow(tableOption, slMusicVolume);
	this.addExpandableRow(tableOption, slFXVolume);
	this.addExpandableRow(tableOption, slMasterVolume);
	this.addExpandableRow(tableOption, t);
	this.addExpandableRow(tableOption, buttonBackFromOptions);

    }

    /**
     * Llamado internamente por create. Crea la tabla principal
     */
    private void createMainTable()
    {
	TooltipManager manager = TooltipManager.getInstance();

	// Configurar los tiempos
	manager.initialTime = 0.5f;

	this.tableMainInUi = new Table();
	this.tableMainInUi.setFillParent(true);
	this.tableMainInUi.top();
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
		UI2D.this.fromInGameTable = false;
	    }
	});

	this.buttonCredits = new TextButton(Messages.CREDITS.getValue(), skin);
	this.buttonCredits.addListener(new ClickListener()
	{
	    @Override
	    public void clicked(InputEvent event, float x, float y)
	    {
		UI2D.this.doCredits();
	    }
	});

	this.buttonExit = new TextButton(Messages.EXIT.getValue(), skin);
	buttonExit.addListener(new ClickListener()
	{
	    @Override
	    public void clicked(InputEvent event, float x, float y)
	    {
		UI2D.this.confirmExitDialog.show(stage);
	    }
	});

	this.selectBoxGameType = new SelectBox<String>(skin);
	this.selectBoxEpisode = new SelectBox<String>(skin);

	this.selectBoxGameType.addListener(new ChangeListener()
	{
	    @Override
	    public void changed(ChangeEvent event, Actor actor)
	    {
		String selected = selectBoxGameType.getSelected();
		UI2D.this.selectBoxEpisode.setVisible(selected.equalsIgnoreCase(Messages.EXTENDED_VERSION.getValue()));

	    }
	});
	this.setTextOfSelectBox();
	Table t = new Table();
	t.add(buttonNewGame).width(350).left().padRight(10);
	t.add(selectBoxGameType).width(400).padRight(10);
	t.add(selectBoxEpisode).width(300).padRight(10);

	labelTitleMain.setAlignment(Align.center);

	tableMainInUi.add(labelTitleMain).colspan(3).expandX().fillX().row();

	tableMainInUi.row();
	this.addExpandableRow(tableMainInUi, t);

	this.addExpandableRow(tableMainInUi, this.buttonOptions);
	this.addExpandableRow(tableMainInUi, this.buttonCredits);
	this.addExpandableRow(tableMainInUi, this.slDificultLevel);
	this.addExpandableRow(tableMainInUi, this.buttonExit);

	if (!this.controler.isFinishedOneTime())
	{
	    this.slDificultLevel.setVisible(false);
	    this.slDificultLevel.setTouchable(Touchable.disabled);
	}
    }

    /**
     * Llamado por create. Crea la tabla de creditos
     */
    private void createCreditsTable()
    {
	this.tableCredits = new Table();
	this.tableCredits.setFillParent(true);
	this.tableCredits.top();

	this.labelTitleCredits = new Label(Messages.CREDITS.getValue(), skin, "myLabelStyleLarge");
	this.labelTitleCredits.setAlignment(Align.center);

	float anchoTexto = stage.getWidth() * 0.8f;
	float altoTexto = stage.getHeight() * 0.6f;
	String textoCreditos = this.controler.getCredits();

	this.creditsLabel = new Label(textoCreditos, skin);
	creditsLabel.setWrap(true);

	creditsLabel.setAlignment(Align.topLeft);

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
	contenedorTexto.background(backgroundBlackTransparent);
	tableCredits.add(labelTitleCredits).colspan(3).expandX().fillX().row();

	tableCredits.row();

	tableCredits.add(scrollPane).width(anchoTexto).height(altoTexto).pad(20).expand().center();
	tableCredits.row();
	tableCredits.add(this.buttonBackFromCredits).expandY().pad(10).left();
	tableCredits.row();

    }

    /**
     * Llamado por create. Crea la tabla del menu durante el juego
     */
    private void createInGameTable()
    {
	this.tableInGame = new Table();
	this.tableInGame.setFillParent(true);
	this.tableInGame.top();
	Label label;
	label = new Label(this.KVName, skin, "myLabelStyleLarge");
	label.setAlignment(Align.center);

	this.buttonRetry = new TextButton(Messages.RETRY.getValue(), skin, "default");
	buttonRetry.addListener(new ClickListener()
	{
	    @Override
	    public void clicked(InputEvent event, float x, float y)
	    {
		UI2D.this.confirmRetryDialog.show(stage);
	    }
	});

	this.buttonOptionsInGame = new TextButton(Messages.OPTIONS.getValue(), skin);
	buttonOptionsInGame.addListener(new ClickListener()
	{
	    @Override
	    public void clicked(InputEvent event, float x, float y)
	    {

		doOptions();
		UI2D.this.fromInGameTable = true;
		// UI2D.this.confirmRetryDialog.show(stage);
	    }
	});

	this.buttonMainMenu = new TextButton(Messages.MAIN_MENU.getValue(), skin);
	buttonMainMenu.addListener(new ClickListener()
	{
	    @Override
	    public void clicked(InputEvent event, float x, float y)
	    {
		UI2D.this.confirmGoMainMenuDialog.show(stage);
	    }
	});
	this.buttonMap = new TextButton(Messages.MAP.getValue(), skin);
	buttonMap.addListener(new ClickListener()
	{
	    @Override
	    public void clicked(InputEvent event, float x, float y)
	    {
		doMap();
	    }
	});
	this.buttonMap.addListener(this.controler.getInputListener());
	this.buttonMap.setUserObject(AbstractControler.SHOW_MAP);
	this.buttonExitInGame = new TextButton(Messages.EXIT.getValue(), skin);
	buttonExitInGame.addListener(new ClickListener()
	{
	    @Override
	    public void clicked(InputEvent event, float x, float y)
	    {
		UI2D.this.confirmExitDialog.show(stage);
	    }
	});

	label.setAlignment(Align.center);

	tableInGame.add(label).colspan(3).expandX().fillX().row();

	tableInGame.row();

	this.addExpandableRow(tableInGame, buttonMap);
	this.addExpandableRow(tableInGame, buttonOptionsInGame);
	this.addExpandableRow(tableInGame, buttonRetry);
	this.addExpandableRow(tableInGame, buttonMainMenu);
	this.addExpandableRow(tableInGame, buttonExitInGame);

    }

    /**
     * Llamado internamente por create. Crea la tabla con el mapa
     */
    private void createMapTable()
    {
	this.tableMap = new Table();
	this.tableMap.setFillParent(true);
	this.tableMap.top();

	this.labelTitleMap = new Label(Messages.MAP.getValue(), skin, "myLabelStyleLarge");
	this.labelTitleMap.setAlignment(Align.center);

	this.buttonBackFromMap = new TextButton(Messages.GO_BACK.getValue(), skin);
	this.buttonBackFromMap.setUserObject(AbstractControler.HIDE_MAP);
	this.buttonBackFromMap.addListener(new ClickListener()
	{
	    @Override
	    public void clicked(InputEvent event, float x, float y)
	    {
		doUiInGame();
	    }
	});
	this.buttonBackFromMap.addListener(this.controler.getInputListener());

	tableMap.add(labelTitleMap).colspan(3).expandX().fillX().row();

	tableMap.row();

	tableMap.add(this.buttonBackFromMap).expandY().pad(10).left();
	tableMap.row();

    }

    @Override
    public void updateLanguage()
    {
	this.setText();
    }

    /**
     * Llamado al comenzar un nuevo juego
     */
    public void doEnterGame()
    {
	this.stage.getRoot().removeActor(this.tableMainActual);
	Gdx.input.setCursorCatched(true);
    }

    /**
     * Retorna el stage de la Ventana
     * 
     * @return El stage de la Ventana
     */
    public Stage getStage()
    {
	return stage;
    }

    /**
     * Llamado al entrar en modo pausa en el juego
     */
    public void doUiInGame()
    {
	this.stage.getRoot().removeActor(this.backgroundImage);
	this.stage.getRoot().removeActor(this.tableMainActual);
	this.tableMainActual = tableInGame;
	this.stage.addActor(tableInGame);
	Gdx.input.setCursorCatched(false);
    }

    /**
     * Llamado internamente cuando se pide mostrar el mapa
     */
    private void doMap()
    {
	this.stage.getRoot().removeActor(this.tableInGame);
	this.tableMainActual = this.tableMap;
	this.stage.addActor(tableMainActual);
    }

    /**
     * Llamado cuando se esta entrando al nivel. Actualiza los niveles terminados en
     * el mapa
     */
    public void doEnteringLevel()
    {
	this.uiMap.generateCompletedPyramids();
    }

    /**
     * Renderiza el mapa
     */
    public void renderMap()
    {
	this.uiMap.render();

    }

    /**
     * Agrega un acto a la tabla con una configuracion predeterminada. Es llamado
     * internamente por los metodos que crean las tablas.
     * 
     * @param table Tabla a agregar el actor
     * @param actor Actor a ser agregado
     */
    private void addExpandableRow(Table table, Actor actor)
    {
	table.add(actor).expandY().fillY().pad(5).left().maxHeight(120).row();
    }

    /**
     * Crea los dialodos de confirmacion
     */
    private void createDialogs()
    {

	this.confirmRetryDialog = new Dialog(Messages.DIALOG_RETRY_TITLE.getValue(), skin)
	{
	    @Override
	    protected void result(Object object)
	    {
		if (object.equals(true))
		{
		    doRetry();
		}
	    }
	};

	confirmRetryDialog.text(Messages.DIALOG_RETRY_TEXT.getValue());
	confirmRetryDialog.button(Messages.YES.getValue(), true);
	confirmRetryDialog.button(Messages.NO.getValue(), false);

	this.confirmExitDialog = new Dialog(Messages.DIALOG_EXIT_TITLE.getValue(), skin)
	{
	    @Override
	    protected void result(Object object)
	    {
		if (object.equals(true))
		{
		    doExit();
		}
	    }
	};

	confirmExitDialog.text(Messages.DIALOG_EXIT_TEXT.getValue());
	confirmExitDialog.button(Messages.YES.getValue(), true);
	confirmExitDialog.button(Messages.NO.getValue(), false);

	this.confirmGoMainMenuDialog = new Dialog(Messages.DIALOG_GO_MAIN_MENU_TITLE.getValue(), skin)
	{
	    @Override
	    protected void result(Object object)
	    {
		if (object.equals(true))
		{
		    doGoBackMainMenu();
		}
	    }

	};

	confirmGoMainMenuDialog.text(Messages.DIALOG_GO_MAIN_MENU_TEXT.getValue());
	confirmGoMainMenuDialog.button(Messages.YES.getValue(), true);
	confirmGoMainMenuDialog.button(Messages.NO.getValue(), false);

    }

    /**
     * Llamado cuando se pide reintentar el nivel
     */
    private void doRetry()
    {
	this.controler.doRetry();
    }

    /**
     * LLamado cuando se pide salir del juego
     */
    private void doExit()
    {
	this.controler.doExit();

    }

    /**
     * Llamado cuando se pide volve al menu pincipal
     */
    private void doGoBackMainMenu()
    {
	this.controler.doBackToMainMenu();
    }

    @Override
    public boolean isExtendedVersion()
    {
	return this.selectBoxGameType.getSelected().equalsIgnoreCase(Messages.EXTENDED_VERSION.getValue());
    }

    @Override
    public int getEpisode()
    {
	return this.arrayEpisodesMessages.indexOf(this.selectBoxEpisode.getSelected(), false) + 1;
    }

    /**
     * Crea el mapa que se muestra con las piramides (actual y completadas),
     * dependendiendo del parametro se crea la version extendida o clasica
     * 
     * @param isExtendedVersion true si es la version extendida, false si es la
     *                          clasica.
     */
    public void createMap(boolean isExtendedVersion)
    {
	if (isExtendedVersion)
	{
	    this.uiMap = new UIExtendedMap(manager.get(this.uiConfig.getExtendedMapFile(), Texture.class),
		    manager.get(this.uiConfig.getPyramidActualFile(), Texture.class),
		    manager.get(this.uiConfig.getPyramidCompletedFile(), Texture.class), 10);
	} else
	{
	    this.uiMap = new UIClassicMap(manager.get(this.uiConfig.getClassicMapFile(), Texture.class),
		    manager.get(this.uiConfig.getPyramidActualFile(), Texture.class),
		    manager.get(this.uiConfig.getPyramidCompletedFile(), Texture.class), 6);
	}

    }
}
