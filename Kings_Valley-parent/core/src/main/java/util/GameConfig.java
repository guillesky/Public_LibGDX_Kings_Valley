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
    private float tileWidth = 64;
    private float tileHeight = 64;
    private String language;
    private float masterVolume;
    private float musicVolume;
    private float soundsVolume;
    private boolean finishedOneTime = false;

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
     * Retorna true si el jugador termino el juego al menos una vez, false en caso
     * contrario. Sirve para habilitar las opciones de nivel de dificultad
     * 
     * @return true si el jugador termino el juego al menos una vez, false en caso
     *         contrario. Sirve para habilitar las opciones de nivel de dificultad
     */
    public boolean isFinishedOneTime()
    {
	return finishedOneTime;
    }

    /**
     * Setea si el jugador termino el juego al menos una vez
     * 
     * @param finishedOneTime true si el jugador termino el juego al menos una vez,
     *                        false en caso contrario. Sirve para habilitar las
     *                        opciones de nivel de dificultad
     */
    public void setFinishedOneTime(boolean finishedOneTime)
    {
	this.finishedOneTime = finishedOneTime;
    }

    /**
     * Retorna el ancho en unidades del tile. Debe ser consistentes con cada uno de
     * los niveles del juego.
     * 
     * @return Ancho en unidades del tile. Debe ser consistentes con cada uno de los
     *         niveles del juego.
     */
    public float getTileWidth()
    {
	return tileWidth;
    }

    /**
     * Setea el ancho en unidades del tile. Debe ser consistentes con cada uno de
     * los niveles del juego.
     * 
     * @param tileWidth Ancho en unidades del tile. Debe ser consistentes con cada
     *                  uno de los niveles del juego.
     */
    public void setTileWidth(float tileWidth)
    {
	this.tileWidth = tileWidth;
    }

    /**
     * Retorna el alto en unidades del tile. Debe ser consistentes con cada uno de
     * los niveles del juego.
     * 
     * @return Alto en unidades del tile. Debe ser consistentes con cada uno de los
     *         niveles del juego.
     */
    public float getTileHeight()
    {
	return tileHeight;
    }

    /**
     * Setea el alto en unidades del tile. Debe ser consistentes con cada uno de los
     * niveles del juego.
     * 
     * @param tileHeight Alto en unidades del tile. Debe ser consistentes con cada
     *                   uno de los niveles del juego.
     */
    public void setTileHeight(float tileHeight)
    {
	this.tileHeight = tileHeight;
    }

}
