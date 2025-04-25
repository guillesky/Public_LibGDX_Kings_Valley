package modelo.level.door;

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
			this.door.doorState = new DoorStateOpening(this.door);
	}

}
