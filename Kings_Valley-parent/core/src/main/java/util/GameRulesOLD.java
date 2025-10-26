package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import audio.AudioConfig;

/**
 * Clase que representa los parametros utilizados en las reglas internas del
 * juego. Modificar los valores podria generar inconsistencias en el juego.
 * Aplica el patron singleton
 * 
 * @author Guillermo Lazzurri
 */
public class GameRulesOLD
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
    private int firstLevel = 1;

    private float characterSpeedFall;
    private float characterVerticalSpeedJump;

    private float playerSpeedWalk;
    private float playerSpeedWalkStairs;

    private float minMummySpawnDistanceToPlayer;
    private float mummyTimeInLimbus;
    private float mummyTimeDying;
    private float mummyTimeAppearing;

    private float[] mummyWhiteParameters;
    private float[] mummyBlueParameters;
    private float[] mummyYellowParameters;
    private float[] mummyPinkParameters;
    private float[] mummyRedParameters;

    private float characterWidth;
    private float playerHeight;
    private float mummyHeight;
    
    private float characterFeetWidth;
    private float characterFeetHeight;

    private float levelObjectWidth;
    private float levelObjectHeight;

    private float stairWidth;
    private float stairHeight;

    private float giratoryWidth;

    private float levelTileWidthUnits;
    private float levelTileHeightUnits;
    private float flyingDaggerSpeed;
    private float flyingDaggerSpeedFall;
    private float timeToEndGiratory = 1f;
    private float timeToEndTrapMechanism = 1f;
    private float timeToOpenCloseDoor = 1f;
    private float timeToEndPicking = 1f;
    private float timeToEndThrowDagger = 0.2f;
    private int scoreForFirstExtraLife = 10000;
    private int scoreForExtraLife = 20000;
    private float characterHorizontalSpeedJump;
   
    
    
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
    private float mummyWhiteSpeedWStairFactor;
    private float mummyWhiteTimeTiDecide;
    private float mummyWhiteTimeDeciding;
    private float mummyPinkSpeedWalkFactor;
    private float mummyPinkSpeedWStairFactor;
    private float mummyPinkTimeTiDecide;
    private float mummyPinkTimeDeciding;
    private float mummyYellowSpeedWalkFactor;
    private float mummyYellowSpeedWStairFactor;
    private float mummyYellowTimeTiDecide;
    private float mummyYellowTimeDeciding;
    private float mummyBlueSpeedWalkFactor;
    private float mummyBlueSpeedWStairFactor;
    private float mummyBlueTimeTiDecide;
    private float mummyBlueTimeDeciding;
    private float mummyRedSpeedWalkFactor;
    private float mummyRedSpeedWStairFactor;
    private float mummyRedTimeTiDecide;
    private float mummyRedTimeDeciding;

    private static final GameRulesOLD instance = new GameRulesOLD();
    private static final String GAMES_RULES_CONFIG_FILE = "games_rules_config.json";
    private static final Json json = new Json();

    /**
     * @param config Graba el archivo de configuracion de audio en audio_config.json
     */
    private static void saveConfig(GameRulesOLD gameRules)
    {
	FileHandle file = Gdx.files.local(GAMES_RULES_CONFIG_FILE);
	json.setUsePrototypes(false);
	file.writeString(json.prettyPrint(gameRules), false);
    }

    /**
     * @return el objecto de AudioConfig a partir del archivo de audio_config.json
     */
    private static GameRulesOLD loadConfig()
    {
	FileHandle file = Gdx.files.local(GAMES_RULES_CONFIG_FILE);

	return json.fromJson(GameRulesOLD.class, file);

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
    private GameRulesOLD()
    {

    }

    /**
     * Inicializa todos los parametros del juego de acuerdo a los valores de ancho y
     * alto de los tiles (usualmente los tiles son cuadrados)
     * 
     * @param levelTileWidthUnits  ancho del tile
     * @param levelTileHeightUnits alto del tile
     */
    public void defaultValues(float levelTileWidthUnits, float levelTileHeightUnits)
    {
	this.levelTileWidthUnits = levelTileWidthUnits;
	this.levelTileHeightUnits = levelTileHeightUnits;

	this.setFactors(levelTileWidthUnits, levelTileHeightUnits);
	
	this.characterSpeedFall =  (this.levelTileHeightUnits * (-60));
	this.characterVerticalSpeedJump =  (this.levelTileHeightUnits * (16));

	this.characterHorizontalSpeedJump =  (6 * this.levelTileWidthUnits);
	this.playerSpeedWalk = (int) (6 * this.levelTileWidthUnits);
	this.playerSpeedWalkStairs = (int) (6 * this.levelTileWidthUnits);
	this.flyingDaggerSpeed = this.levelTileWidthUnits * 18;
	this.flyingDaggerSpeedFall = -this.flyingDaggerSpeed * 0.5f;

	this.setMummyParameters(levelTileWidthUnits);

	this.characterWidth = this.levelTileWidthUnits * 0.6f;
	this.playerHeight = this.levelTileHeightUnits * 1.6f;
	this.mummyHeight = this.levelTileHeightUnits * 1.4f;
	this.characterFeetHeight = this.levelTileHeightUnits * 0.1f;
	this.characterFeetWidth = this.levelTileWidthUnits * 0.2f;

	this.levelObjectWidth = this.levelTileWidthUnits;
	this.levelObjectHeight = this.levelTileHeightUnits;

	this.stairWidth = this.levelTileWidthUnits * 0.1f;
	this.stairHeight = this.levelTileHeightUnits * .1f;

	this.giratoryWidth = this.levelTileWidthUnits * 1.3f;
	

    }

    /**
     * Inicializa los paramtros de las momias de acuerdo al ancho del tile
     * 
     * @param levelTileWidthUnits Ancho del tile
     */
    private void setMummyParameters(float levelTileWidthUnits)
    {

	this.mummyWhiteParameters = new float[4];
	this.mummyBlueParameters = new float[4];
	this.mummyYellowParameters = new float[4];
	this.mummyPinkParameters = new float[4];
	this.mummyRedParameters = new float[4];

	this.minMummySpawnDistanceToPlayer = 64;
	this.mummyTimeAppearing = 2;
	this.mummyTimeDying = 1;
	this.mummyTimeInLimbus = 5;
	this.mummyWhiteParameters[GameRulesOLD.INDEX_SPEED_WALK] = this.playerSpeedWalk * 0.5f;
	this.mummyWhiteParameters[GameRulesOLD.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs * 0.5f;
	this.mummyWhiteParameters[GameRulesOLD.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyWhiteParameters[GameRulesOLD.INDEX_TIME_DECIDING] = 1f;

	this.mummyPinkParameters[GameRulesOLD.INDEX_SPEED_WALK] = this.playerSpeedWalk * 0.5f;
	this.mummyPinkParameters[GameRulesOLD.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs * 2f;
	this.mummyPinkParameters[GameRulesOLD.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyPinkParameters[GameRulesOLD.INDEX_TIME_DECIDING] = 1f;

	this.mummyYellowParameters[GameRulesOLD.INDEX_SPEED_WALK] = this.playerSpeedWalk;
	this.mummyYellowParameters[GameRulesOLD.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs * 0.5f;
	this.mummyYellowParameters[GameRulesOLD.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyYellowParameters[GameRulesOLD.INDEX_TIME_DECIDING] = 1f;

	this.mummyBlueParameters[GameRulesOLD.INDEX_SPEED_WALK] = this.playerSpeedWalk;
	this.mummyBlueParameters[GameRulesOLD.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs;
	this.mummyBlueParameters[GameRulesOLD.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyBlueParameters[GameRulesOLD.INDEX_TIME_DECIDING] = 0f;

	this.mummyRedParameters[GameRulesOLD.INDEX_SPEED_WALK] = this.playerSpeedWalk;
	this.mummyRedParameters[GameRulesOLD.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs * 2f;
	this.mummyRedParameters[GameRulesOLD.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyRedParameters[GameRulesOLD.INDEX_TIME_DECIDING] = 0f;

    }

    /**
     * Retorna la velocidad de caida de los caracteres
     * 
     * @return Velocidad de caida de los caracteres
     */
    public float getCharacterSpeedFall()
    {
	return characterSpeedFall;
    }

    /**
     * Retorna la velocidad de caminata del player
     * 
     * @return Velocidad de caminata del player
     */
    public float getPlayerSpeedWalk()
    {
	return playerSpeedWalk;
    }

    /**
     * Retorna la velocidad en la escalera del player
     * 
     * @return Velocidad en la escalera del player
     */
    public float getPlayerSpeedWalkStairs()
    {
	return playerSpeedWalkStairs;
    }

    /**
     * Retorna el ancho de los caracteres
     * 
     * @return Ancho de los caracteres
     */
    public float getCharacterWidth()
    {
	return characterWidth;
    }

    /**
     * Retorna el alto del player
     * 
     * @return Alto del player
     */
    public float getPlayerHeight()
    {
	return playerHeight;
    }
    
    
    /**
     * Retorna el alto de las momias
     * 
     * @return Alto de las momias
     */
    
    public float getMummyHeight()
    {
        return mummyHeight;
    }
    
    
    

    /**
     * Retorna el ancho de los objetos del nivel (picos, espadas, gemas)
     * 
     * @return Ancho de los objetos del nivel (picos, espadas, gemas)
     */
    public float getLevelObjectWidth()
    {
	return levelObjectWidth;
    }

    /**
     * Retorna el alto de los objetos del nivel (picos, espadas, gemas)
     * 
     * @return Alto de los objetos del nivel (picos, espadas, gemas)
     */
    public float getLevelObjectHeight()
    {
	return levelObjectHeight;
    }

    /**
     * Retorna el ancho de los inicios y fin de escalera (pies y cabecesras)
     * 
     * @return Ancho de los inicios y fin de escalera (pies y cabecesras)
     */
    public float getStairWidth()
    {
	return stairWidth;
    }

    /**
     * Retorna el alto de los inicios y fin de escalera (pies y cabeceras)
     * 
     * @return Alto de los inicios y fin de escalera (pies y cabeceras)
     */
    public float getStairHeight()
    {
	return stairHeight;
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
    public static GameRulesOLD getInstance()
    {
	return instance;
    }

    /**
     * Retorna el ancho de los pies de los caracteres (para colisiones)
     * 
     * @return Ancho de los pies de los caracteres (para colisiones)
     */
    public float getCharacterFeetWidth()
    {
	return characterFeetWidth;
    }

    /**
     * Retorna el alto de los pies de los caracteres (para colisiones)
     * 
     * @return Alto de los pies de los caracteres (para colisiones)
     */
    public float getCharacterFeetHeight()
    {
	return characterFeetHeight;
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
	return giratoryWidth;
    }

    /**
     * Retorna la velocidad del salto de los caracteres (empuje vertical)
     * 
     * @return Velocidad del salto de los caracteres (empuje vertical)
     */
    public float getCharacterVerticalSpeedJump()
    {
	return characterVerticalSpeedJump;
    }

    /**
     * Retorna la velocidad del salto de los caracteres (empuje horizontal)
     * 
     * @return Velocidad del salto de los caracteres (empuje horizontal)
     */
    public float getCharacterHorizontalSpeedJump()
    {
	return characterHorizontalSpeedJump;
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
	return flyingDaggerSpeed;
    }

    /**
     * Retorna la velocidad de caida de la daga
     * 
     * @return Velocidad de caida de la daga
     */
    public float getFlyingDaggerSpeedFall()
    {
	return flyingDaggerSpeedFall;
    }

    /**
     * Retorna un array con los parametros de la momia azul
     * 
     * @return Un array con los parametros de la momia azul
     */
    public float[] getMummyBlueParameters()
    {

	return this.mummyBlueParameters;
    }

    /**
     * Retorna un array con los parametros de la momia blanca
     * 
     * @return Un array con los parametros de la momia blanca
     */
    public float[] getMummyWhiteParameters()
    {

	return this.mummyWhiteParameters;
    }

    /**
     * Retorna un array con los parametros de la momia amarilla
     * 
     * @return Un array con los parametros de la momia amarilla
     */
    public float[] getMummyYellowParameters()
    {

	return this.mummyYellowParameters;
    }

    /**
     * Retorna un array con los parametros de la momia rosa
     * 
     * @return Un array con los parametros de la momia rosa
     */
    public float[] getMummyPinkParameters()
    {

	return this.mummyPinkParameters;
    }

    /**
     * Retorna un array con los parametros de la momia roja
     * 
     * @return Un array con los parametros de la momia roja
     */
    public float[] getMummyRedParameters()
    {

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
     * retorna el identificador del primer nivel del juego (usualmente, 1)
     * @return El identificador del primer nivel del juego (usualmente, 1)
     */
    public int getFirstLevel()
    {
        return firstLevel;
    }

    /**
     * Indica si el juego esta en modo Dios (Si es asi, las momias no mataran al Player)
     * @return true si esta en modo Dios, false en caso contrario.
     */
    public boolean isGodMode()
    {
        return godMode;
    }
    
    
  
    
    
    
    private void setFactors(float levelTileWidthUnits, float levelTileHeightUnits)
    {
	this.levelTileWidthUnits = levelTileWidthUnits;
	this.levelTileHeightUnits = levelTileHeightUnits;

	this.characterSpeedFallFactor=-60f;
	this.characterVerticalSpeedJumpFactor =  16f;

	this.characterHorizontalSpeedJumpFactor =  6f;
	this.playerSpeedWalkFactor =  6f;
	this.playerSpeedWalkStairsFactor = 6f;
	this.flyingDaggerSpeedFactor =18f;
	this.flyingDaggerSpeedFallFactor = 0.5f;



	this.characterWidthFactor = 0.6f;
	this.playerHeightFactor = 1.6f;
	this.mummyHeightFactor =  1.4f;
	this.characterFeetHeightFactor = 0.1f;
	this.characterFeetWidthFactor =  0.2f;

	
	this.stairWidthFactor =  0.1f;
	this.stairHeightFactor =  .1f;

	this.giratoryWidthFactor =  1.3f;
	
	this.mummyWhiteSpeedWalkFactor = 0.5f;
	this.mummyWhiteSpeedWStairFactor=0.5f;
	this.mummyWhiteTimeTiDecide= 1.5f;
	this.mummyWhiteTimeDeciding= 1f;

	
	this.mummyPinkSpeedWalkFactor = 0.5f;
	this.mummyPinkSpeedWStairFactor=2f;
	this.mummyPinkTimeTiDecide= 1.5f;
	this.mummyPinkTimeDeciding= 1f;

	
	this.mummyYellowSpeedWalkFactor = 1f;
	this.mummyYellowSpeedWStairFactor=0.5f;
	this.mummyYellowTimeTiDecide= 1.5f;
	this.mummyYellowTimeDeciding= 1f;

	this.mummyBlueSpeedWalkFactor = 1f;
	this.mummyBlueSpeedWStairFactor=1f;
	this.mummyBlueTimeTiDecide= 1.5f;
	this.mummyBlueTimeDeciding= 0f;
	
	this.mummyRedSpeedWalkFactor = 1f;
	this.mummyRedSpeedWStairFactor=2f;
	this.mummyRedTimeTiDecide= 1.5f;
	this.mummyRedTimeDeciding= 0f;
	
		
	
	
	
	this.saveConfig(this);

    }

   

}
