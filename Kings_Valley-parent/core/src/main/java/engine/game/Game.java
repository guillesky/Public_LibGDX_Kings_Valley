package engine.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Input;

import engine.IGraphic;
import engine.KVEventListener;
import engine.control.Controls;
import engine.level.Level;
import engine.level.door.Door;
import util.Constants;
import util.GameConfig;
import util.GameRules;

/**
 * Clase que representa el Juego. Aplica el patron Singleton y el patron State.
 * Internamente implementa KVEventListener para manejar eventos internos.
 * 
 * @author Guillermo Lazzurri
 * 
 */
public class Game implements KVEventListener
{
    /**
     * Maximo tiempo admitivo entre frames
     */
    public static final float maxDeltaTime = 0.015f;
    /**
     * codigo del estado jugando
     */
    public static final int ST_GAME_PLAYING = 0;
    /**
     * codigo del estado entrando al nivel
     */
    public static final int ST_GAME_ENTERING = 1;
    /**
     * codigo del estado saliendo del nivel
     */
    public static final int ST_GAME_EXITING = 2;
    /**
     * codigo del estado muriendo
     */
    public static final int ST_GAME_DYING = 3;
    /**
     * codigo del estado no estando jugando (en menu principal)
     */
    public static final int ST_NOT_IN_GAME = 100;
    /**
     * codigo del estado terminando el juego
     */
    public static final int ST_ENDING = 99;
    private static Game instance = null;
    private Controls controles = new Controls();
    /**
     * niveles que ya han sido completados
     */
    protected HashMap<Integer, Boolean> completedLevels = new HashMap<Integer, Boolean>();
    private boolean paused = false;
    /**
     * Nivel actual
     */
    protected Level level = null;
    private int idCurrentLevel;
    private int dificultLevel = 0;
    private float delta = 0;
    private IGraphic interfaz = null;
    private ArrayList<KVEventListener> kvEventListeners = new ArrayList<KVEventListener>();
    /**
     * Estado del juego (patron state)
     */
    protected GameState stateGame;

    /**
     * Nombres de los archivos correspondientes a los niveles del juego
     */
    protected HashMap<Integer, String> levelFileName;
    private int score = 0;
    private int lives;
    private GameConfig gameConfig;
    
    private int nextExtraLife = GameRules.getInstance().getScoreForFirstExtraLife();
    private boolean isExtendedVersion;
    
    private String textToEnterLevel;

    /**
     * Instancia de random para utilizar por todas las clases del juego
     */
    public static final Random random = new Random();

    /**
     * Agrega un objeto de tipo KVEventListener a la lista.
     * 
     * @param kvEventListener listener a aser agregado
     */
    public void addKVEventListener(KVEventListener kvEventListener)
    {
	this.kvEventListeners.add(kvEventListener);
    }

    /**
     * Retorna un ArrayList[KVEventListener] con todos los objetos registrados
     * 
     * @return ArrayList[KVEventListener] con todos los objetos registrados
     */
    public ArrayList<KVEventListener> getKvEventListeners()
    {
	return kvEventListeners;
    }

    /**
     * Setea el gameConfig que representa la configuracion del juego.
     * 
     * @param gameConfig que representa la configuracion del juego.
     */
    public void setGameConfig(GameConfig gameConfig)
    {
	this.gameConfig = gameConfig;
    }

    /**
     * Retorna un objeto de tipo GameConfig que representa la configuracion del
     * juego.
     * 
     * @return un objeto de tipo GameConfig que representa la configuracion del
     *         juego.
     */
    public GameConfig getGameConfig()
    {
	return gameConfig;
    }

    /**
     * incrementa el score del juego<br>
     * <b>PRE:</b> cant debe ser mayor o igual que cero.
     * 
     * @param cant cantidad a incrementar
     */
    public void incScore(int cant)
    {
	this.score += cant;
	this.checkExtraLife();
    }

    /**
     * Retorna la cantidad de vidas
     * 
     * @return cantidad de vidas.
     */
    public int getLives()
    {
	return lives;
    }

    /**
     * Retorna el puntaje actual.
     * 
     * @return score actual
     */
    public int getScore()
    {
	return score;
    }

