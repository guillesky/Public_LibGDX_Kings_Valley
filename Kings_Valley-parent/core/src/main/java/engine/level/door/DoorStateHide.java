package engine.level.door;

import engine.gameCharacters.player.Player;

/**
 * @author Guillermo Lazzurri
 * 
 *         Clase que representa el estado "Puerta oculta"
 */
public class DoorStateHide extends DoorState
{

	/**
	 * Constructor de clase, llama a super(door, Door.HIDE);
	 * 
	 * @param door corresponde al sujeto del patron state
	 */
	public DoorStateHide(Door door)
	{
		super(door, Door.HIDE);

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void update(float deltaTime)
	{

	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	public void checkPushLever(Player player)
	{

	}

	/**
	 * Realiza el cambio de estado mediante this.door.doorState = new
	 * DoorStateClosed(this.door);
	 */
	@Override
	protected void setVisible()
	{
		this.door.doorState = new DoorStateClosed(this.door);
	}

	/**
	 * Retorna false (la puerta no es visible)
	 */
	@Override
	public boolean checkEnterPassage(Player player)
	{

		return false;
	}
}
