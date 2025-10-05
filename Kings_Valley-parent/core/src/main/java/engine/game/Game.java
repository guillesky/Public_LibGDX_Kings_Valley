package engine.game;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import engine.IGraphic;
import engine.KVEventListener;
import engine.control.Controls;
import engine.level.Level;
import engine.level.door.Door;
import util.GameRules;
import util.Constants;
import util.GameConfig;

/**
 * @author Guillermo Lazzurri Clase que representa el Juego. Aplica el patron
 *         Singleton y el patron State. Internamente implementa KVEventListener
 *         para manejar eventos internos.
 * 
 */
public class Game implements KVEventListener
{
	public static final int ST_GAME_PLAYING = 0;
	public static final int ST_GAME_ENTERING = 1;
	public static final int ST_GAME_EXITING = 2;
	public static final int ST_GAME_DYING = 3;
	public static final int ST_NOT_IN_GAME = 100;
	public static final int ST_ENDING = 99;
	private static Game instance = null;
	private Controls controles = new Controls();
	protected HashMap<Integer, Boolean> completedLevels = new HashMap<Integer, Boolean>();
	private boolean paused = false;
	protected Level level = null;
	protected int idCurrentLevel;
	private int dificultLevel = 0;
	private float delta = 0;
	private IGraphic interfaz = null;
	private ArrayList<KVEventListener> kvEventListeners = new ArrayList<KVEventListener>();
	protected GameState stateGame;
	protected int state;
	private int score = 0;
	protected int lives;
	private GameConfig gameConfig;
	private boolean goingBack;
	private int nextExtraLife = GameRules.getInstance().getScoreForFirstExtraLife();
	private int firstLevel = 4;

	/**
	 * Agrega un objeto de tipo KVEventListener a la lista.
	 * 
	 * @param kvEventListener
	 */
	public void addKVEventListener(KVEventListener kvEventListener)
	{
		this.kvEventListeners.add(kvEventListener);
	}

	/**
	 * @return ArrayList<KVEventListener> con todos losobjetos registrados
	 */
	public ArrayList<KVEventListener> getKvEventListeners()
	{
		return kvEventListeners;
	}

	/**
	 * @param gameConfig representa la configuracion del juego.
	 */
	public void setGameConfig(GameConfig gameConfig)
	{
		this.gameConfig = gameConfig;
	}

	/**
	 * @return un objeo de tipo GameConfig que representa la configuracion del
	 *         juego.
	 */
	public GameConfig getGameConfig()
	{
		return gameConfig;
	}

	/**
	 * incrementa el score del juego<br>
	 * <b>PRE:</b> cant debe ser >= que cero.
	 * 
	 * @param cant cantidad a incrementar
	 */
	public void incScore(int cant)
	{
		this.score += cant;
		this.checkExtraLife();
	}

	/**
	 * @return cantidad de vidas.
	 */
	public int getLives()
	{
		return lives;
	}

	/**
	 * @return score actual
	 */
	public int getScore()
	{
		return score;
	}

	/**
	 * @return el objeto que implementa la interfaz IGraphic usado para repreentar
	 *         el juego
	 */
	public IGraphic getInterfaz()
	{
		return interfaz;
	}

	/**
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
	 * this.idCurrentLevel = 1; this.score = 0; this.resetCompletedLevels();
	 * this.lives = 3;(score=0;
	 */
	protected void initNewGame()
	{
		this.idCurrentLevel = this.firstLevel;
		this.score = 0;
		this.resetCompletedLevels();
		this.lives = 3;
	}

	/**
	 * Llamado internamente por initGame() o cuando se termina el juego. Indica que
	 * ningun nivel ha sido completado (necesario para el mapa)
	 */
	private void resetCompletedLevels()
	{
		for (int i = 1; i <= 16; i++)
		{
			this.completedLevels.put(i, false);
		}
	}

	/**
	 * @return la unica instancia posible de Game (patron singleton)
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
	 *                  el valor es mayor a 0.015 segundos, se asigna el valor 0.015
	 *                  para evitar errores de fisica en las colisiones. (aunque
	 *                  esto ralentice el juego)
	 */
	public void updateframe(float deltaTime)
	{

		if (deltaTime > 0.015f)
			deltaTime = 0.015f;

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
	 * @return Objeto de tipo Controls para controlar el juego (teclado)
	 */
	public Controls getControles()
	{
		return controles;
	}

	/**
	 * @param controles Objeto de tipo Controls para controlar el juego (teclado)
	 */
	public void setControles(Controls controles)
	{
		this.controles = controles;
	}

	/**
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
	 * @return HashMap<Integer, Boolean> que indica los niveles que han sido
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
	 * @return codigo numerico del estado del juego (no confundir con el patron
	 *         state) <br>
	 *         ST_GAME_PLAYING = 0 <br>
	 *         ST_GAME_ENTERING = 1<br>
	 *         ST_GAME_EXITING = 2<br>
	 *         ST_GAME_DYING = 3<br>
	 *         ST_NOT_IN_GAME = 100<br>
	 *         ST_ENDING = 99<br>
	 */
	public int getState()
	{
		return state;
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

	/*
	 * Metodo usado para debug public void showPlayer() { /* Player player =
	 * this.level.getPlayer(); this.lolo = new Lolo(player);
	 * 
	 * System.out.println("IZQUIERDA: " + lolo.endPlatform(false));
	 * 
	 * System.out.println("Derecha: " + lolo.endPlatform(true));
	 * 
	 * System.out.println("Arriba: " + lolo.getNearStair(true));
	 * 
	 * System.out.println("Abajo: " + lolo.getNearStair(false));
	 */

	/*
	 * System.out.println(this.level.getPlayer());/* Iterator it =
	 * this.level.getMummys().iterator(); while (it.hasNext())
	 * System.out.println(it.next().toString());
	 * 
	 * 
	 * }
	 */

	/**
	 * @return nivel de dificultad actual. Valor entre -4 y 4, siendo 0 el valor
	 *         normal. Si el valor es mayor 4 se tomara el valor 4. Si es menor a -4
	 *         se tomara el valor -4
	 */
	public int getDificultLevel()
	{
		return dificultLevel;
	}

	/**
	 * @param dificultLevel nivel de dificultad actual. Valor entre -4 y 4, siendo 0
	 *                      el valor normal. Si el valor es mayor 4 se tomara el
	 *                      valor 4. Si es menor a -4 se tomara el valor -4
	 */
	public void setDificultLevel(int dificultLevel)
	{
		this.dificultLevel = dificultLevel;
	}

	/**
	 * Inicia un nuevo juego
	 */
	public void startNewGame()
	{
		this.stateGame.startNewGame();

	}

	/**
	 * Termina el juego
	 */
	public void endGame()
	{

		this.stateGame.endGame();
		this.initNewGame();
	}

	/**
	 * Indica si se esta regresando o no a un nivel anterior
	 * 
	 * @param backing true si esta regresando, false en caso contrario
	 */
	public void setGoingBack(boolean backing)
	{

		this.goingBack = backing;

	}

	/**
	 * Indica si se esta regresando o no a un nivel anterior
	 * 
	 * @return true si esta regresando, false en caso contrario
	 */
	public boolean isGoingBack()
	{
		return goingBack;
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
			this.eventFired(KVEventListener.ADD_EXTRA_LIFE, completedLevels);
		}

	}

	

}
