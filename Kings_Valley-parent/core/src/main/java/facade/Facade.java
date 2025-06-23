package facade;

import com.badlogic.gdx.assets.AssetManager;

import modelo.game.Game;
import util.GameConfig;
import util.Utils;

public class Facade
{
	private static Facade instance = null;
	private GameConfig gameConfig;
	private boolean changeConfig = false;
	private final AssetManager manager;

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
		this.gameConfig = GameConfig.loadConfig();
		Utils.i18n(this.gameConfig.getLanguage());
		Game.getInstance().setGameConfig(gameConfig);
		//GameConfig.saveConfig(gameConfig);
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
		Game.getInstance().setDificultLevel(dificultLevel);
		Game.getInstance().start();
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
	
	
	

}
