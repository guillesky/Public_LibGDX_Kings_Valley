package modelo.level.door;

import modelo.gameCharacters.player.Player;

public class DoorStateClosing extends DoorState
{

	public DoorStateClosing(Door door)
	{
		super(door, Door.CLOSING);
	}

	@Override
	public void update(float deltaTime)
	{
		this.door.incTime(deltaTime);
		if (this.door.getTime() >= 1)
			this.door.doorState = new DoorStateClosed(this.door);
	}

	@Override
	public void checkPushLever(Player player)
	{
	}

}
