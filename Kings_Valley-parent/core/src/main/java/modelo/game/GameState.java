package modelo.game;

import modelo.KVEventListener;
import modelo.level.LevelReader;
import modelo.level.door.Door;
import util.Constantes;

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
	this.game.state = state;
	this.game.resetDelta();
    }

    public abstract void startNewGame();

    public void endGame()
    {

	this.game.stateGame = new GameStateEndingGame();
    }

    protected void startNewLevel(Door door, boolean fromDeath)
    {

	this.game.setGoingBack((door != null && door.getLevelConnected() == Door.TO_PREVIUS));

	LevelReader levelReader = new LevelReader();
	if (this.game.level != null)
	    this.game.level.dispose();
	if (Constantes.levelFileName.get(this.game.idCurrentLevel) == null)
	{
	    this.game.eventFired(KVEventListener.FINISH_ALL_LEVELS, null);

	} 
	this.game.level = levelReader.getLevel(this.game.idCurrentLevel,
		Constantes.levelFileName.get(this.game.idCurrentLevel), this.game.getDificultLevel(),
		this.game.completedLevels.get(this.game.idCurrentLevel), door,fromDeath, this.game.getInterfaz());
	this.game.stateGame = new GameStateEntering();
	this.game.getInterfaz().reset();

    }

    protected abstract void dying();

}
