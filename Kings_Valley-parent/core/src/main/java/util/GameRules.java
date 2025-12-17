package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * Clase que representa los parametros utilizados en las reglas internas del
 * juego. Modificar los valores podria generar inconsistencias en el juego.
 * Aplica el patron singleton
 * 
 * @author Guillermo Lazzurri
 */
public class GameRules
{

    /**
     * Indice del array de parametros para las momia que indica la velocidad de
     * caminata
     */
    public static final int INDEX_SPEED_WALK = 0;
    /**
     * Indice del array de parametros para las momia que indica la velocidad en
     * escaleras
     */

    public static final int INDEX_SPEED_STAIR = 1;
    /**
     * Indice del array de parametros para las momia que indica el tiempo que
     * transcurre hasta que toma la siguiente decision
     */

    public static final int INDEX_TIME_TO_DECIDE = 2;
    /**
     * Indice del array de parametros para las momia que indica el tiempo que la
     * momia esta dudando hasta tomar una decision
     */

    public static final int INDEX_TIME_DECIDING = 3;

    private boolean godMode = false;
    private boolean debugMode = false;
    private int firstLevelInDebugMode = 1;
    

    private int initialNumberOfLives = 3;
   
    private float minMummySpawnDistanceToPlayer;
    private float mummyTimeInLimbus;
    private float mummyTimeDying;
    private float mummyTimeAppearing;

    private float[] mummyWhiteParameters = null;
    private float[] mummyBlueParameters = null;
    private float[] mummyYellowParameters = null;
    private float[] mummyPinkParameters = null;
    private float[] mummyRedParameters = null;

    private float levelTileWidthUnits;
    private float levelTileHeightUnits;

    private float timeToEndGiratory = 1f;
    private float timeToEndTrapMechanism = 1f;
    private float timeToOpenCloseDoor = 1f;
    private float timeToEndPicking = 1f;
    private float timeToEndThrowDagger = 0.2f;
    private int scoreForFirstExtraLife = 10000;
    private int scoreForExtraLife = 20000;

    private float characterSpeedFallFactor;
    private float characterVerticalSpeedJumpFactor;
    private float characterHorizontalSpeedJumpFactor;
    private float playerSpeedWalkFactor;
    private float playerSpeedWalkStairsFactor;
    private float flyingDaggerSpeedFactor;
    private float flyingDaggerSpeedFallFactor;
    private float characterWidthFactor;
    private float playerHeightFactor;
    private float mummyHeightFactor;
    private float characterFeetHeightFactor;
    private float characterFeetWidthFactor;
    private float stairWidthFactor;
    private float stairHeightFactor;
    private float giratoryWidthFactor;
    private float mummyWhiteSpeedWalkFactor;
    private float mummyWhiteSpeedStairFactor;
    private float mummyWhiteTimeToDecide;
    private float mummyWhiteTimeDeciding;
    private float mummyPinkSpeedWalkFactor;
    private float mummyPinkSpeedStairFactor;
    private float mummyPinkTimeToDecide;
    private float mummyPinkTimeDeciding;
    private float mummyYellowSpeedWalkFactor;
    private float mummyYellowSpeedStairFactor;
    private float mummyYellowTimeToDecide;
    private float mummyYellowTimeDeciding;
    private float mummyBlueSpeedWalkFactor;
    private float mummyBlueSpeedStairFactor;
    private float mummyBlueTimeToDecide;
    private float mummyBlueTimeDeciding;
    private float mummyRedSpeedWalkFactor;
    private float mummyRedSpeedStairFactor;
    private float mummyRedTimeToDecide;
    private float mummyRedTimeDeciding;

    private static GameRules instance = null;
    private static final String GAMES_RULES_CONFIG_FILE = "games_rules_config.json";
    private static final Json json = new Json();

    /**
     * (Usado solo en etapa de desarrollo) Graba el archivo de configuracion con el
     * objeto pasado por parametro
     * 
     * @param config Objeto que debe grabarse
     */
    private static void saveConfig(GameRules gameRules)
    {
	FileHandle file = Gdx.files.local(GAMES_RULES_CONFIG_FILE);
	json.setUsePrototypes(false);
	file.writeString(json.prettyPrint(gameRules), false);
    }

