package modelo.game;

public abstract class GameState
{
    protected Game game;

    public abstract void updateframe(float deltaTime);

    public GameState()
    {
	this.game = Game.getInstance();
    }
}
