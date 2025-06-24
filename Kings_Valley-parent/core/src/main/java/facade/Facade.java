package facade;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

import i18n.AllLanguages;
import i18n.Language;
import io.github.some_example_name.IMyApplicationListener;
import modelo.control.Controls;
import modelo.game.Game;
import util.GameConfig;
import util.Utils;
import vista2D.GraphicsFileLoader;
import vista2D.TileMapGrafica2D_PARALAX;
import vista2D.ui.Controler2D;
import vista2D.ui.UI2D;

public class Facade implements ApplicationListener
{
    private static Facade instance = null;

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
    private ApplicationListener currentAppListener;
    private IMyApplicationListener grafica;

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
    }

    public void setMusicVolume(float volume)
    {
	this.gameConfig.setMusicVolume(volume);
	this.changeConfig = true;
    }

    public void setSoundsVolume(float volume)
    {
	this.gameConfig.setSoundsVolume(volume);
	this.changeConfig = true;
    }

    public void startNewGame(int dificultLevel)
    {
	grafica = new TileMapGrafica2D_PARALAX(new GraphicsFileLoader(manager), .5f);
	Game.getInstance().setInterfaz(grafica);
	Game.getInstance().setDificultLevel(dificultLevel);
	Game.getInstance().start();
	this.grafica.create();
	this.currentAppListener = this.grafica;
    }

    public void retry()
    {
	Game.getInstance().dying();
    }

    public void saveGameOption()
    {
	if (this.changeConfig)
	    GameConfig.saveConfig(gameConfig);

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

    private void readCredits()
    {
	FileHandle archivo = Gdx.files.internal(this.creditsEsFile); // si está en assets
	this.creditsEs = archivo.readString("UTF-8");
	archivo = Gdx.files.internal(this.creditsEnFile); // si está en assets
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
	this.gameConfig = GameConfig.loadConfig();
	Utils.i18n(this.gameConfig.getLanguage());
	Game.getInstance().setGameConfig(gameConfig);

	this.readLanguageFiles();
	this.readCredits();
	this.generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PAPYRUS.TTF"));
	this.ui = new UI2D(this.manager, generator);
	this.controler = new Controler2D(this.ui);
	GraphicsFileLoader graphicsFileLoader = new GraphicsFileLoader(this.manager);
	this.ui.create();
	this.currentAppListener = this.ui;
    }

    @Override
    public void render()
    {
	this.currentAppListener.render();
	if (this.currentAppListener == this.grafica)
	    this.updateInput();
    }

    @Override
    public void resize(int width, int height)
    {
	this.currentAppListener.resize(width, height);
    }

    @Override
    public void pause()
    {
	this.currentAppListener.pause();
    }

    @Override
    public void resume()
    {
	this.currentAppListener.resume();
    }

    @Override
    public void dispose()
    {

	generator.dispose();
	this.manager.dispose();
	Facade.getInstance().saveGameOption();
    }

    private void updateInput()
    {
	Controls controles = Game.getInstance().getControles();

	float x = 0, y = 0;

	Vector2 aux;
	if (Gdx.input.isKeyPressed(Input.Keys.UP))
	    y += 1;
	if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
	    y -= 1;
	if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
	    x += 1;
	if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
	    x -= 1;
	aux = new Vector2(x, y);

	controles.setNuevoRumbo(aux);
	controles.processKey(Input.Keys.SPACE);
	controles.processKey(Input.Keys.F);
	controles.processKey(Input.Keys.N);
	controles.processKey(Input.Keys.O);

	controles.processKey(Input.Keys.P);
	controles.processKey(Input.Keys.S);
	Game.getInstance().updateframe(Gdx.graphics.getDeltaTime());

    }

}
