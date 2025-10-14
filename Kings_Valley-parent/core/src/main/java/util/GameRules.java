package util;

/**
 * Clase que representa los parametros utilizados en las reglas internas del
 * juego. Modificar los valores podria generar inconsistencias en el juego.
 * Aplica el patron singleton
 * 
 * @author Guillermo Lazzurri
 */
public class GameRules
{

    public static final int INDEX_SPEED_WALK = 0;
    public static final int INDEX_SPEED_STAIR = 1;
    public static final int INDEX_TIME_TO_DECIDE = 2;
    public static final int INDEX_TIME_DECIDING = 3;
    public static final int INDEX_DECICION_FACTOR_FALL = 4;
    public static final int INDEX_DECICION_FACTOR_JUMP = 5;

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
    private float characterHeight;
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
    private int characterHorizontalSpeedJump;

    private static final GameRules instance = new GameRules();

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
	// this.defaultValues(64, 64);
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

	this.characterSpeedFall = (int) (this.levelTileHeightUnits * (-60));
	this.characterVerticalSpeedJump = (int) (this.levelTileHeightUnits * (15.3));

	this.characterHorizontalSpeedJump = (int) (6 * this.levelTileWidthUnits);
	this.playerSpeedWalk = (int) (6 * this.levelTileWidthUnits);
	this.playerSpeedWalkStairs = (int) (6 * this.levelTileWidthUnits);
	this.flyingDaggerSpeed = this.levelTileWidthUnits * 18;
	this.flyingDaggerSpeedFall = -this.flyingDaggerSpeed * 0.5f;

	this.setMummyParameters(levelTileWidthUnits);

	this.characterWidth = this.levelTileWidthUnits * 0.6f;
	this.characterHeight = this.levelTileHeightUnits * 1.6f;
	this.characterFeetHeight = this.levelTileHeightUnits * 0.1f;
	this.characterFeetWidth = this.levelTileWidthUnits * 0.1f;

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

	this.mummyWhiteParameters = new float[10];
	this.mummyBlueParameters = new float[10];
	this.mummyYellowParameters = new float[10];
	this.mummyPinkParameters = new float[10];
	this.mummyRedParameters = new float[10];

