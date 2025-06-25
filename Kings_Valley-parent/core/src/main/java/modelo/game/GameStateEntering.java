package modelo.game;

import modelo.KVEventListener;

public class GameStateEntering extends GameState
{

	public GameStateEntering()
	{
		super(Game.ST_GAME_ENTERING);
		this.game.eventFired(KVEventListener.ENTERING_LEVEL, null);
	}

	@Override
	public void updateframe(float deltaTime)
	{
		super.updateframe(deltaTime);
		if (this.game.getDelta() >= this.game.getInterfaz().getTimeToEnterLevel())
			this.game.stateGame = new GameStatePlaying();
	}

}
