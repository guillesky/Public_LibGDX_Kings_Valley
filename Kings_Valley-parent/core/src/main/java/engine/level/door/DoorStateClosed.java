package engine.level.door;

import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.player.Player;

/**
 * @author Guillermo Lazzurri
 * 
 *         Representa el estado de la puerta "cerrada"
 */
public class DoorStateClosed extends DoorState
{

	/**
	 * Constructor de clase. Llama a super(door, Door.CLOSED);
	 * 
	 * @param door
	 * 
	 */
	public DoorStateClosed(Door door)
	{
		super(door, Door.CLOSED);

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void update(float deltaTime)
	{
	}

	/**
	 * Si el player activa la palanca se realiza el cambio de estado mediante
	 * this.door.doorState = new DoorStateOpening(this.door);
	 */
	@Override
	public void checkPushLever(Player player)
	{

		if (this.door.getLever().isColision(player))
		{
			this.door.doorState = new DoorStateOpening(this.door);
		}
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void setVisible()
	{

	}

	/**
	 *Retorna false (la puerta esta cerrada)
	 */
	@Override
	public boolean checkEnterPassage(Player player)
	{
		
		return false;
	}

}
