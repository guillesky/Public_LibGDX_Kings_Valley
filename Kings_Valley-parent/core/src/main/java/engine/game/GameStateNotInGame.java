package engine.game;

import engine.KVEventListener;

/**
 * Clase que representa el esta No Jugando
 * 
 * @author Guillermo Lazzurri
 */
public class GameStateNotInGame extends GameState
{

	/**
	 * Se llama a super(Game.ST_NOT_IN_GAME);<br>
	 * Se dispara el evento this.game.eventFired(KVEventListener.GAME_OVER, null);
	 */
	public GameStateNotInGame()
	{
		super(Game.ST_NOT_IN_GAME);
		this.game.eventFired(KVEventListener.GAME_OVER, null);
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void updateframe(float deltaTime)
	{
	}

	/**
	 * Se llama a super.startNewLevel(null,false);
	 * 
	 */
	@Override
	public void startNewGame()
	{

		super.startNewLevel(null, false);

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void dying()
	{
	}

}
