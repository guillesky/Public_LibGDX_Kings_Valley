package modelo.level.door;

import modelo.KVEventListener;
import modelo.game.Game;
import modelo.gameCharacters.player.Player;

public class DoorStateOpen extends DoorState
{

    public DoorStateOpen(Door door)
    {
	super(door, Door.OPEN);

    }

    @Override
    public void update(float deltaTime)
    {

    }

    @Override
    public void checkPushLever(Player player)
    {
	if (this.door.getLever().isColision(player))
	{
	    this.door.doorState = new DoorStateClosing(this.door);
	    Game.getInstance().eventFired(KVEventListener.CLOSE_DOOR, this.door);
	}

    }

    @Override
    public boolean checkEnterPassage(Player player)
    {
	return this.door.getPassage().isColision(player);
    }

}
