package engine.level.door;

import engine.KVEventListener;
import engine.game.Game;
import engine.gameCharacters.player.Player;

/**
 * @author Guillermo Lazzurri
 * 
 *         Representa el estado "abriendose"
 */
public class DoorStateOpening extends DoorState
{

	/**
	 * Constructor de clase, llama a super(door, Door.OPENING); y dispara el evento
	 * Game.getInstance().eventFired(KVEventListener.OPENING_DOOR, this.door);
	 * 
	 * @param door Corresponde al sujeto del patron state
	 */
	public DoorStateOpening(Door door)
	{
		super(door, Door.OPENING);
		Game.getInstance().eventFired(KVEventListener.OPENING_DOOR, this.door);

	}

	/**
	 * Si paso el tiempo indicado por this.door.getTimeToEnd(), se realiza el cambio
	 * de estado mediante this.door.doorState = new DoorStateOpen(this.door);
	 */
	@Override
	public void update(float deltaTime)
	{
		this.door.incTime(deltaTime);
		if (this.door.getTime() >= this.door.getTimeToEnd())
			this.door.doorState = new DoorStateOpen(this.door);

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
	 * Retorna false (la puerta se esta abriendo)
	 */
	@Override
	public boolean checkEnterPassage(Player player)
	{

		return false;
	}
}
