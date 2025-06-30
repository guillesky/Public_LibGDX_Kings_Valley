package modelo.game;

public class GameStateEndingGame extends GameState
{

    public GameStateEndingGame()
    {
	super(Game.ST_ENDING);
	
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
