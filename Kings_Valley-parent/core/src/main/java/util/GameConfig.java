package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * Clase que representa configuraciones de usario del juego
 * 
 * @author Guillermo Lazzurri
 */
public class GameConfig
{

	private String language;
	private float masterVolume;
	private float musicVolume;
	private float soundsVolume;
	private boolean enabledSelectDificultLevel = false;
	private int bestExtendedEpisodeFinished = 0;

	private static final String GAME_CONFIG_FILE = "game_config.json";
	private static final Json json = new Json();

	/**
	 * Guarda un objeto de tipo GameConfig en un archivo "game_config.json" en
	 * formato json
	 * 
	 * @param config Objeto a guardar
	 */
	public static void saveConfig(GameConfig config)
	{
		FileHandle file = Gdx.files.local(GAME_CONFIG_FILE);
		json.setUsePrototypes(false);
		file.writeString(json.prettyPrint(config), false);
	}

	/**
	 * Crea y retorna un objeto de tipo GameConfig a partir de los datos leidos
	 * desde el archivo "game_config.json" en formato json
	 * 
	 * @return Objeto leido del archivo "game_config.json"
	 */
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

	/**
	 * Retorna el codigo del idioma utilizado en el juego. Por ejemplo "es" para
	 * espanol o "en" para english
	 * 
	 * @return Codigo del idioma utilizado en el juego. Por ejemplo "es" para
	 *         espanol o "en" para english
	 */
	public String getLanguage()
	{
		return language;
	}

	/**
	 * Setea el codigo del idioma utilizado en el juego. Por ejemplo "es" para
	 * espanol o "en" para english
	 * 
	 * @param language Codigo del idioma utilizado en el juego. Por ejemplo "es"
	 *                 para espanol o "en" para english
	 */
	public void setLanguage(String language)
	{
		this.language = language;
	}

	/**
	 * Retorna el volumen general del juego. Toma valores float entre 0 y 1
	 * 
	 * @return Volumen general del juego. Toma valores float entre 0 y 1
	 */
	public float getMasterVolume()
	{
		return masterVolume;
	}

	/**
	 * Setea el volumen general del juego. Toma valores float entre 0 y 1
	 * 
	 * @param masterVolume Volumen general del juego. Toma valores float entre 0 y 1
	 */
	public void setMasterVolume(float masterVolume)
	{
		this.masterVolume = masterVolume;
	}

	/**
	 * Retorna el volumen de la musica del juego. Toma valores float entre 0 y 1
	 * 
	 * @return Volumen de la musica del juego. Toma valores float entre 0 y 1
	 */
	public float getMusicVolume()
	{
		return musicVolume;
	}

	/**
	 * Setea el volumen de la musica del juego. Toma valores float entre 0 y 1
	 * 
	 * @param musicVolume Volumen de la musica del juego. Toma valores float entre 0
	 *                    y 1
	 */
	public void setMusicVolume(float musicVolume)
	{
		this.musicVolume = musicVolume;
	}

	/**
	 * Retorna el volumen de de los efectos de sonido del juego. Toma valores float
	 * entre 0 y 1
	 * 
	 * @return Volumen de los efectos de sonido del juego. Toma valores float entre
	 *         0 y 1
	 */
	public float getSoundsVolume()
	{
		return soundsVolume;
	}

	/**
	 * Setea el volumen de de los efectos de sonido del juego. Toma valores float
	 * entre 0 y 1
	 * 
	 * @param soundsVolume Volumen de los efectos de sonido del juego. Toma valores
	 *                     float entre 0 y 1
	 */
	public void setSoundsVolume(float soundsVolume)
	{
		this.soundsVolume = soundsVolume;
	}

	/**
	 * Indica si esta habilitada la seleccion de nivel de dificultad
	 * 
	 * @return true si esta habilitada la seleccion de nivel de dificultad, false en
	 *         caso contrario. Se habilita si se termino el juego al menos una vez.
	 */
	public boolean isEnabledSelectDificultLevel()
	{
		return enabledSelectDificultLevel;
	}

	/**
	 * Setea si esta habilitada la seleccion de nivel de dificultad
	 * 
	 * @param finishedOneTime true si esta habilitada la seleccion de nivel de
	 *                        dificultad false en caso contrario. Se habilita si se
	 *                        termino el juego al menos una vez.
	 */
	public void setEnabledSelectDificultLevel(boolean finishedOneTime)
	{
		this.enabledSelectDificultLevel = finishedOneTime;
	}

	/**
	 * Indica cual fue el episodio mas alto terminado en la version extendida
	 * @return cual fue el episodio mas alto terminado en la version extendida. Sera un numero entero entre 0 y 4 inclusive.
	 */
	public int getBestExtendedEpisodeFinished()
	{
		return bestExtendedEpisodeFinished;
	}

	/**
	 * <b>Pre:</b> bestExtendedEpisodeFinished debe ser un numero entero entre 0 y 4 inclusive <br>
	 * Setea cual fue el episodio mas alto terminado en la version extendida
	 * @param bestExtendedEpisodeFinished el episodio mas alto terminado en la version extendida. Debe ser un numero entero entre 0 y 4 inclusive.
	 */
	public void setBestExtendedEpisodeFinished(int bestExtendedEpisodeFinished)
	{
		this.bestExtendedEpisodeFinished = bestExtendedEpisodeFinished;
	}
	
	

}
