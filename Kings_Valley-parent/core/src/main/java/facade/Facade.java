package facade;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import audio.AudioManager;
import engine.ConsoleKVEventListener;
import engine.game.Game;
import i18n.AllLanguages;
import i18n.Language;
import mainPackage.IMyApplicationListener;
import util.GameConfig;
import util.GameRules;
import util.Utils;
import vista2D.TileMapGrafica2D;
import vista2D.ui.Controler2D;
import vista2D.ui.UI2D;
import vista2D.ui.UIConfig;

/**
 * Clase que implementa el patron Facade y el patron Singleton.
 * 
 * @author Guillermo Lazzurri
 */
public class Facade implements ApplicationListener
{
	private static Facade instance = null;
	private final static String CONFIG_UI_FILE = "ui_config.json";

	private static final Json json = new Json();

	private UI2D ui;
	private Controler2D controler;
	private GameConfig gameConfig;
	private boolean changeConfig = false;
	private final AssetManager assetManager;
	private AllLanguages allLanguages = new AllLanguages();
	private String creditsEnFile = "credits/credits.en";
	private String creditsEsFile = "credits/credits.es";
	private String creditsEn;
	private String creditsEs;

	private IMyApplicationListener gameAppListener;
	private Music musicActual;
	private Music musicUI;
	private Music musicIntro;
	private AudioManager audioManager;
	private RenderState renderState;
	private boolean showMap;
	private int dificultLevel;
	private boolean isExtendedVersion;
	private int episode;

	/**
	 * Retorna el assetManager
	 * 
	 * @return El objeto de tipo AssetManager que sera utilizado durante el juego
	 */
	public AssetManager getAssetManager()
	{
		return assetManager;
	}

	/**
	 * Metodo estatico que retorna la unica instancia posible de Facade (patron
	 * singleton)
	 * 
	 * @return retorna la unica instancia posible de Facade
	 */
	public static Facade getInstance()
	{
		if (instance == null)
			instance = new Facade();
		return instance;
	}

	/**
	 * Onstructor privado. Crea tambien el atributo de tipo AssetManager
	 */
	private Facade()
	{
		assetManager = new AssetManager();

	}

	/**
	 * Cambia el volumen general
	 * 
	 * @param volume Valor del volumen general (entre 0 y 1)
	 */
	public void setMasterVolume(float volume)
	{
		this.gameConfig.setMasterVolume(volume);
		this.changeConfig = true;
		float volumen = Facade.getInstance().getGameConfig().getMasterVolume()
				* Facade.getInstance().getGameConfig().getMusicVolume();
		musicActual.setVolume(volumen);
		this.audioManager.updateMusicVolume(volumen);

	}

	/**
	 * Cambia el volumen de la musica
	 * 
	 * @param volume Valor del volumen de la musica (entre 0 y 1)
	 */

	public void setMusicVolume(float volume)
	{
		this.gameConfig.setMusicVolume(volume);
		this.changeConfig = true;
		float volumen = Facade.getInstance().getGameConfig().getMasterVolume()
				* Facade.getInstance().getGameConfig().getMusicVolume();
		musicActual.setVolume(volumen);
		musicActual.setVolume(volumen);
		this.audioManager.updateMusicVolume(volumen);
	}

	/**
	 * Cambia el volumen de los efectos de sonido
	 * 
	 * @param volume Valor del volumen de los efectos de sonido (entre 0 y 1)
	 */

	public void setSoundsVolume(float volume)
	{
		this.gameConfig.setSoundsVolume(volume);
		this.changeConfig = true;
	}