    /**
     * Retorna el objecto de GameRules a partir del archivo de de configuracion
     * 
     * @return el objecto de GameRules a partir del archivo de de configuracion
     */
    private static GameRules loadConfig()
    {
	FileHandle file = Gdx.files.local(GAMES_RULES_CONFIG_FILE);

	return json.fromJson(GameRules.class, file);

    }

    /**
     * Retorna el tiempo que lleva picar una celda
     * 
     * @return Indica el tiempo que lleva picar una celda
     */
    public float getTimeToEndPicking()
    {
	return timeToEndPicking;
    }

    /**
     * Retorna el tiempo que el player permanece quieto lanzando la espada
     * 
     * @return Indica el tiempo que el player permanece quieto lanzando la espada
     */
    public float getTimeToEndThrowDagger()
    {
	return timeToEndThrowDagger;
    }

    /**
     * Constructor de clase privado (para el patron singleton)
     */
    private GameRules()
    {

    }


    /**
     * Inicializa los paramtros de las momias de acuerdo al ancho del tile
     * 
     * @param levelTileWidthUnits Ancho del tile
     */
    private void setMummyParameters()
    {

	this.mummyWhiteParameters = new float[4];
	this.mummyBlueParameters = new float[4];
	this.mummyYellowParameters = new float[4];
	this.mummyPinkParameters = new float[4];
	this.mummyRedParameters = new float[4];
	

	this.mummyWhiteParameters[GameRules.INDEX_SPEED_WALK] = this.getPlayerSpeedWalk()
		* this.mummyWhiteSpeedWalkFactor;
	this.mummyWhiteParameters[GameRules.INDEX_SPEED_STAIR] = this.getPlayerSpeedWalkStairs()
		* this.mummyWhiteSpeedStairFactor;
	this.mummyWhiteParameters[GameRules.INDEX_TIME_TO_DECIDE] = this.mummyWhiteTimeToDecide;
	this.mummyWhiteParameters[GameRules.INDEX_TIME_DECIDING] = this.mummyWhiteTimeDeciding;

	this.mummyPinkParameters[GameRules.INDEX_SPEED_WALK] = this.getPlayerSpeedWalk()
		* this.mummyPinkSpeedWalkFactor;
	this.mummyPinkParameters[GameRules.INDEX_SPEED_STAIR] = this.getPlayerSpeedWalkStairs()
		* this.mummyPinkSpeedStairFactor;
	this.mummyPinkParameters[GameRules.INDEX_TIME_TO_DECIDE] = this.mummyPinkTimeToDecide;
	this.mummyPinkParameters[GameRules.INDEX_TIME_DECIDING] = this.mummyPinkTimeDeciding;

	this.mummyYellowParameters[GameRules.INDEX_SPEED_WALK] = this.getPlayerSpeedWalk()
		* this.mummyYellowSpeedWalkFactor;
	this.mummyYellowParameters[GameRules.INDEX_SPEED_STAIR] = this.getPlayerSpeedWalkStairs()
		* this.mummyYellowSpeedStairFactor;
	this.mummyYellowParameters[GameRules.INDEX_TIME_TO_DECIDE] = this.mummyYellowTimeToDecide;
	this.mummyYellowParameters[GameRules.INDEX_TIME_DECIDING] = this.mummyYellowTimeDeciding;

	this.mummyBlueParameters[GameRules.INDEX_SPEED_WALK] = this.getPlayerSpeedWalk()
		* this.mummyBlueSpeedWalkFactor;
	this.mummyBlueParameters[GameRules.INDEX_SPEED_STAIR] = this.getPlayerSpeedWalkStairs()
		* this.mummyBlueSpeedStairFactor;
	this.mummyBlueParameters[GameRules.INDEX_TIME_TO_DECIDE] = this.mummyBlueTimeToDecide;
	this.mummyBlueParameters[GameRules.INDEX_TIME_DECIDING] = this.mummyBlueTimeDeciding;

	this.mummyRedParameters[GameRules.INDEX_SPEED_WALK] = this.getPlayerSpeedWalk() * this.mummyRedSpeedWalkFactor;
	this.mummyRedParameters[GameRules.INDEX_SPEED_STAIR] = this.getPlayerSpeedWalkStairs()
		* this.mummyRedSpeedStairFactor;
	this.mummyRedParameters[GameRules.INDEX_TIME_TO_DECIDE] = this.mummyRedTimeToDecide;
	this.mummyRedParameters[GameRules.INDEX_TIME_DECIDING] = this.mummyRedTimeDeciding;
	// saveConfig(this);
    }

