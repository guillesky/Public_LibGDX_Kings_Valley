package modelo.game;

public abstract class GameState
{
    protected Game game;

    public void updateframe(float deltaTime) 
    {
    	this.game.incDelta(deltaTime);
    }

    public GameState(int state)
    {
	this.game = Game.getInstance();
	this.game.state=state;
	this.game.resetDelta();
    }
}
