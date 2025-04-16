package modelo.game;

public class GameStateEntering extends GameState
{
    private float delta = 0;

    public GameStateEntering()
    {
	super();
    }

    @Override
    public void updateframe(float deltaTime)
    {
	this.delta += deltaTime;
	if (this.delta>=0.2)
	    this.game.stateGame=new GameStatePlaying();
    }

}