    /**
     * Retorna la velocidad de caida de los caracteres
     * 
     * @return Velocidad de caida de los caracteres
     */
    public float getCharacterSpeedFall()
    {
	return characterSpeedFallFactor * this.levelTileHeightUnits;
    }

    /**
     * Retorna la velocidad de caminata del player
     * 
     * @return Velocidad de caminata del player
     */
    public float getPlayerSpeedWalk()
    {
	return playerSpeedWalkFactor * this.levelTileWidthUnits;
    }

    /**
     * Retorna la velocidad en la escalera del player
     * 
     * @return Velocidad en la escalera del player
     */
    public float getPlayerSpeedWalkStairs()
    {
	return playerSpeedWalkStairsFactor * this.levelTileWidthUnits;
    }

    /**
     * Retorna el ancho de los caracteres
     * 
     * @return Ancho de los caracteres
     */
    public float getCharacterWidth()
    {
	return characterWidthFactor * this.levelTileHeightUnits;
    }

    /**
     * Retorna el alto del player
     * 
     * @return Alto del player
     */
    public float getPlayerHeight()
    {
	return playerHeightFactor * this.levelTileHeightUnits;
    }

    /**
     * Retorna el alto de las momias
     * 
     * @return Alto de las momias
     */

    public float getMummyHeight()
    {
	return mummyHeightFactor * this.levelTileHeightUnits;
    }

    /**
     * Retorna el ancho de los objetos del nivel (picos, espadas, gemas)
     * 
     * @return Ancho de los objetos del nivel (picos, espadas, gemas)
     */
    public float getLevelObjectWidth()
    {
	return levelTileWidthUnits;
    }

    /**
     * Retorna el alto de los objetos del nivel (picos, espadas, gemas)
     * 
     * @return Alto de los objetos del nivel (picos, espadas, gemas)
     */
    public float getLevelObjectHeight()
    {
	return this.levelTileHeightUnits;
    }

    /**
     * Retorna el ancho de los inicios y fin de escalera (pies y cabecesras)
     * 
     * @return Ancho de los inicios y fin de escalera (pies y cabecesras)
     */
    public float getStairWidth()
    {
	return stairWidthFactor * levelTileWidthUnits;
    }

    /**
     * Retorna el alto de los inicios y fin de escalera (pies y cabeceras)
     * 
     * @return Alto de los inicios y fin de escalera (pies y cabeceras)
     */
    public float getStairHeight()
    {
	return stairHeightFactor * this.levelTileHeightUnits;
    }

    /**
     * Retorna el ancho en unidades de los tiles
     * 
     * @return Ancho en unidades de los tiles
     */
    public float getLevelTileWidthUnits()
    {
	return levelTileWidthUnits;
    }

    /**
     * Retorna el alto en unidades de los tiles
     * 
     * @return Alto en unidades de los tiles
     */
    public float getLevelTileHeightUnits()
    {
	return levelTileHeightUnits;
    }

    /**
     * Retorna la unica instancia de la clase (patron singleton)
     * 
     * @return La unica instancia de la clase (patron singleton)
     */
    public static GameRules getInstance()
    {
	if (instance == null)
	{
	    instance = loadConfig();

	}
	return instance;
    }

    /**
     * Retorna el ancho de los pies de los caracteres (para colisiones)
     * 
     * @return Ancho de los pies de los caracteres (para colisiones)
     */
    public float getCharacterFeetWidth()
    {
	return characterFeetWidthFactor * levelTileWidthUnits;
    }

    /**
     * Retorna el alto de los pies de los caracteres (para colisiones)
     * 
     * @return Alto de los pies de los caracteres (para colisiones)
     */
    public float getCharacterFeetHeight()
    {
	return characterFeetHeightFactor * this.levelTileHeightUnits;
    }

    /**
     * Setea el alto de los pies de los caracteres (para colisiones)
     * 
     * @param characterFeetHeight Alto de los pies de los caracteres (para
     *                            colisiones)
     */

    /**
     * Retorna el ancho de las puertas giratorias
     * 
     * @return Ancho de las puertas giratorias
     */
    public float getGiratoryWidth()
    {
	return giratoryWidthFactor * levelTileWidthUnits;
    }

