package modelo.game;

public abstract class StateGame
{
    protected Game game;

    public abstract void updateframe(float deltaTime);

    public StateGame()
    {
	this.game = Game.getInstance();
    }
}
