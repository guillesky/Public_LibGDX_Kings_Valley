package modelo.game;

import modelo.KVEventListener;
import modelo.level.DrawableElement;
import modelo.level.door.Door;
import util.Constantes;

public class GameStateExiting extends GameState
{
    private Door door;

    public GameStateExiting(Door door)
    {
	super(Game.ST_GAME_EXITING);
	this.door = door;
	this.game.getInterfaz().addGraphicElement(new DrawableElement(Constantes.DRAWABLE_EXIT_DOOR, door));
	this.game.eventFired(KVEventListener.EXITING_LEVEL, null);
    }

    @Override
    public void updateframe(float deltaTime)
    {
	super.updateframe(deltaTime);
	if (this.game.getDelta() >= this.game.getInterfaz().getTimeToExitLevel())
	{
	    this.game.goToLevel(door);

	}
    }

    @Override
    public void startNewGame()
    {

    }

    @Override
    protected void dying()
    {

    }

}