    /**
     * Retorna la velocidad del salto de los caracteres (empuje vertical)
     * 
     * @return Velocidad del salto de los caracteres (empuje vertical)
     */
    public float getCharacterVerticalSpeedJump()
    {
	return characterVerticalSpeedJumpFactor * this.levelTileHeightUnits;
    }

    /**
     * Retorna la velocidad del salto de los caracteres (empuje horizontal)
     * 
     * @return Velocidad del salto de los caracteres (empuje horizontal)
     */
    public float getCharacterHorizontalSpeedJump()
    {
	return characterHorizontalSpeedJumpFactor * levelTileWidthUnits;
    }

    /**
     * Retorna la velocidad de la daga al ser lanzada horizontalmente (al ser
     * lanzada verticalemente se calculan otros valores proporcionales a este)
     * 
     * @return Velocidad de la daga al ser lanzada horizontalmente (al ser lanzada
     *         verticalemente se calculan otros valores proporcionales a este)
     */
    public float getFlyingDaggerSpeed()
    {
	return flyingDaggerSpeedFactor * levelTileWidthUnits;
    }

    /**
     * Retorna la velocidad de caida de la daga
     * 
     * @return Velocidad de caida de la daga
     */
    public float getFlyingDaggerSpeedFall()
    {
	return flyingDaggerSpeedFallFactor * this.levelTileHeightUnits;
    }

    /**
     * Retorna un array con los parametros de la momia azul
     * 
     * @return Un array con los parametros de la momia azul
     */
    public float[] getMummyBlueParameters()
    {
	if (this.mummyBlueParameters == null)
	    this.setMummyParameters();
	return this.mummyBlueParameters;
    }

    /**
     * Retorna un array con los parametros de la momia blanca
     * 
     * @return Un array con los parametros de la momia blanca
     */
    public float[] getMummyWhiteParameters()
    {
	if (this.mummyWhiteParameters == null)
	    this.setMummyParameters();

	return this.mummyWhiteParameters;
    }

    /**
     * Retorna un array con los parametros de la momia amarilla
     * 
     * @return Un array con los parametros de la momia amarilla
     */
    public float[] getMummyYellowParameters()
    {
	if (this.mummyYellowParameters == null)
	    this.setMummyParameters();

	return this.mummyYellowParameters;
    }

    /**
     * Retorna un array con los parametros de la momia rosa
     * 
     * @return Un array con los parametros de la momia rosa
     */
    public float[] getMummyPinkParameters()
    {
	if (this.mummyPinkParameters == null)
	    this.setMummyParameters();

	return this.mummyPinkParameters;
    }

    /**
     * Retorna un array con los parametros de la momia roja
     * 
     * @return Un array con los parametros de la momia roja
     */
    public float[] getMummyRedParameters()
    {
	if (this.mummyRedParameters == null)
	    this.setMummyParameters();

	return this.mummyRedParameters;
    }

    /**
     * Retorna la distancia minima a la que puede estar el player cuando la momia se
     * teletransporta
     * 
     * @return Distancia minima a la que puede estar el player cuando la momia se
     *         teletransporta
     */
    public float getMinMummySpawnDistanceToPlayer()
    {
	return minMummySpawnDistanceToPlayer;
    }

    /**
     * Retorna el tiempo que la momia permanece en el limbo
     * 
     * @return Tiempo que la momia permanece en el limbo
     */
    public float getMummyTimeInLimbus()
    {
	return mummyTimeInLimbus;
    }

    /**
     * Retorna el tiempo que la momia tarda en morir
     * 
     * @return Tiempo que la momia tarda en morir
     */
    public float getMummyTimeDying()
    {
	return mummyTimeDying;
    }

    /**
     * Retorna el tiempo que la momia tarda en aparecer
     * 
     * @return Tiempo que la momia tarda en aparecer
     */
    public float getMummyTimeAppearing()
    {
	return mummyTimeAppearing;
    }

    /**
     * Retorna el tiempo que tarda el player en pasar por una giratoria
     * 
     * @return Tiempo que tarda el player en pasar por una giratoria
     */
    public float getTimeToEndGiratory()
    {
	return timeToEndGiratory;
    }

