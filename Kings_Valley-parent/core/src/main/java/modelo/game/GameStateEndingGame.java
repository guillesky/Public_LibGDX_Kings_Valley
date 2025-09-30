package modelo.game;

import modelo.KVEventListener;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa el estado terminando el juego (util para mostrar
 *         un cartel de Game Over)
 */
public class GameStateEndingGame extends GameState
{

	/**
	 * llama a super(Game.ST_ENDING) dispara el evento:
	 * this.game.eventFired(KVEventListener.GAME_OVER_INIT, null);
	 */
	public GameStateEndingGame()
	{
		super(Game.ST_ENDING);
		this.game.eventFired(KVEventListener.GAME_ENDING, null);
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void startNewGame()
	{
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void dying()
	{
	}

	/**
	 * llama a super.updateframe(deltaTime); Si el tiempo transcurrido supera el
	 * estipulado por la interfaz para terminar el juego
	 * (this.game.getInterfaz().getTimeToEndGame()) se pasa al estado
	 * GameStateNotInGame()
	 */
	@Override
	public void updateframe(float deltaTime)
	{
		super.updateframe(deltaTime);
		if (this.game.getDelta() >= this.game.getInterfaz().getTimeToEndGame())
			this.game.stateGame = new GameStateNotInGame();
	}

}
