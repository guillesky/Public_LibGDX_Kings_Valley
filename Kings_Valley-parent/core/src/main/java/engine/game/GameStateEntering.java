package engine.game;

import engine.KVEventListener;

/**
 * Clase que representa el estado Entrando al Nivel
 * 
 * @author Guillermo Lazzurri
 */
public class GameStateEntering extends GameState
{

	/**
	 * Llama a super(); <br>
	 * Dispara el evento: this.game.eventFired(KVEventListener.ENTERING_LEVEL,
	 * null);
	 */
	public GameStateEntering()
	{
		super();
		this.game.eventFired(KVEventListener.ENTERING_LEVEL, this.game.getCurrentLevel());
	}

	/**
	 * Llama a super.updateframe(deltaTime); Si el tiempo transcurrido es mayor al
	 * estipulado por la interfaz para entrar al nivel
	 * (this.game.getInterfaz().getTimeToEnterLevel()) se cambia el estado a
	 * GameStatePlaying
	 */
	@Override
	public void updateframe(float deltaTime)
	{
		super.updateframe(deltaTime);
		if (this.game.getDelta() >= this.game.getInterfaz().getTimeToEnterLevel())
			this.game.stateGame = new GameStatePlaying();
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void startNewGame(boolean isExtendedVersion, int episode)
	{
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
		
		return Game.ST_GAME_ENTERING;
	}

}