    /**
     * Retorna el tiempo que tarda un muro trampa en bajar un tile
     * 
     * @return Tiempo que tarda un muro trampa en bajar un tile
     */
    public float getTimeToEndTrapMechanism()
    {
	return timeToEndTrapMechanism;
    }

    /**
     * Retorna el tiempo que tarda una puerta de entrada y salida en abrirse o
     * cerrarse
     * 
     * @return Tiempo que tarda una puerta de entrada y salida en abrirse o cerrarse
     */
    public float getTimeToOpenCloseDoor()
    {
	return timeToOpenCloseDoor;
    }

    /**
     * Retorna el puntaje necesario para obtener la primera vida extra
     * 
     * @return Puntaje necesario para obtener la primera vida extra
     */
    public int getScoreForFirstExtraLife()
    {
	return scoreForFirstExtraLife;
    }

    /**
     * Retorna el puntaje necesario para obtener la proxima vida extra (despues de
     * la primera)
     * 
     * @return Puntaje necesario para obtener la proxima vida extra (despues de la
     *         primera)
     */
    public int getScoreForExtraLife()
    {
	return scoreForExtraLife;
    }

   

    /**
     * Indica si el juego esta en modo Dios (Si es asi, las momias no mataran al
     * Player)
     * 
     * @return true si esta en modo Dios, false en caso contrario.
     */
    public boolean isGodMode()
    {
	return godMode;
    }

    // Usado en etapa de desarrollo para generar ewl archivo json
    private void setFactors(float levelTileWidthUnits, float levelTileHeightUnits)
    {
	this.levelTileWidthUnits = levelTileWidthUnits;
	this.levelTileHeightUnits = levelTileHeightUnits;

	this.characterSpeedFallFactor = -60f;
	this.characterVerticalSpeedJumpFactor = 16f;

	this.characterHorizontalSpeedJumpFactor = 6f;
	this.playerSpeedWalkFactor = 6f;
	this.playerSpeedWalkStairsFactor = 6f;
	this.flyingDaggerSpeedFactor = 18f;
	this.flyingDaggerSpeedFallFactor = -9f;

	this.characterWidthFactor = 0.6f;
	this.playerHeightFactor = 1.6f;
	this.mummyHeightFactor = 1.4f;
	this.characterFeetHeightFactor = 0.1f;
	this.characterFeetWidthFactor = 0.2f;

	this.stairWidthFactor = 0.1f;
	this.stairHeightFactor = .1f;

	this.giratoryWidthFactor = 1.3f;

	this.mummyWhiteSpeedWalkFactor = 0.5f;
	this.mummyWhiteSpeedStairFactor = 0.5f;
	this.mummyWhiteTimeToDecide = 1.5f;
	this.mummyWhiteTimeDeciding = 1f;

	this.mummyPinkSpeedWalkFactor = 0.5f;
	this.mummyPinkSpeedStairFactor = 2f;
	this.mummyPinkTimeToDecide = 1.5f;
	this.mummyPinkTimeDeciding = 1f;

	this.mummyYellowSpeedWalkFactor = 1f;
	this.mummyYellowSpeedStairFactor = 0.5f;
	this.mummyYellowTimeToDecide = 1.5f;
	this.mummyYellowTimeDeciding = 1f;

	this.mummyBlueSpeedWalkFactor = 1f;
	this.mummyBlueSpeedStairFactor = 1f;
	this.mummyBlueTimeToDecide = 1.5f;
	this.mummyBlueTimeDeciding = 0f;

	this.mummyRedSpeedWalkFactor = 1f;
	this.mummyRedSpeedStairFactor = 2f;
	this.mummyRedTimeToDecide = 1.5f;
	this.mummyRedTimeDeciding = 0f;

    }

    /**
     * Retorna el numero inicial de vidas
     * @return numero inicial de vidas
     */
    public int getInitialNumberOfLives()
    {
	return initialNumberOfLives;
    }

	/**
	 * Indica si el juego esta en modo Debug
	 * @return true si el juego esta en modo Debug, false en caso contrario
	 */
	public boolean isDebugMode()
	{
		return debugMode;
	}

	/**
	 * Retorna el nivel desde donde el juego debe comenzar en caso de estar en modo debug.
	 * @return El nivel desde donde el juego debe comenzar en caso de estar en modo debug.
	 */
	public int getFirstLevelInDebugMode()
	{
		return firstLevelInDebugMode;
	}

}