    /**
     * Retorna el objeto que implementa la interfaz IGraphic usado para representar
     * el juego
     * 
     * @return El objeto que implementa la interfaz IGraphic usado para representar
     *         el juego
     */
    public IGraphic getInterfaz()
    {
	return interfaz;
    }

    /**
     * Setea el objeto que implementa la interfaz IGraphic usado para representar el
     * juego
     * 
     * @param interfaz objeto que implementa la interfaz IGraphic usado para
     *                 repreentar el juego
     */
    public void setInterfaz(IGraphic interfaz)
    {
	this.interfaz = interfaz;
    }

    /**
     * Constructor privado (patron singleton). Llama a initNewGame()
     */
    private Game()
    {
	this.initNewGame();
    }

    /**
     * Inicializa los valores del juego para poder iniciar una nueva partida
     * this.idCurrentLevel = 1;<br>
     * this.score = 0; <br>
     * this.resetCompletedLevels();<br>
     * this.score = 0;<br>
     * this.lives = GameRules.getInstance().getInitialNumberOfLives();
     */
    protected void initNewGame()
    {
	// this.idCurrentLevel = GameRules.getInstance().getFirstLevel();
	this.score = 0;
	this.resetCompletedLevels();
	this.lives = GameRules.getInstance().getInitialNumberOfLives();
    }

    /**
     * Llamado internamente por initGame() o cuando se termina el juego. Indica que
     * ningun nivel ha sido completado (necesario para el mapa)
     */
    private void resetCompletedLevels()
    {
	for (int i = 1; i <= 61; i++)
	{
	    this.completedLevels.put(i, false);
	}
    }

    /**
     * Retorna la unica instancia posible de Game (patron singleton)
     * 
     * @return La unica instancia posible de Game (patron singleton)
     */
    public static Game getInstance()
    {
	if (instance == null)
	{
	    instance = new Game();
	    instance.stateGame = new GameStateNotInGame();
	}
	return instance;
    }

    /**
     * Llamado para actualizar el frame del juego, indicando por parametro la
     * cantidad de segundos transcurridos desde la ultima actualizacion
     * 
     * @param deltaTime Tiempo medido en segundos desde la ultima actualizacion. Si
     *                  el valor es mayor a Game.maxDeltaTime segundos, se asigna el
     *                  valor Game.maxDeltaTime para evitar errores de fisica en las
     *                  colisiones. ( Game.maxDeltaTime=0.015f )
     */
    public void updateframe(float deltaTime)
    {

	if (deltaTime > maxDeltaTime)
	    deltaTime = maxDeltaTime;

	if (controles.getShot(Input.Keys.P) || controles.getShot(Input.Keys.ESCAPE))
	{
	    this.pressPause();
	}

	if (!this.paused)
	{
	    this.stateGame.updateframe(deltaTime);

	}
	for (KVEventListener kvEventListner : this.kvEventListeners)
	    kvEventListner.updateframe(deltaTime);

    }

    /**
     * Llamado para pausar el juego
     */
    public void pressPause()
    {
	this.paused = !this.paused;
	this.eventFired(KVEventListener.PAUSED_IS_CHANGED, this.paused);

    }

    /**
     * retorna el objeto de tipo Controls para controlar el juego (teclado)
     * 
     * @return Objeto de tipo Controls para controlar el juego (teclado)
     */
    public Controls getControles()
    {
	return controles;
    }

    /**
     * Setea el objeto de tipo Controls para controlar el juego (teclado)
     * 
     * @param controles Objeto de tipo Controls para controlar el juego (teclado)
     */
    public void setControles(Controls controles)
    {
	this.controles = controles;
    }

    /**
     * Retorna el nivel actual
     * 
     * @return Nivel actual
     */
    public Level getCurrentLevel()
    {
	return this.level;
    }

    /**
     * LLamado para liberar los recursos
     */
    public void dispose()
    {
	level.getPyramid().getMap().dispose();
    }

    /**
     * Retorna el tiempo medido en segundos desde el ultimo cambio de estado
     * 
     * @return Tiempo medido en segundos desde el ultimo cambio de estado
     */
    public float getDelta()
    {
	return delta;
    }

