package facade;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Json;

import audio.AudioManager;
import i18n.AllLanguages;
import i18n.Language;
import mainPackage.IMyApplicationListener;
import modelo.game.Game;
import util.GameConfig;
import util.Utils;
import vista2D.GraphicsFileLoader;
import vista2D.TileMapGrafica2D_PARALAX;
import vista2D.ui.Controler2D;
import vista2D.ui.UI2D;
import vista2D.ui.UIConfig;

public class Facade implements ApplicationListener
{
    private static Facade instance = null;
    private final static String CONFIG_UI_FILE = "ui_config.json";

    private static final Json json = new Json();

    private UI2D ui;
    private FreeTypeFontGenerator generator;
    private Controler2D controler;
    private GameConfig gameConfig;
    private boolean changeConfig = false;
    private final AssetManager manager;
    private AllLanguages allLanguages = new AllLanguages();
    private String creditsEnFile = "credits/credits.en";
    private String creditsEsFile = "credits/credits.es";
    private String creditsEn;
    private String creditsEs;

    private IMyApplicationListener gameAppListener;
    private GraphicsFileLoader graphicsFileLoader;
    private Music musicActual;
    private Music musicUI;
    private Music musicIntro;
    private AudioManager audioManager;
    private RenderState renderState;
    protected boolean showMap;
    private int dificultLevel;

    public AssetManager getManager()
    {
	return manager;
    }

    public static Facade getInstance()
    {
	if (instance == null)
	    instance = new Facade();
	return instance;
    }

    private Facade()
    {
	manager = new AssetManager();

    }

    public void setLanguage(String lang)
    {
	this.gameConfig.setLanguage(lang);
	this.changeConfig = true;
    }

    public void setMasterVolume(float volume)
    {
	this.gameConfig.setMasterVolume(volume);
	this.changeConfig = true;
	musicActual.setVolume(Facade.getInstance().getGameConfig().getMasterVolume()
		* Facade.getInstance().getGameConfig().getMusicVolume());
	this.audioManager.updateMusicVolume();

    }

    public void setMusicVolume(float volume)
    {
	this.gameConfig.setMusicVolume(volume);
	this.changeConfig = true;
	musicActual.setVolume(Facade.getInstance().getGameConfig().getMasterVolume()
		* Facade.getInstance().getGameConfig().getMusicVolume());
	this.audioManager.updateMusicVolume();
    }

    public void setSoundsVolume(float volume)
    {
	this.gameConfig.setSoundsVolume(volume);
	this.changeConfig = true;
    }

    public void startNewGame(int dificultLevel)
    {

	this.ui.doEnterGame();
	this.musicUI.stop();
	this.musicActual = this.musicIntro;
	this.musicActual.setLooping(false);
	musicActual.setVolume(Facade.getInstance().getGameConfig().getMasterVolume()
		* Facade.getInstance().getGameConfig().getMusicVolume());
	musicActual.play();

	this.renderState.newGame();
	this.dificultLevel = dificultLevel;
    }

    protected void fireGame()
    {
	Game.getInstance().setDificultLevel(dificultLevel);
	Game.getInstance().startNewGame();
	this.gameAppListener.create();

    }

    private void createInterfaces()
    {
	this.audioManager.create();
	gameAppListener = new TileMapGrafica2D_PARALAX(this.graphicsFileLoader, .5f);
	Game.getInstance().setInterfaz(gameAppListener);
	Game.getInstance().addKVEventListener(audioManager);

    }

    public void retry()
    {
	Game.getInstance().dying();
	Game.getInstance().pressPause();

    }

    public void saveGameOption()
    {
	if (this.changeConfig)
	{
	    GameConfig.saveConfig(gameConfig);
	    this.changeConfig = false;
	}
    }

    public GameConfig getGameConfig()
    {
	return gameConfig;
    }

    private void readLanguageFiles()
    {
	FileHandle inputFolder = Gdx.files.local("i18n/");
	FileHandle[] allFiles = inputFolder.list();
	ArrayList<FileHandle> jsonFiles = new ArrayList<FileHandle>();

	for (FileHandle file : allFiles)
	{
	    if (file.extension().toLowerCase().equals("json"))
	    {
		jsonFiles.add(file);
	    }
	}
	if (jsonFiles.isEmpty())
	{
	    System.out.println("No se encontraron archivos PNG en la carpeta input/");
	} else
	{
	    for (FileHandle file : jsonFiles)
	    {
		Language language = Utils.i18nToLanguage(file.nameWithoutExtension());
		this.allLanguages.addLanguaje(language);
	    }
	}
    }

