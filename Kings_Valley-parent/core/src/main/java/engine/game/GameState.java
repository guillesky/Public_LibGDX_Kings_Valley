package engine.game;

import engine.KVEventListener;
import engine.level.LevelReader;
import engine.level.door.Door;
import util.Constants;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase abstracta que representa un estado del juego (patron State)
 */
public abstract class GameState
{
	/**
	 * Sujeto del patron state
	 */
	protected Game game;

	/**
	 * Actualiza un fram del juego.
	 * 
	 * @param deltaTime indica cuanto tiempo transcurrio desde la ultima
	 *                  actualizacion medido en segundos
	 */
	public void updateframe(float deltaTime)
	{
		this.game.incDelta(deltaTime);
	}

	/**
	 * Constructor de clase. Gestiona la doble referencia del patron state. Resetea el delta del juego. 
	 * @param state indica el codigo numerico del estado del juego.
	 */
	public GameState(int state)
	{
		this.game = Game.getInstance();
		this.game.state = state;
		this.game.resetDelta();
	}

	/**
	 * Llamado al iniciar un nuevo juego
	 */
	public abstract void startNewGame();

	/**
	 * Llamado al terminar el juego
	 */
	public void endGame()
	{

		this.game.stateGame = new GameStateEndingGame();
	}

	/**
	 * Metodo llamado para iniciar un nivel
	 * 
	 * @param door      Indica la puerta de origen, es decir, la puerta por la que el player salio del nivel que termino. En caso de no venir de un nivel anterior o de morir, este paremtro podria ser null.
	 * @param fromDeath Indica true si el player acaba de morir y esta reiniciando
	 *                  el nivel, false en caso contrario
	 */
	protected void startNewLevel(Door door, boolean fromDeath)
	{

		this.game.setGoingBack((door != null && door.getLevelConnected() == Door.TO_PREVIUS));

		LevelReader levelReader = new LevelReader();
		if (this.game.level != null)
			this.game.level.dispose();
		if (Constants.levelFileName.get(this.game.getIdCurrentLevel()) == null)
		{
			this.game.eventFired(KVEventListener.FINISH_ALL_LEVELS, null);

		}
		this.game.level = levelReader.getLevel(this.game.getIdCurrentLevel(),
				Constants.levelFileName.get(this.game.getIdCurrentLevel()), this.game.getDificultLevel(),
				this.game.completedLevels.get(this.game.getIdCurrentLevel()), door, fromDeath, this.game.getInterfaz());
		this.game.stateGame = new GameStateEntering();
		this.game.getInterfaz().inicialize();

	}

	/**
	 * Llamado al morir el player
	 */
	protected abstract void dying();

}
