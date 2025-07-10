package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class GameConfig
{
	private String language;
	private float masterVolume ;
	private float musicVolume;
	private float soundsVolume;
	private boolean finishedOneTime=false;

	private static final String GAME_CONFIG_FILE = "game_config.json";
	private static final Json json = new Json();

	public static void saveConfig(GameConfig config)
	{
		FileHandle file = Gdx.files.local(GAME_CONFIG_FILE);
		json.setUsePrototypes(false);
		file.writeString(json.prettyPrint(config), false);
	}

	public static GameConfig loadConfig()
	{
		GameConfig gameConfig;
		FileHandle file = Gdx.files.local(GAME_CONFIG_FILE);
		if (file.exists())
		{
			gameConfig = json.fromJson(GameConfig.class, file);
		} else
			gameConfig = new GameConfig();
		return gameConfig; // Valores por defecto
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public float getMasterVolume()
	{
		return masterVolume;
	}

	public void setMasterVolume(float masterVolume)
	{
		this.masterVolume = masterVolume;
	}

	public float getMusicVolume()
	{
		return musicVolume;
	}

	public void setMusicVolume(float musicVolume)
	{
		this.musicVolume = musicVolume;
	}

	public float getSoundsVolume()
	{
		return soundsVolume;
	}

	public void setSoundsVolume(float soundsVolume)
	{
		this.soundsVolume = soundsVolume;
	}

	public boolean isFinishedOneTime()
	{
	    return finishedOneTime;
	}

	public void setFinishedOneTime(boolean finishedOneTime)
	{
	    this.finishedOneTime = finishedOneTime;
	}

	
	

}