	/**
	 * Prepara las condiciones para iniciar un nuevo juego con el nivel de
	 * dificultad pasado por parametro. Debra lego lamarse al metodo fireGame().
	 * Esto para poder reproducir una musica de inicio u otro tiempo de espera.
	 * 
	 * @param isExtendedVrsion
	 * 
	 * @param dificultLevel    Valor de la dificultad del nivel. Entre -4 y 4 donde
	 *                         0 es la dificultad normal. Valores mayores a 4 seran
	 *                         tomados como 4 y menores que -4 seran tomados como -4
	 * @param episode
	 */
	public void startNewGame(boolean isExtendedVersion, int dificultLevel, int episode)
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
		this.isExtendedVersion = isExtendedVersion;
		this.episode = episode;

	}

	/**
	 * Inicia el juego previamente configurado por startNewGame(int dificultLevel)
	 */
	protected void fireGame()
	{
		Game.getInstance().startNewGame(this.isExtendedVersion, dificultLevel, this.episode);
		this.gameAppListener.create();

	}

	/**
	 * LLamado para reintentar el nivel. Se pierde una vida
	 */
	public void retry()
	{
		Game.getInstance().dying();
		Game.getInstance().pressPause();

	}

	/**
	 * Se guardan las opciones del juego
	 */
	public void saveGameOption()
	{
		if (this.changeConfig)
		{
			GameConfig.saveConfig(gameConfig);
			this.changeConfig = false;
		}
	}

	/**
	 * retorna la configuracion del juego
	 * 
	 * @return objeto que representa la configuracion del juego
	 */
	public GameConfig getGameConfig()
	{
		return gameConfig;
	}

	/**
	 * Lee los textos de todos los idiomas en la carpeta i18n
	 */
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
			System.out.println("No se encontraron archivos JSON en la carpeta i18n/");
		} else
		{
			for (FileHandle file : jsonFiles)
			{
				Language language = Utils.i18nToLanguage(file.nameWithoutExtension());
				this.allLanguages.addLanguaje(language);
			}
		}
	}

	/**
	 * Retorna los idiomas
	 * 
	 * @return objeto de tipo AllLanguages que contiene todos los mensajes del juego
	 *         en todos los idiomas
	 */
	public AllLanguages getAllLanguages()
	{
		return allLanguages;
	}

	/**
	 * Cambia el idioma del juego de acuerdo al parametro
	 * 
	 * @param languageName indica el idioma seleccionado, debe correspondederse con
	 *                     un LANGUAGE_NAME
	 */
	public void changeLanguage(String languageName)
	{
		Language language = allLanguages.getLanguage(languageName);
		Utils.i18n(language);
		this.gameConfig.setLanguage(language.getFileCode());
		this.changeConfig = true;
	}

	/**
	 * Llamao cuando se finalizan todos los niveles. Habilita la seleccion de nivel
	 * de dificultad
	 */
	public void finishAllLevels()
	{
		this.gameConfig.setEnabledSelectDificultLevel(true);
		this.changeConfig = true;
		this.saveGameOption();

	}

	/**
	 * Lee el archivo de creditos correspondiente el idioma seleccionado.
	 */
	private void readCredits()
	{
		FileHandle archivo = Gdx.files.internal(this.creditsEsFile);
		this.creditsEs = archivo.readString("UTF-8");
		archivo = Gdx.files.internal(this.creditsEnFile);
		this.creditsEn = archivo.readString("UTF-8");
	}

	/**
	 * Retorna el texto de los creditos.
	 * 
	 * @return Texto correspondiente a los creditos, en ingles o espanol (cualquier
	 *         otro posible idioma seleccionado retornara creditos en ingles)
	 */
	public String getCredits()
	{
		String r;
		if (this.gameConfig.getLanguage().equalsIgnoreCase("es"))
			r = this.creditsEs;
		else
			r = this.creditsEn;
		return r;
	}

	/**
	 * Inicializa los atributos una vez que esta listo el motor LibGDX
	 */
	@Override
	public void create()
	{
		UIConfig uiConfig = Facade.loadConfig();

		this.gameConfig = GameConfig.loadConfig();
/*
		try
		{
			Utils.checkLevelIntegrity();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			Gdx.app.exit();
		}*/
		Utils.i18n(this.gameConfig.getLanguage());
		Game.getInstance().setGameConfig(gameConfig);

		this.readLanguageFiles();
		this.readCredits();
		assetManager.load(uiConfig.getMusicUIName(), Music.class);
		assetManager.load(uiConfig.getMusicIntroName(), Music.class);

		this.ui = new UI2D(this.assetManager, uiConfig);

		assetManager.finishLoading();// termina la primera carga

		this.controler = new Controler2D(this.ui);
		this.audioManager = new AudioManager(assetManager);
		this.ui.create();
		Game.getInstance().addKVEventListener(controler);
		this.musicUI = assetManager.get(uiConfig.getMusicUIName(), Music.class);
		this.musicIntro = assetManager.get(uiConfig.getMusicIntroName(), Music.class);
		this.audioManager.create();
		gameAppListener = new TileMapGrafica2D(assetManager, .5f);
		Game.getInstance().setInterfaz(gameAppListener);
		Game.getInstance().addKVEventListener(audioManager);

		// solo para debug
		// Game.getInstance().addKVEventListener(new ConsoleKVEventListener());

		this.mainMenu();

	}

	/**
	 * Llama al metodo render del atributo renderState (patron State)
	 */
	@Override
	public void render()
	{
		this.renderState.render();

	}

	/**
	 * Llama al metodo resize del atributo renderState (patron State)
	 */

	@Override
	public void resize(int width, int height)
	{
		this.renderState.resize(width, height);
	}

	/**
	 * Llama al metodo pause del atributo renderState (patron State)
	 */

	@Override
	public void pause()
	{
		this.renderState.pause();
	}

	/**
	 * Llama al metodo resume del atributo renderState (patron State)
	 */

	@Override
	public void resume()
	{
		this.renderState.resume();
	}

	/**
	 * Se llama al terminar el juego para liberar memoria. Llama a los metodos
	 * dispose el del generador de fuentes (FreeTypeFontGenerator) y del
	 * AssetManager
	 */
	@Override
	public void dispose()
	{

		this.assetManager.dispose();
		Facade.getInstance().saveGameOption();
		this.ui.dispose();
	}

	/**
	 * Setea el atributo de tipo RenderState (patron State)
	 * 
	 * @param renderState sera de alguna de las correspondientes subclases de
	 *                    RenderState, no deberia invocarse directamente
	 */
	protected void setRenderState(RenderState renderState)
	{
		this.renderState = renderState;
	}

	/**
	 * Obtiene los valores de configuracion de la UI
	 * 
	 * @return objeto de tipo UIConfig que ontiene los parametros de configuracion
	 *         de la UI
	 */
	private static UIConfig loadConfig()
	{
		UIConfig r;
		FileHandle file = Gdx.files.local(CONFIG_UI_FILE);
		if (file.exists())
		{
			r = json.fromJson(UIConfig.class, file);
		} else
			r = new UIConfig();
		return r;
	}

	/**
	 * Retorna la musica de introduccion
	 * 
	 * @return objeto de tipo Music que representa la musica de introduccion
	 */
	protected Music getMusicIntro()
	{
		return musicIntro;
	}

	/**
	 * Metodo que se invoca al volve el menu principal
	 */
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

	/**
	 * Se llama al mostrar el mostrar el mapa
	 */
	public void showMap()
	{
		this.showMap = true;

	}

	/**
	 * Se llama al ocultar el mapa
	 */

	public void hideMap()
	{
		this.showMap = false;

	}

	/**
	 * Retorna true si el mapa es visible, false en caso contrario
	 * 
	 * @return true si el mapa es visible, false en caso contrario
	 */
	public boolean isShowMap()
	{
		return showMap;
	}

}
