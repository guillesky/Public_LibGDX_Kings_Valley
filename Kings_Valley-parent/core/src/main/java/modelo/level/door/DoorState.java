package modelo.level.door;

import modelo.gameCharacters.player.Player;

public abstract class DoorState
{
	protected Door door;

	public DoorState(Door door, int state)
	{

		this.door = door;
		this.door.setState(state);
		this.door.resetTime();
		
	}

	public abstract void update(float deltaTime);

	public abstract void checkPushLever(Player player);

	public boolean checkEnterPassage(Player player)
	{
		return false;
	}

}
