package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.badlogic.gdx.utils.Json;

/**
 * Clase que representa configuraciones de usario del juego
 * 
 * @author Guillermo Lazzurri
 */
public class GameConfig
{

    private String language = "es";
    private float masterVolume = 1;
    private float musicVolume = 1;
    private float soundsVolume = 1;
    private boolean enabledSelectDificultLevel = false;
    private int bestExtendedEpisodeFinished = 0;
    private int bestGreatTempleFinished = 0;
    private boolean fullScreenMode=true;

    private static final String GAME_CONFIG_FILE = "game_config.json";
    private static final Json json = new Json();

    /**
     * Constructor de clase. Los valores se inicializan con los valores:<br>
     * this.language = "es";<br>
     * this.masterVolume = 1;<br>
     * this.musicVolume = 1;<br>
     * this.soundsVolume = 1;<br>
     * this.enabledSelectDificultLevel = false;<br>
     * this.bestExtendedEpisodeFinished = 0;<br>
     * this.bestGreatTempleFinished = 0;<br>
     * this.fullScreenMode=true;<br>
     * 
     */
    public GameConfig()
    {
    }

    /**
     * Guarda un objeto de tipo GameConfig en un archivo "game_config.json" en
     * formato json
     * 
     * @param config Objeto a guardar
     */

    public static void saveConfig(GameConfig config)
    {
	Path file = GameConfig.getConfigFile();

	json.setUsePrototypes(false);

	try
	{
	    Files.write(file, json.prettyPrint(config).getBytes(StandardCharsets.UTF_8));
	} catch (IOException e)
	{
	    throw new RuntimeException("No se pudo guardar la configuración en " + file, e);
	}
    }
    

    /**
     * Crea y retorna un objeto de tipo GameConfig a partir de los datos leidos
     * desde el archivo "game_config.json" en formato json
     * 
     * @return Objeto leido del archivo "game_config.json"
     */

    public static GameConfig loadConfig()
    {
	Path file = GameConfig.getConfigFile();

	if (!Files.exists(file))
	{
	    return new GameConfig();
	}

	try
	{
	    String jsonText = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);

	    return json.fromJson(GameConfig.class, jsonText);
	} catch (IOException e)
	{
	    throw new RuntimeException("No se pudo leer la configuración desde " + file, e);
	}
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
     * 
     * @return cual fue el episodio mas alto terminado en la version extendida. Sera
     *         un numero entero entre 0 y 4 inclusive.
     */
    public int getBestExtendedEpisodeFinished()
    {
	return bestExtendedEpisodeFinished;
    }

    /**
     * <b>Pre:</b> bestExtendedEpisodeFinished debe ser un numero entero entre 0 y 4
     * inclusive <br>
     * Setea cual fue el episodio mas alto terminado en la version extendida
     * 
     * @param bestExtendedEpisodeFinished el episodio mas alto terminado en la
     *                                    version extendida. Debe ser un numero
     *                                    entero entre 0 y 4 inclusive.
     */
    public void setBestExtendedEpisodeFinished(int bestExtendedEpisodeFinished)
    {
	this.bestExtendedEpisodeFinished = bestExtendedEpisodeFinished;
    }

    @Override
    public String toString()
    {
	return "GameConfig [language=" + language + ", masterVolume=" + masterVolume + ", musicVolume=" + musicVolume
		+ ", soundsVolume=" + soundsVolume + ", enabledSelectDificultLevel=" + enabledSelectDificultLevel
		+ ", bestExtendedEpisodeFinished=" + bestExtendedEpisodeFinished + "]";
    }

    /**
     * Indica cual fue el Gran Templo mas alto terminado en la version "Gran Templo"
     * 
     * @return cual fue el Gran Templo mas alto terminado en la version "Gran
     *         Templo".
     */
    public int getBestGreatTempleFinished()
    {
	return bestGreatTempleFinished;
    }

    /**
     * Setea cual es el gran templo mas alto terminado en la version Gran Templo
     * 
     * @param bestGreatTempleFinished el gran templo mas alto terminado en la
     *                                version Gran Templo
     */
    public void setBestGreatTempleFinished(int bestGreatTempleFinished)
    {
	this.bestGreatTempleFinished = bestGreatTempleFinished;
    }

    /**
     * Retorna el fileHandle del archivo de configuracion dentro de la carpeta
     * APPDATA del sistema
     * 
     * @return El archivo de configuracion dentro de la carpeta APPDATA del sistema
     */

    private static Path getConfigFile()
    {
	try
	{
	    Path configDir;

	    String appData = System.getenv("APPDATA");

	    if (appData != null)
	    {
		configDir = Paths.get(appData, "KingsValley");
	    } else
	    {
		configDir = Paths.get(System.getProperty("user.home"), ".local", "share", "KingsValley");
	    }

	    Files.createDirectories(configDir);

	    return configDir.resolve(GAME_CONFIG_FILE);
	} catch (IOException e)
	{
	    throw new RuntimeException("No se pudo crear el directorio de configuración", e);
	}
    }

    public boolean isFullScreenMode()
    {
        return fullScreenMode;
    }

    public void setFullScreenMode(boolean fullScreenMode)
    {
        this.fullScreenMode = fullScreenMode;
    }

   

}
