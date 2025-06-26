package modelo.level.door;

import modelo.KVEventListener;
import modelo.game.Game;
import modelo.gameCharacters.player.Player;

public class DoorStateClosed extends DoorState
{

	public DoorStateClosed(Door door)
	{
		super(door, Door.CLOSED);

	}

	@Override
	public void update(float deltaTime)
	{
	}

	@Override
	public void checkPushLever(Player player)
	{
		
		if (this.door.getLever().isColision(player))
			{this.door.doorState = new DoorStateOpening(this.door);
			Game.getInstance().eventFired(KVEventListener.OPEN_DOOR, this.door);
			}
	}

}
