package modelo.game;

public class GameStateDying extends GameState
{

	public GameStateDying()
	{
		super(Game.ST_GAME_DYING);
	}

	@Override
	public void updateframe(float deltaTime)
	{
		super.updateframe(deltaTime);
		if (this.game.getDelta() >= this.game.getInterfaz().getTimeDying())
			this.game.start();
	}

}