    /**
     * Metodo llamado para iniciar un nivel
     * 
     * @param door      Indica la puerta de origen, es decir, la puerta por la que
     *                  el player salio del nivel que termino. En caso de no venir
     *                  de un nivel anterior o de morir, este paremtro podria ser
     *                  null.
     * @param fromDeath Indica true si el player acaba de morir y esta reiniciando
     *                  el nivel, false en caso contrario
     */
    public void start(Door door, boolean fromDeath)
    {
	this.stateGame.startNewLevel(door, fromDeath);

    }

    /**
     * Retorna true si el juego esta pausado, false en caso contrario
     * 
     * @return true si el juego esta pausado, false en caso contrario
     */
    public boolean isPaused()
    {
	return paused;
    }

    /**
     * Incrementa el tiempo en segundos desde que el juego esta en el estado actual.
     * 
     * @param delta Tiempo medido en segundos
     */
    protected void incDelta(float delta)
    {
	this.delta += delta;
    }

    /**
     * Pasa al siguiente nivel
     */
    public void nextLevel()
    {
	this.idCurrentLevel++;
	this.start(null, false);
    }

    /**
     * this.delta = 0; llamado en cada cambio de estado
     */
    protected void resetDelta()
    {
	this.delta = 0;
    }

    /**
     * Retorna el HashMap[Integer, Boolean] que indica los niveles que han sido
     * completados (util para usar representar el mapa)
     * 
     * @return HashMap[Integer, Boolean] que indica los niveles que han sido
     *         completados (util para usar representar el mapa)
     */
    public HashMap<Integer, Boolean> getCompletedLevels()
    {
	return completedLevels;
    }

    /**
     * Pasa al nivel indicado por el parametro de tipo Door, permite pasar a niveles
     * anteriores o a atajos (version extendida de King Valley)
     * 
     * @param door objeto de tipo Door con informacion del nivel al que se debe
     *             vincular
     */
    protected void goToLevel(Door door)
    {
	this.completedLevels.put(idCurrentLevel, true);

	if (door.getLevelConnected() == Door.TO_NEXT || door.getLevelConnected() == Door.UNIQUE)
	    this.idCurrentLevel++;
	else if (door.getLevelConnected() == Door.TO_PREVIUS)
	    this.idCurrentLevel--;
	else
	    this.idCurrentLevel = door.getLevelConnected();
	this.start(door, false);

    }

    /**
     * Retorna un codigo numerico que indica como sera la renderizacion de acuerdo
     * al estado del juego. <br>
     * Lo delega a su estado mediante:<br>
     * return this.stateGame.getRenderMode() ST_GAME_PLAYING = 0 <br>
     * ST_GAME_ENTERING = 1<br>
     * ST_GAME_EXITING = 2<br>
     * ST_GAME_DYING = 3<br>
     * ST_NOT_IN_GAME = 100<br>
     * ST_ENDING = 99<br>
     * 
     * @return codigo numerico que indica como sera la renderizacion de acuerdo al
     *         estado del juego<br>
     *         ST_GAME_PLAYING = 0 <br>
     *         ST_GAME_ENTERING = 1<br>
     *         ST_GAME_EXITING = 2<br>
     *         ST_GAME_DYING = 3<br>
     *         ST_NOT_IN_GAME = 100<br>
     *         ST_ENDING = 99<br>
     */
    public int getRenderMode()
    {
	return this.stateGame.getRenderMode();
    }

    /**
     * llamado cuando el player muere
     */
    public void dying()
    {
	this.stateGame.dying();

    }

    /**
     * implementacion del metodo heredado por la interfaz KVEventLitener, no deberia
     * invocarse directamente
     */
    @Override
    public void eventFired(int eventCode, Object param)
    {
	switch (eventCode)
	{
	case KVEventListener.FINISH_ALL_LEVELS:
	    this.idCurrentLevel = 1;
	    this.dificultLevel++;
	    this.resetCompletedLevels();
	    break;

	case KVEventListener.MUMMY_KILLED_BY_SWORD:
	    this.incScore(Constants.MUMMY_KILLED_BY_SWORD_SCORE);
	    break;

	case KVEventListener.PICKUP_JEWEL:
	    this.incScore(Constants.PICKUP_JEWEL_SCORE);
	    break;

	case KVEventListener.EXITING_LEVEL:
	    this.incScore(Constants.FINISH_CURRENT_LEVEL_SCORE);
	    break;

	}
	for (KVEventListener kvEventListner : this.kvEventListeners)
	    kvEventListner.eventFired(eventCode, param);

    }

