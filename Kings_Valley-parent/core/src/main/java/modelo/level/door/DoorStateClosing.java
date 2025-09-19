package modelo.level.door;

import modelo.KVEventListener;
import modelo.game.Game;
import modelo.gameCharacters.player.Player;

/**
 * @author Guillermo Lazzurri
 * 
 *         Representa el estado "cerrandose"
 */
public class DoorStateClosing extends DoorState
{

	/**
	 * Constructor de clase. Llama a super(door, Door.CLOSING); y dispara el evento
	 * Game.getInstance().eventFired(KVEventListener.CLOSING_DOOR, this.door);
	 * 
	 * @param door
	 */
	public DoorStateClosing(Door door)
	{
		super(door, Door.CLOSING);
		Game.getInstance().eventFired(KVEventListener.CLOSING_DOOR, this.door);
	}

	/**
	 * Si paso el tiempo indicado por this.door.getTimeToEnd(), se realiza el cambio
	 * de estado mediante this.door.doorState = new DoorStateClosed(this.door);
	 */
	@Override
	public void update(float deltaTime)
	{
		this.door.incTime(deltaTime);
		if (this.door.getTime() >= this.door.getTimeToEnd())
			this.door.doorState = new DoorStateClosed(this.door);
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void checkPushLever(Player player)
	{
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void setVisible()
	{

	}
	
	
	/**
	 *Retorna false (la puerta se esta cerrando)
	 */
	@Override
	public boolean checkEnterPassage(Player player)
	{
		
		return false;
	}
}
