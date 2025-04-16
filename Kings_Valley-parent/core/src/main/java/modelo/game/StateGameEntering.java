package modelo.game;

public class StateGameEntering extends StateGame
{
    private float delta = 0;

    public StateGameEntering()
    {
	super();
    }

    @Override
    public void updateframe(float deltaTime)
    {
	this.delta += deltaTime;
	if (this.delta>=0.2)
	    this.game.stateGame=new StateGamePlaying();
    }

}