    /**
     * Retorna el nivel de dificultad actual. Valor entre -4 y 4, siendo 0 el valor
     * normal. Si el valor es mayor 4 se tomara el valor 4. Si es menor a -4 se
     * tomara el valor -4
     * 
     * @return nivel de dificultad actual. Valor entre -4 y 4, siendo 0 el valor
     *         normal. Si el valor es mayor 4 se tomara el valor 4. Si es menor a -4
     *         se tomara el valor -4
     */
    public int getDificultLevel()
    {
	return dificultLevel;
    }

    /**
     * Setea el nivel de dificultad actual. Valor entre -4 y 4, siendo 0 el valor
     * normal. Si el valor es mayor 4 se tomara el valor 4. Si es menor a -4 se
     * tomara el valor -4
     * 
     * @param dificultLevel nivel de dificultad actual. Valor entre -4 y 4, siendo 0
     *                      el valor normal. Si el valor es mayor 4 se tomara el
     *                      valor 4. Si es menor a -4 se tomara el valor -4
     */
    public void setDificultLevel(int dificultLevel)
    {
	this.dificultLevel = dificultLevel;
    }

    /**
     * Inicia un nuevo juego de acuerdo a los parametros indicados
     * 
     * @param episode           Si se inicia una version extendida, indica el numero
     *                          de episodio desde el cual se inicia el juego (valor
     *                          numerico de 1 a 4). Si se inicia una version
     *                          clasica, este parametro se ignora
     * @param dificultLevel     nivel de dificultad. Valor entre -4 y 4, siendo 0 el
     *                          valor normal. Si el valor es mayor 4 se tomara el
     *                          valor 4. Si es menor a -4 se tomara el valor -4
     * @param isExtendedVersion true si se inicia una partida en version extendida,
     *                          false en caso contrario
     */
    public void startNewGame(boolean isExtendedVersion, int dificultLevel, int episode)
    {
	this.dificultLevel = dificultLevel;
	this.isExtendedVersion = isExtendedVersion;

	if (this.isExtendedVersion)
	    this.levelFileName = Constants.extendedLevelFileName;
	else
	    this.levelFileName = Constants.classicLevelFileName;
	this.stateGame.startNewGame(isExtendedVersion, episode);

    }

    /**
     * Termina el juego
     */
    public void endGame()
    {
	this.lives = 0;
	this.stateGame.endGame();

    }

   

    /**
     * Verifica si debe incrementar una vida
     */
    private void checkExtraLife()
    {
	if (this.score >= this.nextExtraLife)
	{
	    this.lives++;
	    this.nextExtraLife += GameRules.getInstance().getScoreForExtraLife();
	    this.eventFired(KVEventListener.ADD_EXTRA_LIFE, this.lives);
	}

    }

    /**
     * Retorna el numero identificatorio del nivel actual
     * 
     * @return numero identificatorio del nivel actual
     */
    public int getIdCurrentLevel()
    {
	return idCurrentLevel;
    }

    /**
     * Descuenta una vida
     */
    protected void loseLive()
    {
	this.lives--;
    }

    /**
     * Indica si se esta jugando en modo extendido o clasico
     * 
     * @return true si se esta jugando en modo extendido, false si se esta jugando
     *         en modo clasico
     */
    public boolean isExtendedVersion()
    {
	return isExtendedVersion;
    }

    /**
     * Setea el primer nivel del juego
     * @param idCurrentLevel numero que indica el primer nivel del juego
     */
    protected void setFirstLevel(int idCurrentLevel)
    {
	this.idCurrentLevel = idCurrentLevel;

    }

    /**
     * Setea el texto que se mostrara al iniciar el nivel
     * @param textToEnterLevel Texto que se mostrara al iniciar el nivel
     */
    protected void setTextToEnterLevel(String textToEnterLevel)
    {
	this.textToEnterLevel=textToEnterLevel;
    }

    /**
     * Retorna el texto que se mostrara al iniciar el nivel
     * @return Texto que se mostrara al iniciar el nivel
     */
    public String getTextToEnterLevel()
    {
        return textToEnterLevel;
    }
    

}
