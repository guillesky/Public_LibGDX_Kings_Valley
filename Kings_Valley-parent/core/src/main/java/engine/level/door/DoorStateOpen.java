package engine.level.door;

import engine.gameCharacters.player.Player;

/**
 * Representa el estado de la puerta "abierta"
 * 
 * @author Guillermo Lazzurri
 */
public class DoorStateOpen extends DoorState
{

	/**
	 * Constructor de clase, llama a super(door);
	 * 
	 * @param door Contexto del patron state
	 */
	public DoorStateOpen(Door door)
	{
		super(door);

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
	 * this.door.doorState =new DoorStateClosing(this.door);
	 */
	@Override
	public void checkPushLever(Player player)
	{
		if (this.door.getLever().isColision(player))
		{
			this.door.doorState = new DoorStateClosing(this.door);

		}

	}

	@Override
	public boolean checkEnterPassage(Player player)
	{
		return this.door.getPassage().isColision(player);
	}

	/**
	 * Se sobreescribe como metodo vacio (no hace nada)
	 */
	@Override
	protected void setVisible()
	{

	}
	
	
	@Override
	public int getRenderMode()
	{
		
		return Door.OPEN;
	}
}
