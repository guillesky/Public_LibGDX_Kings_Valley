package modelo.level.door;

import modelo.gameCharacters.player.Player;

public class DoorStateOpening extends DoorState
{

	public DoorStateOpening(Door door)
	{
		super(door, Door.OPENING);

	}

	@Override
	public void update(float deltaTime)
	{
		this.door.incTime(deltaTime);
		if (this.door.getTime() >= 1)
			this.door.doorState = new DoorStateOpen(this.door);
	}

	@Override
	public void checkPushLever(Player player)
	{
	}

}