	this.minMummySpawnDistanceToPlayer = 64;
	this.mummyTimeAppearing = 2;
	this.mummyTimeDying = 1;
	this.mummyTimeInLimbus = 5;
	this.mummyWhiteParameters[GameRules.INDEX_SPEED_WALK] = this.playerSpeedWalk * 0.5f;
	this.mummyWhiteParameters[GameRules.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs * 0.5f;
	this.mummyWhiteParameters[GameRules.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyWhiteParameters[GameRules.INDEX_TIME_DECIDING] = 1f;
	this.mummyWhiteParameters[GameRules.INDEX_DECICION_FACTOR_FALL] = 0.5f;
	this.mummyWhiteParameters[GameRules.INDEX_DECICION_FACTOR_JUMP] = 0.5f;

	this.mummyPinkParameters[GameRules.INDEX_SPEED_WALK] = this.playerSpeedWalk * 0.5f;
	this.mummyPinkParameters[GameRules.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs * 2f;
	this.mummyPinkParameters[GameRules.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyPinkParameters[GameRules.INDEX_TIME_DECIDING] = 1f;
	this.mummyPinkParameters[GameRules.INDEX_DECICION_FACTOR_FALL] = 0.5f;
	this.mummyPinkParameters[GameRules.INDEX_DECICION_FACTOR_JUMP] = 0.5f;

	this.mummyYellowParameters[GameRules.INDEX_SPEED_WALK] = this.playerSpeedWalk;
	this.mummyYellowParameters[GameRules.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs * 0.5f;
	this.mummyYellowParameters[GameRules.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyYellowParameters[GameRules.INDEX_TIME_DECIDING] = 1f;
	this.mummyYellowParameters[GameRules.INDEX_DECICION_FACTOR_FALL] = 0.5f;
	this.mummyYellowParameters[GameRules.INDEX_DECICION_FACTOR_JUMP] = 0.5f;

	this.mummyBlueParameters[GameRules.INDEX_SPEED_WALK] = this.playerSpeedWalk;
	this.mummyBlueParameters[GameRules.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs;
	this.mummyBlueParameters[GameRules.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyBlueParameters[GameRules.INDEX_TIME_DECIDING] = 0f;
	this.mummyBlueParameters[GameRules.INDEX_DECICION_FACTOR_FALL] = 0.5f;
	this.mummyBlueParameters[GameRules.INDEX_DECICION_FACTOR_JUMP] = 0.5f;

	this.mummyRedParameters[GameRules.INDEX_SPEED_WALK] = this.playerSpeedWalk;
	this.mummyRedParameters[GameRules.INDEX_SPEED_STAIR] = this.playerSpeedWalkStairs * 2f;
	this.mummyRedParameters[GameRules.INDEX_TIME_TO_DECIDE] = 1.5f;
	this.mummyRedParameters[GameRules.INDEX_TIME_DECIDING] = 0f;
	this.mummyRedParameters[GameRules.INDEX_DECICION_FACTOR_FALL] = 0.5f;
	this.mummyRedParameters[GameRules.INDEX_DECICION_FACTOR_JUMP] = 0.5f;

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
     * Setea la velocidad de caida de los caracteres
     * 
     * @param characterSpeedFall Velocidad de caida de los caracteres
     */
    public void setCharacterSpeedFall(float characterSpeedFall)
    {
	this.characterSpeedFall = characterSpeedFall;
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
     * Setea la velocidad de caminata del player
     * 
     * @param playerSpeedWalk Velocidad de caminata del player
     */
    public void setPlayerSpeedWalk(float playerSpeedWalk)
    {
	this.playerSpeedWalk = playerSpeedWalk;
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
     * Setea la Velocidad en la escalera del player
     * 
     * @param playerSpeedWalkStairs Velocidad en la escalera del player
     */
    public void setPlayerSpeedWalkStairs(float playerSpeedWalkStairs)
    {
	this.playerSpeedWalkStairs = playerSpeedWalkStairs;
    }

    /**
     * Retorna el ancho de los caracteres
     * @return Ancho de los caracteres
     */
    public float getCharacterWidth()
    {
	return characterWidth;
    }

    /**
     * Setea el ancho de los caracteres
     * 
     * @param characterWidth Ancho de los caracteres
     */
    public void setCharacterWidth(float characterWidth)
    {
	this.characterWidth = characterWidth;
    }

    /**
     * Retorna el alto de los caracteres
     * 
     * @return Alto de los caracteres
     */
    public float getCharacterHeight()
    {
	return characterHeight;
    }

    /**
     * Setea el alto de los caracteres
     * 
     * @param characterHeight Alto de los caracteres
     */
    public void setCharacterHeight(float characterHeight)
    {
	this.characterHeight = characterHeight;
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
     * Setea el ancho de los objetos del nivel (picos, espadas, gemas)
     * 
     * @param levelObjectWidth Ancho de los objetos del nivel (picos, espadas,
     *                         gemas)
     */
    public void setLevelObjectWidth(float levelObjectWidth)
    {
	this.levelObjectWidth = levelObjectWidth;
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
     * Setea el alto de los objetos del nivel (picos, espadas, gemas)
     * 
     * @param levelObjectHeight Alto de los objetos del nivel (picos, espadas,
     *                          gemas)
     */
    public void setLevelObjectHeight(float levelObjectHeight)
    {
	this.levelObjectHeight = levelObjectHeight;
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
     * Setea el ancho de los inicios y fin de escalera (pies y cabecesras)
     * 
     * @param stairWidth Ancho de los inicios y fin de escalera (pies y cabecesras)
     */
    public void setStairWidth(float stairWidth)
    {
	this.stairWidth = stairWidth;
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
     * Setea el alto de los inicios y fin de escalera (pies y cabeceras)
     * 
     * @param stairHeight Alto de los inicios y fin de escalera (pies y cabecesras)
     */
    public void setStairHeight(float stairHeight)
    {
	this.stairHeight = stairHeight;
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
     * @return La unica instancia de la clase (patron singleton)
     */
    public static GameRules getInstance()
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
     * Setea el ancho de los pies de los caracteres (para colisiones)
     * 
     * @param characterFeetWidth Ancho de los pies de los caracteres (para
     *                           colisiones)
     */
    public void setCharacterFeetWidth(float characterFeetWidth)
    {
	this.characterFeetWidth = characterFeetWidth;
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
    public void setCharacterFeetHeight(float characterFeetHeight)
    {
	this.characterFeetHeight = characterFeetHeight;
    }

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
     * Setea el ancho de las puertas giratorias
     * 
     * @param giratoryWidth Ancho de las puertas giratorias
     */
    public void setGiratoryWidth(float giratoryWidth)
    {
	this.giratoryWidth = giratoryWidth;
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
     * Setea la velocidad del salto de los caracteres (empuje vertical)
     * 
     * @param characterVerticalSpeedJump Velocidad del salto de los caracteres
     *                                   (empuje vertical)
     */
    public void setCharacterVerticalSpeedJump(float characterVerticalSpeedJump)
    {
	this.characterVerticalSpeedJump = characterVerticalSpeedJump;
    }

    /**
     * Retorna la velocidad del salto de los caracteres (empuje horizontal)
     * 
     * @return Velocidad del salto de los caracteres (empuje horizontal)
     */
    public int getCharacterHorizontalSpeedJump()
    {
	return characterHorizontalSpeedJump;
    }

    /**
     * Setea la velocidad del salto de los caracteres (empuje horizontal)
     * 
     * @param characterHorizontalSpeedJump Velocidad del salto de los caracteres
     *                                     (empuje horizontal)
     */
    public void setCharacterHorizontalSpeedJump(int characterHorizontalSpeedJump)
    {
	this.characterHorizontalSpeedJump = characterHorizontalSpeedJump;
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
     * Setea la velocidad de la daga al ser lanzada horizontalmente (al ser lanzada
     * verticalemente se calculan otros valores proporcionales a este)
     * 
     * @param flyingDaggerSpeed Velocidad de la daga al ser lanzada horizontalmente
     *                          (al ser lanzada verticalemente se calculan otros
     *                          valores proporcionales a este)
     */
    public void setFlyingDaggerSpeed(float flyingDaggerSpeed)
    {
	this.flyingDaggerSpeed = flyingDaggerSpeed;
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
     * Setea la velocidad de caida de la daga
     * 
     * @param flyingDaggerSpeedFall Velocidad de caida de la daga
     */
    public void setFlyingDaggerSpeedFall(float flyingDaggerSpeedFall)
    {
	this.flyingDaggerSpeedFall = flyingDaggerSpeedFall;
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
     * Setea la distancia minima a la que puede estar el player cuando la momia se
     * teletransporta
     * 
     * @param minMummySpawnDistanceToPlayer Distancia minima a la que puede estar el
     *                                      player cuando la momia se teletransporta
     */
    public void setMinMummySpawnDistanceToPlayer(float minMummySpawnDistanceToPlayer)
    {
	this.minMummySpawnDistanceToPlayer = minMummySpawnDistanceToPlayer;
    }

    /**
     * Setea el array con los parametros de la momia blanca
     * 
     * @param mummyWhiteParameters Un array con los parametros de la momia blanca
     */
    public void setMummyWhiteParameters(float[] mummyWhiteParameters)
    {
	this.mummyWhiteParameters = mummyWhiteParameters;
    }

    /**
     * Setea el array con los parametros de la momia azul
     * 
     * @param mummyBlueParameters Un array con los parametros de la momia azul
     */
    public void setMummyBlueParameters(float[] mummyBlueParameters)
    {
	this.mummyBlueParameters = mummyBlueParameters;
    }

    /**
     * Setea el array con los parametros de la momia amarilla
     * 
     * @param mummyYellowParameters Un array con los parametros de la momia amarilla
     */
    public void setMummyYellowParameters(float[] mummyYellowParameters)
    {
	this.mummyYellowParameters = mummyYellowParameters;
    }

    /**
     * Setea el array con los parametros de la momia rosa
     * 
     * @param mummyPinkParameters Un array con los parametros de la momia rosa
     */
    public void setMummyPinkParameters(float[] mummyPinkParameters)
    {
	this.mummyPinkParameters = mummyPinkParameters;
    }

    /**
     * 
     * Setea el array con los parametros de la momia roja
     * 
     * @param mummyRedParameters Un array con los parametros de la momia roja
     */
    public void setMummyRedParameters(float[] mummyRedParameters)
    {
	this.mummyRedParameters = mummyRedParameters;
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
     * Setea el tiempo que la momia permanece en el limbo
     * 
     * @param mummyTimeInLimbus Tiempo que la momia permanece en el limbo
     */
    public void setMummyTimeInLimbus(float mummyTimeInLimbus)
    {
	this.mummyTimeInLimbus = mummyTimeInLimbus;
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
     * Setea el tiempo que la momia tarda en morir
     * 
     * @param mummyTimeDying Tiempo que la momia tarda en morir
     */
    public void setMummyTimeDying(float mummyTimeDying)
    {
	this.mummyTimeDying = mummyTimeDying;
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
     * Setea el tiempo que la momia tarda en aparecer
     * 
     * @param mummyTimeAppearing Tiempo que la momia tarda en aparecer
     */
    public void setMummyTimeAppearing(float mummyTimeAppearing)
    {
	this.mummyTimeAppearing = mummyTimeAppearing;
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
     * Setea el tiempo que tarda el player en pasar por una giratoria
     * 
     * @param timeToEndGiratory Tiempo que tarda el player en pasar por una
     *                          giratoria
     */
    public void setTimeToEndGiratory(float timeToEndGiratory)
    {
	this.timeToEndGiratory = timeToEndGiratory;
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
     * Setea el tiempo que tarda un muro trampa en bajar un tile
     * 
     * @param timeToEndTrapMechanism Tiempo que tarda un muro trampa en bajar un
     *                               tile
     */
    public void setTimeToEndTrapMechanism(float timeToEndTrapMechanism)
    {
	this.timeToEndTrapMechanism = timeToEndTrapMechanism;
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
     * Setea el tiempo que tarda una puerta de entrada y salida en abrirse o
     * cerrarse
     * 
     * @param timeToOpenCloseDoor Tiempo que tarda una puerta de entrada y salida en
     *                            abrirse o cerrarse
     */
    public void setTimeToOpenCloseDoor(float timeToOpenCloseDoor)
    {
	this.timeToOpenCloseDoor = timeToOpenCloseDoor;
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
     * Setea el puntaje necesario para obtener la primera vida extra
     * 
     * @param scoreForFirstExtraLife Puntaje necesario para obtener la primera vida
     *                               extra
     */
    public void setScoreForFirstExtraLife(int scoreForFirstExtraLife)
    {
	this.scoreForFirstExtraLife = scoreForFirstExtraLife;
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
     * Setea el puntaje necesario para obtener la proxima vida extra (despues de la
     * primera)
     * 
     * @param scoreForExtraLife Puntaje necesario para obtener la proxima vida extra
     *                          (despues de la primera)
     */
    public void setScoreForExtraLife(int scoreForExtraLife)
    {
	this.scoreForExtraLife = scoreForExtraLife;
    }

}
