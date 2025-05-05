package modelo.game;

public class GameStateEntering extends GameState
{

	public GameStateEntering()
	{
		super(Game.ST_GAME_ENTERING);
	}

	@Override
	public void updateframe(float deltaTime)
	{
		super.updateframe(deltaTime);
		if (this.game.getDelta() >= this.game.getInterfaz().getTimeToEnterLevel())
			this.game.stateGame = new GameStatePlaying();
	}

}
