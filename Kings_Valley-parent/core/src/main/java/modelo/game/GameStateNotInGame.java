package modelo.game;

import modelo.KVEventListener;

public class GameStateNotInGame extends GameState
{

	public GameStateNotInGame()
	{
		super(Game.ST_NOT_IN_GAME);
		this.game.eventFired(KVEventListener.GAME_OVER, null);
	}

	@Override
	public void updateframe(float deltaTime)
	{
	}

	@Override
	public void startNewGame()
	{
	    super.startNewLevel(null,false);
	   
	}

	
	

	@Override
	protected void dying()
	{
	}

}