    public AllLanguages getAllLanguages()
    {
	return allLanguages;
    }

    public void changeLanguage(String languageName)
    {
	Language language = allLanguages.getLanguage(languageName);
	Utils.i18n(language);
	this.setLanguage(language.getFileCode());
    }

    public void finishAllLevels()
    {
	this.gameConfig.setFinishedOneTime(true);
	this.changeConfig = true;
	this.saveGameOption();

    }

    private void readCredits()
    {
	FileHandle archivo = Gdx.files.internal(this.creditsEsFile); 
	this.creditsEs = archivo.readString("UTF-8");
	archivo = Gdx.files.internal(this.creditsEnFile); 
	this.creditsEn = archivo.readString("UTF-8");
    }

    public String getCredits()
    {
	String r;
	if (this.gameConfig.getLanguage().equalsIgnoreCase("es"))
	    r = this.creditsEs;
	else
	    r = this.creditsEn;
	return r;
    }

    @Override
    public void create()
    {
	UIConfig uiConfig = Facade.loadConfig();

	this.gameConfig = GameConfig.loadConfig();
	Utils.i18n(this.gameConfig.getLanguage());
	Game.getInstance().setGameConfig(gameConfig);

	this.readLanguageFiles();
	this.readCredits();
	this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PAPYRUS.TTF"));
	manager.load(uiConfig.getMusicUIName(), Music.class);
	manager.load(uiConfig.getMusicIntroName(), Music.class);

	this.ui = new UI2D(this.manager, generator, uiConfig);

	manager.finishLoading();// termina la primera carga

	this.controler = new Controler2D(this.ui);
	this.graphicsFileLoader = new GraphicsFileLoader(this.manager);
	this.audioManager = new AudioManager(manager);
	this.ui.create();
	Game.getInstance().addKVEventListener(controler);
	this.musicUI = manager.get(uiConfig.getMusicUIName(), Music.class);
	this.musicIntro = manager.get(uiConfig.getMusicIntroName(), Music.class);
	this.createInterfaces();

	this.mainMenu();

    }

    @Override
    public void render()
    {
	this.renderState.render();

    }

    @Override
    public void resize(int width, int height)
    {
	this.renderState.resize(width, height);
    }

    @Override
    public void pause()
    {
	this.renderState.pause();
    }

    @Override
    public void resume()
    {
	this.renderState.resume();
    }

    @Override
    public void dispose()
    {

	generator.dispose();
	this.manager.dispose();
	Facade.getInstance().saveGameOption();
    }

    protected void setRenderState(RenderState renderState)
    {
	this.renderState = renderState;
    }

    private static void saveConfig(UIConfig config)
    {
	FileHandle file = Gdx.files.local(CONFIG_UI_FILE);
	json.setUsePrototypes(false);
	file.writeString(json.prettyPrint(config), false);
    }

    private static UIConfig loadConfig()
    {
	FileHandle file = Gdx.files.local(CONFIG_UI_FILE);
	if (file.exists())
	{
	    return json.fromJson(UIConfig.class, file);
	}
	return new UIConfig(); // Valores por defecto
    }

    protected UI2D getUi()
    {
	return ui;
    }

    protected IMyApplicationListener getGameAppListener()
    {
	return gameAppListener;
    }

    protected Music getMusicIntro()
    {
	return musicIntro;
    }

    public void mainMenu()
    {
	this.ui.doEnterUi();

	this.musicActual = this.musicUI;
	musicActual.setLooping(true);
	musicActual.setVolume(Facade.getInstance().getGameConfig().getMasterVolume()
		* Facade.getInstance().getGameConfig().getMusicVolume());
	musicActual.play();
	this.renderState = new RenderStateInUI(this.ui, this.gameAppListener);

    }

    public void showMap()
    {
	this.showMap = true;

    }

    public void hideMap()
    {
	this.showMap = false;

    }

}
