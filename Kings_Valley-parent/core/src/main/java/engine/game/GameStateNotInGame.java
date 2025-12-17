package engine.game;

import engine.KVEventListener;
import util.GameRules;

/**
 * Clase que representa el esta No Jugando
 * 
 * @author Guillermo Lazzurri
 */
public class GameStateNotInGame extends GameState
{

	/**
	 * Se llama a super();<br>
	 * Se dispara el evento this.game.eventFired(KVEventListener.GAME_OVER, null);
	 */
	public GameStateNotInGame()
	{
		super();
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
	 * Se llama a super.startNewLevel(null,false); (salvo que se este en modo debug, en ese caso se toma el nivel que indique el archivo game_rules_config.json)
	 * 
	 */
	@Override
	public void startNewGame(boolean isExtendedVersion, int episode)
	{
		if (GameRules.getInstance().isDebugMode())
			this.game.setFirstLevel(GameRules.getInstance().getFirstLevelInDebugMode());
		else
		{
			if (isExtendedVersion && episode > 1)
				this.game.setFirstLevel((episode - 1) * 15 + 1);
			else
				this.game.setFirstLevel(1);
		}
		super.startNewLevel(null, false);

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void dying()
	{
	}

	@Override
	public int getRenderMode()
	{

		return Game.ST_NOT_IN_GAME;
	}

}
