package modelo.game;

import modelo.KVEventListener;

public class GameStateEndingGame extends GameState
{

    public GameStateEndingGame()
    {
	super(Game.ST_ENDING);
	this.game.eventFired(KVEventListener.GAME_OVER_INIT, null);
    }

    @Override
    public void startNewGame()
    {
    }

    @Override
    protected void dying()
    {
    }
    
    
    @Override
	public void updateframe(float deltaTime)
	{
		super.updateframe(deltaTime);
		if (this.game.getDelta() >= this.game.getInterfaz().getTimeToEndGame())
			this.game.stateGame = new GameStateNotInGame();
	}

}
